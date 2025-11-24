package domain;

import domain.enums.GameEventType;
import domain.enums.PlayerType;
import domain.enums.Direction;
import domain.events.EventDispatcher;
import domain.events.GameEvent;
import interfaces.Movable;

import java.util.Map;

/**
 * Player avatar able to move, create ice blocks, and collect fruits.
 */
public class Player extends Entity implements Movable {

    private int lives = 3;
    private int score;
    private Direction facing = Direction.UP;
    private boolean canCreateIce = true;
    private final PlayerType type = PlayerType.SOLO;
    private DopoIceCream game;
    private final EventDispatcher dispatcher;

    /**
     * @param id         unique identifier for event payloads.
     * @param startPos   initial spawn location.
     * @param game       owning game instance (may be bound later).
     * @param dispatcher dispatcher for event publication.
     */
    public Player(String id, Vector2 startPos, DopoIceCream game, EventDispatcher dispatcher) {
        super(id, startPos, 1, 1);
        this.game = game;
        this.dispatcher = dispatcher;
    }

    /**
     * Rebinds the player to the active {@link DopoIceCream} session (useful when reloading saves).
     */
    public void bindGame(DopoIceCream game) {
        this.game = game;
    }

    @Override
    /**
     * Moves the player in the provided direction using {@link DopoIceCream#moveEntity(Entity, Vector2)}.
     *
     * @throws IceException when movement violates map constraints.
     */
    public void move(Direction direction) throws IceException {
        if (game == null) {
            throw new IceException("Player not bound to game session");
        }
        this.facing = direction;
        Vector2 destination = position.plus(direction);
        game.moveEntity(this, destination);
    }

    /**
     * Creates an ice block in front of the player's facing direction.
     *
     * @throws IceException if the target cell is invalid or occupied.
     */
    public void createIce() throws IceException {
        if (!canCreateIce) {
            return;
        }
        if (game == null) {
            throw new IceException("Player not bound to game session");
        }
        Vector2 target = position.plus(facing);
        game.createIceAt(target, this);
        if (dispatcher != null) {
            dispatcher.dispatch(new GameEvent(GameEventType.BLOCK_CREATED, this, Map.of("x", target.getX(), "y", target.getY())));
        }
    }

    /**
     * Attempts to destroy an ice block relative to the provided direction.
     *
     * @throws IceException when the position is invalid.
     */
    public void destroyIce(Direction direction) throws IceException {
        if (game == null) {
            throw new IceException("Player not bound to game session");
        }
        Vector2 target = position.plus(direction);
        game.destroyIceAt(target);
    }

    /**
     * Adds score to the player.
     */
    public void addScore(int points) {
        score += points;
    }

    /**
     * @return accumulated score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Deducts a life, publishing PLAYER_HIT and PLAYER_DIED when appropriate.
     */
    public void loseLife() {
        lives--;
        if (dispatcher != null) {
            dispatcher.dispatch(new GameEvent(GameEventType.PLAYER_HIT, this, Map.of("remainingLives", lives)));
            if (lives <= 0) {
                dispatcher.dispatch(new GameEvent(GameEventType.PLAYER_DIED, this, Map.of()));
            }
        }
    }

    /**
     * @return remaining lives.
     */
    public int getLives() {
        return lives;
    }

    /**
     * @return current facing direction.
     */
    public Direction getFacing() {
        return facing;
    }

    /**
     * Sets the direction the player is currently facing (used for ice creation).
     */
    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    /**
     * @return player archetype.
     */
    public PlayerType getType() {
        return type;
    }

    @Override
    /**
     * Handles cooldown logic; currently a placeholder for future behavior.
     */
    public void update() {
        // Placeholder for cooldown logic.
    }
}
