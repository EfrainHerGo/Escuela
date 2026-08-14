package com.efrain.escuela.controller;


import com.efrain.escuela.dto.grupo.GrupoRequest;
import com.efrain.escuela.dto.grupo.GrupoResponse;
import com.efrain.escuela.service.grupo.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService>{
    public GrupoController(GrupoService service) {
        super(service);
    }
}
