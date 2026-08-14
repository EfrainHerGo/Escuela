package com.efrain.escuela.service.curso;


import com.efrain.escuela.dto.aula.AulaRequest;
import com.efrain.escuela.dto.cursos.CursoRequest;
import com.efrain.escuela.dto.cursos.CursoResponse;
import com.efrain.escuela.entities.Curso;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.exceptions.EntidadRelacionadaException;
import com.efrain.escuela.mappers.AlumnoMapper;
import com.efrain.escuela.mappers.CursoMapper;
import com.efrain.escuela.repositories.CursoRepository;
import com.efrain.escuela.repositories.GrupoRespository;
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

public class CursoServiceImpl implements CursoService {

    private final CursoMapper cursoMapper;
    private final CursoRepository cursoRepository;
    private final GrupoRespository grupoRespository;


    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        log.info("El listado de todos lods cursos");
        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();
        //Si me marca error en el entidadAResponce corroborar que en el repository este los p
        // parametros de Entidad, Long
    }

    @Override
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Registrar curso");
        nombreUnico(request);

        Curso curso = cursoMapper.requestAEntidad(
                request);
        cursoRepository.save(curso);
        log.info("El curso fue registrado con exito");
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        Curso curso = obtenerCurso(id);
        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );
        log.info("Datos actualizados del curso");
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(Long id) {
        Curso curso = obtenerCurso(id);
        log.info("Eliminación del curso: {}", id);
        if (grupoRespository.existsByAulaId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar cruso ya que tiene grupo asignada"
            );
        cursoRepository.delete(curso);
        log.info("El curso {} eliminado correctamente.", curso.getNombre());
    }

    private Curso obtenerCurso(Long id){
        return ServiceUtils.obtenerIntenidadOException(cursoRepository, id, Curso.class);
    }
    private void nombreUnico(CursoRequest request){
        log.info("Validar que el nombre del curso no se repita");
        if (cursoRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw  new IllegalArgumentException("Ya existe un curso con este nombre");
    }
}
