package ar.uba.dc.invariant.spec.compiler.compilation.fullreferences;

import ar.uba.dc.invariant.spec.compiler.exceptions.UnknownInvariantException;

/**
 * El scope se asemeja a un {@link ClassLoader} para los {@link InvariantAdapter}.
 * 
 * El scope permite encontrar los {@link InvariantAdapter} a los que puede referenciar
 * un {@link InvariantAdapter} en particular.
 * 
 * Las busquedas se hacen como en un {@link ClassLoader}, primero verificamos en el padre 
 * si conoce la referencia. De no conocerlo, buscamos en la instancia.
 * 
 * @author testis
 */
public abstract class AbstractInvariantScope {

	protected AbstractInvariantScope parent = null;
	
	public AbstractInvariantScope(AbstractInvariantScope parent) {
		this.parent = parent;
	}
	
	public InvariantAdapter find(String invariantId) throws UnknownInvariantException {
		InvariantAdapter result = null;
	
		// Si existe un padre para este scope, delegamos la busqueda en el.
		if (parent != null) {
			result = parent.find(invariantId);
		}
		
		// Si no encontramos resultados, buscamos en nosotros mismos
		if (result == null) {
			result = search(invariantId);
		}
		
		return result;
	}

	protected abstract InvariantAdapter search(String invariantId);
}
