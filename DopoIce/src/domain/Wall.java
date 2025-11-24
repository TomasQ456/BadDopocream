package domain;

import domain.enums.GameEventType;
import domain.events.EventDispatcher;
import domain.events.GameEvent;
import interfaces.Destructible;

import java.util.Map;

/**
 * Basic wall that can optionally be destroyed.
 */
public class Wall extends GameObject implements Destructible {

    private final boolean breakable;
    private int hitPoints;
    private boolean destroyed;

    /**
     * @param id         unique identifier.
     * @param position   tile coordinates.
     * @param breakable  whether the wall can be destroyed via {@link #breakBlock()}.
     * @param hitPoints  number of hits required to destroy (ignored when unbreakable).
     * @param dispatcher dispatcher for BLOCK_DESTROYED.
     */
    public Wall(String id, Vector2 position, boolean breakable, int hitPoints, EventDispatcher dispatcher) {
        super(id, position, true, "wall", dispatcher);
        this.breakable = breakable;
        this.hitPoints = hitPoints;
    }

    /**
     * @return whether the instance can be destroyed.
     */
    public boolean isBreakable() {
        return breakable;
    }

    @Override
    /**
     * Applies one hit of damage and publishes BLOCK_DESTROYED when the wall crumbles.
     */
    public void breakBlock() {
        if (!breakable || destroyed) {
            return;
        }
        hitPoints--;
        if (hitPoints <= 0) {
            destroyed = true;
            if (dispatcher != null) {
                dispatcher.dispatch(new GameEvent(GameEventType.BLOCK_DESTROYED, this, Map.of("id", id)));
            }
        }
    }

    @Override
    /**
     * @return {@code true} once the wall has been destroyed.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
