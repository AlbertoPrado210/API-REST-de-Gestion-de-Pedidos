package com.codigo.examenPractico.service;

import com.codigo.examenPractico.dto.request.ClienteRequest;
import com.codigo.examenPractico.dto.response.ClienteResponse;

public interface ClienteService {
    ClienteResponse registrarCliente(ClienteRequest request);
    ClienteResponse buscarClientePorId(Long id);
}
