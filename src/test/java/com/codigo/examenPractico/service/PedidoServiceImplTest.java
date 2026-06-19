package com.codigo.examenPractico.service;

import com.codigo.examenPractico.dto.request.ItemPedidoRequest;
import com.codigo.examenPractico.dto.request.PedidoRequest;
import com.codigo.examenPractico.dto.response.DetallePedidoResponse;
import com.codigo.examenPractico.dto.response.PedidoResponse;
import com.codigo.examenPractico.entity.Cliente;
import com.codigo.examenPractico.entity.DetallePedido;
import com.codigo.examenPractico.entity.Pedido;
import com.codigo.examenPractico.entity.Producto;
import com.codigo.examenPractico.exception.PedidoNotFoundException;
import com.codigo.examenPractico.exception.StockInsuficienteException;
import com.codigo.examenPractico.mapper.PedidoMapper;
import com.codigo.examenPractico.repository.ClienteRepository;
import com.codigo.examenPractico.repository.PedidoRepository;
import com.codigo.examenPractico.repository.ProductoRepository;
import com.codigo.examenPractico.service.impl.PedidoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Cliente cliente;
    private Producto producto;
    private PedidoRequest pedidoRequest;
    private ItemPedidoRequest itemRequest;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .dni("12345678")
                .correo("juan@gmail.com")
                .fechaRegistro(LocalDateTime.now())
                .build();

        producto = Producto.builder()
                .id(1L)
                .nombre("Teclado mecánico")
                .descripcion("Teclado RGB")
                .precio(150.00)
                .stock(20)
                .estado(true)
                .build();

        itemRequest = new ItemPedidoRequest();
        itemRequest.setProductoId(1L);
        itemRequest.setCantidad(2);

        pedidoRequest = new PedidoRequest();
        pedidoRequest.setClienteId(1L);
        pedidoRequest.setItems(List.of(itemRequest));
    }

    @Test
    @DisplayName("Crear pedido exitosamente cuando los datos son válidos")
    void crearPedido_cuandoDatosSonValidos_retornaPedidoCreado() {
        Pedido pedidoGuardado = Pedido.builder()
                .id(1L)
                .cliente(cliente)
                .estado("CREADO")
                .total(300.00)
                .fechaPedido(LocalDateTime.now())
                .detalles(List.of(
                        DetallePedido.builder()
                                .id(1L)
                                .productoId(1L)
                                .nombreProducto("Teclado mecánico")
                                .cantidad(2)
                                .precioUnitario(150.00)
                                .subtotal(300.00)
                                .build()
                ))
                .build();

        PedidoResponse pedidoResponse = PedidoResponse.builder()
                .id(1L)
                .cliente("Juan Pérez")
                .estado("CREADO")
                .total(300.00)
                .detalles(List.of(
                        DetallePedidoResponse.builder()
                                .productoId(1L)
                                .nombreProducto("Teclado mecánico")
                                .cantidad(2)
                                .precioUnitario(150.00)
                                .subtotal(300.00)
                                .build()
                ))
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);
        when(pedidoMapper.toResponse(any(Pedido.class))).thenReturn(pedidoResponse);

        PedidoResponse resultado = pedidoService.crearPedido(pedidoRequest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("CREADO", resultado.getEstado());
        assertEquals(300.00, resultado.getTotal());
        assertEquals("Juan Pérez", resultado.getCliente());

        verify(clienteRepository).findById(1L);
        verify(productoRepository).findById(1L);
        verify(productoRepository).save(any(Producto.class));
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Lanza StockInsuficienteException cuando el stock es insuficiente")
    void crearPedido_cuandoStockEsInsuficiente_lanzaStockInsuficienteException() {
        producto.setStock(1);
        itemRequest.setCantidad(5);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        StockInsuficienteException excepcion = assertThrows(StockInsuficienteException.class,
                () -> pedidoService.crearPedido(pedidoRequest));

        assertTrue(excepcion.getMessage().contains("Stock insuficiente"));

        verify(pedidoRepository, never()).save(any(Pedido.class));
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    @DisplayName("Lanza PedidoNotFoundException cuando el pedido no existe")
    void buscarPedido_cuandoNoExiste_lanzaPedidoNotFoundException() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        PedidoNotFoundException excepcion = assertThrows(PedidoNotFoundException.class,
                () -> pedidoService.buscarPedidoPorId(99L));

        assertTrue(excepcion.getMessage().contains("99"));
        verify(pedidoRepository).findById(99L);
    }
}
