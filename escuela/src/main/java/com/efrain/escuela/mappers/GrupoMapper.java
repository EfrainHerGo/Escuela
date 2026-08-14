package com.efrain.escuela.mappers;

import com.efrain.escuela.dto.datos.DatosGrupo;
import com.efrain.escuela.dto.datos.HorarioReponse;
import com.efrain.escuela.dto.grupo.GrupoRequest;
import com.efrain.escuela.dto.grupo.GrupoResponse;
import com.efrain.escuela.entities.Aula;
import com.efrain.escuela.entities.Curso;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo >{

    private final CursoMapper cursoMapper;
    private final MaestroMapper maestroMapper;
    private final AulaMapper aulaMapper;

    @Override
    public Grupo requestAEntidad(GrupoRequest request ) {
        if (request == null) return null;

        return Grupo.builder()
                .periodo(request.periodo())
                .build();
    }
    //Sobrescritura
    public Grupo requestAEntidad(GrupoRequest request, Curso curso, Maestro maestro, Aula aula){
            if (request == null ) return null;


            return Grupo.builder()
                    .periodo(request.periodo())
                    .curso(curso)
                    .maestro(maestro)
                    .aula(aula)
                    .build();
    }

    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {
        if (entidad == null) return null;

        //puede serviri para pasar el horario
        List<String> listaHorarios = (entidad.getHorarios() != null)
                ? entidad.getHorarios().stream()
                .map(h -> h.getDiaSemana() + " " + h.getHoraInicio() + " - " + h.getHoraFin()) // Ajusta a tus campos de Horario
                .toList()
                : Collections.emptyList();


        return new GrupoResponse(entidad.getId(),
                cursoMapper.entidadAGrupoResponse(entidad.getCurso()),
                maestroMapper.entidadAGrupoResponse(entidad.getMaestro()),
                aulaMapper.entidadAGrupoResponse(entidad.getAula()),
                listaHorarios,
                entidad.getPeriodo());

    }
    public DatosGrupo entidadADatoGrupo(Grupo entidad) {
        if (entidad == null)
            return null;

        return new DatosGrupo(
                entidad.getCurso().getNombre(),
                String.join(" ",
                        entidad.getMaestro().getNombre(),
                        entidad.getMaestro().getApellidoPaterno(),
                        entidad.getMaestro().getApellidoMaterno()),
                entidad.getAula().getNombre(),
                entidad.getPeriodo()
        );
    }

}
