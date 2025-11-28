package test.domain;

import main.domain.DopoIceCream;
import main.domain.entities.*;
import main.domain.enums.*;
import main.domain.event.*;
import main.domain.exception.IceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración para el sistema completo del juego.
 */
@DisplayName("Integration Tests")
class IntegrationTest {

    private DopoIceCream game;
    private IceCream player;
    private Troll troll;
    private Pot pot;
    private Grape grape;
    private Banana banana;

    @BeforeEach
    void setUp() throws IceException {
        game = new DopoIceCream(15, 15);
        player = new IceCream(7, 7, IceCreamFlavor.VANILLA);
        troll = new Troll(2, 2);
        pot = new Pot(12, 12);
        grape = new Grape(8, 7);
        banana = new Banana(7, 8);
    }

    @Nested
    @DisplayName("Flujo de juego completo")
    class GameFlowTests {

        @Test
        @DisplayName("INT-01: Ciclo completo de victoria")
        void testCompleteVictoryFlow() throws IceException {
            // Arrange
            game.addPlayer(player);
            game.addFruit(grape);
            game.start();
            
            // Act - mover hacia la fruta
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            assertTrue(grape.isCollected());
            assertEquals(grape.getPoints(), player.getScore());
            assertEquals(GameState.VICTORY, game.getGameState());
        }

        @Test
        @DisplayName("INT-02: Ciclo completo de derrota")
        void testCompleteDefeatFlow() throws IceException {
            // Arrange
            player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Troll monster = new Troll(5, 5);
            game.addPlayer(player);
            game.addMonster(monster);
            game.addFruit(grape); // Para que no gane automáticamente
            game.start();
            
            // Act - el monstruo colisiona con el jugador múltiples veces
            game.checkCollisions(); // vida 1
            player.respawn();
            player.setPosition(5, 5); // volver a colisionar
            game.checkCollisions(); // vida 2
            player.respawn();
            player.setPosition(5, 5);
            game.checkCollisions(); // vida 3 - muerte
            game.checkDefeatCondition();
            
            // Assert
            assertEquals(GameState.DEFEAT, game.getGameState());
        }

        @Test
        @DisplayName("INT-03: Pausar y reanudar juego")
        void testPauseResumeFlow() throws IceException {
            // Arrange
            game.addPlayer(player);
            game.start();
            
            // Act & Assert
            assertEquals(GameState.PLAYING, game.getGameState());
            
            game.pause();
            assertEquals(GameState.PAUSED, game.getGameState());
            
            game.resume();
            assertEquals(GameState.PLAYING, game.getGameState());
        }

        @Test
        @DisplayName("INT-04: Reset completo del juego")
        void testCompleteResetFlow() throws IceException {
            // Arrange
            game.addPlayer(player);
            game.addMonster(troll);
            game.addFruit(grape);
            game.start();
            
            // Modificar estado
            game.movePlayer(0, Direction.UP);
            troll.move(Direction.RIGHT);
            grape.collect();
            
            // Act
            game.reset();
            
            // Assert
            assertEquals(GameState.MENU, game.getGameState());
            assertEquals(7, player.getX());
            assertEquals(7, player.getY());
            assertEquals(2, troll.getX());
            assertEquals(2, troll.getY());
            assertFalse(grape.isCollected());
        }
    }

    @Nested
    @DisplayName("Interacción jugador-entorno")
    class PlayerEnvironmentTests {

        @Test
        @DisplayName("INT-05: Jugador recolecta múltiples frutas")
        void testCollectMultipleFruits() throws IceException {
            // Arrange
            game.addPlayer(player);
            game.addFruit(grape); // en (8,7)
            game.addFruit(banana); // en (7,8)
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT); // recolectar grape
            game.movePlayer(0, Direction.DOWN);  // bajar
            game.movePlayer(0, Direction.LEFT);  // ir a banana
            
            // Assert
            assertTrue(grape.isCollected());
            assertEquals(2, player.getFruitsCollected());
            assertEquals(grape.getPoints() + banana.getPoints(), player.getScore());
        }

        @Test
        @DisplayName("INT-06: Jugador rompe muro de hielo")
        void testPlayerBreaksIceWall() throws IceException {
            // Arrange
            IceWall iceWall = new IceWall(8, 7);
            game.addPlayer(player);
            game.placeWall(iceWall);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            assertTrue(iceWall.isDestroyed());
        }

        @Test
        @DisplayName("INT-07: Jugador no puede atravesar muro indestructible")
        void testPlayerCannotPassIndestructibleWall() throws IceException {
            // Arrange
            IndestructibleWall wall = new IndestructibleWall(8, 7);
            game.addPlayer(player);
            game.placeWall(wall);
            game.start();
            
            int startX = player.getX();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            assertEquals(startX, player.getX());
            assertFalse(wall.isDestroyed());
        }

