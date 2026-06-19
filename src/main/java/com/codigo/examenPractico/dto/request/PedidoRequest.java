package com.codigo.examenPractico.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequest {

    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    @NotEmpty(message = "Debe incluir al menos un producto en el pedido")
    @Valid
    private List<ItemPedidoRequest> items;
}
