package test.presentation;

import domain.DopoIceCream;
import domain.IceException;
import domain.Vector2;
import domain.enums.Direction;
import domain.enums.FruitType;
import memory.LevelDescriptor;
import memory.LevelRepository;
import memory.GameState;
import presentation.InputCommand;
import presentation.InputCommand.CommandType;
import presentation.PresentationBridge;
import presentation.Renderer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Presentation bridge tests covering command forwarding and view refreshes.
 */
class PresentationBridgeTest {

    private DopoIceCream game;
    private PresentationBridge bridge;
    private RecordingRenderer renderer;

    /**
     * Prepares a simple game state and renderer spy for each scenario.
     */
    @BeforeEach
    void setUp() throws IceException {
        LevelDescriptor descriptor = LevelDescriptor.builder()
                .levelId(5)
                .dimensions(3, 3)
                .playerSpawn(new Vector2(1, 1))
                .addFruit(LevelDescriptor.FruitDescriptor.of(FruitType.CHERRY, new Vector2(2, 1), 50))
                .build();
        game = new DopoIceCream(3, 3, new BridgeRepository(Map.of(5, descriptor)));
        game.loadLevel(5);
        renderer = new RecordingRenderer();
        bridge = new PresentationBridge(game, renderer);
    }

    /**
     * Ensures MOVE commands trigger the domain and cause a rendering pass.
     */
    @Test
    void onUserInput_moveCommand_delegatesToDomain() throws IceException {
        InputCommand moveRight = InputCommand.move(Direction.RIGHT);
        bridge.onUserInput(moveRight);

        assertEquals(new Vector2(2, 1), game.getPlayer().getPosition());
        assertEquals(1, renderer.renderCount.get());
    }

    private static final class RecordingRenderer implements Renderer {
        private final AtomicInteger renderCount = new AtomicInteger();

        @Override
        public void renderSnapshot(PresentationBridge.ViewModel viewModel) {
            renderCount.incrementAndGet();
        }
    }

    private static final class BridgeRepository implements LevelRepository {
        private final Map<Integer, LevelDescriptor> levels;

        private BridgeRepository(Map<Integer, LevelDescriptor> levels) {
            this.levels = levels;
        }

        @Override
        public LevelDescriptor loadLevel(int id) throws IceException {
            LevelDescriptor descriptor = levels.get(id);
            if (descriptor == null) {
                throw new IceException("missing level");
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
