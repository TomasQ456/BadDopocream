package domain;

import domain.enemies.Monster;
import domain.enums.Direction;
import domain.enums.ErrorCode;
import domain.enums.GameEventType;
import domain.enums.GameState;
import domain.events.EventDispatcher;
import domain.events.GameEvent;
import domain.events.GameEventListener;
import interfaces.Destructible;
import memory.LevelDescriptor;
import memory.LevelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Core domain aggregate orchestrating map state, player logic, enemy behaviors, persistence access,
 * and event publication. All state-changing operations funnel through this class to keep the
 * presentation layer completely decoupled from the simulation details.
 */
public class DopoIceCream {

    private final LevelRepository levelRepository;
    private final EventDispatcher dispatcher;
    private Cell[][] map;
    private Player player;
    private final List<Monster> enemies = new ArrayList<>();
    private int width;
    private int height;
    private GameState state = GameState.INITIALIZING;
    private int currentLevelId;

    /**
     * Creates a new game world with the specified dimensions and repository dependency.
     *
     * @param width  initial width used before a level is loaded.
     * @param height initial height used before a level is loaded.
     * @param levelRepository persistence abstraction for levels and saved games.
     */
    public DopoIceCream(int width, int height, LevelRepository levelRepository) {
        this.width = width;
        this.height = height;
        this.levelRepository = levelRepository;
        this.dispatcher = new EventDispatcher();
    }

