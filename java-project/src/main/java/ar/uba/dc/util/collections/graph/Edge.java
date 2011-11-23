package ar.uba.dc.util.collections.graph;

/**
 * Interfaz que representa un eje de un {@link DirectedGraph}
 * @author testis
 *
 * @param <N> tipo de nodos del grafo
 */ 
public interface Edge<N> {
	
	/** Retorna el destino del eje */
	public N getTarget();
    
	/** Retorna el origen del eje */
    public N getSource();
}
