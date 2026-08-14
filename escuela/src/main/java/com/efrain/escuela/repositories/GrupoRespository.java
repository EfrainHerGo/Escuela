package com.efrain.escuela.repositories;

import com.efrain.escuela.entities.Aula;
import com.efrain.escuela.entities.Curso;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRespository extends JpaRepository<Grupo, Long> {
    boolean existsByCursoId(Long idCurso);
    boolean existsByMaestroId(Long idMaestro);
    boolean existsByAulaId(Long idAula);
    boolean existsByCursoAndMaestroAndAulaAndPeriodo(Curso curso, Maestro maestro, Aula aula, String periodo);
    boolean existsByCursoAndMaestroAndAulaAndPeriodoAndIdNot(Curso curso, Maestro maestro, Aula aula, String periodo, Long id);

}
