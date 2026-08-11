package com.efrain.escuela.entities;


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
}
