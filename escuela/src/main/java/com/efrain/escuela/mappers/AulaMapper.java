package com.efrain.escuela.mappers;

import com.efrain.escuela.dto.aula.AulaRequest;
import com.efrain.escuela.dto.aula.AulaResponse;
import com.efrain.escuela.dto.grupo.AulaGrupoResponse;
import com.efrain.escuela.dto.grupo.CursoGrupoResponse;
import com.efrain.escuela.entities.Aula;
import com.efrain.escuela.entities.Curso;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor

public class AulaMapper implements CommonMapper<AulaRequest, AulaResponse, Aula> {

    @Override
    public Aula requestAEntidad(AulaRequest request) {
        if (request == null) return null;

        return Aula.builder()
                .nombre(request.nombre().trim())
                .capacidad(request.capacidad())
                .build();
    }

    @Override
    public AulaResponse entidadAResponse(Aula entidad) {
        if (entidad == null) return null;

        return new AulaResponse(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getCapacidad()
        );
    }
    public AulaGrupoResponse entidadAGrupoResponse(Aula aula) {
        if (aula == null) return null;
        return new AulaGrupoResponse(
                aula.getNombre(),
                aula.getCapacidad()
        );
    }
}
