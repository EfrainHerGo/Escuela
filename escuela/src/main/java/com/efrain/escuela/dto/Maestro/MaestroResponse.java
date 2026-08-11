package com.efrain.escuela.dto.Maestro;

import com.efrain.escuela.dto.datos.DatosCurso;

import java.util.List;

public record MaestroResponse(
        Long id, String nombre,
        String email, String telefono,
        List<DatosCurso> cursos) {
}
