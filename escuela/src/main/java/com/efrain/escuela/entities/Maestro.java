package com.efrain.escuela.entities;

import com.efrain.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MAESTROS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Maestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MAESTRO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "TELEFONO", nullable = false, length = 10, unique = true)
    private String telefono;

    @OneToMany(mappedBy = "maestro", fetch = FetchType.LAZY)
    private List<Grupo> grupos = new ArrayList<>();

    public void validarDatos(String apellidoPaterno, String nombre, String apellidoMaterno, String email, String telefono) {
        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "El nombre es requerido y debe ser de 1 a 50 caracteres");
        StringCustomUtils.validarTamanio(apellidoPaterno, 1, 50,
                "El apellido paterno es requerido y debe ser de 1 a 50 cracteres");
        StringCustomUtils.validarTamanio(apellidoMaterno, 1, 50,
                "EL apellido materno es requerido y debe ser de 1 a 50 caracteres");
        StringCustomUtils.validarTamanio(email, 1, 100,
                "El email es requerido y debe ser de 1 a 100 caracteres");
        StringCustomUtils.validarTamanio(telefono, 10, 10,
                "EL telefono es requerida y debe ser exactamente 10 carcateres");
    }

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
    }
}
