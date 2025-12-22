package cl.kemolinaj.ms.usuario.services;

import cl.kemolinaj.ms.usuario.dtos.LoginRqDto;
import cl.kemolinaj.ms.usuario.dtos.LoginRsDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.exceptions.UsuarioException;
import cl.kemolinaj.ms.usuario.services.impl.AuthServiceImpl;
import cl.kemolinaj.ms.usuario.utils.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test suite para AuthServiceImpl")
class AuthServiceImplTest {
    @Mock
    private UsuarioService usuarioService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginRqDto loginRqDto;
    private UsuarioRsDto usuarioRsDto;
    private String tokenGenerado;

    @BeforeEach
    void setUp() {
        loginRqDto = new LoginRqDto("usuario_test", "password123");
        usuarioRsDto = new UsuarioRsDto(1L, "Juan", "juan@ejemplo.com", "true", "", true, new ArrayList<>());
        tokenGenerado = "token_jwt_valido";
    }

    @Test
    @DisplayName("Debe retornar LoginRsDto con token cuando las credenciales son válidas")
    void testLoginExitoso() throws UsuarioException {
        // Arrange
        Mockito.when(usuarioService.getUsuarioByUsernameAndPassword(
                loginRqDto.userName(),
                loginRqDto.password()))
                .thenReturn(usuarioRsDto);
        Mockito.when(jwtProvider.generateToken(usuarioRsDto))
                .thenReturn(tokenGenerado);

        // Act
        LoginRsDto resultado = authService.login(loginRqDto);

        // Assert
        Assertions.assertNotNull(resultado);

    }

    @Test
    @DisplayName("Debe lanzar UsuarioException cuando usuarioRsDto es null")
    void testLoginWithNullUsuarioRsDtoThrowsException() throws UsuarioException {
        // Arrange
        Mockito.when(usuarioService.getUsuarioByUsernameAndPassword(
                        loginRqDto.userName(),
                        loginRqDto.password()))
                .thenReturn(null);

        // Act & Assert: Verificar que lanza UsuarioException
        UsuarioException exception = Assertions.assertThrows(
                UsuarioException.class,
                () -> authService.login(loginRqDto)
        );

        // Assert: Validar el mensaje de la excepción
        Assertions.assertEquals("Usuario no encontrado", exception.getMessage());
    }


}
