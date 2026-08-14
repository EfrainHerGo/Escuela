package com.efrain.escuela.controller;

import com.efrain.escuela.dto.inscripcion.InscripcionRequest;
import com.efrain.escuela.dto.inscripcion.InscripcionResponse;
import com.efrain.escuela.service.Inscripcion.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripcion")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService>{
    public InscripcionController(InscripcionService service) {
        super(service);
    }
}
