package com.efrain.escuela.controller;


import com.efrain.escuela.dto.alumno.AlumnoResponse;
import com.efrain.escuela.dto.aula.AulaRequest;
import com.efrain.escuela.dto.aula.AulaResponse;
import com.efrain.escuela.service.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService>{
    public AulaController(AulaService service) {
        super(service);
    }
}

