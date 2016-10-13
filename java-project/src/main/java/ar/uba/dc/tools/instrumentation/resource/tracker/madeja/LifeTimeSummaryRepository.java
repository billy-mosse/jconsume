package ar.uba.dc.tools.instrumentation.resource.tracker.madeja;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import soot.SootMethod;

public class LifeTimeSummaryRepository implements SummaryRepository<LifeTimeSummary, SootMethod> {

	private Map<SootMethod, LifeTimeSummary> data = new HashMap<SootMethod, LifeTimeSummary>();

	@Override
	public boolean contains(SootMethod method) {
		return this.data.containsKey(method);
	}

	@Override
	public LifeTimeSummary get(SootMethod method) {
		return this.data.get(method);
	}
	
	public void put(SootMethod method, LifeTimeSummary summary) {
		this.data.put(method, summary);
	}
	
	public Set<SootMethod> getMethods() {
		return this.data.keySet();
	}	
}
