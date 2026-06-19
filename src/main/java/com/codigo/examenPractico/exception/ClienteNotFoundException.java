package com.codigo.examenPractico.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String mensaje) {
        super(mensaje);
    }
}
