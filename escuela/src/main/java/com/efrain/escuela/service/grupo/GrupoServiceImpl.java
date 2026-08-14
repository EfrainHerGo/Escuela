package com.efrain.escuela.service.grupo;

import com.efrain.escuela.dto.grupo.GrupoRequest;
import com.efrain.escuela.dto.grupo.GrupoResponse;
import com.efrain.escuela.entities.Aula;
import com.efrain.escuela.entities.Curso;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Maestro;
import com.efrain.escuela.exceptions.EntidadRelacionadaException;
import com.efrain.escuela.mappers.GrupoMapper;
import com.efrain.escuela.mappers.MaestroMapper;
import com.efrain.escuela.repositories.*;
import com.efrain.escuela.service.CrudService;
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

//Comprobar que sea el service del mismo tipo
public class GrupoServiceImpl implements GrupoService {
    private final GrupoMapper grupoMapper;
    private final GrupoRespository grupoRespository;
    private final CursoRepository cursoRepository;
    private final MaestroMapper maestroMapper;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;
    private final HorarioRepository horarioRepository;
    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GrupoResponse> listar() {
        log.info("El listado de los grupos");
        return grupoRespository.findAll().stream()
                .map(grupoMapper::entidadAResponse).toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Registrar Grupo");
        Curso curso = obtenerCurso(request.idCurso());
        Maestro maestro = obtenerMaestro(request.idMaestro());
        Aula aula = obtenerAula(request.idAula());
        validarDuplicidad(curso, maestro, aula, request.periodo(), null);
        Grupo grupo = grupoMapper.requestAEntidad(request, curso, maestro, aula);

        grupoRespository.save(grupo);
        log.info("EL grupo fue resgitrado con exito");
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        Curso curso = obtenerCurso(request.idCurso());
        Maestro maestro = obtenerMaestro(request.idMaestro());
        Aula aula = obtenerAula(request.idAula());
        Grupo grupo = obtenerGrupo(id);
        validarDuplicidad(curso, maestro, aula, request.periodo(), id);
        grupo.actualizar(
                curso,
                maestro,
                aula,
                request.periodo()
        );
        log.info("Actualizado con exito");
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        Grupo grupo = obtenerGrupo(id);
        log.info("Eliminando el grupo");
        if (horarioRepository.existsByGrupoId(id)) {
            throw new EntidadRelacionadaException("No se puede eliminar el grupo porque tiene horarios asociados.");
        }

        if (inscripcionRepository.existsByGrupoId(id)) {
            throw new EntidadRelacionadaException("No se puede eliminar el grupo porque tiene inscripciones asociadas.");
        }
        grupoRespository.delete(grupo);
        log.info("Eliminado con exito");
    }

    private Grupo obtenerGrupo(Long id){
        return ServiceUtils.obtenerIntenidadOException(grupoRespository, id, Grupo.class);
    }
    private Curso obtenerCurso(Long id){
        return ServiceUtils.obtenerIntenidadOException(cursoRepository, id, Curso.class);
    }
    private Maestro obtenerMaestro(Long id){
        return ServiceUtils.obtenerIntenidadOException(maestroRepository, id, Maestro.class);
    }
    private Aula obtenerAula(Long id){
        return ServiceUtils.obtenerIntenidadOException(aulaRepository, id, Aula.class);
    }

    private void validarDuplicidad(Curso curso, Maestro maestro, Aula aula, String periodo, Long idActual) {
        boolean existe;
        if (idActual == null) {
            existe = grupoRespository.existsByCursoAndMaestroAndAulaAndPeriodo(curso, maestro, aula, periodo);
        } else {
            existe = grupoRespository.existsByCursoAndMaestroAndAulaAndPeriodoAndIdNot(curso, maestro, aula, periodo, idActual);
        }
        if (existe) {
            log.error("Duplicidad detectada para Curso: {}, Maestro: {}, Aula: {}, Periodo: {}",
                    curso.getId(), maestro.getId(), aula.getId(), periodo);
            throw new EntidadRelacionadaException(
                    "Ya existe un grupo asignado con el mismo Curso, Maestro, Aula y Periodo (" + periodo + ")."
            );
        }
    }


}
