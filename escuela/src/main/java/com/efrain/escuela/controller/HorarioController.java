package com.efrain.escuela.controller;

import com.efrain.escuela.dto.datos.HorarioReponse;
import com.efrain.escuela.dto.datos.HorarioRequest;
import com.efrain.escuela.service.horario.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horario")
public class HorarioController extends CommonController<HorarioRequest, HorarioReponse, HorarioService>{
    public HorarioController(HorarioService service) {
        super(service);
    }
}