        @Test
        @DisplayName("INT-08: Jugador no puede salir del mapa")
        void testPlayerCannotLeaveMap() throws IceException {
            // Arrange
            player = new IceCream(0, 0, IceCreamFlavor.VANILLA);
            game.addPlayer(player);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.LEFT);
            game.movePlayer(0, Direction.UP);
            
            // Assert
            assertEquals(0, player.getX());
            assertEquals(0, player.getY());
        }
    }

    @Nested
    @DisplayName("Interacción monstruo-jugador")
    class MonsterPlayerTests {

        @Test
        @DisplayName("INT-09: Pot persigue al jugador con A*")
        void testPotChasesPlayer() throws IceException {
            // Arrange
            pot = new Pot(10, 7);
            game.addPlayer(player);
            game.addMonster(pot);
            game.addFruit(grape);
            game.start();
            
            int startDist = Math.abs(pot.getX() - player.getX()) + Math.abs(pot.getY() - player.getY());
            
            // Act
            game.update();
            
            int endDist = Math.abs(pot.getX() - player.getX()) + Math.abs(pot.getY() - player.getY());
            
            // Assert - pot se acercó al jugador
            assertTrue(endDist <= startDist);
        }

        @Test
        @DisplayName("INT-10: Troll sigue su patrón")
        void testTrollFollowsPattern() throws IceException {
            // Arrange
            game.addPlayer(player);
            game.addMonster(troll);
            game.addFruit(grape);
            game.start();
            
            int startX = troll.getX();
            
            // Act
            game.update();
            
            // Assert - troll se movió según patrón (primera dirección: RIGHT)
            assertEquals(startX + 1, troll.getX());
        }

        @Test
        @DisplayName("INT-11: Colisión reduce vidas del jugador")
        void testCollisionReducesLives() throws IceException {
            // Arrange
            troll = new Troll(7, 7); // misma posición que jugador
            game.addPlayer(player);
            game.addMonster(troll);
            game.addFruit(grape);
            game.start();
            
            int startLives = player.getLives();
            
            // Act
            game.checkCollisions();
            
            // Assert
            assertEquals(startLives - 1, player.getLives());
        }

        @Test
        @DisplayName("INT-12: Jugador respawnea después de hit")
        void testPlayerRespawnsAfterHit() throws IceException {
            // Arrange
            player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            troll = new Troll(5, 5);
            game.addPlayer(player);
            game.addMonster(troll);
            game.addFruit(grape);
            game.start();
            
            // Mover jugador
            game.movePlayer(0, Direction.RIGHT);
            assertEquals(6, player.getX());
            
            // Simular colisión
            player.setPosition(5, 5); // volver a posición del troll
            
            // Act
            game.handlePlayerHit(player);
            
            // Assert - jugador volvió a spawn
            assertEquals(5, player.getX());
            assertEquals(5, player.getY());
        }
    }

    @Nested
    @DisplayName("Sistema de eventos")
    class EventSystemIntegrationTests {

        private List<GameEvent> capturedEvents;
        private GameEventListener testListener;

        @BeforeEach
        void setUpListener() {
            capturedEvents = new ArrayList<>();
            testListener = event -> capturedEvents.add(event);
        }

        @Test
        @DisplayName("INT-13: Secuencia completa de eventos")
        void testEventSequence() throws IceException {
            // Arrange
            game.addEventListener(testListener);
            game.addPlayer(player);
            game.addFruit(grape);
            
            // Act
            game.start();
            game.movePlayer(0, Direction.RIGHT); // recolecta fruta y gana
            
            // Assert
            List<EventType> eventTypes = capturedEvents.stream()
                    .map(GameEvent::getType)
                    .toList();
            
            assertTrue(eventTypes.contains(EventType.GAME_STARTED));
            assertTrue(eventTypes.contains(EventType.PLAYER_MOVED));
            assertTrue(eventTypes.contains(EventType.FRUIT_COLLECTED));
            assertTrue(eventTypes.contains(EventType.GAME_WON));
        }

        @Test
        @DisplayName("INT-14: Evento de destrucción de hielo")
        void testIceDestroyedEvent() throws IceException {
            // Arrange
            IceWall iceWall = new IceWall(8, 7);
            game.addEventListener(testListener);
            game.addPlayer(player);
            game.placeWall(iceWall);
            game.addFruit(grape);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            boolean hasIceDestroyedEvent = capturedEvents.stream()
                    .anyMatch(e -> e.getType() == EventType.ICE_DESTROYED);
            assertTrue(hasIceDestroyedEvent);
        }

        @Test
        @DisplayName("INT-15: Evento de jugador golpeado")
        void testPlayerHitEvent() throws IceException {
            // Arrange
            troll = new Troll(7, 7);
            game.addEventListener(testListener);
            game.addPlayer(player);
            game.addMonster(troll);
            game.addFruit(grape);
            game.start();
            
            // Act
            game.checkCollisions();
            
            // Assert
            boolean hasPlayerHitEvent = capturedEvents.stream()
                    .anyMatch(e -> e.getType() == EventType.PLAYER_HIT);
            assertTrue(hasPlayerHitEvent);
        }
    }

    @Nested
    @DisplayName("Modo cooperativo")
    class CooperativeModeTests {

        @Test
        @DisplayName("INT-16: Dos jugadores en modo cooperativo")
        void testTwoPlayersCooperative() throws IceException {
            // Arrange
            game.setGameMode(GameMode.COOPERATIVE);
            IceCream player1 = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            IceCream player2 = new IceCream(10, 10, IceCreamFlavor.CHOCOLATE);
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.addFruit(grape);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            game.movePlayer(1, Direction.LEFT);
            
            // Assert
            assertEquals(6, player1.getX());
            assertEquals(9, player2.getX());
        }

        @Test
        @DisplayName("INT-17: Ambos jugadores pueden recolectar frutas")
        void testBothPlayersCollectFruits() throws IceException {
            // Arrange
            game.setGameMode(GameMode.COOPERATIVE);
            IceCream player1 = new IceCream(7, 7, IceCreamFlavor.VANILLA);
            IceCream player2 = new IceCream(6, 8, IceCreamFlavor.CHOCOLATE);
            Grape grape2 = new Grape(6, 7);
            
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.addFruit(grape); // en (8,7)
            game.addFruit(grape2); // en (6,7)
            game.addFruit(banana); // en (7,8)
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT); // player1 recoge grape
            game.movePlayer(1, Direction.UP);     // player2 recoge grape2
            
            // Assert
            assertTrue(grape.isCollected());
            assertTrue(grape2.isCollected());
        }

        @Test
        @DisplayName("INT-18: Derrota solo cuando todos los jugadores mueren")
        void testDefeatOnlyWhenAllPlayersDie() throws IceException {
            // Arrange
            game.setGameMode(GameMode.COOPERATIVE);
            IceCream player1 = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            IceCream player2 = new IceCream(10, 10, IceCreamFlavor.CHOCOLATE);
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.addFruit(grape);
            game.start();
            
            // Act - matar a player1
            player1.decreaseLives();
            player1.decreaseLives();
            player1.decreaseLives();
            game.checkDefeatCondition();
            
            // Assert - player2 todavía vive
            assertEquals(GameState.PLAYING, game.getGameState());
            
            // Matar a player2
            player2.decreaseLives();
            player2.decreaseLives();
            player2.decreaseLives();
            game.checkDefeatCondition();
            
            // Assert - ahora sí es derrota
            assertEquals(GameState.DEFEAT, game.getGameState());
        }
    }

    @Nested
    @DisplayName("Escenarios complejos")
    class ComplexScenarioTests {

        @Test
        @DisplayName("INT-19: Laberinto con múltiples entidades")
        void testMazeWithMultipleEntities() throws IceException {
            // Arrange - crear un pequeño laberinto
            game = new DopoIceCream(10, 10);
            player = new IceCream(1, 1, IceCreamFlavor.VANILLA);
            
            // Muros formando un pasillo
            game.placeWall(new IndestructibleWall(0, 2));
            game.placeWall(new IndestructibleWall(1, 2));
            game.placeWall(new IndestructibleWall(2, 2));
            game.placeWall(new IceWall(3, 2)); // muro rompible
            
            Grape targetFruit = new Grape(4, 1);
            Pot chaser = new Pot(8, 8);
            
            game.addPlayer(player);
            game.addMonster(chaser);
            game.addFruit(targetFruit);
            game.start();
            
            // Act - navegar hacia la fruta
            game.movePlayer(0, Direction.RIGHT); // x=2
            game.movePlayer(0, Direction.RIGHT); // x=3
            game.movePlayer(0, Direction.RIGHT); // x=4, rompe hielo y llega a fruta
            
            // Assert
            assertEquals(4, player.getX());
            assertTrue(targetFruit.isCollected());
            assertEquals(GameState.VICTORY, game.getGameState());
        }

        @Test
        @DisplayName("INT-20: OrangeSquid puede romper hielo")
        void testOrangeSquidBreaksIce() throws IceException {
            // Arrange
            OrangeSquid squid = new OrangeSquid(5, 5);
            game.addPlayer(player);
            game.addMonster(squid);
            game.addFruit(grape);
            game.start();
            
            // Act
            Direction brokenDir = squid.breakIceBlock(Direction.RIGHT);
            
            // Assert
            assertEquals(Direction.RIGHT, brokenDir);
            assertTrue(squid.canBreakIce());
        }
    }
}
