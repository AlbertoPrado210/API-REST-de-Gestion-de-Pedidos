package com.codigo.examenPractico.controller;

import com.codigo.examenPractico.dto.request.ClienteRequest;
import com.codigo.examenPractico.dto.response.ClienteResponse;
import com.codigo.examenPractico.response.BaseResponse;
import com.codigo.examenPractico.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<BaseResponse<ClienteResponse>> registrarCliente(
            @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.registrarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.<ClienteResponse>builder()
                        .codigo(201)
                        .mensaje("Cliente registrado correctamente")
                        .objeto(response)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ClienteResponse>> buscarClientePorId(@PathVariable Long id) {
        ClienteResponse response = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(BaseResponse.<ClienteResponse>builder()
                .codigo(200)
                .mensaje("Cliente encontrado")
                .objeto(response)
                .build());
    }
}
