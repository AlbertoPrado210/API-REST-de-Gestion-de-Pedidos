package com.codigo.examenPractico.exception;

import com.codigo.examenPractico.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handlePedidoNotFound(PedidoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.<Object>builder()
                        .codigo(404)
                        .mensaje(ex.getMessage())
                        .objeto(null)
                        .build());
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleClienteNotFound(ClienteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.<Object>builder()
                        .codigo(404)
                        .mensaje(ex.getMessage())
                        .objeto(null)
                        .build());
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleProductoNotFound(ProductoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.<Object>builder()
                        .codigo(404)
                        .mensaje(ex.getMessage())
                        .objeto(null)
                        .build());
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<BaseResponse<Object>> handleStockInsuficiente(StockInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(BaseResponse.<Object>builder()
                        .codigo(409)
                        .mensaje(ex.getMessage())
                        .objeto(null)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.<Map<String, String>>builder()
                        .codigo(400)
                        .mensaje("Error de validación en los datos enviados")
                        .objeto(errores)
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.<Object>builder()
                        .codigo(400)
                        .mensaje(ex.getMessage())
                        .objeto(null)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.<Object>builder()
                        .codigo(500)
                        .mensaje("Error interno del servidor: " + ex.getMessage())
                        .objeto(null)
                        .build());
    }
}
