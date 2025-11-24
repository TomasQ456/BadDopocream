package test.domain;

import domain.DopoIceCream;
import domain.IceException;
import domain.enemies.Monster;
import domain.enums.Direction;
import domain.enums.GameEventType;
import domain.enums.MonsterType;
import domain.Vector2;
import memory.LevelDescriptor;
import memory.LevelRepository;
import memory.GameState;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Monster behavior validations.
 */
class MonsterTest {

    private DopoIceCream game;

    /**
     * Loads a small board with a single patrolling monster for each scenario.
     */
    @BeforeEach
    void setUp() throws IceException {
        LevelDescriptor descriptor = LevelDescriptor.builder()
                .levelId(7)
                .dimensions(5, 5)
                .playerSpawn(new Vector2(2, 2))
                .addEnemy(LevelDescriptor.EnemyDescriptor.patrol(MonsterType.TROLL, new Vector2(1, 1), List.of(Direction.RIGHT, Direction.DOWN)))
                .build();
        game = new DopoIceCream(5, 5, new MonsterRepository(Map.of(7, descriptor)));
        game.loadLevel(7);
    }

    /**
     * Validates that tick-driven movement advances the monster along its path.
     */
    @Test
    void update_monsterFollowsPatrolPattern() throws IceException {
        Monster monster = game.getEnemies().get(0);
        Vector2 original = monster.getPosition();

        game.tick();

        assertNotEquals(original, monster.getPosition());
    }

    /**
     * Ensures overlapping the monster with the player produces a PLAYER_HIT event.
     */
    @Test
    void collisionWithPlayer_publishesPlayerHit() throws IceException {
        Monster monster = game.getEnemies().get(0);
        AtomicInteger hits = new AtomicInteger();
        game.registerListener(GameEventType.PLAYER_HIT, event -> hits.incrementAndGet());

        monster.setPosition(game.getPlayer().getPosition());
        game.resolveCollisions();

        assertEquals(1, hits.get());
    }

    private static final class MonsterRepository implements LevelRepository {
        private final Map<Integer, LevelDescriptor> levels;

        private MonsterRepository(Map<Integer, LevelDescriptor> levels) {
            this.levels = levels;
        }

        @Override
        public LevelDescriptor loadLevel(int id) throws IceException {
            LevelDescriptor descriptor = levels.get(id);
            if (descriptor == null) {
                throw new IceException("Missing level");
            }
            return descriptor;
        }

        @Override
        public void saveGameState(GameState state) {
        }

        @Override
        public GameState loadGameState(String path) throws IceException {
            throw new IceException("unsupported");
        }
    }
}
