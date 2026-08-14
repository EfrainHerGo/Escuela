package com.efrain.escuela.dto.aula;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AulaRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotNull(message = "La capacidad es requerido")
        @Positive(message = "La capacidad debe ser positiva")
        Integer capacidad
) {
}
