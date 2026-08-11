package com.efrain.escuela.controller;

import com.efrain.escuela.dto.Maestro.MaestroRequest;
import com.efrain.escuela.dto.Maestro.MaestroResponse;
import com.efrain.escuela.service.maestros.MaestroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/maestros")
public class MaestroController extends CommonController<MaestroRequest, MaestroResponse, MaestroService>{
    public MaestroController(MaestroService service) {
        super(service);
    }
}
