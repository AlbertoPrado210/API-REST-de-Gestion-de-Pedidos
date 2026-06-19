package com.codigo.examenPractico.service.impl;

import com.codigo.examenPractico.dto.request.ClienteRequest;
import com.codigo.examenPractico.dto.response.ClienteResponse;
import com.codigo.examenPractico.entity.Cliente;
import com.codigo.examenPractico.exception.ClienteNotFoundException;
import com.codigo.examenPractico.mapper.ClienteMapper;
import com.codigo.examenPractico.repository.ClienteRepository;
import com.codigo.examenPractico.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteResponse registrarCliente(ClienteRequest request) {
        if (clienteRepository.existsByDni(request.getDni())) {
            throw new IllegalArgumentException("Ya existe un cliente con el DNI: " + request.getDni());
        }
        if (clienteRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un cliente con el correo: " + request.getCorreo());
        }
        Cliente cliente = clienteMapper.toEntity(request);
        Cliente guardado = clienteRepository.save(cliente);
        return clienteMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("No se encontró el cliente con ID: " + id));
        return clienteMapper.toResponse(cliente);
    }
}
