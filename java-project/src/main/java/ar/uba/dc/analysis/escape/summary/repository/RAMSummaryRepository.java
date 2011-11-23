package ar.uba.dc.analysis.escape.summary.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.EscapeSummary;

public class RAMSummaryRepository implements SummaryRepository<EscapeSummary> {

	private Map<SootMethod, EscapeSummary> data;
	
	public RAMSummaryRepository() {
		this.data = new HashMap<SootMethod, EscapeSummary>();
	}
	
	@Override
	public boolean contains(SootMethod method) {
		return data.containsKey(method);
	}

	@Override
	public EscapeSummary get(SootMethod method) {
		return data.get(method);
	}
	
	public void put(SootMethod method, EscapeSummary summary) {
		data.put(method, summary);
	}

	public void clear() {
		data.clear();
	}

	public Collection<EscapeSummary> values() {
		return data.values();
	}
}