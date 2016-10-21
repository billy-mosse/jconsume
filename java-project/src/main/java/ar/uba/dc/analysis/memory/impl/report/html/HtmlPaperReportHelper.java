package ar.uba.dc.analysis.memory.impl.report.html;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class HtmlPaperReportHelper {

	private MemorySummaryInterpreter<PaperMemorySummary> interpreter;
	
	public HtmlPaperReportHelper(MemorySummaryInterpreter<PaperMemorySummary> interpreter) {
		this.interpreter = interpreter;
	}
	
	public String getName(PaperMemorySummary summary) {
		return StringEscapeUtils.escapeHtml(summary.getTarget().getDeclaration());
	}
	
	public String getLinkId(PaperMemorySummary summary) {
		return summary.getTarget().getDeclaringClass() + summary.getTarget().getNumber();
	}
	
	public String getLink(PaperMemorySummary summary) {
		return "#" + summary.getTarget().getDeclaringClass() + summary.getTarget().getNumber();
	}
	
	public String getFormalParameters(PaperMemorySummary summary) {
		return StringUtils.join(summary.getParameters(), ", ");
	}
	
	public String getResidual(PaperMemorySummary summary) {
		return interpreter.getResidual(summary);
	}

	public String getMemReq(PaperMemorySummary summary) {
		return interpreter.getMemReq(summary);
	}

	
	public boolean hasEscapeDetail(PaperMemorySummary summary) {
		return interpreter.hasEscapeDetail(summary);
	}
	
	public String getEscapeDetail(PaperMemorySummary summary) {
		return interpreter.getEscapeDetail(summary);
	}
	
	public boolean hasMemoryDetail(PaperMemorySummary summary) {
		return interpreter.hasMemoryDetail(summary);
	}
	
	public String getMemoryDetail(PaperMemorySummary summary) {
		return interpreter.getMemoryDetail(summary);
	}
	
	public Map<String, String> getResidualPartitions(PaperMemorySummary summary) {
		return interpreter.getResidualPartitions(summary);
	}
}
