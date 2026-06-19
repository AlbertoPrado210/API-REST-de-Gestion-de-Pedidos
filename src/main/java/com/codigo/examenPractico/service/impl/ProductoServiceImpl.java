package com.codigo.examenPractico.service.impl;

import com.codigo.examenPractico.dto.request.ProductoRequest;
import com.codigo.examenPractico.dto.response.ProductoResponse;
import com.codigo.examenPractico.entity.Producto;
import com.codigo.examenPractico.mapper.ProductoMapper;
import com.codigo.examenPractico.repository.ProductoRepository;
import com.codigo.examenPractico.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional
    public ProductoResponse registrarProducto(ProductoRequest request) {
        Producto producto = productoMapper.toEntity(request);
        Producto guardado = productoRepository.save(producto);
        return productoMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listarProductos() {
        return productoRepository.findByEstadoTrue().stream()
                .map(productoMapper::toResponse)
                .toList();
    }
}
