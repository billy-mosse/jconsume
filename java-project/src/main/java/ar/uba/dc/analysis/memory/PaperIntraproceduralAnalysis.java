package ar.uba.dc.analysis.memory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class PaperIntraproceduralAnalysis {

	protected PaperMemorySummaryFactory summaryFactory;
	private static Log log = LogFactory.getLog(PaperIntraproceduralAnalysis.class);

	public PaperMemorySummary run(IntermediateRepresentationMethod ir_method) {
		log.debug(" |- Intraprocedural Analysis for: " + ir_method.toString());
		
		PaperMemorySummary summary = summaryFactory.initialSummary(ir_method);
		return null;
	}

}
