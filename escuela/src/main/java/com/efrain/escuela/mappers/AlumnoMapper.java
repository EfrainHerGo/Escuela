package com.efrain.escuela.mappers;

import com.efrain.escuela.dto.alumno.AlumnoRequest;
import com.efrain.escuela.dto.alumno.AlumnoResponse;
import com.efrain.escuela.dto.datos.DatosCalificacion;
import com.efrain.escuela.entities.Alumno;
import com.efrain.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno>{
    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if (request == null) return null;
        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim()) //trim para quitar los espacios
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    }
    //SobreeCarga
    public Alumno requestAEntidad(AlumnoRequest request, String email, String matricula) {
        if (request == null) return null;
        Alumno alumno = requestAEntidad(request);
        alumno.asignarDatosAcademicos(email, matricula);
        return alumno;
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if (entidad == null )return null;
        List<DatosCalificacion> calificaciones = entidadADattosCalificacion(entidad);
        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils.localDateAAstring(
                        entidad.getFechaIngreso()),
                calificaciones,
                entidad.calcularPromedio()
        );
    }
    private List<DatosCalificacion> entidadADattosCalificacion(Alumno entidad){
        if (entidad == null || entidad.getInscripcions() == null || entidad.getInscripcions().isEmpty())
            return List.of();

        return entidad.getInscripcions().stream()
                .map(inscripcion -> new DatosCalificacion(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() != null
                            ? inscripcion.getCalificacion().getCalificacion()
                                : null
                )).toList();
    }


}
