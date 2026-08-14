package com.efrain.escuela.service.calificacion;

import com.efrain.escuela.dto.calificacion.CalificacionRequest;
import com.efrain.escuela.dto.calificacion.CalificacionResponse;
import com.efrain.escuela.entities.Calificacion;
import com.efrain.escuela.entities.Horario;
import com.efrain.escuela.entities.Inscripcion;
import com.efrain.escuela.mappers.CalificacionMapper;
import com.efrain.escuela.repositories.CalificacionRepository;
import com.efrain.escuela.repositories.InscripcionRepository;
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
public class CalificacionServiceImpl implements CalificacionService{
    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;
    private final InscripcionRepository inscripcionRepository;
    @Override
    public List<CalificacionResponse> listar() {
        log.info("Listar las calificaciones");
        return calificacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse).toList();
    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadAResponse(obtenerCalificacion(id));
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {
        log.info("Registra calificacion");
        Inscripcion inscripcion = obtenerInscripcion(request.idIscripcion());
        Calificacion calificacion = calificacionMapper.requestAEntidad(request, inscripcion);
        if (calificacionRepository.existsByInscripcionId(request.idIscripcion())){
            throw new IllegalArgumentException("La inscripcion ya tienen una calificacion asiganda");
        }
        calificacionRepository.save(calificacion);

        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        Calificacion calificacion = obtenerCalificacion(id);
        log.info("Actualizar Calificaion");
        Inscripcion inscripcion = obtenerInscripcion(request.idIscripcion());
        if (calificacionRepository.existsByInscripcionIdAndIdNot(request.idIscripcion(), id)){
            throw new IllegalArgumentException("La inscripcion ya tiene calificacion asiganda");
        }
        calificacion.actualizar(request.calificacion(), inscripcion);
        log.info("Actualizado con exito");
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerCalificacion(id);
        log.info("Elimacion de calificacion");
        calificacionRepository.delete(calificacion);
        log.info("Se elimino con exito el id: {}", id);

    }
    private Calificacion obtenerCalificacion(Long id){
        return ServiceUtils.obtenerIntenidadOException(calificacionRepository, id, Calificacion.class);
    }
    private Inscripcion obtenerInscripcion(Long id){
        return ServiceUtils.obtenerIntenidadOException(inscripcionRepository, id, Inscripcion.class);
    }
}
