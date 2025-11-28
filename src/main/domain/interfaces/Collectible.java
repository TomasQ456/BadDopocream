package main.domain.interfaces;

/**
 * Interfaz para entidades que pueden ser recolectadas.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public interface Collectible {

    /**
     * Recolecta la entidad.
     */
    void collect();

    /**
     * Obtiene los puntos que otorga la entidad.
     * 
     * @return los puntos
     */
    int getPoints();

    /**
     * Verifica si la entidad ya fue recolectada.
     * 
     * @return true si ya fue recolectada
     */
    boolean isCollected();
}
