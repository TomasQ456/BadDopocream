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
 * Pruebas unitarias para DopoIceCream - controlador principal del juego.
 */
@DisplayName("DopoIceCream Tests")
class DopoIceCreamTest {

    private DopoIceCream game;

    @BeforeEach
    void setUp() throws IceException {
        game = new DopoIceCream(20, 15);
    }

    @Nested
    @DisplayName("Inicialización del juego")
    class InitializationTests {

        @Test
        @DisplayName("DC-01: Constructor crea juego con dimensiones correctas")
        void testConstructorWithDimensions() {
            assertEquals(20, game.getWidth());
            assertEquals(15, game.getHeight());
        }

        @Test
        @DisplayName("DC-02: Estado inicial es MENU")
        void testInitialState() {
            assertEquals(GameState.MENU, game.getGameState());
        }

        @Test
        @DisplayName("DC-03: Modo de juego por defecto")
        void testDefaultGameMode() {
            assertEquals(GameMode.SINGLE_PLAYER, game.getGameMode());
        }

        @Test
        @DisplayName("DC-04: Crear juego con dimensiones inválidas lanza excepción")
        void testInvalidDimensions() {
            assertThrows(IceException.class, () -> new DopoIceCream(0, 10));
            assertThrows(IceException.class, () -> new DopoIceCream(10, 0));
            assertThrows(IceException.class, () -> new DopoIceCream(-5, -5));
        }

        @Test
        @DisplayName("DC-05: Mapa inicializado después de construcción")
        void testMapInitialized() {
            assertNotNull(game.getMap());
        }

        @Test
        @DisplayName("DC-06: Lista de jugadores vacía al inicio")
        void testNoPlayersAtStart() {
            assertTrue(game.getPlayers().isEmpty());
        }

        @Test
        @DisplayName("DC-07: Lista de monstruos vacía al inicio")
        void testNoMonstersAtStart() {
            assertTrue(game.getMonsters().isEmpty());
        }

        @Test
        @DisplayName("DC-08: Lista de frutas vacía al inicio")
        void testNoFruitsAtStart() {
            assertTrue(game.getFruits().isEmpty());
        }
    }

    @Nested
    @DisplayName("Gestión de jugadores")
    class PlayerManagementTests {

        @Test
        @DisplayName("PM-01: Añadir jugador")
        void testAddPlayer() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            
            // Act
            game.addPlayer(player);
            
            // Assert
            assertEquals(1, game.getPlayers().size());
            assertTrue(game.getPlayers().contains(player));
        }

        @Test
        @DisplayName("PM-02: Añadir jugador null lanza excepción")
        void testAddNullPlayer() {
            assertThrows(IceException.class, () -> game.addPlayer(null));
        }

        @Test
        @DisplayName("PM-03: Obtener jugador por índice")
        void testGetPlayerByIndex() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            game.addPlayer(player);
            
