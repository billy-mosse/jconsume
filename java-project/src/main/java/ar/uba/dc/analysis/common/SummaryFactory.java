package ar.uba.dc.analysis.common;

import soot.SootMethod;

public interface SummaryFactory<T> {

	T conservativeGraph(SootMethod method, boolean withEffect);

	T freshGraph(SootMethod method);
	
}
