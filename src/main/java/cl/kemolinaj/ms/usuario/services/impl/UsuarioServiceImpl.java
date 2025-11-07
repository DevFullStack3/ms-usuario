package cl.kemolinaj.ms.usuario.services.impl;

import cl.kemolinaj.ms.usuario.dtos.DeleteRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.entities.UsuarioEntity;
import cl.kemolinaj.ms.usuario.exceptions.BadRequestException;
import cl.kemolinaj.ms.usuario.exceptions.UsuarioException;
import cl.kemolinaj.ms.usuario.mappers.UsuarioMapper;
import cl.kemolinaj.ms.usuario.repositories.UsuarioRepository;
import cl.kemolinaj.ms.usuario.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuarioRsDto agregarUsuario(UsuarioRqDto rqDto) throws UsuarioException {
        try{
            if (rqDto.id() != null) throw new BadRequestException("No se puede agregar un usuario");
            return usuarioMapper.toUsuarioRsDto(usuarioRepository.save(usuarioMapper.toUsuarioEntity(rqDto)));
        } catch (Exception e) {
            throw new UsuarioException("Error al ingresar el usuario");
        }
    }

    @Override
    public List<UsuarioRsDto> getAllUsuarios() throws UsuarioException {
        try {
            return usuarioMapper.toUsuarioRsDtoList((List<UsuarioEntity>) usuarioRepository.findAll());
        } catch (Exception e) {
            throw new UsuarioException("Error al obtener todos los usuarios");
        }
    }

    @Override
    public UsuarioRsDto actualizarUsuario(UsuarioRqDto rqDto) throws UsuarioException {
        try {
            if (rqDto.id() == null) throw new BadRequestException("No se puede acttualizar usuario");
            return usuarioMapper.toUsuarioRsDto(usuarioRepository.save(usuarioMapper.toUsuarioEntity(rqDto)));
        } catch (Exception e) {
            throw new UsuarioException("Error al actualizar el usuario");
        }
    }

    @Override
    public void borrarUsuario(DeleteRqDto rqDto) throws UsuarioException {
        try {
            usuarioRepository.deleteById(rqDto.id());
        } catch (Exception e) {
            throw new UsuarioException("Error al borrar el usuario");
        }
    }
}
