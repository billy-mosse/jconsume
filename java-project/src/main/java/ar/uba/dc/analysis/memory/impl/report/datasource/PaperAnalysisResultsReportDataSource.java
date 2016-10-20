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
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.report.html.ReportDataSource;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class PaperAnalysisResultsReportDataSource implements ReportDataSource<String, PaperMemorySummary> {

	private static Log log = LogFactory.getLog(PaperAnalysisResultsReportDataSource.class);
	
	protected String mainClass;
	
	protected Map<String, PaperMemorySummary> results;
	
	public PaperAnalysisResultsReportDataSource(String mainMethod, Map<String, PaperMemorySummary> results) {
		this.mainClass = mainMethod;
		this.results = results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, SortedSet<PaperMemorySummary>> getClassIndex() {
		Map<String, SortedSet<PaperMemorySummary>> summariesGroupedByClass = new TreeMap<String, SortedSet<PaperMemorySummary>>(new BeanComparator("name"));
		
		for (PaperMemorySummary summary : results.values()) {
			log.debug("Sorting summary for [" + summary.getTarget() + "]");
			String clazz = summary.getTarget().getDeclaringClass();
					SortedSet<PaperMemorySummary> summariesOfClass = summariesGroupedByClass.get(clazz);
			if (summariesOfClass == null) {
				summariesOfClass = new TreeSet<PaperMemorySummary>(new BeanComparator("target.declaration"));
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
