package cl.kemolinaj.ms.usuario.controllers;

import cl.kemolinaj.ms.usuario.dtos.DeleteRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.exceptions.UsuarioException;
import cl.kemolinaj.ms.usuario.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping()
    public ResponseEntity<UsuarioRsDto> ingresar(@RequestBody @Valid UsuarioRqDto rqDto) throws UsuarioException {
        return ResponseEntity.ok(usuarioService.agregarUsuario(rqDto));
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioRsDto>> buscarTodo() throws UsuarioException {
        log.info("Buscando todos los usuarios");
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @PutMapping()
    public ResponseEntity<UsuarioRsDto> actualizar(@RequestBody @Valid UsuarioRqDto rqDto) throws UsuarioException {
        log.info("Actualizando usuario con id: {}", rqDto.id());
        return ResponseEntity.ok(usuarioService.actualizarUsuario(rqDto));
    }

    @DeleteMapping()
    public void borrar(@RequestBody @Valid DeleteRqDto rqDto) throws UsuarioException {
        log.info("Borrando usuario con id: {}", rqDto.id());
        usuarioService.borrarUsuario(rqDto);
    }
}