    /**
     * Loads the requested level descriptor, instantiates all runtime objects, and emits a
     * {@link GameEventType#LEVEL_LOADED} notification.
     *
     * @param levelId identifier known by the {@link LevelRepository}.
     * @throws IceException if the repository fails or an invalid descriptor is provided.
     */
    public void loadLevel(int levelId) throws IceException {
        LevelDescriptor descriptor = levelRepository.loadLevel(levelId);
        this.currentLevelId = levelId;
        this.width = descriptor.getWidth();
        this.height = descriptor.getHeight();
        this.map = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = new Cell(x, y, dispatcher);
            }
        }
        enemies.clear();
        player = new Player("player-" + levelId, descriptor.getPlayerSpawn(), this, dispatcher);
        getCell(player.getPosition().getX(), player.getPosition().getY()).setDynamicObject(player);
        buildStaticObjects(descriptor);
        buildFruits(descriptor);
        buildEnemies(descriptor);
        state = GameState.RUNNING;
        dispatcher.dispatch(new GameEvent(GameEventType.LEVEL_LOADED, this, Map.of("levelId", levelId)));
    }

    /**
     * Populates the grid with static objects declared in the descriptor.
     */
    private void buildStaticObjects(LevelDescriptor descriptor) throws IceException {
        for (LevelDescriptor.StaticObjectDescriptor staticDescriptor : descriptor.getStaticObjects()) {
            Vector2 position = staticDescriptor.getPosition();
            GameObject object;
                switch (staticDescriptor.getType()) {
                case BREAKABLE_WALL -> object = new Wall("wall-" + position, position, true,
                    staticDescriptor.getHitPoints(), dispatcher);
                default -> object = new IndestructibleWall("wall-" + position, position, dispatcher);
                }
            getCell(position.getX(), position.getY()).setStaticObject(object);
        }
    }

    /**
     * Places all fruit collectibles on their respective cells.
     */
    private void buildFruits(LevelDescriptor descriptor) throws IceException {
        for (LevelDescriptor.FruitDescriptor fruitDescriptor : descriptor.getFruits()) {
            Vector2 position = fruitDescriptor.getPosition();
            Fruit fruit = new Fruit("fruit-" + position, fruitDescriptor.getType(),
                    fruitDescriptor.getPoints(), position, dispatcher);
            getCell(position.getX(), position.getY()).setStaticObject(fruit);
        }
    }

    /**
     * Instantiates enemy actors using the descriptor-provided patrol patterns.
     */
    private void buildEnemies(LevelDescriptor descriptor) throws IceException {
        int index = 0;
        for (LevelDescriptor.EnemyDescriptor enemyDescriptor : descriptor.getEnemies()) {
            MovementPattern pattern = createPatrolPattern(enemyDescriptor.getPatrolPath());
            Monster monster = new Monster("enemy-" + index++, enemyDescriptor.getMonsterType(),
                    enemyDescriptor.getSpawn(), pattern, this, dispatcher);
            getCell(monster.getPosition().getX(), monster.getPosition().getY()).setDynamicObject(monster);
            enemies.add(monster);
            dispatcher.dispatch(new GameEvent(GameEventType.ENEMY_SPAWNED, monster,
                    Map.of("enemyId", monster.getId())));
        }
    }

    /**
     * Creates a movement pattern that loops through the provided directional path.
     */
    private MovementPattern createPatrolPattern(List<Direction> path) {
        return new MovementPattern() {
            private int cursor;

            @Override
            public Direction nextDirection(Entity entity, DopoIceCream game) {
                if (path.isEmpty()) {
                    return Direction.values()[cursor++ % Direction.values().length];
                }
                Direction direction = path.get(cursor);
                cursor = (cursor + 1) % path.size();
                return direction;
            }
        };
    }

    /**
     * Retrieves the cell at the requested coordinates enforcing bounds validation.
     *
     * @param x horizontal coordinate.
     * @param y vertical coordinate.
     * @return the addressed {@link Cell}.
     * @throws IceException when the coordinates fall outside the active map.
     */
    public Cell getCell(int x, int y) throws IceException {
        if (x < 0 || x >= width || y < 0 || y >= height || map == null) {
            throw new IceException("Coordinates out of bounds", ErrorCode.INVALID_COORDINATES);
        }
        return map[y][x];
    }

    /**
     * @return the current {@link Player} instance or {@code null} prior to level loading.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return live list of enemies spawned for the current level.
     */
    public List<Monster> getEnemies() {
        return enemies;
    }

    /**
     * Registers a listener targeting a specific event type.
     *
     * @param type      domain event to listen for.
     * @param listener  callback that receives events.
     */
    public void registerListener(GameEventType type, GameEventListener listener) {
        dispatcher.addListener(type, listener);
    }

    /**
     * Registers a global listener invoked for every dispatched event.
     *
     * @param listener callback invoked for each event.
     */
    public void registerListener(GameEventListener listener) {
        dispatcher.addListener(listener);
    }

    /**
     * Moves the supplied entity to the destination cell, resolving solid collisions, updating cell
     * occupancy, and publishing relevant events.
     *
     * @param entity      the entity to move.
     * @param destination absolute destination coordinates.
     * @throws IceException when the destination is blocked or outside the map.
     */
    public synchronized void moveEntity(Entity entity, Vector2 destination) throws IceException {
        Objects.requireNonNull(entity, "entity");
        Cell destinationCell = getCell(destination.getX(), destination.getY());
        GameObject staticObject = destinationCell.getStaticObject();
        if (staticObject != null && staticObject.isSolid()) {
            throw new IceException("Cell is blocked", ErrorCode.CELL_BLOCKED);
        }
        Entity occupying = destinationCell.getDynamicObject();
        if (occupying != null && occupying != entity) {
            resolveCollision(entity, occupying);
            return;
        }
        Cell origin = getCell(entity.getPosition().getX(), entity.getPosition().getY());
        origin.setDynamicObject(null);
        destinationCell.setDynamicObject(entity);
        entity.setPosition(destination);
        if (entity instanceof Player playerEntity) {
            dispatcher.dispatch(new GameEvent(GameEventType.PLAYER_MOVED, playerEntity,
                    Map.of("x", destination.getX(), "y", destination.getY())));
            handleStaticInteraction(destinationCell, playerEntity);
        }
    }

    /**
     * Performs post-move static interactions such as fruit collection and completion detection.
     */
    private void handleStaticInteraction(Cell cell, Player actor) throws IceException {
        GameObject staticObject = cell.getStaticObject();
        if (staticObject instanceof Fruit fruit) {
            fruit.collect(actor);
            cell.setStaticObject(null);
            countRemainingFruits();
        }
    }

    /**
     * Creates an ice block at the designated position for the provided player.
     *
     * @param position destination coordinates where the block should appear.
     * @param owner    player responsible for the action to enrich events.
     * @throws IceException when the cell is already occupied or outside the bounds.
     */
    public void createIceAt(Vector2 position, Player owner) throws IceException {
        Cell cell = getCell(position.getX(), position.getY());
        if (cell.getStaticObject() != null) {
            throw new IceException("Cannot create ice on occupied cell", ErrorCode.CELL_BLOCKED);
        }
        IceBlock block = new IceBlock("ice-" + position, position, 5, dispatcher);
        cell.setStaticObject(block);
        dispatcher.dispatch(new GameEvent(GameEventType.ICE_CREATED, owner,
                Map.of("x", position.getX(), "y", position.getY())));
    }

    /**
     * Requests destruction of an ice block (or other destructible) occupying the supplied position.
     *
     * @param position coordinates to inspect.
     * @throws IceException if the coordinates are invalid.
     */
    public void destroyIceAt(Vector2 position) throws IceException {
        Cell cell = getCell(position.getX(), position.getY());
        GameObject staticObject = cell.getStaticObject();
        if (staticObject instanceof Destructible destructible) {
            destructible.breakBlock();
            if (destructible.isDestroyed()) {
                cell.setStaticObject(null);
                dispatcher.dispatch(new GameEvent(GameEventType.BLOCK_DESTROYED, this,
                        Map.of("x", position.getX(), "y", position.getY())));
            }
        }
    }

    /**
     * Counts how many collectible fruits remain on the board and emits a
     * {@link GameEventType#LEVEL_COMPLETED} event when the count reaches zero while running.
     *
     * @return remaining fruit count.
     * @throws IceException if the map has not been initialised yet.
     */
    public int countRemainingFruits() throws IceException {
        if (map == null) {
            return 0;
        }
        int remaining = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                GameObject object = map[y][x].getStaticObject();
                if (object instanceof Fruit fruit && !fruit.isCollected()) {
                    remaining++;
                }
            }
        }
        if (remaining == 0 && state == GameState.RUNNING) {
            dispatcher.dispatch(new GameEvent(GameEventType.LEVEL_COMPLETED, this,
                    Map.of("levelId", currentLevelId)));
            state = GameState.COMPLETED;
        }
        return remaining;
    }

    /**
     * Advances the simulation one frame by updating the player, enemies, ice blocks, and collision state.
     *
     * @throws IceException if any nested operation violates domain rules.
     */
    public void tick() throws IceException {
        if (map == null) {
            return;
        }
        if (player != null) {
            player.update();
        }
        for (Monster enemy : enemies) {
            enemy.update();
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                GameObject object = map[y][x].getStaticObject();
                if (object instanceof IceBlock iceBlock) {
                    iceBlock.update();
                    if (iceBlock.isDestroyed()) {
                        map[y][x].setStaticObject(null);
                    }
                }
            }
        }
        resolveCollisions();
    }

    /**
     * Resolves direct clashes between a moving entity and the existing occupant of a destination cell.
     */
    private void resolveCollision(Entity mover, Entity occupant) {
        if (mover instanceof Player player && occupant instanceof Monster monster) {
            monster.collideWithPlayer(player);
        } else if (mover instanceof Monster monster && occupant instanceof Player player) {
            monster.collideWithPlayer(player);
        }
    }

    /**
     * Resolves player/enemy overlaps after all movements for the frame have been processed.
     */
    public void resolveCollisions() {
        if (player == null) {
            return;
        }
        for (Monster enemy : enemies) {
            if (enemy.getPosition().equals(player.getPosition())) {
                enemy.collideWithPlayer(player);
            }
        }
    }

    /**
     * Persists the current game snapshot through the repository and emits {@link GameEventType#SAVE_GAME}.
     *
     * @param slot logical slot identifier (included in the event payload).
     * @throws IceException if persistence fails.
     */
    public void saveGame(String slot) throws IceException {
        memory.GameState gameState = memory.GameState.builder()
                .levelId(currentLevelId)
                .score(player.getScore())
                .playerPosition(player.getPosition())
                .build();
        levelRepository.saveGameState(gameState);
        dispatcher.dispatch(new GameEvent(GameEventType.SAVE_GAME, this, Map.of("slot", slot)));
    }

    /**
     * Restores a previously saved snapshot and repositions the player accordingly.
     *
     * @param path repository-specific save identifier.
     * @throws IceException if the save cannot be loaded.
     */
    public void loadGame(String path) throws IceException {
        memory.GameState gameState = levelRepository.loadGameState(path);
        loadLevel(gameState.getLevelId());
        Cell currentCell = getCell(player.getPosition().getX(), player.getPosition().getY());
        currentCell.setDynamicObject(null);
        player.setPosition(gameState.getPlayerPosition());
        getCell(player.getPosition().getX(), player.getPosition().getY()).setDynamicObject(player);
        dispatcher.dispatch(new GameEvent(GameEventType.LOAD_GAME, this, Map.of("levelId", gameState.getLevelId())));
    }

    /**
     * @return current map width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return current map height.
     */
    public int getHeight() {
        return height;
    }
}
