package com.efrain.escuela.service.alumnos;

import com.efrain.escuela.dto.alumno.AlumnoRequest;
import com.efrain.escuela.dto.alumno.AlumnoResponse;
import com.efrain.escuela.entities.Alumno;
import com.efrain.escuela.entities.Maestro;
import com.efrain.escuela.exceptions.EntidadRelacionadaException;
import com.efrain.escuela.mappers.AlumnoMapper;
import com.efrain.escuela.repositories.AlumnoRepository;
import com.efrain.escuela.repositories.InscripcionRepository;
import com.efrain.escuela.repositories.MaestroRepository;
import com.efrain.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@AllArgsConstructor
@Slf4j

public class AlumnoServiceImpl implements AlumnoService {
    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper; //Comprobar que el mapper tenga el @component

    @Override
    @Transactional
    public List<AlumnoResponse> listar() {
        log.info("Listando todo los alumnos");
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }

    @Override
    public AlumnoResponse obtenerPorId(Long id) {

        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        log.info("Registro de alumno nuevo");
        Alumno alumno = alumnoMapper.requestAEntidad(
                request, generarEmail(request), generarMatricula(request)
        );
        alumnoRepository.save(alumno);
        log.info("Nuevo alumno {} registrado corectamenre", alumno.getNombre());
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumno alumno = obtenerAlumno(id);
        if (alumno.cambioEnDatos(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
        )) {
            alumno.actualizar(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    generarEmail(request),
                    generarMatricula(request)
            );
            log.info("Datos academico regenerados para los alumnos con id: {}", id);
        }
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAlumno(id);
        log.info("Eliminar alumno por id: {}", id);
        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException(
                    "No se puede elimnar el alumno ya que tiene inscripsiones asignadas");
        alumnoRepository.delete(alumno);
        log.info("Alumno {} elimnar correctamente", alumno.getNombre());

    }

    private Alumno obtenerAlumno(Long id){
        return ServiceUtils.obtenerIntenidadOException(alumnoRepository, id, Alumno.class);
    }
    private String generarMatricula(AlumnoRequest request){
        log.info("Generado de matricula");
        return alumnoRepository.generarMatricula(request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim());

    }
    private String generarEmail(AlumnoRequest request){
        log.info("Generado de email");
        return alumnoRepository.generarEmail(request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim());
    }
}
