package com.efrain.escuela.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;

@Entity
@Table(name = "INSCRIPCIONES", uniqueConstraints = @UniqueConstraint(
        name = "UQ_INSCRIPCIONES_ALU_GRU",
        columnNames = {"ID_ALUMNOS", "ID_GRUPO"}
))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Builder.Default
    @Column(name = "FECHA_INSCRIPCION")
    private LocalDate fechaInscripcion = LocalDate.now();

    /*El padre de la relacion no lleva nada, la hija kkeva la llave foranea*/
    @OneToOne(mappedBy = "inscripcion", fetch = FetchType.EAGER)
    private Calificacion calificacion;

}
