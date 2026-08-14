package com.efrain.escuela.entities;


import com.efrain.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "CURSOS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column (name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column (name = "DESCRIPCION", nullable = false, length = 200)
    private String descripcion;

    @Column (name = "CREDITOS", nullable = false)
    private Integer creditos;

    public void validarDatos( String nombre) {
        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "El nombre es requerido y debe ser de 1 a 50 caracteres");
    }
    public void actualizar(String nombre, String descripcion, Integer creditos){
        validarDatos(nombre);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
    }

}
