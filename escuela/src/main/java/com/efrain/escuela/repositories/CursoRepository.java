package com.efrain.escuela.repositories;

import com.efrain.escuela.dto.cursos.CursoRequest;
import com.efrain.escuela.dto.cursos.CursoResponse;
import com.efrain.escuela.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNombreIgnoreCaseAndIdNot
            (String nombre, Long id);
    boolean existsByNombreIgnoreCase(String nombre);
}
