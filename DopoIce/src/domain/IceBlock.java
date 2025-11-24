package domain;

import domain.enums.GameEventType;
import domain.events.EventDispatcher;
import domain.events.GameEvent;
import interfaces.Destructible;

import java.util.Map;

/**
 * Temporary block created by the player.
 */
public class IceBlock extends GameObject implements Destructible {

    private int lifetimeTicks;
    private boolean destroyed;

    /**
     * @param id            unique identifier.
     * @param position      tile coordinates.
     * @param lifetimeTicks number of ticks before the block melts automatically.
     * @param dispatcher    dispatcher used for ICE_DESTROYED/BLOCK_DESTROYED.
     */
    public IceBlock(String id, Vector2 position, int lifetimeTicks, EventDispatcher dispatcher) {
        super(id, position, true, "ice-block", dispatcher);
        this.lifetimeTicks = lifetimeTicks;
    }

    @Override
    /**
     * Decrements the lifetime counter and emits ICE_DESTROYED when it expires.
     */
    public void update() {
        if (destroyed || lifetimeTicks <= 0) {
            return;
        }
        lifetimeTicks--;
        if (lifetimeTicks == 0) {
            destroyed = true;
            if (dispatcher != null) {
                dispatcher.dispatch(new GameEvent(GameEventType.ICE_DESTROYED, this, Map.of("id", id)));
            }
        }
    }

    @Override
    /**
     * Forces the block to shatter immediately, emitting ICE_DESTROYED and BLOCK_DESTROYED events.
     */
    public void breakBlock() {
        if (destroyed) {
            return;
        }
        destroyed = true;
        if (dispatcher != null) {
            dispatcher.dispatch(new GameEvent(GameEventType.ICE_DESTROYED, this, Map.of("id", id)));
            dispatcher.dispatch(new GameEvent(GameEventType.BLOCK_DESTROYED, this, Map.of("id", id)));
        }
    }

    @Override
    /**
     * @return {@code true} when the block has been destroyed or melted.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
