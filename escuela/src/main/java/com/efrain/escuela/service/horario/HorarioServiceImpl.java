package com.efrain.escuela.service.horario;

import com.efrain.escuela.dto.datos.HorarioReponse;
import com.efrain.escuela.dto.datos.HorarioRequest;
import com.efrain.escuela.entities.Grupo;
import com.efrain.escuela.entities.Horario;
import com.efrain.escuela.enums.DiaSemana;
import com.efrain.escuela.mappers.HorarioMappe;
import com.efrain.escuela.repositories.GrupoRespository;
import com.efrain.escuela.repositories.HorarioRepository;
import com.efrain.escuela.utils.ServiceUtils;
import com.efrain.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class HorarioServiceImpl implements HorarioService{
    private final HorarioRepository horarioRepository;
    private final HorarioMappe horarioMappe;
    private final GrupoRespository grupoRespository;


    @Override
    @Transactional(readOnly = true)
    public List<HorarioReponse> listar() {
        log.info("EL listado de los horarios");
        return horarioRepository.findAll().stream()
                .map(horarioMappe::entidadAResponse).toList();
    }

    @Override
    public HorarioReponse obtenerPorId(Long id) {
        return horarioMappe.entidadAResponse(obtenerHorario(id));
    }

    @Override
    public HorarioReponse registrar(HorarioRequest request) {
        log.info("registrar horario");
        Grupo grupo = obtenerGrupo(request.idGrupo());
        DiaSemana diaSemana = obtenerDiaSemanaPorDescripcion(request.dia());
        List<Horario> horarios = horarioRepository.obtenerHorariosConflicto(diaSemana, request.idGrupo(), grupo.getAula().getId(), grupo.getPeriodo());
        LocalTime horaIncio = parseStringALocalTime(request.horaInicio());
        LocalTime horaFin = parseStringALocalTime(request.horaFin());
        Horario horario = horarioMappe.requestAEntidad(request, grupo, diaSemana);
        //Falta vañidacion de que no choquen los horarios
        if (!horaIncio.isBefore(horaFin)){
            throw new IllegalArgumentException("La hora de inicio debe ser menor que la hora de fin");
        }
        validarTraslape(horarios, horaIncio, horaFin);
        horarioRepository.save(horario);
        log.info("Registrado con exito");
        return horarioMappe.entidadAResponse(horario);
    }

    @Override
    public HorarioReponse actualizar(HorarioRequest request, Long id) {
        Horario horario = obtenerHorario(id);
        Grupo grupo = obtenerGrupo(request.idGrupo());
        DiaSemana diaSemana = obtenerDiaSemanaPorDescripcion(request.dia());
        LocalTime horaInicio = parseStringALocalTime(request.horaInicio());
        LocalTime horaFin = parseStringALocalTime(request.horaFin());
        if (!horaInicio.isBefore(horaFin)){
            throw new IllegalArgumentException("La hora de inicio debe ser menos que la hora fin");
        }
        List<Horario> horarios = horarioRepository.obtenerHorariosConflicto(diaSemana, request.idGrupo(), grupo.getAula().getId(), grupo.getPeriodo(), id);
        horario.actualizar(
                grupo,
                diaSemana,
                request.horaInicio(),
                request.horaFin()
        );
        log.info("Actualizado con exito");
        return horarioMappe.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerHorario(id);
        log.info("Eliminar horario");
        horarioRepository.delete(horario);
        log.info("Se elimino con exito");

    }
    private Horario obtenerHorario(Long id){
        return ServiceUtils.obtenerIntenidadOException(horarioRepository, id, Horario.class);
    }
    private DiaSemana obtenerDiaSemanaPorDescripcion(String descripcion) {
        return DiaSemana.obstenerDiaSemanaPorDescripcion(descripcion.trim());
    }
    private Grupo obtenerGrupo(Long id){
        return ServiceUtils.obtenerIntenidadOException(grupoRespository, id, Grupo.class);
    }
    private LocalTime parseStringALocalTime(String horaStr)
    {
        return LocalTime.parse(horaStr, StringCustomUtils.FORMATOHORA);
    }
    private void validarTraslape(List<Horario> horarios, LocalTime horaIncioNueva, LocalTime horaFinNueva){
        horarios.stream().forEach(horario -> {
            LocalTime horaInicioexistente =
                    LocalTime.parse(horario.getHoraInicio(), StringCustomUtils.FORMATOHORA);
            LocalTime horaFinExistente =
                    LocalTime.parse(horario.getHoraFin(), StringCustomUtils.FORMATOHORA);
            boolean traslape =
                    horaInicioexistente.isBefore(horaFinNueva)
                    && horaFinExistente.isAfter(horaIncioNueva);
            if (traslape) {
                throw new IllegalArgumentException(
                        "El horario se traslapa con otro horario existente"
                );
            }
        });
    }
}
