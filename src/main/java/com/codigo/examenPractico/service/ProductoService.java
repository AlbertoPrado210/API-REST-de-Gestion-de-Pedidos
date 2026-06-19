package com.codigo.examenPractico.service;

import com.codigo.examenPractico.dto.request.ProductoRequest;
import com.codigo.examenPractico.dto.response.ProductoResponse;

import java.util.List;

public interface ProductoService {
    ProductoResponse registrarProducto(ProductoRequest request);
    List<ProductoResponse> listarProductos();
}
