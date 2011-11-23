package ar.uba.dc.analysis.memory.impl.report.datasource;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.memory.impl.report.html.ReportDataSource;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class AnalysisResultsReportDataSource implements ReportDataSource {

	private static Log log = LogFactory.getLog(AnalysisResultsReportDataSource.class);
	
	protected String mainClass;
	
	protected Map<SootMethod, MemorySummary> results;
	
	public AnalysisResultsReportDataSource(SootMethod mainMethod, Map<SootMethod, MemorySummary> results) {
		this.mainClass = mainMethod.getDeclaringClass().getName();
		this.results = results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<SootClass, SortedSet<MemorySummary>> getClassIndex() {
		Map<SootClass, SortedSet<MemorySummary>> summariesGroupedByClass = new TreeMap<SootClass, SortedSet<MemorySummary>>(new BeanComparator("name"));
		
		for (MemorySummary summary : results.values()) {
			log.debug("Sorting summary for [" + summary.getTarget() + "]");
			SootClass clazz = summary.getTarget().getDeclaringClass();
			SortedSet<MemorySummary> summariesOfClass = summariesGroupedByClass.get(clazz);
			if (summariesOfClass == null) {
				summariesOfClass = new TreeSet<MemorySummary>(new BeanComparator("target.declaration"));
				summariesGroupedByClass.put(clazz, summariesOfClass);
			}
			summariesOfClass.add(summary);
		}

		return summariesGroupedByClass;
	}

	@Override
	public String getMainClass() {
		return mainClass;
	}
	
	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}
}