package ar.uba.dc.analysis.common;

import soot.SootMethod;

public interface MethodInformationProvider {

	Boolean isAnalyzable(SootMethod method);

	Boolean hasFreshGraph(SootMethod method);

	Boolean hasConservativaGraph(SootMethod method);
	
	Boolean hasNonConservativaGraph(SootMethod method);

	Boolean isExcluded(SootMethod method);
}
