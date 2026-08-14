package com.efrain.escuela.entities;


import com.efrain.escuela.utils.StringCustomUtils;
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

    public void validarDatos(String nombre, Integer capacidad){
        StringCustomUtils.validarTamanio(nombre, 1, 100,
                "El es requerido y debe tener entre 1 a 100 caracteres");
        StringCustomUtils.validarCapacidad(capacidad, 1, 35,
                "El tamaño debe ser maximo de 35");
    }
    public void actualizar(String nombre, Integer capacidad){
        this.nombre = nombre;
        this.capacidad = capacidad;
    }


}
