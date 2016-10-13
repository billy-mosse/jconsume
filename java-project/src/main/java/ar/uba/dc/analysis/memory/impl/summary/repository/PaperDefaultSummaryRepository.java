package ar.uba.dc.analysis.memory.impl.summary.repository;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.SummaryFactory;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class PaperDefaultSummaryRepository implements SummaryRepository<PaperMemorySummary, IntermediateRepresentationMethod>{

	private static Log log = LogFactory.getLog(PaperDefaultSummaryRepository.class);
	
	protected SummaryFactory<PaperMemorySummary, IntermediateRepresentationMethod> summaryFactory;
	
	
	protected MethodInformationProvider methodInformationProvider; //este tiene para sootMethod y IR_method
	
	public PaperMemorySummary get(IntermediateRepresentationMethod method) {
		PaperMemorySummary elem = null;
		
		log.debug("Search default summary for [" + method + "]");
		
    	if (methodInformationProvider.hasFreshGraph(method)) {
    		elem = summaryFactory.freshGraph(method);
    		log.debug("Method is fresh");
    	}
    	
    	if (methodInformationProvider.hasConservativaGraph(method)) {
    		elem = summaryFactory.conservativeGraph(method, false);
    		log.debug("Method is conservaitve");
    	}
    	
    	if (methodInformationProvider.hasNonConservativaGraph(method)) {
    		// impure with side-effect, unless otherwise specified
    		elem = summaryFactory.conservativeGraph(method, true);
    		log.debug("Method is non conservaitve");
    	}
    	
    	return elem;
	}
	
	public boolean contains(IntermediateRepresentationMethod m) {
		return !methodInformationProvider.isAnalyzable(m);
	}

	public void setSummaryFactory(SummaryFactory<PaperMemorySummary, IntermediateRepresentationMethod> summaryFactory) {
		this.summaryFactory = summaryFactory;
	}

	public void setMethodInformationProvider(MethodInformationProvider methodInformationProvider) {
		this.methodInformationProvider = methodInformationProvider;
	}
}
