package com.efrain.escuela.dto.datos;

import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Horario;

import java.util.List;

public record HorarioReponse(
    Long id,
    DatosGrupo grupo,
    String horario

) {
}
