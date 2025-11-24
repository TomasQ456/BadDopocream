package domain;

import domain.events.EventDispatcher;

/**
 * Static map objects such as walls, fruits, and ice blocks.
 */
public abstract class GameObject extends Entity {

    protected final boolean solid;
    protected final String spriteId;
    protected final EventDispatcher dispatcher;

    /**
     * @param id         unique identifier.
     * @param position   fixed grid coordinates.
     * @param solid      whether the object blocks movement.
     * @param spriteId   presentation hint for rendering.
     * @param dispatcher dispatcher used to publish events (may be {@code null} in tests).
     */
    protected GameObject(String id, Vector2 position, boolean solid, String spriteId, EventDispatcher dispatcher) {
        super(id, position, 1, 1);
        this.solid = solid;
        this.spriteId = spriteId;
        this.dispatcher = dispatcher;
    }

    /**
     * Convenience constructor for objects that do not need to publish events.
     */
    protected GameObject(String id, Vector2 position, boolean solid, String spriteId) {
        this(id, position, solid, spriteId, null);
    }

    /**
     * @return whether the object blocks entity movement.
     */
    public boolean isSolid() {
        return solid;
    }

    /**
     * @return sprite identifier consumed by the presentation layer.
     */
    public String getSpriteId() {
        return spriteId;
    }

    @Override
    /**
     * Default update no-op for static objects. Subclasses override if animation or timers are required.
     */
    public void update() {
        // Most static objects have no update loop.
    }
}
