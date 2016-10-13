package ar.uba.dc.analysis.common;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import soot.SootMethod;

public interface SummaryRepository<T, S> {

	T get(S method);	
	
	boolean contains(S method);
	
}
