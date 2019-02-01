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

	
	@Override
	public boolean want(SootMethod method, String mainClass) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void build(String mainClass) {
		// TODO Auto-generated method stub
		
	}
}
