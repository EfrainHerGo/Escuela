package com.efrain.escuela.mappers;

import com.efrain.escuela.dto.Maestro.MaestroRequest;
import com.efrain.escuela.dto.Maestro.MaestroResponse;
import com.efrain.escuela.dto.datos.DatosCurso;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class MaestroMapper implements  CommonMapper<MaestroRequest, MaestroResponse, Maestro>{

    private final CursoMapper cursoMapper;
    @Override
    public Maestro requestAEntidad(MaestroRequest request) {
        if (request == null) return null;

        return Maestro.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidMaterno().trim())
                .email(request.email().trim())
                .telefono(request.telefono().trim())
                .build();

    }

    @Override
    public MaestroResponse entidadAResponse(Maestro entidad) {
        if (entidad == null )return null;
        List<DatosCurso> cursos = entidadDatosCurso(entidad);
        return new MaestroResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono(),
                cursos
        );
    }

    private List<DatosCurso> entidadDatosCurso(Maestro entidad){
        if (entidad == null) return List.of();

        return entidad.getGrupos().stream()
                .map(Grupo::getCurso)
                .map(cursoMapper::entidadADatosCurso).toList();
    }
}
