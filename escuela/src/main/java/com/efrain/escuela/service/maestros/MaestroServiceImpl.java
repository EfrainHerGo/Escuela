package com.efrain.escuela.service.maestros;

import com.efrain.escuela.dto.Maestro.MaestroRequest;
import com.efrain.escuela.dto.Maestro.MaestroResponse;
import com.efrain.escuela.entities.Maestro;
import com.efrain.escuela.mappers.MaestroMapper;
import com.efrain.escuela.repositories.MaestroRepository;
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

public class MaestroServiceImpl implements MaestroService {

    private final MaestroMapper maestroMapper;
    private final MaestroRepository maestroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {
        log.info("Listando todos los maestros");
        return maestroRepository.findAll().stream()
                .map(maestroMapper::entidadAResponse).toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {

        return maestroMapper.entidadAResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {
        log.info("registrar nuevo maestro");
        validardatosunicos(request);
        Maestro maestro = maestroMapper.requestAEntidad(request);
        maestroRepository.save(maestro);

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {
        Maestro maestro = obtenerMaestro(id);
        validarCambiosUnicos(request, id);
        maestro.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidMaterno(),
                request.email(),
                request.telefono()
        );
    log.info("Maestro {} actualizado correctamente", maestro.getNombre());
        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {
        Maestro maestro = obtenerMaestro(id);
        log.info("Eliminar maestro por id: {}", id);
        maestroRepository.delete(maestro);
        log.info("mestro {} eliminado correctamente", maestro.getNombre());
    }

    private Maestro obtenerMaestro(Long id){
        return ServiceUtils.obtenerIntenidadOException(maestroRepository, id, Maestro.class);
    }

    private void validardatosunicos(MaestroRequest request){
        log.info("Validar email unico....");
        if (maestroRepository.existsByEmailIgnoreCase(request.email().trim()))
            throw new IllegalArgumentException("Ya existe un maestro registradi con el email: " + request.email());
        log.info("Validando telefono unico...");
        if (maestroRepository.existsByTelefono(request.email().trim()))
            throw new IllegalArgumentException("Ya existe un maestro registradi con el email: " + request.email());

    }
    private void validarCambiosUnicos(MaestroRequest request, Long id){
        log.info("Validar telefono unico para cammbiar ....");
        if (maestroRepository.existsByEmailIgnoreCaseAndIdNot(request.email().trim(), id))
            throw new IllegalArgumentException("Ya existe un maestro registradi con el email: " + request.email());
        log.info("Validando telefono unico para cambiar ...");
        if (maestroRepository.existsByTelefonoAndIdNot(request.email().trim(), id))
            throw new IllegalArgumentException("Ya existe un maestro registradi con el email: " + request.email());

    }

}
