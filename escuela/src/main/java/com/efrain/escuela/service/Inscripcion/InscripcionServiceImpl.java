package com.efrain.escuela.service.Inscripcion;

import com.efrain.escuela.dto.inscripcion.InscripcionRequest;
import com.efrain.escuela.dto.inscripcion.InscripcionResponse;
import com.efrain.escuela.entities.Alumno;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Inscripcion;
import com.efrain.escuela.mappers.InscripcionMapper;
import com.efrain.escuela.repositories.AlumnoRepository;
import com.efrain.escuela.repositories.CalificacionRepository;
import com.efrain.escuela.repositories.GrupoRespository;
import com.efrain.escuela.repositories.InscripcionRepository;
import com.efrain.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j

public class InscripcionServiceImpl implements InscripcionService{
    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper inscripcionMapper;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRespository grupoRespository;
    private final CalificacionRepository calificacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() {
        log.info("Listar las inscripciones");
        return inscripcionRepository.findAll().stream()
                .map(inscripcionMapper::entidadAResponse).toList();
    }

    @Override
    public InscripcionResponse obtenerPorId(Long id) {
        log.info("Listado por id: {}" , id);
        return inscripcionMapper.entidadAResponse(obtenerInscripcion(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {
        log.info("Registrar inscripcion");
        Alumno alumno = obtenerAlumno(request.idAlumno());
        Grupo grupo = obtenerGrupo(request.idGrupo());
        if (inscripcionRepository.existsByAlumnoIdAndGrupoId(request.idAlumno(), request.idGrupo())){
            throw new IllegalArgumentException("El alumno ya fue registrado con ese grupo");
        }
        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request, alumno, grupo);
        inscripcionRepository.save(inscripcion);
        log.info("La inscripcion fue realizada con exito");
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        log.info("Actualizar inscripcion");
        Inscripcion inscripcion = obtenerInscripcion(id);
        Alumno alumno = obtenerAlumno(request.idAlumno());
        Grupo grupo = obtenerGrupo(request.idGrupo());
        if (inscripcionRepository.existsByAlumnoIdAndGrupoIdAndIdNot(request.idAlumno(), request.idGrupo(), id)){
            throw new IllegalArgumentException("El alumno ya fue registrado con ese grupo");
        }
        inscripcion.actualizar(alumno, grupo);
        log.info("Actualiza con exito");
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);
        log.info("Eliminacion deinscripcion");
        inscripcionRepository.delete(inscripcion);
        log.info("Elimnado con exito la inscripcion con id: ", id);

    }
    private Inscripcion obtenerInscripcion(Long id){
        return ServiceUtils.obtenerIntenidadOException(inscripcionRepository, id, Inscripcion.class);
    }
    private Alumno obtenerAlumno(Long id){
        return ServiceUtils.obtenerIntenidadOException(alumnoRepository, id, Alumno.class);
    }
    private Grupo obtenerGrupo(Long id){
        return ServiceUtils.obtenerIntenidadOException(grupoRespository, id, Grupo.class);
    }
}
