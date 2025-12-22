package cl.kemolinaj.ms.usuario.services;

import cl.kemolinaj.ms.usuario.dtos.DeleteRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.exceptions.UsuarioException;

import java.util.List;

public interface UsuarioService {
    UsuarioRsDto agregarUsuario(UsuarioRqDto rqDto) throws UsuarioException;

    List<UsuarioRsDto> getAllUsuarios() throws UsuarioException;
    UsuarioRsDto getUsuarioByUsernameAndPassword(String username, String password) throws UsuarioException;

    UsuarioRsDto actualizarUsuario(UsuarioRqDto rqDto) throws UsuarioException;

    void borrarUsuario(DeleteRqDto rqDto) throws UsuarioException;
}
