package ar.uba.dc.analysis.common;

import soot.SootMethod;

public interface SummaryFactory<T, S> {

	T conservativeGraph(S method, boolean withEffect);

	T freshGraph(S method);
	
}
