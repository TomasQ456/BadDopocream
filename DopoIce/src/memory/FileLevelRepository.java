package memory;

import domain.IceException;
import domain.Vector2;
import domain.enums.Direction;
import domain.enums.ErrorCode;
import domain.enums.FruitType;
import domain.enums.MonsterType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * File-system backed repository using JSON descriptors parsed with an internal lightweight parser.
 */
public class FileLevelRepository implements LevelRepository {

    private final Path levelsFolder;

    /**
     * @param levelsFolder folder containing JSON level and save files.
     */
    public FileLevelRepository(Path levelsFolder) {
        this.levelsFolder = levelsFolder;
    }

    /**
     * Loads a level descriptor from the pre-defined JSON file.
     *
     * @param id identifier matching the filename suffix.
     * @throws IceException when the file is missing or cannot be parsed.
     */
    @Override
    public LevelDescriptor loadLevel(int id) throws IceException {
        Path file = levelsFolder.resolve("level-" + id + ".json");
        if (!Files.exists(file)) {
            throw new IceException("Level file does not exist: " + file, ErrorCode.LEVEL_NOT_FOUND);
        }
        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            @SuppressWarnings("unchecked")
            Map<String, Object> root = (Map<String, Object>) expectObject(SimpleJsonParser.parse(json), "level");
            return buildDescriptor(root);
        } catch (IOException | IllegalArgumentException e) {
            throw new IceException("Failed to read level file", ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    /**
     * Serializes the provided game state to a JSON snapshot.
     *
     * @param state runtime state to persist.
     * @throws IceException when the save file cannot be written.
     */
    @Override
    public void saveGameState(GameState state) throws IceException {
        Path file = levelsFolder.resolve("save-game.json");
        try {
            Files.createDirectories(levelsFolder);
            String json = new GameStateRecord(state.getLevelId(), state.getScore(), state.getPlayerPosition()).toJson();
            Files.writeString(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IceException("Failed to store save file", ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    /**
     * Loads a saved game from a relative path.
     *
     * @param path relative path to the save JSON file.
     * @throws IceException when the file is missing or malformed.
     */
    @Override
    public GameState loadGameState(String path) throws IceException {
        Path file = levelsFolder.resolve(path);
        if (!Files.exists(file)) {
            throw new IceException("Save file missing: " + file, ErrorCode.PERSISTENCE_ERROR);
        }
        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            @SuppressWarnings("unchecked")
            Map<String, Object> root = (Map<String, Object>) expectObject(SimpleJsonParser.parse(json), "save");
            GameStateRecord record = GameStateRecord.fromMap(root);
            return GameState.builder()
                    .levelId(record.levelId())
                    .score(record.score())
                    .playerPosition(record.playerPosition())
                    .build();
        } catch (IOException | IllegalArgumentException e) {
            throw new IceException("Failed to load save file", ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    private LevelDescriptor buildDescriptor(Map<String, Object> root) {
        LevelDescriptor.Builder builder = LevelDescriptor.builder()
                .levelId(expectInt(root.get("id"), "level.id"))
                .dimensions(expectInt(root.get("width"), "level.width"), expectInt(root.get("height"), "level.height"))
                .playerSpawn(toVector(root.get("player"), "level.player"));
        objectList(root.get("statics"), "level.statics").forEach(data -> builder.addStaticObject(toStaticDescriptor(data)));
        objectList(root.get("fruits"), "level.fruits").forEach(data -> builder.addFruit(toFruitDescriptor(data)));
        objectList(root.get("enemies"), "level.enemies").forEach(data -> builder.addEnemy(toEnemyDescriptor(data)));
        return builder.build();
    }

    private LevelDescriptor.StaticObjectDescriptor toStaticDescriptor(Map<String, Object> data) {
        String type = expectString(data.get("type"), "static.type");
        Vector2 position = toVector(data.get("position"), "static.position");
        if ("breakable".equalsIgnoreCase(type)) {
            int hitPoints = expectInt(data.getOrDefault("hp", 1), "static.hp");
            return LevelDescriptor.StaticObjectDescriptor.breakableWall(position, hitPoints);
        }
        return LevelDescriptor.StaticObjectDescriptor.indestructibleWall(position);
    }

    private LevelDescriptor.FruitDescriptor toFruitDescriptor(Map<String, Object> data) {
        String typeName = expectString(data.get("type"), "fruit.type");
        FruitType type = FruitType.valueOf(typeName.toUpperCase());
        int points = expectInt(data.getOrDefault("points", 0), "fruit.points");
        Vector2 position = toVector(data.get("position"), "fruit.position");
        return LevelDescriptor.FruitDescriptor.of(type, position, points);
    }

    private LevelDescriptor.EnemyDescriptor toEnemyDescriptor(Map<String, Object> data) {
        String typeName = expectString(data.get("type"), "enemy.type");
        MonsterType type = MonsterType.valueOf(typeName.toUpperCase());
        Vector2 spawn = toVector(data.get("spawn"), "enemy.spawn");
        List<String> path = stringList(data.get("path"), "enemy.path");
        List<Direction> directions = new ArrayList<>(path.size());
        for (String step : path) {
            directions.add(Direction.valueOf(step.toUpperCase()));
        }
        return LevelDescriptor.EnemyDescriptor.patrol(type, spawn, directions);
    }

    private Vector2 toVector(Object raw, String path) {
        Map<String, Object> node = expectObject(raw, path);
        return new Vector2(expectInt(node.get("x"), path + ".x"), expectInt(node.get("y"), path + ".y"));
    }

    private List<Map<String, Object>> objectList(Object raw, String path) {
        if (raw == null) {
            return List.of();
        }
        List<?> items = expectArray(raw, path);
        List<Map<String, Object>> result = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            result.add(expectObject(items.get(i), path + "[" + i + "]"));
        }
        return result;
    }

    private List<String> stringList(Object raw, String path) {
        if (raw == null) {
            return List.of();
        }
        List<?> items = expectArray(raw, path);
        List<String> result = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            Object value = items.get(i);
            if (!(value instanceof String str)) {
                throw new IllegalArgumentException("Expected string at " + path + "[" + i + "]");
            }
            result.add(str);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> expectObject(Object raw, String path) {
        if (!(raw instanceof Map<?, ?> map)) {
            throw new IllegalArgumentException("Expected object at " + path);
        }
        return (Map<String, Object>) map;
    }

    private static List<?> expectArray(Object raw, String path) {
        if (!(raw instanceof List<?> list)) {
            throw new IllegalArgumentException("Expected array at " + path);
        }
        return list;
    }

    private static int expectInt(Object raw, String path) {
        if (raw instanceof Number number) {
            return number.intValue();
        }
        throw new IllegalArgumentException("Expected numeric value at " + path);
    }

    private static String expectString(Object raw, String path) {
        if (raw instanceof String str) {
            return str;
        }
        throw new IllegalArgumentException("Expected string at " + path);
    }

    /**
     * Serializable projection of the runtime {@link GameState} used for persistence.
     */
    private record GameStateRecord(int levelId, int score, Vector2 playerPosition) {

        String toJson() {
            return "{\n"
                    + "  \"levelId\": " + levelId + ",\n"
                    + "  \"score\": " + score + ",\n"
                    + "  \"playerPosition\": {\n"
                    + "    \"x\": " + playerPosition.getX() + ",\n"
                    + "    \"y\": " + playerPosition.getY() + "\n"
                    + "  }\n"
                    + "}";
        }

        static GameStateRecord fromMap(Map<String, Object> node) {
            int levelId = expectInt(node.get("levelId"), "save.levelId");
            int score = expectInt(node.get("score"), "save.score");
            Map<String, Object> positionNode = expectObject(node.get("playerPosition"), "save.playerPosition");
            Vector2 position = new Vector2(
                    expectInt(positionNode.get("x"), "save.playerPosition.x"),
                    expectInt(positionNode.get("y"), "save.playerPosition.y"));
            return new GameStateRecord(levelId, score, position);
        }
    }
}
