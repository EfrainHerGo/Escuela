package com.efrain.escuela.mappers;

import com.efrain.escuela.dto.cursos.CursoRequest;
import com.efrain.escuela.dto.cursos.CursoResponse;
import com.efrain.escuela.dto.datos.DatosCurso;
import com.efrain.escuela.dto.grupo.CursoGrupoResponse;
import com.efrain.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso>{

    @Override
    public Curso requestAEntidad(CursoRequest request) {
        if (request == null) return null;
        String descripcion = request.descripcion() != null
                ? request.descripcion().trim() : null;

        return Curso.builder()
                .nombre(request.nombre().trim())
                .descripcion(descripcion)
                .creditos(request.creditos())
                .build();
    }
    @Override
    public CursoResponse entidadAResponse(Curso entidad) {
        if (entidad == null) return null;
        String descripcion = entidad.getDescripcion() == null
                ? "Sin descripcion" : entidad.getDescripcion();
        return new CursoResponse(
                entidad.getId(),
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }
    public CursoGrupoResponse entidadAGrupoResponse(Curso curso) {
        if (curso == null) return null;
        return new CursoGrupoResponse(
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getCreditos()
        );
    }

    public DatosCurso entidadADatosCurso(Curso entidad) {
        if (entidad == null) return null;
        String descripcion = entidad.getDescripcion() == null
                ? "Sin descripcion" : entidad.getDescripcion();
        return new DatosCurso(
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }
}
