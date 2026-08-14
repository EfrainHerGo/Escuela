package com.efrain.escuela.dto.calificacion;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CalificacionRequest(
        @NotNull(message = "El id de inscripcion es necesario")
        @Positive(message = "El id debe ser positivo")
        Long idIscripcion,

        @NotNull(message = "La calificacion es obligatoria")
        @Min(value = 0, message = "La calificación debe ser minimo 0")
        @Max(value = 10, message = "La calificacion maxima es 10")
        BigDecimal calificacion
) {
}
