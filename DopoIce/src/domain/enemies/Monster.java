package domain.enemies;

import domain.DopoIceCream;
import domain.Entity;
import domain.IceException;
import domain.MovementPattern;
import domain.Player;
import domain.Vector2;
import domain.enums.Direction;
import domain.enums.GameEventType;
import domain.enums.MonsterType;
import domain.events.EventDispatcher;
import domain.events.GameEvent;
import interfaces.AIControlled;
import interfaces.Movable;

import java.util.Map;
import java.util.Objects;

/**
 * Base enemy controller capable of following a {@link MovementPattern}.
 */
public class Monster extends Entity implements Movable, AIControlled {

    private final MonsterType type;
    private MovementPattern pattern;
    private final DopoIceCream game;
    private final EventDispatcher dispatcher;
    private Direction facing = Direction.UP;

    /**
     * @param id         unique identifier.
     * @param type       monster archetype.
     * @param position   spawn coordinates.
     * @param pattern    AI movement pattern.
     * @param game       game orchestrator responsible for authoritative movement.
     * @param dispatcher dispatcher used to publish collision events.
     */
    public Monster(String id, MonsterType type, Vector2 position, MovementPattern pattern,
                   DopoIceCream game, EventDispatcher dispatcher) {
        super(id, position, 1, 1);
        this.type = type;
        this.pattern = Objects.requireNonNull(pattern);
        this.game = game;
        this.dispatcher = dispatcher;
    }

    @Override
    /**
     * Executes the movement pattern each tick.
     */
    public void update() throws IceException {
        Direction next = decideNextMove();
        move(next);
    }

    @Override
    /**
     * Moves the monster in the specified direction via {@link DopoIceCream#moveEntity(Entity, Vector2)}.
     */
    public void move(Direction direction) throws IceException {
        this.facing = direction;
        Vector2 destination = position.plus(direction);
        game.moveEntity(this, destination);
    }

    @Override
    /**
     * Asks the movement pattern for the next direction.
     */
    public Direction decideNextMove() {
        return pattern.nextDirection(this, game);
    }

    /**
     * Replaces the active movement pattern.
     */
    public void setMovementPattern(MovementPattern pattern) {
        this.pattern = pattern;
    }

    /**
     * @return monster archetype.
     */
    public MonsterType getType() {
        return type;
    }

    /**
     * @return last direction the monster moved.
     */
    public Direction getFacing() {
        return facing;
    }

    /**
     * Handles a collision with the player, emitting a COLLISION event and damaging the player.
     */
    public void collideWithPlayer(Player player) {
        if (dispatcher != null) {
            dispatcher.dispatch(new GameEvent(GameEventType.COLLISION, this,
                Map.of("enemyId", id, "playerId", player.getId())));
        }
        player.loseLife();
    }
}
