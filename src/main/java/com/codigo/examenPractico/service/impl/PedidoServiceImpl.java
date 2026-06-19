package com.codigo.examenPractico.service.impl;

import com.codigo.examenPractico.dto.request.ItemPedidoRequest;
import com.codigo.examenPractico.dto.request.PedidoRequest;
import com.codigo.examenPractico.dto.response.PedidoResponse;
import com.codigo.examenPractico.entity.Cliente;
import com.codigo.examenPractico.entity.DetallePedido;
import com.codigo.examenPractico.entity.Pedido;
import com.codigo.examenPractico.entity.Producto;
import com.codigo.examenPractico.exception.ClienteNotFoundException;
import com.codigo.examenPractico.exception.PedidoNotFoundException;
import com.codigo.examenPractico.exception.ProductoNotFoundException;
import com.codigo.examenPractico.exception.StockInsuficienteException;
import com.codigo.examenPractico.mapper.PedidoMapper;
import com.codigo.examenPractico.repository.ClienteRepository;
import com.codigo.examenPractico.repository.PedidoRepository;
import com.codigo.examenPractico.repository.ProductoRepository;
import com.codigo.examenPractico.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    @Transactional
    public PedidoResponse crearPedido(PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNotFoundException(
                        "No se encontró el cliente con ID: " + request.getClienteId()));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .estado("CREADO")
                .total(0.0)
                .detalles(new ArrayList<>())
                .build();

        double total = 0.0;

        for (ItemPedidoRequest item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ProductoNotFoundException(
                            "No se encontró el producto con ID: " + item.getProductoId()));

            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para el producto '" + producto.getNombre()
                        + "'. Stock disponible: " + producto.getStock()
                        + ", cantidad solicitada: " + item.getCantidad());
            }

            double subtotal = producto.getPrecio() * item.getCantidad();

            DetallePedido detalle = DetallePedido.builder()
                    .productoId(producto.getId())
                    .nombreProducto(producto.getNombre())
                    .cantidad(item.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .subtotal(subtotal)
                    .pedido(pedido)
                    .build();

            pedido.getDetalles().add(detalle);
            total += subtotal;

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        pedido.setTotal(total);
        Pedido guardado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponse buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(
                        "No se encontró el pedido con ID: " + id));
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> listarPedidosPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ClienteNotFoundException("No se encontró el cliente con ID: " + clienteId);
        }
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }
}
