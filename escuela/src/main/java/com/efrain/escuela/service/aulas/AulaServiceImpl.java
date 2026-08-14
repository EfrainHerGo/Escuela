package com.efrain.escuela.service.aulas;

import com.efrain.escuela.dto.aula.AulaRequest;
import com.efrain.escuela.dto.aula.AulaResponse;
import com.efrain.escuela.entities.Aula;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.exceptions.EntidadRelacionadaException;
import com.efrain.escuela.mappers.AulaMapper;
import com.efrain.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService{
    private final GrupoRespository grupoRespository;
    private final AulaMapper aulaMapper;
    private final AulaRepository aulaRepository;
    @Transactional(readOnly = true)
    @Override
    public List<AulaResponse> listar() {
        log.info("Listado de las aulas");
        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse).toList();
    }

    @Override
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obteneAula(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrar aula");
        nombreUnico(request);
        Aula aula = aulaMapper.requestAEntidad(request);
        aulaRepository.save(aula);
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        Aula aula = obteneAula(id);
        cambioNombreUnico(request, id);
        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );
        log.info("Aula {} actualizado correctamente", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {
        Aula aula = obteneAula(id);
        log.info("Elimnar aula por id: {}", id);
        if (grupoRespository.existsByAulaId(id))
            throw  new EntidadRelacionadaException(
                    "No se puede eliminar el aula porque tiene grupo asignado"
            );
        aulaRepository.delete(aula);
        log.info("El Aula {} elimnado correctamente", aula.getNombre());

    }
    private Aula obteneAula(Long id){
        return ServiceUtils.obtenerIntenidadOException(aulaRepository, id, Aula.class);
    }


    private void nombreUnico(AulaRequest request){
        log.info("Validar que el nombre no se repita");
        if (aulaRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw  new IllegalArgumentException("Ya existe una aula con este nombre");
    }
    private void cambioNombreUnico(AulaRequest request, Long id){
        log.info("Validar que el nombre no se repita para cambiar");
        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw  new IllegalArgumentException("Ya existe una aula con este nombre");
    }
}
