package ar.uba.dc.analysis.common;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import soot.SootMethod;

public interface MethodInformationProvider {

	

	Boolean isAnalyzable(SootMethod method);

	Boolean hasFreshGraph(SootMethod method);

	Boolean hasConservativaGraph(SootMethod method);
	
	Boolean hasNonConservativaGraph(SootMethod method);

	Boolean isExcluded(SootMethod method);	

	Boolean isAnalyzable(IntermediateRepresentationMethod ir_method);

	Boolean hasFreshGraph(IntermediateRepresentationMethod ir_method);

	Boolean hasConservativaGraph(IntermediateRepresentationMethod ir_method);
	
	Boolean hasNonConservativaGraph(IntermediateRepresentationMethod ir_method);

	Boolean isExcluded(IntermediateRepresentationMethod ir_method);
}
