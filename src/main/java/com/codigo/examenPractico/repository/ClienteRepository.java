package com.codigo.examenPractico.repository;

import com.codigo.examenPractico.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDni(String dni);
    boolean existsByCorreo(String correo);
}
