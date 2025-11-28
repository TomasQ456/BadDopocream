package main.domain;

import main.domain.entities.*;
import main.domain.enums.*;
import main.domain.event.*;
import main.domain.exception.IceException;
import main.domain.util.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Controlador principal del juego Bad Dopo-Cream.
 * Gestiona el estado del juego, jugadores, monstruos, frutas y el mapa.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class DopoIceCream {

    /** Ancho del mapa */
    private final int width;
    
    /** Alto del mapa */
    private final int height;
    
    /** Estado actual del juego */
    private GameState gameState;
    
    /** Modo de juego */
    private GameMode gameMode;
    
    /** Mapa de celdas */
    private Cell[][] map;
    
    /** Lista de jugadores */
    private final List<Player> players;
    
    /** Lista de monstruos */
    private final List<Monster> monsters;
    
    /** Lista de frutas */
    private final List<Fruit> fruits;
    
    /** Lista de muros */
    private final List<Wall> walls;
    
    /** Listeners de eventos */
    private final List<GameEventListener> eventListeners;
    
    /** PathFinder para monstruos */
    private PathFinder pathFinder;

    /**
     * Constructor de DopoIceCream.
     * 
     * @param width ancho del mapa
     * @param height alto del mapa
     * @throws IceException si las dimensiones son inválidas
     */
    public DopoIceCream(int width, int height) throws IceException {
        if (width <= 0 || height <= 0) {
            throw new IceException(ErrorCode.INVALID_DIMENSION);
        }
        
        this.width = width;
        this.height = height;
        this.gameState = GameState.MENU;
        this.gameMode = GameMode.SINGLE_PLAYER;
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.fruits = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.eventListeners = new CopyOnWriteArrayList<>();
        
        initializeMap();
        initializePathFinder();
    }

    /**
     * Inicializa el mapa de celdas.
     */
    private void initializeMap() {
        map = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = new Cell(x, y);
            }
        }
    }

    /**
     * Inicializa el PathFinder.
     */
    private void initializePathFinder() {
        pathFinder = new PathFinder(map);
    }

    // ==================== Getters ====================

    /**
     * Obtiene el ancho del mapa.
     * 
     * @return el ancho
     */
    public int getWidth() {
        return width;
    }

    /**
     * Obtiene el alto del mapa.
     * 
     * @return el alto
     */
    public int getHeight() {
        return height;
    }

    /**
     * Obtiene el estado actual del juego.
     * 
     * @return el estado
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Obtiene el modo de juego.
     * 
     * @return el modo
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Establece el modo de juego.
     * 
     * @param gameMode el nuevo modo
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Obtiene el mapa de celdas.
     * 
     * @return el mapa
     */
    public Cell[][] getMap() {
        return map;
    }

    /**
     * Obtiene una celda específica.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @return la celda o null si está fuera de rango
     */
    public Cell getCell(int x, int y) {
        if (!isValidPosition(x, y)) {
            return null;
        }
        return map[x][y];
    }

    /**
     * Obtiene la lista de jugadores.
     * 
     * @return lista de jugadores
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Obtiene un jugador por índice.
     * 
     * @param index el índice
     * @return el jugador
     */
    public Player getPlayer(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index);
        }
        return null;
    }

    /**
     * Obtiene el jugador 1.
     * 
     * @return el primer jugador
     */
    public Player getPlayer1() {
        return getPlayer(0);
    }

    /**
     * Obtiene el jugador 2 (modo cooperativo).
     * 
     * @return el segundo jugador
     */
    public Player getPlayer2() {
        return getPlayer(1);
    }

    /**
     * Obtiene la lista de monstruos.
     * 
     * @return lista de monstruos
     */
    public List<Monster> getMonsters() {
        return new ArrayList<>(monsters);
    }

    /**
     * Obtiene la lista de frutas.
     * 
     * @return lista de frutas
     */
    public List<Fruit> getFruits() {
        return new ArrayList<>(fruits);
    }

    /**
     * Cuenta las frutas no colectadas.
     * 
     * @return cantidad de frutas restantes
     */
    public int getRemainingFruitsCount() {
        return (int) fruits.stream().filter(f -> !f.isCollected()).count();
    }

    /**
     * Obtiene el muro en una posición.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @return el muro o null
     */
    public Wall getWallAt(int x, int y) {
        return walls.stream()
                .filter(w -> w.getX() == x && w.getY() == y && !isWallDestroyed(w))
                .findFirst()
                .orElse(null);
    }

    // ==================== Gestión de Entidades ====================

    /**
     * Añade un jugador al juego.
     * 
     * @param player el jugador a añadir
     * @throws IceException si el jugador es null
     */
    public void addPlayer(Player player) throws IceException {
        if (player == null) {
            throw new IceException(ErrorCode.NULL_PLAYER);
        }
        players.add(player);
        updateCellWithPlayer(player);
    }

    /**
     * Remueve un jugador del juego.
     * 
     * @param player el jugador a remover
     */
    public void removePlayer(Player player) {
        players.remove(player);
        clearCellPlayer(player);
    }

    /**
     * Añade un monstruo al juego.
     * 
     * @param monster el monstruo a añadir
     */
    public void addMonster(Monster monster) {
        if (monster != null) {
            monsters.add(monster);
            
            // Configurar PathFinder para monstruos que lo necesiten
            if (monster instanceof Pot) {
                ((Pot) monster).setPathFinder(pathFinder);
            } else if (monster instanceof OrangeSquid) {
                ((OrangeSquid) monster).setPathFinder(pathFinder);
            }
        }
    }

    /**
     * Remueve un monstruo del juego.
     * 
     * @param monster el monstruo a remover
     */
    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }

    /**
     * Añade una fruta al juego.
     * 
     * @param fruit la fruta a añadir
     */
    public void addFruit(Fruit fruit) {
        if (fruit != null) {
            fruits.add(fruit);
        }
    }

    /**
     * Remueve una fruta del juego.
     * 
     * @param fruit la fruta a remover
     */
    public void removeFruit(Fruit fruit) {
        fruits.remove(fruit);
    }

    /**
     * Coloca un muro en el mapa.
     * 
     * @param wall el muro a colocar
     */
    public void placeWall(Wall wall) {
        if (wall != null) {
            walls.add(wall);
            Cell cell = getCell(wall.getX(), wall.getY());
            if (cell != null) {
                cell.setStaticObject(wall);
            }
        }
    }

    // ==================== Control del Juego ====================

    /**
     * Inicia el juego.
     * 
     * @throws IceException si no hay jugadores
     */
    public void start() throws IceException {
        if (players.isEmpty()) {
            throw new IceException(ErrorCode.NO_PLAYERS);
        }
        
        gameState = GameState.PLAYING;
        
        // Configurar target para todos los monstruos
        Player mainTarget = players.get(0);
        for (Monster monster : monsters) {
            monster.setTarget(mainTarget);
        }
        
        fireEvent(EventType.GAME_STARTED, null);
    }

    /**
     * Pausa el juego.
     */
    public void pause() {
        if (gameState == GameState.PLAYING) {
            gameState = GameState.PAUSED;
            fireEvent(EventType.GAME_PAUSED, null);
        }
    }

    /**
     * Reanuda el juego.
     */
    public void resume() {
        if (gameState == GameState.PAUSED) {
            gameState = GameState.PLAYING;
            fireEvent(EventType.GAME_RESUMED, null);
        }
    }

    /**
     * Reinicia el juego.
     */
    public void reset() {
        gameState = GameState.MENU;
        
        // Reset jugadores
        for (Player player : players) {
            player.respawn();
        }
        
        // Reset monstruos
        for (Monster monster : monsters) {
            monster.reset();
        }
        
        // Reset frutas
        for (Fruit fruit : fruits) {
            fruit.reset();
        }
        
        // Reset muros
        for (Wall wall : walls) {
            wall.reset();
        }
        
        fireEvent(EventType.GAME_RESET, null);
    }

    /**
     * Actualiza el estado del juego.
     */
    public void update() {
        if (gameState != GameState.PLAYING) {
            return;
        }
        
        // Verificar colisiones al inicio (para colisiones ya existentes)
        checkCollisions();
        
        // Actualizar monstruos
        for (Monster monster : monsters) {
            monster.update();
        }
        
        // Verificar colisiones después del movimiento
        checkCollisions();
        
        // Verificar condiciones de victoria/derrota
        checkVictoryCondition();
        checkDefeatCondition();
    }

    // ==================== Movimiento ====================

    /**
     * Mueve un jugador en una dirección.
     * 
     * @param playerIndex índice del jugador
     * @param direction dirección del movimiento
     */
    public void movePlayer(int playerIndex, Direction direction) {
        if (gameState != GameState.PLAYING) {
            return;
        }
        
        Player player = getPlayer(playerIndex);
        if (player == null || direction == null) {
            return;
        }
        
        int newX = player.getX() + direction.getDeltaX();
        int newY = player.getY() + direction.getDeltaY();
        
        // Verificar límites del mapa
        if (!isValidPosition(newX, newY)) {
            return;
        }
        
        // Verificar si hay un muro
        Wall wall = getWallAt(newX, newY);
        if (wall != null) {
            if (wall instanceof IceWall) {
                // Romper muro de hielo
                ((IceWall) wall).destroy();
                fireEvent(EventType.ICE_DESTROYED, wall);
            } else {
                // No puede pasar
                return;
            }
        }
        
        // Mover jugador
        clearCellPlayer(player);
        player.move(direction);
        updateCellWithPlayer(player);
        
        // Verificar frutas
        checkFruitCollection(player);
        
        // Verificar victoria después de recolectar
        checkVictoryCondition();
        
        fireEvent(EventType.PLAYER_MOVED, player);
    }

    // ==================== Colisiones ====================

    /**
     * Verifica las colisiones monstruo-jugador.
     */
    public void checkCollisions() {
        for (Monster monster : monsters) {
            for (Player player : players) {
                if (monster.hasCollidedWith(player)) {
                    handlePlayerHit(player);
                }
            }
        }
    }

    /**
     * Maneja cuando un jugador es golpeado por un monstruo.
     * 
     * @param player el jugador golpeado
     */
    public void handlePlayerHit(Player player) {
        player.kill();
        fireEvent(EventType.PLAYER_HIT, player);
        
        if (player.getLives() > 0) {
            player.respawn();
        }
    }

    /**
     * Verifica si el jugador recolecta una fruta.
     * 
     * @param player el jugador
     */
    private void checkFruitCollection(Player player) {
        for (Fruit fruit : fruits) {
            if (!fruit.isCollected() && 
                    fruit.getX() == player.getX() && 
                    fruit.getY() == player.getY()) {
                
                // Primero procesar puntos y contador antes de marcar como collected
                if (player instanceof IceCream) {
                    IceCream iceCream = (IceCream) player;
                    iceCream.addScore(fruit.getPoints());
                    iceCream.incrementFruitsCollected();
                }
                
                fruit.collect();
                fireEvent(EventType.FRUIT_COLLECTED, fruit);
            }
        }
    }

    // ==================== Condiciones de Victoria/Derrota ====================

    /**
     * Verifica la condición de victoria.
     */
    public void checkVictoryCondition() {
        if (gameState != GameState.PLAYING && gameState != GameState.VICTORY) {
            return;
        }
        
        if (getRemainingFruitsCount() == 0 && !fruits.isEmpty()) {
            gameState = GameState.VICTORY;
            fireEvent(EventType.GAME_WON, null);
        }
    }

    /**
     * Verifica la condición de derrota.
     */
    public void checkDefeatCondition() {
        if (gameState != GameState.PLAYING && gameState != GameState.DEFEAT) {
            return;
        }
        
        boolean allPlayersDead = players.stream().allMatch(p -> p.getLives() <= 0);
        
        if (allPlayersDead) {
            gameState = GameState.DEFEAT;
            fireEvent(EventType.GAME_LOST, null);
        }
    }

    // ==================== Utilidades del Mapa ====================

    /**
     * Verifica si una posición es válida en el mapa.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @return true si es válida
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Verifica si una posición está bloqueada.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @return true si está bloqueada
     */
    public boolean isBlocked(int x, int y) {
        if (!isValidPosition(x, y)) {
            return true;
        }
        
        Wall wall = getWallAt(x, y);
        return wall != null && !isWallDestroyed(wall);
    }

    /**
     * Verifica si un muro está destruido.
     * 
     * @param wall el muro a verificar
     * @return true si está destruido
     */
    private boolean isWallDestroyed(Wall wall) {
        if (wall instanceof IceWall) {
            return ((IceWall) wall).isDestroyed();
        }
        return false; // Los muros indestructibles nunca están destruidos
    }

    /**
     * Actualiza la celda con la referencia al jugador.
     * 
     * @param player el jugador
     */
    private void updateCellWithPlayer(Player player) {
        Cell cell = getCell(player.getX(), player.getY());
        if (cell != null) {
            cell.setPlayer(player);
        }
    }

    /**
     * Limpia la referencia del jugador de la celda.
     * 
     * @param player el jugador
     */
    private void clearCellPlayer(Player player) {
        Cell cell = getCell(player.getX(), player.getY());
        if (cell != null && cell.getPlayer() == player) {
            cell.setPlayer(null);
        }
    }

    // ==================== Sistema de Eventos ====================

    /**
     * Añade un listener de eventos.
     * 
     * @param listener el listener
     */
    public void addEventListener(GameEventListener listener) {
        if (listener != null) {
            eventListeners.add(listener);
        }
    }

    /**
     * Remueve un listener de eventos.
     * 
     * @param listener el listener
     */
    public void removeEventListener(GameEventListener listener) {
        eventListeners.remove(listener);
    }

    /**
     * Dispara un evento a todos los listeners.
     * 
     * @param type tipo del evento
     * @param data datos del evento
     */
    public void fireEvent(EventType type, Object data) {
        GameEvent event = new GameEvent(type, data);
        for (GameEventListener listener : eventListeners) {
            listener.onEvent(event);
        }
    }

    @Override
    public String toString() {
        return "DopoIceCream{" +
                "width=" + width +
                ", height=" + height +
                ", state=" + gameState +
                ", mode=" + gameMode +
                ", players=" + players.size() +
                ", monsters=" + monsters.size() +
                ", fruits=" + fruits.size() +
                '}';
    }
}
