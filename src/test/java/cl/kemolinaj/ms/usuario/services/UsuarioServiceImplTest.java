
package cl.kemolinaj.ms.usuario.services;

import cl.kemolinaj.ms.usuario.dtos.DeleteRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.entities.UsuarioEntity;
import cl.kemolinaj.ms.usuario.exceptions.UsuarioException;
import cl.kemolinaj.ms.usuario.mappers.UsuarioMapper;
import cl.kemolinaj.ms.usuario.repositories.UsuarioRepository;
import cl.kemolinaj.ms.usuario.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - UsuarioServiceImpl")
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioRqDto usuarioRqDto;
    private UsuarioRsDto usuarioRsDto;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        usuarioRqDto = new UsuarioRqDto(null, "Juan", "juan@ejemplo.com", "password123", "true", "", true, new ArrayList<>());
        usuarioRsDto = new UsuarioRsDto(1L, "Juan", "juan@ejemplo.com", "password123", "true", "", true, new ArrayList<>());
        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNombre("Juan");
        usuarioEntity.setEmail("juan@ejemplo.com");
        usuarioEntity.setActivo(true);
    }

    // ==================== Pruebas para agregarUsuario ====================

    @Test
    @DisplayName("Debe agregar un usuario exitosamente")
    void testAgregarUsuarioExitoso() throws UsuarioException {
        // Arrange
        when(usuarioMapper.toUsuarioEntity(usuarioRqDto)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapper.toUsuarioRsDto(usuarioEntity)).thenReturn(usuarioRsDto);

        // Act
        UsuarioRsDto resultado = usuarioService.agregarUsuario(usuarioRqDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioRsDto.id(), resultado.id());
        assertEquals(usuarioRsDto.nombre(), resultado.nombre());
        assertEquals(usuarioRsDto.email(), resultado.email());
        verify(usuarioRepository, times(1)).save(usuarioEntity);
        verify(usuarioMapper, times(1)).toUsuarioEntity(usuarioRqDto);
        verify(usuarioMapper, times(1)).toUsuarioRsDto(usuarioEntity);
    }

    @Test
    @DisplayName("Debe lanzar BadRequestException cuando el usuario ya tiene ID")
    void testAgregarUsuarioConIdExistente() {
        // Arrange
        UsuarioRqDto usuarioConId = new UsuarioRqDto(1L, "Juan", "juan@ejemplo.com", "password123", "true", "", true, new ArrayList<>());

        // Act & Assert
        assertThrows(UsuarioException.class, () -> usuarioService.agregarUsuario(usuarioConId));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando falla la persistencia")
    void testAgregarUsuarioFallaPersistencia() {
        // Arrange
        when(usuarioMapper.toUsuarioEntity(usuarioRqDto)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenThrow(new RuntimeException("Error en BD"));

        // Act & Assert
        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.agregarUsuario(usuarioRqDto));
        assertEquals("Error al ingresar el usuario", exception.getMessage());
        verify(usuarioRepository, times(1)).save(usuarioEntity);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la conversión del DTO falla")
    void testAgregarUsuarioFallaConversion() {
        // Arrange
        when(usuarioMapper.toUsuarioEntity(usuarioRqDto)).thenThrow(new RuntimeException("Error en mapeo"));

        // Act & Assert
        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.agregarUsuario(usuarioRqDto));
        assertEquals("Error al ingresar el usuario", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    // ==================== Pruebas para getAllUsuarios ====================

    @Test
    @DisplayName("Debe obtener todos los usuarios exitosamente")
    void testGetAllUsuariosExitoso() throws UsuarioException {
        // Arrange
        List<UsuarioEntity> usuarioEntities = new ArrayList<>();
        usuarioEntities.add(usuarioEntity);

        UsuarioEntity usuarioEntity2 = new UsuarioEntity();
        usuarioEntity2.setId(2L);
        usuarioEntity2.setNombre("Carlos");
        usuarioEntity2.setEmail("carlos@ejemplo.com");
        usuarioEntities.add(usuarioEntity2);

        UsuarioRsDto usuarioRsDto2 = new UsuarioRsDto(2L, "Carlos", "carlos@ejemplo.com", "true", "", "true", true, new ArrayList<>());
        List<UsuarioRsDto> usuarioRsDtos = new ArrayList<>();
        usuarioRsDtos.add(usuarioRsDto);
        usuarioRsDtos.add(usuarioRsDto2);

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        when(usuarioMapper.toUsuarioRsDtoList(usuarioEntities)).thenReturn(usuarioRsDtos);

        // Act
        List<UsuarioRsDto> resultado = usuarioService.getAllUsuarios();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(usuarioRsDto.nombre(), resultado.get(0).nombre());
        assertEquals(usuarioRsDto2.nombre(), resultado.get(1).nombre());
        verify(usuarioRepository, times(1)).findAll();
        verify(usuarioMapper, times(1)).toUsuarioRsDtoList(usuarioEntities);
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay usuarios")
    void testGetAllUsuariosListaVacia() throws UsuarioException {
        // Arrange
        List<UsuarioEntity> usuarioEntities = new ArrayList<>();
        List<UsuarioRsDto> usuarioRsDtos = new ArrayList<>();

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        when(usuarioMapper.toUsuarioRsDtoList(usuarioEntities)).thenReturn(usuarioRsDtos);

        // Act
        List<UsuarioRsDto> resultado = usuarioService.getAllUsuarios();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando falla la consulta de todos los usuarios")
    void testGetAllUsuariosFalla() {
        // Arrange
        when(usuarioRepository.findAll()).thenThrow(new RuntimeException("Error en BD"));

        // Act & Assert
        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.getAllUsuarios());
        assertEquals("Error al obtener todos los usuarios", exception.getMessage());
        verify(usuarioRepository, times(1)).findAll();
    }

    // ==================== Pruebas para actualizarUsuario ====================

    @Test
    @DisplayName("Debe actualizar un usuario exitosamente")
    void testActualizarUsuarioExitoso() throws UsuarioException {
        // Arrange
        UsuarioRqDto usuarioActualizar = new UsuarioRqDto(1L, "Juan Actualizado", "juan.updated@ejemplo.com", "newPassword", "", "true", true, new ArrayList<>());
        UsuarioRsDto usuarioRsDtoActualizado = new UsuarioRsDto(1L, "Juan Actualizado", "juan.updated@ejemplo.com", "true", "", "true", true, new ArrayList<>());

        when(usuarioMapper.toUsuarioEntity(usuarioActualizar)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapper.toUsuarioRsDto(usuarioEntity)).thenReturn(usuarioRsDtoActualizado);

        // Act
        UsuarioRsDto resultado = usuarioService.actualizarUsuario(usuarioActualizar);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioRsDtoActualizado.id(), resultado.id());
        assertEquals(usuarioRsDtoActualizado.nombre(), resultado.nombre());
        verify(usuarioRepository, times(1)).save(usuarioEntity);
        verify(usuarioMapper, times(1)).toUsuarioEntity(usuarioActualizar);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando falla la actualización")
    void testActualizarUsuarioFalla() {
        // Arrange
        UsuarioRqDto usuarioActualizar = new UsuarioRqDto(1L, "Juan", "juan@ejemplo.com", "password", "true", "", true, new ArrayList<>());
        when(usuarioMapper.toUsuarioEntity(usuarioActualizar)).thenThrow(new RuntimeException("Error de mapeo"));

        // Act & Assert
        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.actualizarUsuario(usuarioActualizar));
        assertEquals("Error al actualizar el usuario", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la persistencia falla en actualización")
    void testActualizarUsuarioFallaPersistencia() {
        // Arrange
        UsuarioRqDto usuarioActualizar = new UsuarioRqDto(1L, "Juan", "juan@ejemplo.com", "password", "true", "", true, new ArrayList<>());
        when(usuarioMapper.toUsuarioEntity(usuarioActualizar)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenThrow(new RuntimeException("Error en BD"));

        // Act & Assert
        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.actualizarUsuario(usuarioActualizar));
        assertEquals("Error al actualizar el usuario", exception.getMessage());
        verify(usuarioRepository, times(1)).save(usuarioEntity);
    }

    @Test
    @DisplayName("Debe lanzar exception BadRequestException cuando id a modificar es null")
    void testActualizarUsuarioConIdNulo() {
        UsuarioRqDto usuarioActualizar = new UsuarioRqDto(null, "Juan", "juan@ejemplo.com", "password", "true", "", true, new ArrayList<>());
        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.actualizarUsuario(usuarioActualizar));
        assertEquals("Error al actualizar el usuario", exception.getMessage());
    }

    // ==================== Pruebas para borrarUsuario ====================

    @Test
    @DisplayName("Debe borrar un usuario exitosamente")
    void testBorrarUsuarioExitoso() {
        // Arrange
        DeleteRqDto deleteRqDto = new DeleteRqDto(1L);
        doNothing().when(usuarioRepository).deleteById(1L);

        // Act & Assert
        assertDoesNotThrow(() -> usuarioService.borrarUsuario(deleteRqDto));
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando falla la eliminación")
    void testBorrarUsuarioFalla() {
        // Arrange
        DeleteRqDto deleteRqDto = new DeleteRqDto(1L);
        doThrow(new RuntimeException("Error en BD")).when(usuarioRepository).deleteById(1L);

        // Act & Assert
        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.borrarUsuario(deleteRqDto));
        assertEquals("Error al borrar el usuario", exception.getMessage());
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el objeto DeleteRqDto es nulo")
    void testBorrarUsuarioConDeleteDtoNulo() {
        // Act & Assert
        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.borrarUsuario(null));
        assertEquals("Error al borrar el usuario", exception.getMessage());
        verify(usuarioRepository, never()).deleteById(any());
    }
}