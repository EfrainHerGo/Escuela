package com.efrain.escuela.utils;

import com.efrain.escuela.exceptions.RecursoNoEncontradoExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

@Slf4j
public class ServiceUtils {
    public static <E, ID> E obtenerIntenidadOException(
            JpaRepository<E, ID> repository,
            ID id,
            Class<E> clase
    ){
        String nombreEntidad = clase.getSimpleName();
        log.info("Buscando {} con id: ", nombreEntidad, id);
        return repository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoExceptions(nombreEntidad + "no encontrado con id: " + id)
        );
    }
}
