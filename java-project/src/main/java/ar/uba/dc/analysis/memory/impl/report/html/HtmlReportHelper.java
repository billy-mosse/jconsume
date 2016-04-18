package ar.uba.dc.analysis.memory.impl.report.html;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class HtmlReportHelper {

	private MemorySummaryInterpreter interpreter;
	
	public HtmlReportHelper(MemorySummaryInterpreter interpreter) {
		this.interpreter = interpreter;
	}
	
	public String getName(MemorySummary summary) {
		return StringEscapeUtils.escapeHtml(summary.getTarget().getDeclaration());
	}
	
	public String getLinkId(MemorySummary summary) {
		return summary.getTarget().getDeclaringClass().getName() + summary.getTarget().getNumber();
	}
	
	public String getLink(MemorySummary summary) {
		return "#" + summary.getTarget().getDeclaringClass().getName() + summary.getTarget().getNumber();
	}
	
	public String getFormalParameters(MemorySummary summary) {
		return StringUtils.join(summary.getParameters(), ", ");
	}
	
	public String getTemporal(MemorySummary summary) {
		return interpreter.getTemporal(summary);
	}
	
	public String getResidual(MemorySummary summary) {
		return interpreter.getResidual(summary);
	}

	public String getMemReq(MemorySummary summary) {
		return interpreter.getMemReq(summary);
	}

	public String getMemReq2(MemorySummary summary) {
		return interpreter.getMemReq2(summary);
	}
	
	public boolean hasEscapeDetail(MemorySummary summary) {
		return interpreter.hasEscapeDetail(summary);
	}
	
	public String getEscapeDetail(MemorySummary summary) {
		return interpreter.getEscapeDetail(summary);
	}
	
	public boolean hasMemoryDetail(MemorySummary summary) {
		return interpreter.hasMemoryDetail(summary);
	}
	
	public String getMemoryDetail(MemorySummary summary) {
		return interpreter.getMemoryDetail(summary);
	}
	
	public Map<String, String> getResidualPartitions(MemorySummary summary) {
		return interpreter.getResidualPartitions(summary);
	}
}
