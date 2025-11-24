package domain;

import domain.enums.ErrorCode;
import domain.enums.GameEventType;
import domain.events.EventDispatcher;
import domain.events.GameEvent;

import java.util.Map;

/**
 * Represents a single tile on the playfield capable of hosting one static object and one dynamic entity.
 * The class acts as the authoritative source for collision restrictions and raises {@link IceException}
 * whenever a placement request violates the design contract.
 */
public class Cell {

    private final int x;
    private final int y;
    private final EventDispatcher dispatcher;
    private GameObject staticObject;
    private Entity dynamicObject;

    /**
     * Creates a new cell at the provided grid coordinates.
     *
     * @param x the horizontal coordinate.
     * @param y the vertical coordinate.
     * @param dispatcher dispatcher used to publish {@link GameEventType#CELL_UPDATED} notifications.
     */
    public Cell(int x, int y, EventDispatcher dispatcher) {
        this.x = x;
        this.y = y;
        this.dispatcher = dispatcher;
    }

    /**
     * @return the static object currently occupying the tile, or {@code null} when empty.
     */
    public GameObject getStaticObject() {
        return staticObject;
    }

    /**
     * @return the dynamic entity currently standing on the tile, or {@code null} when empty.
     */
    public Entity getDynamicObject() {
        return dynamicObject;
    }

    /**
     * Assigns a static object to this tile while ensuring no other static instance is already set.
     *
     * @param obj the new static occupant; may be {@code null} to clear the cell.
     * @throws IceException when the cell already contains a different static object.
     */
    public void setStaticObject(GameObject obj) throws IceException {
        if (staticObject != null && obj != null && staticObject != obj) {
            throw new IceException("Cell already contains a static object", ErrorCode.CELL_BLOCKED);
        }
        this.staticObject = obj;
        emitCellEvent();
    }

    /**
     * Assigns a dynamic entity to the tile. The request is rejected if another entity is already present.
     *
     * @param entity the entity that should stand on the tile; may be {@code null} to clear it.
     * @throws IceException when another entity already resides in the cell.
     */
    public void setDynamicObject(Entity entity) throws IceException {
        if (dynamicObject != null && entity != null && dynamicObject != entity) {
            throw new IceException("Cell already contains a dynamic entity", ErrorCode.CELL_OCCUPIED);
        }
        this.dynamicObject = entity;
        emitCellEvent();
    }

    /**
     * @return {@code true} when the cell contains no dynamic entity and the static occupant (if any)
     * is not solid.
     */
    public boolean isFreeForDynamic() {
        return dynamicObject == null && (staticObject == null || !staticObject.isSolid());
    }

    /**
     * @return the x coordinate of the cell.
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate of the cell.
     */
    public int getY() {
        return y;
    }

    /**
     * Publishes CELL_UPDATED and MAP_UPDATED events when a structural change occurs.
     */
    private void emitCellEvent() {
        if (dispatcher == null) {
            return;
        }
        Map<String, Object> payload = Map.of("x", x, "y", y);
        dispatcher.dispatch(new GameEvent(GameEventType.CELL_UPDATED, this, payload));
        dispatcher.dispatch(new GameEvent(GameEventType.MAP_UPDATED, this, payload));
    }
}
