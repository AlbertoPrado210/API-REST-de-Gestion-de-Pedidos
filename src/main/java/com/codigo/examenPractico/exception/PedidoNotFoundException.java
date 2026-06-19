package com.codigo.examenPractico.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
