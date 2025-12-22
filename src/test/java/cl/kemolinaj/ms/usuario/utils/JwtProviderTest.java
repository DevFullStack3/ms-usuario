package cl.kemolinaj.ms.usuario.utils;

import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.entities.RolEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

@DisplayName("Pruebas unitarias para JwtProvider")
@ExtendWith(MockitoExtension.class)
class JwtProviderTest {
    @InjectMocks
    private JwtProvider jwtProvider;

    @Mock
    private UsuarioRsDto usuarioRsDto;

    @Mock
    private RolEntity rolEntity;

    private static final String JWT_SECRET = "miClaveSecretaMuyLargaParaJWTDeAlMenos256BitsDeSeguridad12345678";
    private static final int JWT_EXPIRATION_MS = 86400000; // 24 horas

    @BeforeEach
    void setUp() {
        // Configurar las propiedades usando ReflectionTestUtils
        ReflectionTestUtils.setField(jwtProvider, "jwtSecret", JWT_SECRET);
        ReflectionTestUtils.setField(jwtProvider, "jwtExpirationMs", JWT_EXPIRATION_MS);
    }

    @Test
    @DisplayName("El token generado debe contener el nombre completo del usuario")
    void testGenerateToken_ContainsFullName() {
        // Arrange
        Mockito.when(usuarioRsDto.nombre()).thenReturn("Carlos");
        Mockito.when(usuarioRsDto.apellido()).thenReturn("López");
        Mockito.when(usuarioRsDto.roles()).thenReturn(List.of());

        // Act
        String token = jwtProvider.generateToken(usuarioRsDto);
        String username = jwtProvider.getUsernameFromToken(token);

        // Assert
        Assertions.assertEquals("Carlos López", username);
    }

    @Test
    @DisplayName("Debe extraer correctamente los roles del token JWT")
    void testGetRolesFromToken_Success() {
        // Arrange
        Mockito.when(usuarioRsDto.nombre()).thenReturn("Laura");
        Mockito.when(usuarioRsDto.apellido()).thenReturn("Sánchez");
        List<RolEntity> roles = List.of(rolEntity);
        Mockito.when(usuarioRsDto.roles()).thenReturn(roles);
        Mockito.when(rolEntity.getNombre()).thenReturn("ROLE_ADMIN");

        String token = jwtProvider.generateToken(usuarioRsDto);

        // Act
        List<String> rolesFromToken = jwtProvider.getRolesFromToken(token);

        // Assert
        Assertions.assertNotNull(rolesFromToken);
    }

    @Test
    @DisplayName("Debe validar correctamente un token JWT válido")
    void testValidateToken_WithValidToken_ReturnTrue() {
        // Arrange
        Mockito.when(usuarioRsDto.nombre()).thenReturn("Juan");
        Mockito.when(usuarioRsDto.apellido()).thenReturn("Pérez");
        Mockito.when(usuarioRsDto.roles()).thenReturn(List.of());

        String token = jwtProvider.generateToken(usuarioRsDto);

        // Act
        boolean isValid = jwtProvider.validateToken(token);

        // Assert
        Assertions.assertTrue(isValid, "El token generado debe ser válido");
    }

}
