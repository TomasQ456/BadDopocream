package test.domain;

import domain.DopoIceCream;
import domain.IceBlock;
import domain.IceException;
import domain.Player;
import domain.Vector2;
import domain.enums.Direction;
import domain.enums.FruitType;
import domain.enums.GameEventType;
import domain.events.EventDispatcher;
import memory.LevelDescriptor;
import memory.LevelRepository;
import memory.GameState;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Player interaction tests focusing on movement, fruit gathering, and ice manipulation.
 */
class PlayerTest {

    private DopoIceCream game;

    /**
     * Initializes a small arena with a collectible fruit for each scenario.
     */
    @BeforeEach
    void setUp() throws IceException {
        LevelDescriptor descriptor = LevelDescriptor.builder()
                .levelId(42)
                .dimensions(4, 4)
                .playerSpawn(new Vector2(1, 1))
                .addFruit(LevelDescriptor.FruitDescriptor.of(FruitType.GRAPE, new Vector2(2, 1), 100))
                .addStaticObject(LevelDescriptor.StaticObjectDescriptor.indestructibleWall(new Vector2(0, 0)))
                .build();
        game = new DopoIceCream(4, 4, new PlayerTestRepository(Map.of(42, descriptor)));
        game.loadLevel(42);
    }

    /**
     * Confirms that moving into an empty cell updates coordinates and emits PLAYER_MOVED.
     */
    @Test
    void move_intoEmptyCell_updatesPositionAndPublishesEvent() throws IceException {
        Player player = game.getPlayer();
        AtomicInteger moveEvents = new AtomicInteger();
        game.registerListener(GameEventType.PLAYER_MOVED, event -> moveEvents.incrementAndGet());

        player.move(Direction.RIGHT);

        assertEquals(new Vector2(2, 1), player.getPosition());
        assertEquals(1, moveEvents.get());
    }

    /**
     * Verifies that stepping onto a fruit awards points and decrements the remaining count.
     */
    @Test
    void move_intoFruit_collectsFruitAndIncreasesScore() throws IceException {
        Player player = game.getPlayer();
        player.move(Direction.RIGHT);

        assertEquals(100, player.getScore());
        assertEquals(0, game.countRemainingFruits());
    }

    /**
     * Ensures createIce drops an IceBlock ahead of the player and raises ICE_CREATED.
     */
    @Test
    void createIce_placesIceBlockOnFacingCell() throws IceException {
        Player player = game.getPlayer();
        player.setFacing(Direction.UP);
        AtomicInteger iceCreatedEvents = new AtomicInteger();
        game.registerListener(GameEventType.ICE_CREATED, event -> iceCreatedEvents.incrementAndGet());

        player.createIce();

        assertTrue(game.getCell(1, 0).getStaticObject() instanceof IceBlock);
        assertEquals(1, iceCreatedEvents.get());
    }

    private static final class PlayerTestRepository implements LevelRepository {
        private final Map<Integer, LevelDescriptor> levels;

        private PlayerTestRepository(Map<Integer, LevelDescriptor> levels) {
            this.levels = levels;
        }

        @Override
        public LevelDescriptor loadLevel(int id) throws IceException {
            LevelDescriptor descriptor = levels.get(id);
            if (descriptor == null) {
                throw new IceException("Missing level " + id);
            }
            return descriptor;
        }

        @Override
        public void saveGameState(GameState state) {
            // no-op for tests
        }

        @Override
        public GameState loadGameState(String path) throws IceException {
            throw new IceException("unsupported");
        }
    }
}
