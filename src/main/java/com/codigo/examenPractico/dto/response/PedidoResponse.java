package com.codigo.examenPractico.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {
    private Long id;
    private LocalDateTime fechaPedido;
    private String estado;
    private Double total;
    private String cliente;
    private List<DetallePedidoResponse> detalles;
}
