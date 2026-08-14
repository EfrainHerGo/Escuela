package com.efrain.escuela.controller;

import com.efrain.escuela.dto.alumno.AlumnoRequest;
import com.efrain.escuela.dto.alumno.AlumnoResponse;
import com.efrain.escuela.service.alumnos.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/alumnos")

public class AlumnoController extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService> {
    public AlumnoController(AlumnoService service) {
        super(service);
    }
}
