package ar.uba.dc.analysis.memory.summary;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;

public interface MemReqSummarizer <M extends MemorySummary, P extends ParametricExpression> {

	public P getMemReq(M summary);
	
}