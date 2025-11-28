package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración ErrorCode.
 */
@DisplayName("ErrorCode Enum Tests")
class ErrorCodeTest {

    @Test
    @DisplayName("EC-01: Debe contener todos los códigos de error")
    void testErrorCodeValues() {
        // Arrange & Act
        ErrorCode[] codes = ErrorCode.values();
        
        // Assert
        assertEquals(11, codes.length);
    }

    @Test
    @DisplayName("EC-01: INVALID_POSITION existe y tiene mensaje")
    void testInvalidPosition() {
        ErrorCode code = ErrorCode.INVALID_POSITION;
        assertNotNull(code);
        assertNotNull(code.getMessage());
        assertFalse(code.getMessage().isEmpty());
    }

    @Test
    @DisplayName("EC-01: INVALID_MOVE existe y tiene mensaje")
    void testInvalidMove() {
        ErrorCode code = ErrorCode.INVALID_MOVE;
        assertNotNull(code);
        assertNotNull(code.getMessage());
        assertFalse(code.getMessage().isEmpty());
    }

    @Test
    @DisplayName("EC-01: CANNOT_CREATE_ICE existe y tiene mensaje")
    void testCannotCreateIce() {
        ErrorCode code = ErrorCode.CANNOT_CREATE_ICE;
        assertNotNull(code);
        assertNotNull(code.getMessage());
        assertFalse(code.getMessage().isEmpty());
    }

    @Test
    @DisplayName("EC-01: CANNOT_DESTROY_ICE existe y tiene mensaje")
    void testCannotDestroyIce() {
        ErrorCode code = ErrorCode.CANNOT_DESTROY_ICE;
        assertNotNull(code);
        assertNotNull(code.getMessage());
        assertFalse(code.getMessage().isEmpty());
    }

    @Test
    @DisplayName("EC-01: LEVEL_LOAD_ERROR existe y tiene mensaje")
    void testLevelLoadError() {
        ErrorCode code = ErrorCode.LEVEL_LOAD_ERROR;
        assertNotNull(code);
        assertNotNull(code.getMessage());
        assertFalse(code.getMessage().isEmpty());
    }

    @Test
    @DisplayName("EC-01: SAVE_LOAD_ERROR existe y tiene mensaje")
    void testSaveLoadError() {
        ErrorCode code = ErrorCode.SAVE_LOAD_ERROR;
        assertNotNull(code);
        assertNotNull(code.getMessage());
        assertFalse(code.getMessage().isEmpty());
    }

    @Test
    @DisplayName("EC-01: INVALID_GAME_STATE existe y tiene mensaje")
    void testInvalidGameState() {
        ErrorCode code = ErrorCode.INVALID_GAME_STATE;
        assertNotNull(code);
        assertNotNull(code.getMessage());
        assertFalse(code.getMessage().isEmpty());
    }

    @Test
    @DisplayName("EC-01: COLLISION_ERROR existe y tiene mensaje")
    void testCollisionError() {
        ErrorCode code = ErrorCode.COLLISION_ERROR;
        assertNotNull(code);
        assertNotNull(code.getMessage());
        assertFalse(code.getMessage().isEmpty());
    }

    @Test
    @DisplayName("Todos los códigos tienen mensajes no vacíos")
    void testAllCodesHaveMessages() {
        for (ErrorCode code : ErrorCode.values()) {
            assertNotNull(code.getMessage(), "El código " + code + " debe tener mensaje");
            assertFalse(code.getMessage().isEmpty(), 
                "El mensaje del código " + code + " no debe estar vacío");
        }
    }
}
