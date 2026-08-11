package com.efrain.escuela.dto.cursos;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

public record CursoRequest(
        @NotBlank (message = "El nombre es requerido")
        @Size(min = 5, max = 100, message = "Debe tener entre 5 y 100 caracteres")
        String nombre,

        @Size(max = 200, message = "La descripcion debe tener macimo 200 caracteres")
        String descripcion,

        @NotNull(message = "Los creditos son requeridos")
        @Min(value = 1, message = "Los creditos minimo son 1")
        @Max(value = 10, message = "los creditos maximo son 10")
        Integer creditos
) {
}
