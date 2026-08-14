package com.efrain.escuela.dto.alumno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlumnoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 50, message = "El nombre debe de tener de 1 a 50 caracteres")
        String nombre,

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 50, message = "El nombre debe de tener de 1 a 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 50, message = "El nombre debe de tener de 1 a 50 caracteres")
        String apellidoMaterno
) {
}
