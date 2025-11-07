package cl.kemolinaj.ms.usuario.dtos;

import cl.kemolinaj.ms.usuario.entities.RolEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record UsuarioRqDto(
        Long id,

        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede estar vacio")
        @Size(min = 3, max = 50, message = "El valor debe ser mayor a 3 y menor 50")
        String nombre,

        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede estar vacio")
        @Size(min = 3, max = 50, message = "El valor debe ser mayor a 3 y menor 50")
        String apellido,

        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede estar vacio")
        @Email(message = "Debe ser un email valido")
        String email,

        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede estar vacio")
        @Size(min = 8, max = 16, message = "El valor debe ser mayor a 8 y menor 16")
        String password,

        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede estar vacio")
        @Size(min = 3, max = 8, message = "El valor debe ser mayor a 3 y menor 8")
        String userName,

        @NotNull(message = "Es obligatorio")
        Boolean activo,

        List<RolEntity> roles
) {}
