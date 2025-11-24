package domain;

import domain.enums.ErrorCode;

/**
 * Custom exception used throughout the domain to indicate user-facing errors.
 */
public class IceException extends Exception {

    private final ErrorCode errorCode;

    /**
     * Creates an exception with a default {@link ErrorCode#INVALID_OPERATION} code.
     */
    public IceException(String message) {
        this(message, ErrorCode.INVALID_OPERATION, null);
    }

    /**
     * Creates an exception bound to the provided {@link ErrorCode}.
     */
    public IceException(String message, ErrorCode code) {
        this(message, code, null);
    }

    /**
     * Fully parameterized constructor.
     */
    public IceException(String message, ErrorCode code, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
    }

    /**
     * @return associated error classification.
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
