package domain;

import domain.enums.GameEventType;
import domain.enums.FruitType;
import domain.events.EventDispatcher;
import domain.events.GameEvent;
import interfaces.Collectible;

import java.util.Map;

/**
 * Collectible fruit items required for level completion.
 */
public class Fruit extends GameObject implements Collectible {

    private final FruitType type;
    private final int points;
    private boolean collected;

    /**
     * @param id        unique identifier.
     * @param type      fruit variety.
     * @param points    score awarded when collected.
     * @param position  grid position.
     * @param dispatcher event dispatcher for FRUIT_COLLECTED.
     */
    public Fruit(String id, FruitType type, int points, Vector2 position, EventDispatcher dispatcher) {
        super(id, position, false, "fruit:" + type.name().toLowerCase(), dispatcher);
        this.type = type;
        this.points = points;
    }

    /**
     * @return fruit type metadata.
     */
    public FruitType getType() {
        return type;
    }

    @Override
    /**
     * Marks the fruit as collected, awards score, and emits FRUIT_COLLECTED.
     */
    public void collect(Player player) {
        if (collected) {
            return;
        }
        collected = true;
        player.addScore(points);
        if (dispatcher != null) {
            dispatcher.dispatch(new GameEvent(GameEventType.FRUIT_COLLECTED, this,
                Map.of("fruitId", id, "points", points)));
        }
    }

    @Override
    /**
     * @return {@code true} if the fruit was already collected.
     */
    public boolean isCollected() {
        return collected;
    }
}
