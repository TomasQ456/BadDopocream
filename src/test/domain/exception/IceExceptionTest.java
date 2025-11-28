package test.domain.exception;

import main.domain.exception.*;

import main.domain.enums.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para IceException.
 */
@DisplayName("IceException Tests")
class IceExceptionTest {

    @Test
    @DisplayName("EX-01: Crear excepción con código de error")
    void testIceExceptionWithErrorCode() {
        // Arrange
        ErrorCode code = ErrorCode.INVALID_POSITION;
        
        // Act
        IceException exception = new IceException(code);
        
        // Assert
        assertEquals(code, exception.getErrorCode());
        assertEquals(code.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("EX-02: Crear excepción con código y mensaje personalizado")
    void testIceExceptionWithMessage() {
        // Arrange
        ErrorCode code = ErrorCode.INVALID_MOVE;
        String customMessage = "No se puede mover a (5,5)";
        
        // Act
        IceException exception = new IceException(code, customMessage);
        
        // Assert
        assertEquals(code, exception.getErrorCode());
        assertTrue(exception.getMessage().contains(code.getMessage()));
        assertTrue(exception.getMessage().contains(customMessage));
    }

    @Test
    @DisplayName("EX-03: Crear excepción con código y causa")
    void testIceExceptionWithCause() {
        // Arrange
        ErrorCode code = ErrorCode.LEVEL_LOAD_ERROR;
        Throwable cause = new RuntimeException("Archivo no encontrado");
        
        // Act
        IceException exception = new IceException(code, cause);
        
        // Assert
        assertEquals(code, exception.getErrorCode());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("EX-03: Crear excepción con código, mensaje y causa")
    void testIceExceptionWithMessageAndCause() {
        // Arrange
        ErrorCode code = ErrorCode.SAVE_LOAD_ERROR;
        String customMessage = "Error al guardar nivel 5";
        Throwable cause = new RuntimeException("IO Error");
        
        // Act
        IceException exception = new IceException(code, customMessage, cause);
        
        // Assert
        assertEquals(code, exception.getErrorCode());
        assertEquals(cause, exception.getCause());
        assertTrue(exception.getMessage().contains(code.getMessage()));
        assertTrue(exception.getMessage().contains(customMessage));
    }

    @Test
    @DisplayName("EX-04: Probar INVALID_POSITION")
    void testInvalidPosition() {
        // Arrange & Act
        IceException exception = new IceException(ErrorCode.INVALID_POSITION);
        
        // Assert
        assertEquals(ErrorCode.INVALID_POSITION, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("EX-05: Probar INVALID_MOVE")
    void testInvalidMove() {
        // Arrange & Act
        IceException exception = new IceException(ErrorCode.INVALID_MOVE);
        
        // Assert
        assertEquals(ErrorCode.INVALID_MOVE, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("EX-06: Probar CANNOT_CREATE_ICE")
    void testCannotCreateIce() {
        // Arrange & Act
        IceException exception = new IceException(ErrorCode.CANNOT_CREATE_ICE);
        
        // Assert
        assertEquals(ErrorCode.CANNOT_CREATE_ICE, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("EX-07: Probar CANNOT_DESTROY_ICE")
    void testCannotDestroyIce() {
        // Arrange & Act
        IceException exception = new IceException(ErrorCode.CANNOT_DESTROY_ICE);
        
        // Assert
        assertEquals(ErrorCode.CANNOT_DESTROY_ICE, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("EX-08: Probar LEVEL_LOAD_ERROR")
    void testLevelLoadError() {
        // Arrange & Act
        IceException exception = new IceException(ErrorCode.LEVEL_LOAD_ERROR);
        
        // Assert
        assertEquals(ErrorCode.LEVEL_LOAD_ERROR, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("EX-09: Probar SAVE_LOAD_ERROR")
    void testSaveLoadError() {
        // Arrange & Act
        IceException exception = new IceException(ErrorCode.SAVE_LOAD_ERROR);
        
        // Assert
        assertEquals(ErrorCode.SAVE_LOAD_ERROR, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("EX-10: Probar INVALID_GAME_STATE")
    void testInvalidGameState() {
        // Arrange & Act
        IceException exception = new IceException(ErrorCode.INVALID_GAME_STATE);
        
        // Assert
        assertEquals(ErrorCode.INVALID_GAME_STATE, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("EX-11: Probar COLLISION_ERROR")
    void testCollisionError() {
        // Arrange & Act
        IceException exception = new IceException(ErrorCode.COLLISION_ERROR);
        
        // Assert
        assertEquals(ErrorCode.COLLISION_ERROR, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("IceException es subclase de Exception")
    void testIsException() {
        IceException exception = new IceException(ErrorCode.INVALID_POSITION);
        assertTrue(exception instanceof Exception);
    }

    @Test
    @DisplayName("Puede ser lanzada y capturada")
    void testThrowAndCatch() {
        // Arrange
        ErrorCode expectedCode = ErrorCode.INVALID_MOVE;
        
        // Act & Assert
        IceException caught = assertThrows(IceException.class, () -> {
            throw new IceException(expectedCode);
        });
        
        assertEquals(expectedCode, caught.getErrorCode());
    }
}
