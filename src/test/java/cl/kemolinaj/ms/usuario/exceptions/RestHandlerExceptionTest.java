package cl.kemolinaj.ms.usuario.exceptions;

import cl.kemolinaj.ms.usuario.dtos.ErrorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("RestHandlerException Tests")
class RestHandlerExceptionTest {

    private RestHandlerException restHandlerException;

    @BeforeEach
    void setUp() {
        restHandlerException = new RestHandlerException();
    }

    @Test
    @DisplayName("Debe manejar MethodArgumentNotValidException correctamente")
    void testHandleMethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("usuario", "email", "El email es inválido");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(exception.getBindingResult().getFieldErrors())
                .thenReturn(List.of(fieldError));

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().code());
        assertEquals("NOK", response.getBody().message());
        assertNotNull(response.getBody().description());
    }

    @Test
    @DisplayName("Debe manejar BadRequestException correctamente")
    void testHandleBadRequestException() {
        // Arrange
        String errorMessage = "Solicitud inválida";
        BadRequestException exception = new BadRequestException(errorMessage);

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().code());
        assertEquals("NOK", response.getBody().message());
        assertEquals(errorMessage, response.getBody().description());
    }

    @Test
    @DisplayName("Debe manejar UsuarioException correctamente")
    void testHandleUsuarioException() {
        // Arrange
        String errorMessage = "Error en operación de usuario";
        UsuarioException exception = new UsuarioException(errorMessage);

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().code());
        assertEquals("NOK", response.getBody().message());
        assertEquals(errorMessage, response.getBody().description());
    }

    @Test
    @DisplayName("Debe manejar excepciones genéricas con respuesta de error interno")
    void testHandleGenericException() {
        // Arrange
        Exception exception = new Exception("Error no controlado");

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Debe manejar múltiples errores de validación")
    void testHandleMultipleValidationErrors() {
        // Arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError1 = new FieldError("usuario", "email", "El email es requerido");
        FieldError fieldError2 = new FieldError("usuario", "nombre", "El nombre es requerido");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(fieldError1, fieldError2));


        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> errors = (List<Map<String, Object>>) response.getBody().description();
        assertEquals(2, errors.size());
        assertEquals("email", errors.get(0).get("field"));
        assertEquals("nombre", errors.get(1).get("field"));
    }

    @Test
    @DisplayName("Debe retornar código y mensaje NOK para BadRequestException")
    void testBadRequestExceptionCodeAndMessage() {
        // Arrange
        BadRequestException exception = new BadRequestException("Validación fallida");

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().code());
        assertEquals("NOK", response.getBody().message());
    }

    @Test
    @DisplayName("Debe retornar código y mensaje NOK para UsuarioException")
    void testUsuarioExceptionCodeAndMessage() {
        // Arrange
        UsuarioException exception = new UsuarioException("Usuario no encontrado");

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().code());
        assertEquals("NOK", response.getBody().message());
    }

}