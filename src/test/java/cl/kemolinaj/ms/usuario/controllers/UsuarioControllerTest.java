
package cl.kemolinaj.ms.usuario.controllers;

import cl.kemolinaj.ms.usuario.dtos.DeleteRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRqDto;
import cl.kemolinaj.ms.usuario.dtos.UsuarioRsDto;
import cl.kemolinaj.ms.usuario.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioController - Pruebas Unitarias")
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UsuarioRsDto usuarioRsDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        objectMapper = new ObjectMapper();

        // Inicializar datos de prueba
        usuarioRsDto = new UsuarioRsDto(1L, "Juan", "juan@ejemplo.com", "true", "", true, new ArrayList<>());
    }

    // ==================== PRUEBAS POST (INGRESAR) ====================

    @Test
    @DisplayName("POST /usuario - Falla sin request body")
    void testIngresarUsuarioSinRequestBody() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Verify
        verify(usuarioService, never()).agregarUsuario(any(UsuarioRqDto.class));
    }

    @Test
    void ingresarUsuarioOk() throws Exception {
        // Arrange
        UsuarioRqDto rqDto = new UsuarioRqDto(null, "Juan", "Perez", "juan@ejemplo.com", "password", "username", true, null);

        when(usuarioService.agregarUsuario(any(UsuarioRqDto.class)))
                .thenReturn(usuarioRsDto);

        // Act
        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rqDto)))
                // Assert
                .andExpect(status().isOk());
    }

    // ==================== PRUEBAS GET (BUSCAR) ====================

    @Test
    @DisplayName("GET /usuario - Obtener todos los usuarios exitosamente")
    void testBuscarTodoExitoso() throws Exception {
        // Arrange
        List<UsuarioRsDto> usuarios = List.of(
                usuarioRsDto,
                new UsuarioRsDto(2L, "María", "maria@ejemplo.com", "true", "", true, new ArrayList<>())
        );
        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        // Act & Assert
        mockMvc.perform(get("/usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Juan")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("María")));

        // Verify
        verify(usuarioService, times(1)).getAllUsuarios();
    }

    @Test
    @DisplayName("GET /usuario - Lista vacía de usuarios")
    void testBuscarTodoListaVacia() throws Exception {
        // Arrange
        when(usuarioService.getAllUsuarios()).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        // Verify
        verify(usuarioService, times(1)).getAllUsuarios();
    }

    // ==================== PRUEBAS PUT (ACTUALIZAR) ====================

    @Test
    @DisplayName("PUT /usuario - Falla sin request body")
    void testActualizarUsuarioSinRequestBody() throws Exception {
        // Act & Assert
        mockMvc.perform(put("/usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Verify
        verify(usuarioService, never()).actualizarUsuario(any(UsuarioRqDto.class));
    }

    @Test
    @DisplayName("PUT /usuario - Falla sin request body")
    void testActualizarUsuarioOk() throws Exception {
        // Arrange
        UsuarioRqDto rqDto = new UsuarioRqDto(null, "Juan", "Perez", "juan@ejemplo.com", "password", "username", true, null);

        when(usuarioService.actualizarUsuario(any(UsuarioRqDto.class)))
                .thenReturn(usuarioRsDto);

        // Act
        mockMvc.perform(put("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rqDto)))
                // Assert
                .andExpect(status().isOk());
    }

    // ==================== PRUEBAS DELETE (BORRAR) ====================

    @Test
    @DisplayName("DELETE /usuario - Borrar usuario exitosamente")
    void testBorrarUsuarioExitoso() throws Exception {
        // Arrange
        DeleteRqDto deleteRqDto = new DeleteRqDto(1L);
        doNothing().when(usuarioService).borrarUsuario(any(DeleteRqDto.class));

        // Act & Assert
        mockMvc.perform(delete("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRqDto)))
                .andExpect(status().isOk());

        // Verify
        verify(usuarioService, times(1)).borrarUsuario(any(DeleteRqDto.class));
    }

    @Test
    @DisplayName("DELETE /usuario - Falla sin request body")
    void testBorrarUsuarioSinRequestBody() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Verify
        verify(usuarioService, never()).borrarUsuario(any(DeleteRqDto.class));
    }
}