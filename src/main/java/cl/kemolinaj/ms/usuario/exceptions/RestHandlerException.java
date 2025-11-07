package cl.kemolinaj.ms.usuario.exceptions;

import cl.kemolinaj.ms.usuario.dtos.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestHandlerException {
    private final static Integer CODENOK = 1;
    private final static String NOK = "NOK";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException e) return argumError(e);
        if (ex instanceof BadRequestException e) return badRequest(e.getMessage());
        if (ex instanceof UsuarioException e) return internalError(e.getMessage());
        else return ResponseEntity.internalServerError().build();
    }

    private ResponseEntity<ErrorDto> argumError(MethodArgumentNotValidException exception) {
        List<Map<String, Object>> listError = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            assert error.getDefaultMessage() != null;
            Map<String, Object> mapError = Map.of("field", error.getField(), "message", error.getDefaultMessage());
            listError.add(mapError);
        });
        return ResponseEntity.badRequest().body(new ErrorDto(CODENOK, NOK, listError));
    }

    private ResponseEntity<ErrorDto> badRequest(String message) {
        return ResponseEntity.badRequest().body(new ErrorDto(CODENOK, NOK, message));
    }

    private ResponseEntity<ErrorDto> internalError(String message) {
        return ResponseEntity.internalServerError().body(new ErrorDto(CODENOK, NOK, message));
    }
}
