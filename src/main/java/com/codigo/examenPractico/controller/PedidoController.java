package com.codigo.examenPractico.controller;

import com.codigo.examenPractico.dto.request.PedidoRequest;
import com.codigo.examenPractico.dto.response.PedidoResponse;
import com.codigo.examenPractico.response.BaseResponse;
import com.codigo.examenPractico.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<BaseResponse<PedidoResponse>> crearPedido(
            @Valid @RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.<PedidoResponse>builder()
                        .codigo(201)
                        .mensaje("Pedido creado correctamente")
                        .objeto(response)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PedidoResponse>> buscarPedidoPorId(@PathVariable Long id) {
        PedidoResponse response = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(BaseResponse.<PedidoResponse>builder()
                .codigo(200)
                .mensaje("Pedido encontrado")
                .objeto(response)
                .build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<BaseResponse<List<PedidoResponse>>> listarPedidosPorCliente(
            @PathVariable Long clienteId) {
        List<PedidoResponse> pedidos = pedidoService.listarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(BaseResponse.<List<PedidoResponse>>builder()
                .codigo(200)
                .mensaje("Pedidos del cliente listados correctamente")
                .objeto(pedidos)
                .build());
    }
}
