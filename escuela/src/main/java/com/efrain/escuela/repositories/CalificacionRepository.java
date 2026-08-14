package com.efrain.escuela.repositories;

import com.efrain.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    boolean existsByInscripcionId(Long idInscripcion);
    boolean existsByInscripcionIdAndIdNot(Long idInscripcion, Long idCalificacion);
}
