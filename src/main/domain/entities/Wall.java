package main.domain.entities;

/**
 * Clase abstracta base para muros.
 * Los muros son objetos estáticos sólidos.
 * 
 */
public abstract class Wall extends StaticObject {

    /**
     * Constructor de Wall.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @param symbol símbolo del muro
     * @param isDestructible si puede ser destruido
     */
    protected Wall(int x, int y, char symbol, boolean isDestructible) {
        super(x, y, symbol, isDestructible, true); // Los muros siempre son sólidos
    }

    /**
     * Reinicia el muro a su estado inicial.
     */
    public void reset() {
        // Las subclases implementan el reset específico
    }

    /**
     * Indica si el muro ha sido destruido.
     * Por defecto retorna false. Las subclases destructibles lo sobrescriben.
     * 
     * @return true si está destruido, false en caso contrario
     */
    public boolean isDestroyed() {
        return false;
    }
}
