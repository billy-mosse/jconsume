package ar.uba.dc.invariant;

import java.util.Set;

import soot.SootMethod;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.code.Statement;
import ar.uba.dc.barvinok.expression.DomainSet;

/**
 * Interfaz que determina el contrato que cumple un proveedor de invariantes
 * 
 * @author testis
 */
public interface InvariantProvider {

	/** Enum especificando la opreacion a la que puede estar asociado un invariante */
	enum Operation { 
		MAXIMIZE("max"), 
		SUMMATE("sum");
		
		private String operationString;
		
		Operation(String operationString) {
			this.operationString = operationString;
		}
		
		public String getString() {
			return operationString;
		}
	};
	
	DomainSet getInvariant(Statement stmt, Operation operation);
	
	DomainSet getInvariant(Statement stmt);

	/**
	 * Retorna los parametros relevantes asociado a un metodo
	 */
	Set<String> getRelevantParameters(SootMethod method);

	/**
	 * Devuelve los requerimientos de un metodo. Los mismos son un predicado al igual que los invariantes
	 */
	DomainSet getRequeriments(SootMethod method);

	/**
	 * Indica con true que la variable no cambia a lo largo de un ciclo. 
	 * Esto define que hay invarianza de polimorfismo dentro del cuerpo de un ciclo para 
	 * ese statement.
	 * 
	 * Conocer esta informacion permite optimizar el analisis de polimorfismo para los calls.
	 * 
	 * Lo mas conservador es devolver false pues esto obliga a tomar el peor escenario posible.
	 */
	boolean isLoopInvariant(Statement stmt);
	
	/**
	 * Indica si las particiones asociadas al statement deben ser capturadas al margen de lo que diga
	 * el {@link LifeTimeOracle}.
	 * @return true en caso de que se deba capturar. Null o false en caso de tener que respetar lo que dice el analisis.
	 */
	boolean captureAllPartitions(Statement stmt);
}
