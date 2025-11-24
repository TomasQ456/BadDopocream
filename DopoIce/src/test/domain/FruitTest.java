package test.domain;

import domain.Fruit;
import domain.IceException;
import domain.Player;
import domain.Vector2;
import domain.enums.FruitType;
import domain.enums.GameEventType;
import domain.events.EventDispatcher;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ensures fruit collection updates scoring and emits events.
 */
class FruitTest {

    /**
     * Validates that collecting a fruit updates the player score and emits FRUIT_COLLECTED.
     */
    @Test
    void collect_marksFruitAndUpdatesScore() throws IceException {
        EventDispatcher dispatcher = new EventDispatcher();
        AtomicBoolean collectedEvent = new AtomicBoolean(false);
        dispatcher.addListener(GameEventType.FRUIT_COLLECTED, event -> collectedEvent.set(true));
        Fruit fruit = new Fruit("fruit-1", FruitType.GRAPE, 50, new Vector2(2, 2), dispatcher);
        Player player = new Player("player", new Vector2(0, 0), null, dispatcher);

        fruit.collect(player);

        assertTrue(fruit.isCollected());
        assertEquals(50, player.getScore());
        assertTrue(collectedEvent.get());
    }
}
