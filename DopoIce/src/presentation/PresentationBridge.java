package presentation;

import domain.DopoIceCream;
import domain.IceException;
import domain.Player;
import domain.Vector2;
import domain.enums.Direction;
import domain.events.GameEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Sole adapter between presentation and domain layers. Translates user inputs into domain commands
 * and relays event-driven updates back to UI listeners.
 */
public class PresentationBridge {

    private final DopoIceCream game;
    private final Renderer renderer;
    private final List<UIListener> uiListeners = new CopyOnWriteArrayList<>();

    /**
     * @param game      domain entry point.
     * @param renderer  rendering abstraction to notify after state changes.
     */
    public PresentationBridge(DopoIceCream game, Renderer renderer) {
        this.game = game;
        this.renderer = renderer;
        this.game.registerListener(this::notifyListeners);
    }

    /**
     * Executes one iteration of the game loop consisting of a domain tick followed by a render call.
     */
    public void startLoop() throws IceException {
        game.tick();
        renderer.renderSnapshot(buildViewModel());
    }

    /**
     * Routes an {@link InputCommand} to the appropriate domain method and triggers rendering afterward.
     */
    public void onUserInput(InputCommand command) throws IceException {
        Player player = game.getPlayer();
        switch (command.getType()) {
            case MOVE -> player.move(command.getDirection());
            case CREATE_ICE -> player.createIce();
            case DESTROY_ICE -> player.destroyIce(command.getDirection() == null ? Direction.UP : command.getDirection());
            case SAVE -> game.saveGame(command.getArgument());
            case LOAD -> game.loadGame(command.getArgument());
            default -> throw new IllegalStateException("Unsupported command: " + command.getType());
        }
        renderer.renderSnapshot(buildViewModel());
    }

    /**
     * Registers a UI listener interested in receiving event/view model pairs.
     */
    public void addUIListener(UIListener listener) {
        uiListeners.add(listener);
    }

    /**
     * Forwards domain events to registered presentation listeners with a fresh snapshot.
     */
    private void notifyListeners(GameEvent event) {
        ViewModel viewModel = buildViewModelSafely();
        for (UIListener listener : uiListeners) {
            listener.onEvent(event, viewModel);
        }
    }

    /**
     * Builds a view model while converting any {@link IceException} to unchecked exceptions for UI layers.
     */
    private ViewModel buildViewModelSafely() {
        try {
            return buildViewModel();
        } catch (IceException e) {
            throw new IllegalStateException("Unable to build view model", e);
        }
    }

    /**
     * Creates the minimal state required by the renderer/UI.
     */
    private ViewModel buildViewModel() throws IceException {
        Player player = game.getPlayer();
        if (player == null) {
            return new ViewModel(new Vector2(0, 0), 0, game.countRemainingFruits());
        }
        int fruits = game.countRemainingFruits();
        return new ViewModel(player.getPosition(), player.getScore(), fruits);
    }

    /**
     * Lightweight view representation consumed by Renderer/UI listeners.
     */
    public static final class ViewModel {
        private final Vector2 playerPosition;
        private final int score;
        private final int remainingFruits;

        /**
         * @param playerPosition focus position for the player sprite.
         * @param score          latest score.
         * @param remainingFruits remaining fruit count that drives level completion UI.
         */
        public ViewModel(Vector2 playerPosition, int score, int remainingFruits) {
            this.playerPosition = playerPosition;
            this.score = score;
            this.remainingFruits = remainingFruits;
        }

        /**
         * @return player position snapshot.
         */
        public Vector2 getPlayerPosition() {
            return playerPosition;
        }

        /**
         * @return current score.
         */
        public int getScore() {
            return score;
        }

        /**
         * @return remaining fruit count.
         */
        public int getRemainingFruits() {
            return remainingFruits;
        }
    }
}
