package ar.uba.dc.analysis.memory.impl;

import ar.uba.dc.analysis.memory.impl.report.html.ReportDataSource;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import soot.SootMethod;

public interface ReportWriter<S, T> {

	void write(ReportDataSource<S, T> ds);

}
