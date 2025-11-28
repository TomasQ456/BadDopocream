package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración MonsterType.
 */
@DisplayName("MonsterType Enum Tests")
class MonsterTypeTest {

    @Test
    @DisplayName("MT-01: Debe contener los 3 tipos de monstruo")
    void testMonsterTypeValues() {
        // Arrange & Act
        MonsterType[] types = MonsterType.values();
        
        // Assert
        assertEquals(3, types.length);
        assertNotNull(MonsterType.TROLL);
        assertNotNull(MonsterType.POT);
        assertNotNull(MonsterType.ORANGE_SQUID);
    }

    @Test
    @DisplayName("MT-01: TROLL tiene símbolo 'T'")
    void testTrollSymbol() {
        assertEquals('T', MonsterType.TROLL.getSymbol());
    }

    @Test
    @DisplayName("MT-01: POT tiene símbolo 'O'")
    void testPotSymbol() {
        assertEquals('O', MonsterType.POT.getSymbol());
    }

    @Test
    @DisplayName("MT-01: ORANGE_SQUID tiene símbolo 'Q'")
    void testOrangeSquidSymbol() {
        assertEquals('Q', MonsterType.ORANGE_SQUID.getSymbol());
    }

    @Test
    @DisplayName("TROLL no puede romper hielo")
    void testTrollCannotBreakIce() {
        assertFalse(MonsterType.TROLL.canBreakIce());
    }

    @Test
    @DisplayName("POT no puede romper hielo")
    void testPotCannotBreakIce() {
        assertFalse(MonsterType.POT.canBreakIce());
    }

    @Test
    @DisplayName("ORANGE_SQUID puede romper hielo")
    void testOrangeSquidCanBreakIce() {
        assertTrue(MonsterType.ORANGE_SQUID.canBreakIce());
    }

    @Test
    @DisplayName("Solo ORANGE_SQUID puede romper hielo")
    void testOnlyOrangeSquidBreaksIce() {
        int iceBreakersCount = 0;
        for (MonsterType type : MonsterType.values()) {
            if (type.canBreakIce()) {
                iceBreakersCount++;
                assertEquals(MonsterType.ORANGE_SQUID, type);
            }
        }
        assertEquals(1, iceBreakersCount);
    }
}
