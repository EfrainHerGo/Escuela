package com.efrain.escuela.mappers;

import com.efrain.escuela.dto.calificacion.CalificacionRequest;
import com.efrain.escuela.dto.calificacion.CalificacionResponse;
import com.efrain.escuela.dto.datos.DatosAlumno;
import com.efrain.escuela.dto.datos.DatosGrupo;
import com.efrain.escuela.dto.datos.DatosInscripcion;
import com.efrain.escuela.entities.Alumno;
import com.efrain.escuela.entities.Calificacion;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Inscripcion;
import com.efrain.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion>{
    private final AlumnoMapper alumnoMapper;
    private final GrupoMapper grupoMapper;
    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        if(request == null) return null;
        return Calificacion.builder()
                .calificacion(request.calificacion())
                .fechaResgistro(LocalDate.now())
                .build();
    }
    public Calificacion requestAEntidad(CalificacionRequest request, Inscripcion inscripcion) {
        if(request == null) return null;
        Calificacion calificacion = requestAEntidad(request);
        calificacion.asignarinscripcion(inscripcion);
        return calificacion;
    }

    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {
        if (entidad == null) return null;
        DatosInscripcion datosInscripcion = entidadDatosInscripcion(entidad.getInscripcion());
        return new CalificacionResponse(
                entidad.getId(),
                datosInscripcion,
                entidad.getCalificacion(),
                entidad.getFechaResgistro().format(StringCustomUtils.FORMATOFECHA)
        );

    }
    public DatosInscripcion entidadDatosInscripcion(Inscripcion inscripcion){
        if (inscripcion == null) return null;
        DatosGrupo datosGrupo = grupoMapper.entidadADatoGrupo(inscripcion.getGrupo());
        DatosAlumno datosAlumno = entidadADatoAlumno(inscripcion);
        return new DatosInscripcion(
                datosAlumno,
                datosGrupo,
                inscripcion.getFechaInscripcion().format(StringCustomUtils.FORMATOFECHA)
        );
    }
    public DatosAlumno entidadADatoAlumno(Inscripcion entidad) {
        if (entidad == null) return null;

        return new DatosAlumno(
                String.join(" ",
                        entidad.getAlumno().getNombre(),
                        entidad.getAlumno().getApellidoPaterno(),
                        entidad.getAlumno().getApellidoMaterno()),
                entidad.getAlumno().getMatricula(),
                entidad.getAlumno().getEmail(),
                entidad.getAlumno().getFechaIngreso().format(StringCustomUtils.FORMATOFECHA)
        );
    }
}
