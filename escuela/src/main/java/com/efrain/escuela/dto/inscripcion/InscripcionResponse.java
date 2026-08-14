package com.efrain.escuela.dto.inscripcion;

import com.efrain.escuela.dto.datos.DatosAlumno;
import com.efrain.escuela.dto.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion
) {
}