            // Act & Assert
            assertEquals(player, game.getPlayer(0));
        }

        @Test
        @DisplayName("PM-04: Obtener jugador 1 (SINGLE_PLAYER)")
        void testGetPlayer1() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            game.addPlayer(player);
            
            // Act & Assert
            assertEquals(player, game.getPlayer1());
        }

        @Test
        @DisplayName("PM-05: Modo COOPERATIVE permite 2 jugadores")
        void testCooperativeModeTwoPlayers() throws IceException {
            // Arrange
            game.setGameMode(GameMode.COOPERATIVE);
            IceCream player1 = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            IceCream player2 = new IceCream(10, 10, IceCreamFlavor.CHOCOLATE);
            
            // Act
            game.addPlayer(player1);
            game.addPlayer(player2);
            
            // Assert
            assertEquals(2, game.getPlayers().size());
        }

        @Test
        @DisplayName("PM-06: Obtener jugador 2 en modo cooperativo")
        void testGetPlayer2() throws IceException {
            // Arrange
            game.setGameMode(GameMode.COOPERATIVE);
            IceCream player1 = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            IceCream player2 = new IceCream(10, 10, IceCreamFlavor.CHOCOLATE);
            game.addPlayer(player1);
            game.addPlayer(player2);
            
            // Act & Assert
            assertEquals(player2, game.getPlayer2());
        }

        @Test
        @DisplayName("PM-07: Remover jugador")
        void testRemovePlayer() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            game.addPlayer(player);
            
            // Act
            game.removePlayer(player);
            
            // Assert
            assertTrue(game.getPlayers().isEmpty());
        }
    }

    @Nested
    @DisplayName("Gestión de monstruos")
    class MonsterManagementTests {

        @Test
        @DisplayName("MO-01: Añadir monstruo")
        void testAddMonster() {
            // Arrange
            Troll troll = new Troll(5, 5);
            
            // Act
            game.addMonster(troll);
            
            // Assert
            assertEquals(1, game.getMonsters().size());
            assertTrue(game.getMonsters().contains(troll));
        }

        @Test
        @DisplayName("MO-02: Añadir múltiples monstruos")
        void testAddMultipleMonsters() {
            // Arrange
            Troll troll = new Troll(5, 5);
            Pot pot = new Pot(10, 10);
            OrangeSquid squid = new OrangeSquid(15, 5);
            
            // Act
            game.addMonster(troll);
            game.addMonster(pot);
            game.addMonster(squid);
            
            // Assert
            assertEquals(3, game.getMonsters().size());
        }

        @Test
        @DisplayName("MO-03: Remover monstruo")
        void testRemoveMonster() {
            // Arrange
            Troll troll = new Troll(5, 5);
            game.addMonster(troll);
            
            // Act
            game.removeMonster(troll);
            
            // Assert
            assertTrue(game.getMonsters().isEmpty());
        }

        @Test
        @DisplayName("MO-04: Monstruos reciben target al iniciar juego")
        void testMonstersGetTarget() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Troll troll = new Troll(10, 10);
            game.addPlayer(player);
            game.addMonster(troll);
            
            // Act
            game.start();
            
            // Assert
            assertEquals(player, troll.getTarget());
        }
    }

    @Nested
    @DisplayName("Gestión de frutas")
    class FruitManagementTests {

        @Test
        @DisplayName("FR-01: Añadir fruta")
        void testAddFruit() {
            // Arrange
            Grape grape = new Grape(5, 5);
            
            // Act
            game.addFruit(grape);
            
            // Assert
            assertEquals(1, game.getFruits().size());
            assertTrue(game.getFruits().contains(grape));
        }

        @Test
        @DisplayName("FR-02: Remover fruta")
        void testRemoveFruit() {
            // Arrange
            Grape grape = new Grape(5, 5);
            game.addFruit(grape);
            
            // Act
            game.removeFruit(grape);
            
            // Assert
            assertTrue(game.getFruits().isEmpty());
        }

        @Test
        @DisplayName("FR-03: Contar frutas no colectadas")
        void testCountRemainingFruits() {
            // Arrange
            Grape grape = new Grape(5, 5);
            Banana banana = new Banana(10, 10);
            game.addFruit(grape);
            game.addFruit(banana);
            grape.collect(); // colectar una
            
            // Act & Assert
            assertEquals(1, game.getRemainingFruitsCount());
        }
    }

    @Nested
    @DisplayName("Control del estado del juego")
    class GameStateTests {

        @Test
        @DisplayName("GS-01: Iniciar juego cambia estado a PLAYING")
        void testStartGameChangesState() throws IceException {
            // Arrange
            game.addPlayer(new IceCream(5, 5, IceCreamFlavor.VANILLA));
            
            // Act
            game.start();
            
            // Assert
            assertEquals(GameState.PLAYING, game.getGameState());
        }

        @Test
        @DisplayName("GS-02: No se puede iniciar sin jugadores")
        void testCannotStartWithoutPlayers() {
            assertThrows(IceException.class, () -> game.start());
        }

        @Test
        @DisplayName("GS-03: Pausar juego")
        void testPauseGame() throws IceException {
            // Arrange
            game.addPlayer(new IceCream(5, 5, IceCreamFlavor.VANILLA));
            game.start();
            
            // Act
            game.pause();
            
            // Assert
            assertEquals(GameState.PAUSED, game.getGameState());
        }

        @Test
        @DisplayName("GS-04: Reanudar juego pausado")
        void testResumeGame() throws IceException {
            // Arrange
            game.addPlayer(new IceCream(5, 5, IceCreamFlavor.VANILLA));
            game.start();
            game.pause();
            
            // Act
            game.resume();
            
            // Assert
            assertEquals(GameState.PLAYING, game.getGameState());
        }

        @Test
        @DisplayName("GS-05: Victoria cuando todas las frutas son colectadas")
        void testVictoryWhenAllFruitsCollected() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Grape grape = new Grape(5, 5);
            game.addPlayer(player);
            game.addFruit(grape);
            game.start();
            
            // Act
            grape.collect();
            game.checkVictoryCondition();
            
            // Assert
            assertEquals(GameState.VICTORY, game.getGameState());
        }

        @Test
        @DisplayName("GS-06: Derrota cuando jugador sin vidas")
        void testDefeatWhenNoLives() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            game.addPlayer(player);
            game.start();
            
            // Act
            player.decreaseLives(); // 2
            player.decreaseLives(); // 1
            player.decreaseLives(); // 0
            game.checkDefeatCondition();
            
            // Assert
            assertEquals(GameState.DEFEAT, game.getGameState());
        }

        @Test
        @DisplayName("GS-07: Reset reinicia todo el juego")
        void testResetGame() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Troll troll = new Troll(10, 10);
            game.addPlayer(player);
            game.addMonster(troll);
            game.start();
            player.move(Direction.RIGHT);
            troll.move(Direction.LEFT);
            
            // Act
            game.reset();
            
            // Assert
            assertEquals(GameState.MENU, game.getGameState());
            assertEquals(5, player.getX());
            assertEquals(10, troll.getX());
        }
    }

    @Nested
    @DisplayName("Movimiento de jugador")
    class PlayerMovementTests {

        @Test
        @DisplayName("MV-01: Mover jugador en dirección válida")
        void testMovePlayer() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            game.addPlayer(player);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            assertEquals(6, player.getX());
        }

        @Test
        @DisplayName("MV-02: No mover jugador contra pared indestructible")
        void testNoMoveAgainstWall() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            IndestructibleWall wall = new IndestructibleWall(6, 5);
            game.addPlayer(player);
            game.placeWall(wall);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert - no se movió
            assertEquals(5, player.getX());
        }

        @Test
        @DisplayName("MV-03: Romper muro de hielo al moverse")
        void testBreakIceWallOnMove() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            IceWall iceWall = new IceWall(6, 5);
            game.addPlayer(player);
            game.placeWall(iceWall);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            assertTrue(iceWall.isDestroyed());
        }

        @Test
        @DisplayName("MV-04: Recolectar fruta al moverse")
        void testCollectFruitOnMove() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Grape grape = new Grape(6, 5);
            game.addPlayer(player);
            game.addFruit(grape);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            assertTrue(grape.isCollected());
            assertEquals(grape.getPoints(), player.getScore());
        }

        @Test
        @DisplayName("MV-05: No mover jugador fuera del mapa")
        void testNoMoveOutsideMap() throws IceException {
            // Arrange
            IceCream player = new IceCream(0, 5, IceCreamFlavor.VANILLA);
            game.addPlayer(player);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.LEFT);
            
            // Assert - no se movió
            assertEquals(0, player.getX());
        }
    }

    @Nested
    @DisplayName("Colisiones")
    class CollisionTests {

        @Test
        @DisplayName("CO-01: Detectar colisión monstruo-jugador")
        void testMonsterPlayerCollision() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Troll troll = new Troll(5, 5);
            game.addPlayer(player);
            game.addMonster(troll);
            game.start();
            
            // Act
            game.checkCollisions();
            
            // Assert - jugador pierde una vida
            assertEquals(2, player.getLives());
        }

        @Test
        @DisplayName("CO-02: Respawn de jugador después de colisión")
        void testPlayerRespawnAfterCollision() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Troll troll = new Troll(5, 5);
            game.addPlayer(player);
            game.addMonster(troll);
            game.start();
            player.move(Direction.RIGHT); // posición 6,5
            
            // Act
            game.handlePlayerHit(player);
            
            // Assert - respawneó en posición inicial
            assertEquals(5, player.getX());
            assertEquals(5, player.getY());
        }
    }

    @Nested
    @DisplayName("Sistema de eventos")
    class EventSystemTests {

        @Test
        @DisplayName("EV-01: Registrar listener de eventos")
        void testAddEventListener() {
            // Arrange
            TestEventListener listener = new TestEventListener();
            
            // Act
            game.addEventListener(listener);
            
            // Assert - no lanza excepción
            assertDoesNotThrow(() -> game.fireEvent(EventType.GAME_STARTED, null));
        }

        @Test
        @DisplayName("EV-02: Evento GAME_STARTED al iniciar")
        void testGameStartedEvent() throws IceException {
            // Arrange
            TestEventListener listener = new TestEventListener();
            game.addEventListener(listener);
            game.addPlayer(new IceCream(5, 5, IceCreamFlavor.VANILLA));
            
            // Act
            game.start();
            
            // Assert
            assertTrue(listener.receivedEvents.stream()
                    .anyMatch(e -> e.getType() == EventType.GAME_STARTED));
        }

        @Test
        @DisplayName("EV-03: Evento PLAYER_MOVED al mover")
        void testPlayerMovedEvent() throws IceException {
            // Arrange
            TestEventListener listener = new TestEventListener();
            game.addEventListener(listener);
            game.addPlayer(new IceCream(5, 5, IceCreamFlavor.VANILLA));
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            assertTrue(listener.receivedEvents.stream()
                    .anyMatch(e -> e.getType() == EventType.PLAYER_MOVED));
        }

        @Test
        @DisplayName("EV-04: Evento FRUIT_COLLECTED al recolectar")
        void testFruitCollectedEvent() throws IceException {
            // Arrange
            TestEventListener listener = new TestEventListener();
            game.addEventListener(listener);
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Grape grape = new Grape(6, 5);
            game.addPlayer(player);
            game.addFruit(grape);
            game.start();
            
            // Act
            game.movePlayer(0, Direction.RIGHT);
            
            // Assert
            assertTrue(listener.receivedEvents.stream()
                    .anyMatch(e -> e.getType() == EventType.FRUIT_COLLECTED));
        }

        @Test
        @DisplayName("EV-05: Evento GAME_WON al ganar")
        void testGameWonEvent() throws IceException {
            // Arrange
            TestEventListener listener = new TestEventListener();
            game.addEventListener(listener);
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Grape grape = new Grape(5, 5);
            game.addPlayer(player);
            game.addFruit(grape);
            game.start();
            
            // Act
            grape.collect();
            game.checkVictoryCondition();
            
            // Assert
            assertTrue(listener.receivedEvents.stream()
                    .anyMatch(e -> e.getType() == EventType.GAME_WON));
        }

        @Test
        @DisplayName("EV-06: Evento GAME_LOST al perder")
        void testGameLostEvent() throws IceException {
            // Arrange
            TestEventListener listener = new TestEventListener();
            game.addEventListener(listener);
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            game.addPlayer(player);
            game.start();
            
            // Act
            player.decreaseLives();
            player.decreaseLives();
            player.decreaseLives();
            game.checkDefeatCondition();
            
            // Assert
            assertTrue(listener.receivedEvents.stream()
                    .anyMatch(e -> e.getType() == EventType.GAME_LOST));
        }

        @Test
        @DisplayName("EV-07: Remover listener de eventos")
        void testRemoveEventListener() throws IceException {
            // Arrange
            TestEventListener listener = new TestEventListener();
            game.addEventListener(listener);
            game.addPlayer(new IceCream(5, 5, IceCreamFlavor.VANILLA));
            
            // Act
            game.removeEventListener(listener);
            game.start();
            
            // Assert - no recibió eventos
            assertTrue(listener.receivedEvents.isEmpty());
        }
    }

    @Nested
    @DisplayName("Actualización del juego")
    class GameUpdateTests {

        @Test
        @DisplayName("UP-01: Update mueve monstruos")
        void testUpdateMovesMonsters() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Troll troll = new Troll(10, 10);
            game.addPlayer(player);
            game.addMonster(troll);
            game.start();
            
            int startX = troll.getX();
            
            // Act
            game.update();
            
            // Assert - troll se movió según su patrón
            assertNotEquals(startX, troll.getX());
        }

        @Test
        @DisplayName("UP-02: Update no ejecuta en PAUSED")
        void testUpdateNotExecuteWhenPaused() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Troll troll = new Troll(10, 10);
            game.addPlayer(player);
            game.addMonster(troll);
            game.start();
            game.pause();
            
            int startX = troll.getX();
            
            // Act
            game.update();
            
            // Assert - troll no se movió
            assertEquals(startX, troll.getX());
        }

        @Test
        @DisplayName("UP-03: Update verifica colisiones")
        void testUpdateChecksCollisions() throws IceException {
            // Arrange
            IceCream player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
            Troll troll = new Troll(5, 5);
            Grape grape = new Grape(10, 10); // Fruta lejos para evitar victoria
            game.addPlayer(player);
            game.addMonster(troll);
            game.addFruit(grape);
            game.start();
            
            int initialLives = player.getLives();
            
            // Act
            game.update();
            
            // Assert - jugador perdió vida
            assertTrue(player.getLives() < initialLives);
        }
    }

    @Nested
    @DisplayName("Gestión del mapa")
    class MapManagementTests {

        @Test
        @DisplayName("MP-01: Colocar muro en el mapa")
        void testPlaceWall() {
            // Arrange
            IndestructibleWall wall = new IndestructibleWall(5, 5);
            
            // Act
            game.placeWall(wall);
            
            // Assert
            assertNotNull(game.getWallAt(5, 5));
        }

        @Test
        @DisplayName("MP-02: Obtener celda del mapa")
        void testGetCell() {
            // Assert
            assertNotNull(game.getCell(5, 5));
        }

        @Test
        @DisplayName("MP-03: Verificar si posición es válida")
        void testIsValidPosition() {
            assertTrue(game.isValidPosition(5, 5));
            assertTrue(game.isValidPosition(0, 0));
            assertTrue(game.isValidPosition(19, 14));
            assertFalse(game.isValidPosition(-1, 5));
            assertFalse(game.isValidPosition(20, 5));
            assertFalse(game.isValidPosition(5, 15));
        }

        @Test
        @DisplayName("MP-04: Verificar si posición está bloqueada")
        void testIsBlocked() {
            // Arrange
            IndestructibleWall wall = new IndestructibleWall(5, 5);
            game.placeWall(wall);
            
            // Assert
            assertTrue(game.isBlocked(5, 5));
            assertFalse(game.isBlocked(6, 5));
        }
    }

    /**
     * Listener de prueba para capturar eventos.
     */
    private static class TestEventListener implements GameEventListener {
        List<GameEvent> receivedEvents = new ArrayList<>();

        @Override
        public void onEvent(GameEvent event) {
            receivedEvents.add(event);
        }
    }
}
