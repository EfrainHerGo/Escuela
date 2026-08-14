package com.efrain.escuela.controller;

import com.efrain.escuela.dto.cursos.CursoRequest;
import com.efrain.escuela.dto.cursos.CursoResponse;
import com.efrain.escuela.service.curso.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
public class CursoController extends CommonController<CursoRequest, CursoResponse, CursoService>{
    public CursoController(CursoService service) {
        super(service);
    }
}
