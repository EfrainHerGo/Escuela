package com.efrain.escuela.dto.alumno;

import com.efrain.escuela.dto.datos.DatosCalificacion;

import java.math.BigDecimal;
import java.util.List;

public record AlumnoResponse(
        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso,
        List<DatosCalificacion> calificaciones,
        BigDecimal promedio) {
}
