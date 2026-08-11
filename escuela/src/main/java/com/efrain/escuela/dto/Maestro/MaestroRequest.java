package com.efrain.escuela.dto.Maestro;

import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;

public record MaestroRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 50, message = "El nombre debe tener 1 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 1, max = 50, message = "El apellido debe tener entre 1 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 1, max = 50, message = "El apellido materno debe tener entre 1 a 50 caracteres")
        String apellidMaterno,

        @NotBlank(message = "El email es requerido")
        @Size(min = 8, max = 100, message = "Debe tener entre 8 y 100 caracteres")
        @Email(message = "El email debe trener un formato valido(ejemplo@dominio.com)")
        String email,

        @NotBlank(message = "El telefono es requerido")
        @Pattern(regexp = "^[0-9]{10}", message = "El telefono debe tener solo 10 digitos")
        String telefono
) {


}
