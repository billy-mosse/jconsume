package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.analysis.memory.impl.report.html.MemorySummaryInterpreter;
import ar.uba.dc.analysis.memory.summary.MemReqSummarizer;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.ResidualSummarizer;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

public class SummaryInterpreter  implements MemorySummaryInterpreter {

	protected ResidualSummarizer<MemorySummary, ParametricExpression> residualSummarizer;
	
	protected MemReqSummarizer<MemorySummary, ParametricExpression> memReqSummarizer;
	
	private boolean isccFormat = false;

	public MemReqSummarizer<MemorySummary, ParametricExpression> getMemReqSummarizer() {
		return memReqSummarizer;
	}

	public void setMemReqSummarizer(
			MemReqSummarizer<MemorySummary, ParametricExpression> memReqSummarizer) {
		this.memReqSummarizer = memReqSummarizer;
	}

	public ResidualSummarizer<MemorySummary, ParametricExpression> getResidualSummarizer() {
		return residualSummarizer;
	}

	public void setResidualSummarizer(
			ResidualSummarizer<MemorySummary, ParametricExpression> residualSummarizer) {
		this.residualSummarizer = residualSummarizer;
	}

	@Override
	public String getResidual(MemorySummary summary) {
		return expressionToString(residualSummarizer.getResidual(summary));
	}

	@Override
	public String getTemporal(MemorySummary summary) {
		return expressionToString(summary.getTemporal());
	}
	
	protected String expressionToString2(ParametricExpression expr) {
		return expr.toString();
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
	@Override
	public String getEscapeDetail(MemorySummary summary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMemoryDetail(MemorySummary summary) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean hasEscapeDetail(MemorySummary summary) {
		return false;
	}

	@Override
	public boolean hasMemoryDetail(MemorySummary summary) {
		return false;
	}
	

	@Override
	public Map<String, String> getResidualPartitions(MemorySummary summary) {
		
		Map<String, String> residual = new LinkedHashMap<String, String>();
		for (HeapPartition part : summary.getResidualPartitions()) {
			MadejaHeapPartition hp = (MadejaHeapPartition) part;
			residual.put(hp.key(), this.expressionToString(summary.getResidual(hp)));
		}
		
		return  residual;
	}

	@Override
	public String getMemReq(MemorySummary summary) {
		
		ParametricExpression memReq = this.memReqSummarizer.getMemReq(summary);
		return this.isccFormat ? memReq.toString() : expressionToString(memReq);
	}
	
	
	@Override
	public String getMemReq2(MemorySummary summary) {
		
		//BILLY, cambiado
		ParametricExpression memReq = this.memReqSummarizer.getMemReq2(summary);
		return this.isccFormat ? memReq.toString() : expressionToString(memReq);
	}
	
	
}
