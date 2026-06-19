package com.codigo.examenPractico.mapper;

import com.codigo.examenPractico.dto.request.ClienteRequest;
import com.codigo.examenPractico.dto.response.ClienteResponse;
import com.codigo.examenPractico.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {
        return Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .correo(request.getCorreo())
                .build();
    }

    public ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .dni(cliente.getDni())
                .correo(cliente.getCorreo())
                .fechaRegistro(cliente.getFechaRegistro())
                .build();
    }
}
