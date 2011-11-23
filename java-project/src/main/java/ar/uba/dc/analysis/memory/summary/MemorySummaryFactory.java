package ar.uba.dc.analysis.memory.summary;

import soot.SootMethod;

public interface MemorySummaryFactory {

	public MemorySummary initialSummary(SootMethod method);

}
