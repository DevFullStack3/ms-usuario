package cl.kemolinaj.ms.usuario.dtos;

import cl.kemolinaj.ms.usuario.entities.RolEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record UsuarioRsDto (
        Long id,
        String nombre,
        String apellido,
        String email,
        String userName,
        Boolean activo,
        List<RolEntity> roles
){}
