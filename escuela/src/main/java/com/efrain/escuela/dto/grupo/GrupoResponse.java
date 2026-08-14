package com.efrain.escuela.dto.grupo;


import java.util.List;

public record GrupoResponse(
        Long id,
        CursoGrupoResponse curso,
        MaestroGrupoResponse maestro,
        AulaGrupoResponse aula,
        List<String>horarios,
        String periodo


) {
}
