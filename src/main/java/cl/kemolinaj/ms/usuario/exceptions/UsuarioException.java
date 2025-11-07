package cl.kemolinaj.ms.usuario.exceptions;

public class UsuarioException extends Exception{
    public UsuarioException(String message) {
        super(message);
    }

    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
}
