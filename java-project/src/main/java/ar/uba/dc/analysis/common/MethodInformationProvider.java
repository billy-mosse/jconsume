package ar.uba.dc.analysis.common;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import soot.SootMethod;

public interface MethodInformationProvider {

	

	Boolean isAnalyzable(SootMethod method);

	Boolean hasFreshGraph(SootMethod method);

	Boolean hasConservativaGraph(SootMethod method);
	
	Boolean hasNonConservativeGraph(SootMethod method);

	Boolean isExcluded(SootMethod method);	

	Boolean isAnalyzable(IntermediateRepresentationMethod ir_method);

	Boolean hasFreshGraph(IntermediateRepresentationMethod ir_method);

	Boolean hasConservativeGraph(IntermediateRepresentationMethod ir_method);
	
	Boolean hasNonConservativeGraph(IntermediateRepresentationMethod ir_method);

	Boolean isExcluded(IntermediateRepresentationMethod ir_method);
}
