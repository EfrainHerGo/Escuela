package com.efrain.escuela.mappers;

import com.efrain.escuela.dto.datos.DatosAlumno;
import com.efrain.escuela.dto.datos.DatosGrupo;
import com.efrain.escuela.dto.inscripcion.InscripcionRequest;
import com.efrain.escuela.dto.inscripcion.InscripcionResponse;
import com.efrain.escuela.entities.Alumno;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Inscripcion;
import com.efrain.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion>{
    private final AlumnoMapper alumnoMapper;
    private final GrupoMapper grupoMapper;
    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        if (request == null) return null;

        return Inscripcion.builder()
                .fechaInscripcion(LocalDate.now())
                .build();
    };
    public Inscripcion requestAEntidad(InscripcionRequest request, Alumno alumno, Grupo grupo) {
        if (request == null) return null;

        return Inscripcion.builder()
                .alumno(alumno)
                .grupo(grupo)
                .fechaInscripcion(LocalDate.now())
                .build();
    };



    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        if (entidad == null) return null;

        DatosAlumno datosAlumno = entidadADatoAlumno(entidad);

        DatosGrupo datosGrupo = grupoMapper.entidadADatoGrupo(entidad.getGrupo());

        return new InscripcionResponse(
                entidad.getId(),
                datosAlumno,
                datosGrupo,
                entidad.getCalificacion() != null ? entidad.getCalificacion().getCalificacion() : null,
                entidad.getFechaInscripcion().format(StringCustomUtils.FORMATOFECHA)
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
