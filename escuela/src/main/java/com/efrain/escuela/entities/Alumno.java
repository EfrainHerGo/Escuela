package com.efrain.escuela.entities;


import com.efrain.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Table (name = "ALUMNOS")
@AllArgsConstructor
@NoArgsConstructor@Builder
@Getter

public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private Long id;

    @Column (name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column (name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column (name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column (name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column (name = "MATRICULA", nullable = false, length = 10, unique = true)
    private String matricula;

    @Builder.Default
    @Column (name = "FECHA_INGRESO")
    private LocalDate fechaIngreso = LocalDate.now();

    public void validarDatos(String apellidoPaterno, String nombre, String apellidoMaterno) {
        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "El nombre es requerido y debe ser de 1 a 50 caracteres");
        StringCustomUtils.validarTamanio(apellidoPaterno, 1, 50,
                "El apellido paterno es requerido y debe ser de 1 a 50 cracteres");
        StringCustomUtils.validarTamanio(apellidoMaterno, 1, 50,
                "EL apellido materno es requerido y debe ser de 1 a 50 caracteres");

    }

    public boolean cambioEnDatos(String apellidoPaterno, String nombre, String apellidoMaterno) {
        return !this.nombre.equals(nombre) ||
                !this.apellidoPaterno.equals(apellidoPaterno) ||
                !this.apellidoMaterno.equals(apellidoMaterno);
    }

    public void asignarDatosAcademicos(String email, String matricula){
        StringCustomUtils.validarTamanio(email, 1, 100,
                "El email es requerido y debe ser de 1 a 100 caracteres");
        StringCustomUtils.validarTamanio(matricula, 10, 10,
                "La matricula es requerida y debe ser exactamente 10 carcateres");
        this.email = email.toLowerCase().trim();
        this.matricula = matricula.trim();
    }
    public void actualizar(String apellidoPaterno, String nombre,
                           String apellidoMaterno, String email, String matricula){
        validarDatos(nombre, apellidoPaterno, apellidoPaterno);
        asignarDatosAcademicos(email, matricula);
        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();

    }


}

