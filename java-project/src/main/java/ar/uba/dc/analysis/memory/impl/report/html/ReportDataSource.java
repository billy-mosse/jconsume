package ar.uba.dc.analysis.memory.impl.report.html;

import java.util.Map;
import java.util.SortedSet;

import ar.uba.dc.analysis.memory.summary.MemorySummary;

import soot.SootClass;

public interface ReportDataSource {

	String getMainClass();

	Map<SootClass, SortedSet<MemorySummary>> getClassIndex();

}
