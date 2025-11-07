package cl.kemolinaj.ms.usuario.mappers;

import cl.kemolinaj.ms.usuario.dtos.UsuarioRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.entities.UsuarioEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioEntity toUsuarioEntity(UsuarioRqDto usuarioRqDto);
    UsuarioRsDto toUsuarioRsDto(UsuarioEntity usuarioEntity);
    List<UsuarioRsDto> toUsuarioRsDtoList(List<UsuarioEntity> usuarioEntityList);
}
