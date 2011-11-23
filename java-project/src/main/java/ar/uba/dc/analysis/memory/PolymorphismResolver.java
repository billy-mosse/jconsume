package ar.uba.dc.analysis.memory;

import soot.SootClass;
import ar.uba.dc.analysis.memory.code.CallStatement;

public interface PolymorphismResolver {

	/**
	 * Dado un call, indica la clase a la que pertenece la invocacion (si hay muchas posibles implementaciones elije la correcta). 
	 * Esto permite mejorar la precision del call-graph en lo que se refiere a invocaciones 
	 * polimorficas (virtual/interface invokes).
	 * 
	 * Retorna null en caso de no poder dar con una implementacion concreta (en caso de no poder resolver el polimorfismo).
	 */
	SootClass getTarget(CallStatement callStmt);

}
