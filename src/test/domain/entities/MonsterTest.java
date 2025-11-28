package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.Direction;
import main.domain.enums.IceCreamFlavor;
import main.domain.enums.MonsterType;
import main.domain.interfaces.AIControlled;
import main.domain.interfaces.Movable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Monster (usando Troll como implementación).
 */
@DisplayName("Monster Tests")
class MonsterTest {

    @Test
    @DisplayName("MN-01: Tipo de monstruo")
    void testMonsterType() {
        // Arrange & Act
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertEquals(MonsterType.TROLL, troll.getMonsterType());
    }

    @Test
    @DisplayName("MN-02: Troll no puede romper hielo")
    void testTrollCannotBreakIce() {
        // Arrange & Act
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertFalse(troll.canBreakIce());
    }

    @Test
    @DisplayName("MN-02: OrangeSquid puede romper hielo")
    void testOrangeSquidCanBreakIce() {
        // Arrange & Act
        OrangeSquid squid = new OrangeSquid(5, 5);
        
        // Assert
        assertTrue(squid.canBreakIce());
    }

    @Test
    @DisplayName("MN-03: Patrón de movimiento")
    void testMonsterPattern() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertNotNull(troll.getPattern());
    }

    @Test
    @DisplayName("MN-04: Objetivo")
    void testMonsterTarget() {
        // Arrange
        Troll troll = new Troll(5, 5);
        IceCream player = new IceCream(10, 10, IceCreamFlavor.VANILLA);
        
        // Act
        troll.setTarget(player);
        
        // Assert
        assertEquals(player, troll.getTarget());
    }

    @Test
    @DisplayName("MN-05: Método update")
    void testMonsterUpdate() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Act & Assert
        assertDoesNotThrow(() -> troll.update());
    }

    @Test
    @DisplayName("MN-06: Monster implementa AIControlled")
    void testMonsterIsAIControlled() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertTrue(troll instanceof AIControlled);
    }

    @Test
    @DisplayName("Monster implementa Movable")
    void testMonsterImplementsMovable() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertTrue(troll instanceof Movable);
    }

    @Test
    @DisplayName("Monster hereda de GameObject")
    void testMonsterInheritsGameObject() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertTrue(troll instanceof GameObject);
    }

    @Test
    @DisplayName("Monster puede moverse")
    void testMonsterMove() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Act
        troll.move(Direction.RIGHT);
        
        // Assert
        assertEquals(6, troll.getX());
        assertEquals(5, troll.getY());
    }

    @Test
    @DisplayName("Monster detecta colisión con jugador")
    void testMonsterCollisionWithPlayer() {
        // Arrange
        Troll troll = new Troll(5, 5);
        IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
        
        // Assert
        assertTrue(troll.hasCollidedWith(player));
    }

    @Test
    @DisplayName("Monster no detecta colisión sin jugador")
    void testMonsterNoCollisionWithNullPlayer() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertFalse(troll.hasCollidedWith(null));
    }

    @Test
    @DisplayName("Monster no detecta colisión en posición diferente")
    void testMonsterNoCollisionDifferentPosition() {
        // Arrange
        Troll troll = new Troll(5, 5);
        IceCream player = new IceCream(10, 10, IceCreamFlavor.VANILLA);
        
        // Assert
        assertFalse(troll.hasCollidedWith(player));
    }

    @Test
    @DisplayName("Monster reset vuelve a posición inicial")
    void testMonsterReset() {
        // Arrange
        Troll troll = new Troll(5, 5);
        troll.move(Direction.RIGHT);
        troll.move(Direction.DOWN);
        
        // Act
        troll.reset();
        
        // Assert
        assertEquals(5, troll.getX());
        assertEquals(5, troll.getY());
    }

    @Test
    @DisplayName("Monster tiene símbolo correcto")
    void testMonsterSymbol() {
        // Arrange
        Troll troll = new Troll(0, 0);
        Pot pot = new Pot(0, 0);
        OrangeSquid squid = new OrangeSquid(0, 0);
        
        // Assert
        assertEquals('T', troll.getSymbol());
        assertEquals('O', pot.getSymbol());
        assertEquals('Q', squid.getSymbol());
    }

    @Test
    @DisplayName("getCurrentDirection retorna dirección actual")
    void testGetCurrentDirection() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertEquals(Direction.RIGHT, troll.getCurrentDirection());
        
        // Act
        troll.setCurrentDirection(Direction.UP);
        
        // Assert
        assertEquals(Direction.UP, troll.getCurrentDirection());
    }

    @Test
    @DisplayName("canMoveTo retorna true por defecto")
    void testCanMoveTo() {
        // Arrange
        Troll troll = new Troll(5, 5);
        
        // Assert
        assertTrue(troll.canMoveTo(10, 10));
    }
}
