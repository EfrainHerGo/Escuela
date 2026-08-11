package com.efrain.escuela.enums;

import com.efrain.escuela.exceptions.RecursoNoEncontradoExceptions;
import com.efrain.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {

    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado");
    private final String descripcion;


    public  static DiaSemana obstenerDiaSemanaPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es necesaria");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for (DiaSemana diaSemana: values()){
            if (StringCustomUtils.quitarAcentos(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return  diaSemana;
        }
        throw new RecursoNoEncontradoExceptions("No existe una categoria con la descripcion" + descripcion);

    }
}
