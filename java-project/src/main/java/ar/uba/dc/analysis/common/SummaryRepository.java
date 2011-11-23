package ar.uba.dc.analysis.common;

import soot.SootMethod;

public interface SummaryRepository<T> {

	T get(SootMethod method);

	boolean contains(SootMethod method);
	
}
