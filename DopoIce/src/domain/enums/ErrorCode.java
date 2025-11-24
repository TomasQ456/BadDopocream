package domain.enums;

/**
 * Domain-specific error codes attached to {@link domain.IceException} instances.
 */
public enum ErrorCode {
    LEVEL_NOT_FOUND,
    INVALID_COORDINATES,
    CELL_BLOCKED,
    CELL_OCCUPIED,
    INVALID_OPERATION,
    PERSISTENCE_ERROR
}
