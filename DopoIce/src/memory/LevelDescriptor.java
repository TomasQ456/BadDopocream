package memory;

import domain.Vector2;
import domain.enums.Direction;
import domain.enums.FruitType;
import domain.enums.MonsterType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data structure describing how to build a level.
 */
public class LevelDescriptor {

    private final int levelId;
    private final int width;
    private final int height;
    private final Vector2 playerSpawn;
    private final List<StaticObjectDescriptor> staticObjects;
    private final List<FruitDescriptor> fruits;
    private final List<EnemyDescriptor> enemies;

    private LevelDescriptor(Builder builder) {
        this.levelId = builder.levelId;
        this.width = builder.width;
        this.height = builder.height;
        this.playerSpawn = builder.playerSpawn;
        this.staticObjects = Collections.unmodifiableList(new ArrayList<>(builder.staticObjects));
        this.fruits = Collections.unmodifiableList(new ArrayList<>(builder.fruits));
        this.enemies = Collections.unmodifiableList(new ArrayList<>(builder.enemies));
    }

    /**
     * @return unique level identifier.
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * @return level width in tiles.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return level height in tiles.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return player spawn location.
     */
    public Vector2 getPlayerSpawn() {
        return playerSpawn;
    }

    /**
     * @return static object definitions.
     */
    public List<StaticObjectDescriptor> getStaticObjects() {
        return staticObjects;
    }

    /**
     * @return fruit definitions.
     */
    public List<FruitDescriptor> getFruits() {
        return fruits;
    }

    /**
     * @return enemy definitions.
     */
    public List<EnemyDescriptor> getEnemies() {
        return enemies;
    }

    /**
     * @return builder helper for descriptors.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Fluent builder that mirrors the JSON schema used by the repository layer.
     */
    public static final class Builder {
        private int levelId;
        private int width;
        private int height;
        private Vector2 playerSpawn = new Vector2(0, 0);
        private final List<StaticObjectDescriptor> staticObjects = new ArrayList<>();
        private final List<FruitDescriptor> fruits = new ArrayList<>();
        private final List<EnemyDescriptor> enemies = new ArrayList<>();

        /**
         * @param id level identifier.
         */
        public Builder levelId(int id) {
            this.levelId = id;
            return this;
        }

        /**
         * @param width  width in tiles.
         * @param height height in tiles.
         */
        public Builder dimensions(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * @param spawn spawn coordinates.
         */
        public Builder playerSpawn(Vector2 spawn) {
            this.playerSpawn = spawn;
            return this;
        }

        /**
         * Adds a static object description.
         */
        public Builder addStaticObject(StaticObjectDescriptor descriptor) {
            this.staticObjects.add(descriptor);
            return this;
        }

        /**
         * Adds a fruit description.
         */
        public Builder addFruit(FruitDescriptor descriptor) {
            this.fruits.add(descriptor);
            return this;
        }

        /**
         * Adds an enemy description.
         */
        public Builder addEnemy(EnemyDescriptor descriptor) {
            this.enemies.add(descriptor);
            return this;
        }

        /**
         * @return immutable descriptor.
         */
        public LevelDescriptor build() {
            return new LevelDescriptor(this);
        }
    }

    /**
     * Value object representing a static tile entity such as walls.
     */
    public static final class StaticObjectDescriptor {
        public enum Type { INDESTRUCTIBLE_WALL, BREAKABLE_WALL }

        private final Type type;
        private final Vector2 position;
        private final int hitPoints;

        private StaticObjectDescriptor(Type type, Vector2 position, int hitPoints) {
            this.type = type;
            this.position = position;
            this.hitPoints = hitPoints;
        }

        /**
         * @return descriptor for an indestructible wall.
         */
        public static StaticObjectDescriptor indestructibleWall(Vector2 position) {
            return new StaticObjectDescriptor(Type.INDESTRUCTIBLE_WALL, position, Integer.MAX_VALUE);
        }

        /**
         * @return descriptor for a breakable wall with the specified hit points.
         */
        public static StaticObjectDescriptor breakableWall(Vector2 position, int hitPoints) {
            return new StaticObjectDescriptor(Type.BREAKABLE_WALL, position, hitPoints);
        }

        /**
         * @return descriptor type.
         */
        public Type getType() {
            return type;
        }

        /**
         * @return tile coordinates.
         */
        public Vector2 getPosition() {
            return position;
        }

        /**
         * @return configured hit points.
         */
        public int getHitPoints() {
            return hitPoints;
        }
    }

    /**
     * Definition of a fruit collectible and its reward value.
     */
    public static final class FruitDescriptor {
        private final FruitType type;
        private final Vector2 position;
        private final int points;

        private FruitDescriptor(FruitType type, Vector2 position, int points) {
            this.type = type;
            this.position = position;
            this.points = points;
        }

        /**
         * @return descriptor for the provided fruit metadata.
         */
        public static FruitDescriptor of(FruitType type, Vector2 position, int points) {
            return new FruitDescriptor(type, position, points);
        }

        /**
         * @return fruit type.
         */
        public FruitType getType() {
            return type;
        }

        /**
         * @return position of the fruit.
         */
        public Vector2 getPosition() {
            return position;
        }

        /**
         * @return score granted when collected.
         */
        public int getPoints() {
            return points;
        }
    }

    /**
     * Descriptor for an AI-controlled monster and its patrol route.
     */
    public static final class EnemyDescriptor {
        private final MonsterType monsterType;
        private final Vector2 spawn;
        private final List<Direction> patrolPath;

        private EnemyDescriptor(MonsterType monsterType, Vector2 spawn, List<Direction> patrolPath) {
            this.monsterType = monsterType;
            this.spawn = spawn;
            this.patrolPath = patrolPath;
        }

        /**
         * @return descriptor describing a patrol enemy.
         */
        public static EnemyDescriptor patrol(MonsterType type, Vector2 spawn, List<Direction> path) {
            return new EnemyDescriptor(type, spawn, List.copyOf(path));
        }

        /**
         * @return monster type.
         */
        public MonsterType getMonsterType() {
            return monsterType;
        }

        /**
         * @return spawn coordinate.
         */
        public Vector2 getSpawn() {
            return spawn;
        }

        /**
         * @return patrol directions.
         */
        public List<Direction> getPatrolPath() {
            return patrolPath;
        }
    }
}
