package com.efrain.escuela.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.event.SpringApplicationEvent;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GRUPOS", uniqueConstraints = @UniqueConstraint(
        name = "UQ_GRUPO_CU_MA_AU_PE",
        columnNames = {"ID_CURSO", "ID_MAESTRO", "ID_AULA", "PERIODO"}
))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo",
            orphanRemoval = true, cascade = CascadeType.ALL)
    private  List<Horario> horarios = new ArrayList<>();

    @Column(name = "PERIODO", nullable = false)
    private String periodo;

    public void actualizar(Curso curso, Maestro maestro,
                           Aula aula, String periodo){
        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo;
    }
}
