package cl.kemolinaj.ms.usuario.controllers;

import cl.kemolinaj.ms.usuario.dtos.LoginRqDto;
import cl.kemolinaj.ms.usuario.dtos.LoginRsDto;
import cl.kemolinaj.ms.usuario.services.AuthService;
import cl.kemolinaj.ms.usuario.utils.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@DisplayName("AuthController Tests")
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @SuppressWarnings("deprecation")
    @MockBean  // ← Cambia @Mock a @MockBean
    private AuthService authService;

    @SuppressWarnings("deprecation")
    @MockBean  // ← Cambia @Mock a @MockBean
    private JwtProvider jwtProvider;

    private LoginRqDto validLoginRequest;

    @BeforeEach
    void setUp() {
        validLoginRequest = new LoginRqDto("usuario_test", "password123");
    }

    @Test
    void testLogin() throws Exception {
        // Arrange
        LoginRsDto expectedResponse = new LoginRsDto("token123");
        when(authService.login(any(LoginRqDto.class)))
                .thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isOk());
    }

}
