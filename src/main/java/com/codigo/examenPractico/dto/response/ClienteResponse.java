package com.codigo.examenPractico.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private LocalDateTime fechaRegistro;
}
