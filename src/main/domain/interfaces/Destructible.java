package main.domain.interfaces;

/**
 * Interfaz para entidades que pueden ser destruidas.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public interface Destructible {

    /**
     * Destruye la entidad.
     */
    void destroy();

    /**
     * Verifica si la entidad está destruida.
     * 
     * @return true si está destruida
     */
    boolean isDestroyed();
}
