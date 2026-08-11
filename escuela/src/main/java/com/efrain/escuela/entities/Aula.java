package com.efrain.escuela.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AULAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column (name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column (name = "CAPACIDAD", nullable = false, length = 50)
    private Integer capacidad;


}
