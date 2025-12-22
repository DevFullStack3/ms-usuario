package cl.kemolinaj.ms.usuario.services;

import cl.kemolinaj.ms.usuario.dtos.LoginRqDto;
import cl.kemolinaj.ms.usuario.dtos.LoginRsDto;
import cl.kemolinaj.ms.usuario.exceptions.UsuarioException;

public interface AuthService {
    LoginRsDto login(LoginRqDto rqDto) throws UsuarioException;
}
