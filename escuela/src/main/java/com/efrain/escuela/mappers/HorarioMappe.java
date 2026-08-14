package com.efrain.escuela.mappers;

import com.efrain.escuela.dto.datos.DatosGrupo;
import com.efrain.escuela.dto.datos.HorarioReponse;
import com.efrain.escuela.dto.datos.HorarioRequest;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Horario;
import com.efrain.escuela.enums.DiaSemana;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HorarioMappe implements CommonMapper<HorarioRequest, HorarioReponse, Horario>{
    private final GrupoMapper grupoMapper;
    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        return Horario.builder()
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .build();
    }
    public Horario requestAEntidad(HorarioRequest request, Grupo grupo, DiaSemana diaSemana){
        if (request == null) return null;
        Horario horario = requestAEntidad(request);
        horario.asignarGrupo(grupo);
        horario.asignarDiaSemana(diaSemana);
        return horario;

    }

    @Override
    public HorarioReponse entidadAResponse(Horario entidad) {
        DatosGrupo datosGrupo = grupoMapper.entidadADatoGrupo(entidad.getGrupo());
        return new HorarioReponse(
                entidad.getId(),
                datosGrupo,
                String.join(" ",
                        entidad.getDiaSemana().getDescripcion(),
                        entidad.getHoraInicio(),
                        entidad.getHoraFin())
        );
    }
}
