package ar.uba.dc.soot;

import java.util.Iterator;

import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.Filter;
import soot.util.queue.QueueReader;

/**
 * Extension de la clase {@link Filter} utilizada para corregir lo que creemos es un comportamiento 
 * equivocado de la clase.
 * 
 * El filtro original wrapea un {@link QueueReader}. Esta estructura es dinamica en el sentido de que
 * pueden agregarse objetos en cualquier momento y el iterador se da cuenta.
 * 
 * Sin embargo se estaba precalculando el valor de next. A la hora de preguntar si 
 * hay un next se deberia vereificar si la estructura tiene un next y no si el next anteriormente
 * calculado es null.
 * 
 * @author testis
 */
public class EdgeFilter extends Filter {

	protected Iterator<Edge> source;
	protected EdgePredicate pred;
	protected Edge next = null;
	
	public EdgeFilter(EdgePredicate pred) {
		super(null);
		this.pred = pred;
	}
	    
	public Iterator<Edge> wrap( Iterator<Edge> source ) {
		if (pred != null) {
			EdgeFilter ret = new EdgeFilter(this.pred);
			ret.source = source;
			ret.advance();
	        return ret;
		} else {
			return source;
		}
    }
	
    
    public boolean hasNext() {
    	if (next == null) {
        	// Si estabamos en el final, verificamos si no se agrego nada nuevo.
    		// Si asi y todo seguimos en null, es que realmente no hay nada para adelante
    		advance();
    	}
    	
        return next != null;
    }
    
    public Edge next() {
    	if (next == null) {
        	// Si estabamos en el final, verificamos si no se agrego nada nuevo.
    		// Si asi y todo seguimos en null, es que realmente no hay nada para adelante
    		advance();
    	}
        Edge ret = next;
        advance();
        return ret;
    }
    
    public void remove() {
        throw new UnsupportedOperationException(); 
    }
    
    private void advance() {
	    while (source.hasNext() ) {
	        next = (Edge) source.next();
	        if( pred.want( next ) ) {
	            return;
	        }
	    }
        next = null;
    }
}
