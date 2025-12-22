package cl.kemolinaj.ms.usuario.controllers;

import cl.kemolinaj.ms.usuario.dtos.LoginRqDto;
import cl.kemolinaj.ms.usuario.dtos.LoginRsDto;
import cl.kemolinaj.ms.usuario.exceptions.UsuarioException;
import cl.kemolinaj.ms.usuario.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login")
    public LoginRsDto login (@RequestBody @Valid LoginRqDto rqDto) throws UsuarioException {
        return authService.login(rqDto);
    }
}
