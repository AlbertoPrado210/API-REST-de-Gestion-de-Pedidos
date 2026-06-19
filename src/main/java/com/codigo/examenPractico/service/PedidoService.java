package com.codigo.examenPractico.service;

import com.codigo.examenPractico.dto.request.PedidoRequest;
import com.codigo.examenPractico.dto.response.PedidoResponse;

import java.util.List;

public interface PedidoService {
    PedidoResponse crearPedido(PedidoRequest request);
    PedidoResponse buscarPedidoPorId(Long id);
    List<PedidoResponse> listarPedidosPorCliente(Long clienteId);
}
