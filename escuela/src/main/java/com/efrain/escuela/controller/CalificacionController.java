package com.efrain.escuela.controller;

import com.efrain.escuela.dto.calificacion.CalificacionRequest;
import com.efrain.escuela.dto.calificacion.CalificacionResponse;
import com.efrain.escuela.service.calificacion.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService>{
    public CalificacionController(CalificacionService service) {
        super(service);
    }
}
