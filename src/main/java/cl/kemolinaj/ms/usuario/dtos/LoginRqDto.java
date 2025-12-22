package cl.kemolinaj.ms.usuario.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record LoginRqDto(
        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede estar vacio")
        String userName,
        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede estar vacio")
        String password
) {
}
