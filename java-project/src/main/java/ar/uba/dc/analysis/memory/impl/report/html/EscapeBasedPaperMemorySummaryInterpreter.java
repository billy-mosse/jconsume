package ar.uba.dc.analysis.memory.impl.report.html;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemReqSummarizer;
import ar.uba.dc.analysis.memory.summary.PaperMemReqSummarizer;
import ar.uba.dc.analysis.memory.summary.PaperResidualSummarizer;
import ar.uba.dc.analysis.memory.summary.ResidualSummarizer;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.util.location.MethodLocationStrategy;

public class EscapeBasedPaperMemorySummaryInterpreter implements MemorySummaryInterpreter<PaperMemorySummary> {

	protected PaperResidualSummarizer<PaperMemorySummary, ParametricExpression> residualSummarizer;
	
	protected PaperMemReqSummarizer<PaperMemorySummary, ParametricExpression> memReqSummarizer;
	
	
	protected MethodLocationStrategy escapeLocationStrategy;
	
	protected MethodLocationStrategy memoryLocationStrategy;
	
	protected MethodLocationStrategy reportEscapeLocationStrategy;
	
	protected MethodLocationStrategy reportMemoryLocationStrategy;
	
	@Override
	public String getResidual(PaperMemorySummary summary) {
		return expressionToString(residualSummarizer.getResidual(summary));
	}
	
	@Override
	public String getNonHTMLResidual(PaperMemorySummary summary) {
		return expressionToNonHTMLString(residualSummarizer.getResidual(summary));
	}
	
	protected String expressionToString(ParametricExpression expr) {
		String result = null;
		
		if (expr instanceof BarvinokParametricExpression) {
			BarvinokParametricExpression paramExpr = (BarvinokParametricExpression) expr;
			LinkedList<String> parts = new LinkedList<String>(); 
			
			for (QuasiPolynomial q : paramExpr.getExpression().getPieces()) {
				if (!q.getPolynomial().equals("0")) {
					parts.add(q.asString());
				}
			}
			
			if (parts.isEmpty()) {
				parts.add("0");
			}
			
			if (parts.size() > 1) {
				result = "<ul><li>" + StringUtils.join(parts, "</li><li>") + "</li></ul>";
			} else {
				result = parts.getFirst();
			}
		} else {
			result = expr.toString();
		}
		return result;
	}
	
	protected String expressionToNonHTMLString(ParametricExpression expr) {
		String result = null;
		
		if (expr instanceof BarvinokParametricExpression) {
			BarvinokParametricExpression paramExpr = (BarvinokParametricExpression) expr;
			LinkedList<String> parts = new LinkedList<String>(); 
			
			for (QuasiPolynomial q : paramExpr.getExpression().getPieces()) {
				if (!q.getPolynomial().equals("0")) {
					parts.add(q.asString());
				}
			}
			
			if (parts.isEmpty()) {
				parts.add("0");
			}
			
			if (parts.size() > 1) {
				result = StringUtils.join(parts, ";") ;
			} else {
				result = parts.getFirst();
			}
		} else {
			result = expr.toString();
		}
		return result;
	}
	
	@Override
	public boolean hasEscapeDetail(PaperMemorySummary summary) {
		moveDotIfExists(summary, escapeLocationStrategy, reportEscapeLocationStrategy);
		
		return new File(reportEscapeLocationStrategy.getLocation(summary.getTarget())).exists();
	}
	
	@Override
	public String getEscapeDetail(PaperMemorySummary summary) {
		moveDotIfExists(summary, escapeLocationStrategy, reportEscapeLocationStrategy);
		
		return reportEscapeLocationStrategy.getLocation(summary.getTarget());
	}

	@Override
	public boolean hasMemoryDetail(PaperMemorySummary summary) {
		moveDotIfExists(summary, memoryLocationStrategy, reportMemoryLocationStrategy);
		
		return new File(reportMemoryLocationStrategy.getLocation(summary.getTarget())).exists();
	}
	
	@Override
	public String getMemoryDetail(PaperMemorySummary summary) {
		moveDotIfExists(summary, memoryLocationStrategy, reportMemoryLocationStrategy);
		
		return reportMemoryLocationStrategy.getLocation(summary.getTarget());
	}
	
	protected void moveDotIfExists(PaperMemorySummary summary, MethodLocationStrategy srcStrategy, MethodLocationStrategy destStrategy) {
		File srcFile = new File(srcStrategy.getLocation(summary.getTarget()));
		File destFile = new File(destStrategy.getLocation(summary.getTarget()));
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {}
	}
	
	public void setResidualSummarizer(PaperResidualSummarizer<PaperMemorySummary, ParametricExpression> residualSummarizer) {
		this.residualSummarizer = residualSummarizer;
	}

	public void setEscapeLocationStrategy(MethodLocationStrategy strategy) {
		this.escapeLocationStrategy = strategy;
	}
	
	public void setReportEscapeLocationStrategy(MethodLocationStrategy strategy) {
		this.reportEscapeLocationStrategy = strategy;
	}
	
	public void setMemoryLocationStrategy(MethodLocationStrategy strategy) {
		this.memoryLocationStrategy = strategy;
	}
	
	public void setReportMemoryLocationStrategy(MethodLocationStrategy strategy) {
		this.reportMemoryLocationStrategy = strategy;
	}
	
	

	public PaperMemReqSummarizer<PaperMemorySummary, ParametricExpression> getMemReqSummarizer() {
		return memReqSummarizer;
	}

	public void setMemReqSummarizer(
			PaperMemReqSummarizer<PaperMemorySummary, ParametricExpression> memReqSummarizer) {
		this.memReqSummarizer = memReqSummarizer;
	}

	@Override
	public Map<String, String> getResidualPartitions(PaperMemorySummary summary) {
		return new HashMap<String, String>(); //TODO
	}

	@Override
	public String getMemReq(PaperMemorySummary summary) {
		return this.expressionToString(this.memReqSummarizer.getMemReq(summary));
	}
	
	@Override
	public String getNonHTMLMemReq(PaperMemorySummary summary) {
		return this.expressionToNonHTMLString(this.memReqSummarizer.getMemReq(summary));
	}
}
