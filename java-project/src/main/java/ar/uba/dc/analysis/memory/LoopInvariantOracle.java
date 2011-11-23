package ar.uba.dc.analysis.memory;

import ar.uba.dc.analysis.memory.code.CallStatement;

/**
 * Aquel que implemente esta intefaz debe ser capaz de responder si ante un 
 * call el mismo se ejecuta en invarianza de polimorfismo 
 * (lo que seria responder si el call se ejecuta en loop invariant in code motion sense).
 * 
 * @author testis
 */
public interface LoopInvariantOracle {

	public boolean isLoopInvariant(CallStatement callStmt);
	
}
