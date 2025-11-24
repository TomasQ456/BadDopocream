package domain;

import domain.events.GameEvent;
import domain.events.GameEventListener;

/**
 * Base element for all map actors. Provides shared identity, sizing, and update hooks that
 * specialized entities override.
 */
public abstract class Entity implements GameEventListener {

    protected final String id;
    protected Vector2 position;
    protected final int width;
    protected final int height;

    /**
     * @param id       unique identifier used in events and persistence.
     * @param position starting coordinates.
     * @param width    width measured in tiles.
     * @param height   height measured in tiles.
     */
    protected Entity(String id, Vector2 position, int width, int height) {
        this.id = id;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * @return unique identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * @return current position.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Updates the entity's position without performing validation. Only {@link DopoIceCream}
     * should invoke this method as part of authoritative moves.
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Called each tick to advance the entity's internal state.
     *
     * @throws IceException when the entity violates a movement rule during its update.
     */
    public abstract void update() throws IceException;

    @Override
    /**
     * Default listener implementation ignored by most entities.
     */
    public void onEvent(GameEvent event) {
        // default: no-op
    }
}
