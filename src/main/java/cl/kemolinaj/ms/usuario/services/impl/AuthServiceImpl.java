package cl.kemolinaj.ms.usuario.services.impl;

import cl.kemolinaj.ms.usuario.dtos.LoginRqDto;
import cl.kemolinaj.ms.usuario.dtos.LoginRsDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.exceptions.UsuarioException;
import cl.kemolinaj.ms.usuario.services.AuthService;
import cl.kemolinaj.ms.usuario.services.UsuarioService;
import cl.kemolinaj.ms.usuario.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("authServiceImpl")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsuarioService usuarioService;
    private final JwtProvider jwtProvider;

    @Override
    public LoginRsDto login(LoginRqDto rqDto) throws UsuarioException {
        UsuarioRsDto usuarioRsDto = usuarioService.getUsuarioByUsernameAndPassword(rqDto.userName(), rqDto.password());
        if (usuarioRsDto == null) throw new UsuarioException("Usuario no encontrado");
        String token = jwtProvider.generateToken(usuarioRsDto);
        return LoginRsDto.builder()
                .token(token)
                .build();
    }
}
