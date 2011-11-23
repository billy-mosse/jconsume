package ar.uba.dc.analysis.common;

import soot.SootMethod;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootMethodFilter;

/**
 * Filtro que acepta todos los metodos. Util si no queremos filtrar nada
 * de un callgraph pero neceistamos armar un {@link DirectedCallGraph}
 */
public class AcceptAllMethodFilter implements SootMethodFilter {

	public static final SootMethodFilter INSTANCE = new AcceptAllMethodFilter();

	public boolean want(SootMethod method) {
		return true;
	}
}
