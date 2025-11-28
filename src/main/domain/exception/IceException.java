package main.domain.exception;

import main.domain.enums.ErrorCode;

/**
 * Excepción personalizada para el juego Bad Dopo-Cream.
 * Encapsula todos los errores del dominio del juego.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class IceException extends Exception {

    private final ErrorCode errorCode;

    /**
     * Constructor con código de error.
     * 
     * @param errorCode el código de error
     */
    public IceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * Constructor con código de error y mensaje personalizado.
     * 
     * @param errorCode el código de error
     * @param message mensaje adicional
     */
    public IceException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + ": " + message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor con código de error y causa.
     * 
     * @param errorCode el código de error
     * @param cause la causa original
     */
    public IceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructor con código de error, mensaje y causa.
     * 
     * @param errorCode el código de error
     * @param message mensaje adicional
     * @param cause la causa original
     */
    public IceException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.getMessage() + ": " + message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Obtiene el código de error.
     * 
     * @return el código de error
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
