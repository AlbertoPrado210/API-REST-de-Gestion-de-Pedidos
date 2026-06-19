package com.codigo.examenPractico.controller;

import com.codigo.examenPractico.dto.request.ProductoRequest;
import com.codigo.examenPractico.dto.response.ProductoResponse;
import com.codigo.examenPractico.response.BaseResponse;
import com.codigo.examenPractico.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductoResponse>> registrarProducto(
            @Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.registrarProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.<ProductoResponse>builder()
                        .codigo(201)
                        .mensaje("Producto registrado correctamente")
                        .objeto(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductoResponse>>> listarProductos() {
        List<ProductoResponse> productos = productoService.listarProductos();
        return ResponseEntity.ok(BaseResponse.<List<ProductoResponse>>builder()
                .codigo(200)
                .mensaje("Productos listados correctamente")
                .objeto(productos)
                .build());
    }
}
