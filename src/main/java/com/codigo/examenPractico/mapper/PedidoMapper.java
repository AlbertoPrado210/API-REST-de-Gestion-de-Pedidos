package com.codigo.examenPractico.mapper;

import com.codigo.examenPractico.dto.response.DetallePedidoResponse;
import com.codigo.examenPractico.dto.response.PedidoResponse;
import com.codigo.examenPractico.entity.DetallePedido;
import com.codigo.examenPractico.entity.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public PedidoResponse toResponse(Pedido pedido) {
        List<DetallePedidoResponse> detalles = pedido.getDetalles().stream()
                .map(this::toDetalleResponse)
                .toList();

        return PedidoResponse.builder()
                .id(pedido.getId())
                .fechaPedido(pedido.getFechaPedido())
                .estado(pedido.getEstado())
                .total(pedido.getTotal())
                .cliente(pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido())
                .detalles(detalles)
                .build();
    }

    private DetallePedidoResponse toDetalleResponse(DetallePedido detalle) {
        return DetallePedidoResponse.builder()
                .id(detalle.getId())
                .productoId(detalle.getProductoId())
                .nombreProducto(detalle.getNombreProducto())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}
