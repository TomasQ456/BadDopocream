package test.domain;

import domain.DopoIceCream;
import domain.IceException;
import domain.Player;
import domain.Vector2;
import domain.enums.Direction;
import domain.enums.FruitType;
import domain.enums.GameEventType;
import memory.GameState;
import memory.LevelDescriptor;
import memory.LevelRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DopoIceCream aggregate that cover level loading, cell access,
 * fruit tracking, and event publication requirements.
 */
class DopoIceCreamTest {

    private LevelDescriptor descriptor;
    private StubLevelRepository repository;

    /**
     * Builds a reusable level descriptor consumed by each scenario.
     */
    @BeforeEach
    void setUp() {
        descriptor = LevelDescriptor.builder()
                .levelId(1)
                .dimensions(3, 4)
                .playerSpawn(new Vector2(1, 1))
                .addStaticObject(LevelDescriptor.StaticObjectDescriptor.indestructibleWall(new Vector2(0, 0)))
                .addStaticObject(LevelDescriptor.StaticObjectDescriptor.breakableWall(new Vector2(2, 0), 1))
                .addFruit(LevelDescriptor.FruitDescriptor.of(FruitType.GRAPE, new Vector2(1, 2), 100))
                .build();
        repository = new StubLevelRepository(Map.of(1, descriptor));
    }

    /**
     * Verifies that a successful level load positions the player and emits LEVEL_LOADED.
     */
    @Test
    void loadLevel_validLevel_initializesMapAndPlayer() throws IceException {
        DopoIceCream game = new DopoIceCream(3, 4, repository);
        AtomicBoolean levelLoaded = new AtomicBoolean(false);
        game.registerListener(GameEventType.LEVEL_LOADED, event -> levelLoaded.set(true));

        game.loadLevel(1);

        assertTrue(levelLoaded.get(), "LEVEL_LOADED event should fire");
        assertNotNull(game.getPlayer(), "Player must be instantiated after level load");
        assertEquals(new Vector2(1, 1), game.getPlayer().getPosition(), "Player spawn mismatch");
        assertEquals(1, game.countRemainingFruits(), "Fruit count should match descriptor");
    }

    /**
     * Ensures lookups beyond the board bounds raise an IceException.
     */
    @Test
    void getCell_outOfBounds_throwsIceException() throws IceException {
        DopoIceCream game = new DopoIceCream(3, 4, repository);
        game.loadLevel(1);

        assertThrows(IceException.class, () -> game.getCell(-1, 0));
        assertThrows(IceException.class, () -> game.getCell(3, 3));
    }

    /**
     * Confirms that collecting all fruits decrements the counter and triggers LEVEL_COMPLETED.
     */
    @Test
    void countRemainingFruits_afterCollecting_zeroWhenAllCollected() throws IceException {
        DopoIceCream game = new DopoIceCream(3, 4, repository);
        AtomicBoolean levelCompleted = new AtomicBoolean(false);
        game.registerListener(GameEventType.LEVEL_COMPLETED, e -> levelCompleted.set(true));

        game.loadLevel(1);
        Player player = game.getPlayer();
        assertNotNull(player);

        player.move(Direction.DOWN);

        assertEquals(0, game.countRemainingFruits());
        assertTrue(levelCompleted.get(), "Collecting all fruits should publish LEVEL_COMPLETED");
    }

    private static final class StubLevelRepository implements LevelRepository {
        private final Map<Integer, LevelDescriptor> levels;

        private StubLevelRepository(Map<Integer, LevelDescriptor> levels) {
            this.levels = levels;
        }

        @Override
        public LevelDescriptor loadLevel(int id) throws IceException {
            LevelDescriptor descriptor = levels.get(id);
            if (descriptor == null) {
                throw new IceException("Level not found: " + id);
            }
            return descriptor;
        }

        @Override
        public void saveGameState(GameState state) throws IceException {
            // No-op for tests; verifying integration happens elsewhere
        }

        @Override
        public GameState loadGameState(String path) throws IceException {
            throw new IceException("Not supported in stub");
        }
    }
}
