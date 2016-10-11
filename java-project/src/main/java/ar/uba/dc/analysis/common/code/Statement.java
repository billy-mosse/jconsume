package ar.uba.dc.analysis.common.code;

import java.util.Set;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import soot.SootMethod;
import soot.jimple.Stmt;

public interface Statement {

	public <T> T apply(StatementVisitor<T> visitor);
	
	/**
	 * Devuelve el {@link Stmt} representado por la instancia del objeto {@link Statement}
	 */
	public Stmt getStatement();

	/**
	 * Devuelve el metodo al que pertenece el statement representado
	 */
	public SootMethod belongsTo();
	
		
	/**
	 * Devuelve el valor para el contador relativo de statements representado. Por ejemplo, si el statement es el 3er new del 
	 * metodo debemos retornar un 2 (el contador es base 0).
	 */
	Long getCounter();

	public String getIntermediateRepresentationName();

	public Set<IntermediateRepresentationParameter> getIntermediateRepresentationParameters();
}
