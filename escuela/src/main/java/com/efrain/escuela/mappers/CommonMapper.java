package com.efrain.escuela.mappers;

//Interfaz generica
public interface CommonMapper <RQ, RS, E>{
    E requestAEntidad(RQ request);
    RS entidadAResponse(E entidad);
}
