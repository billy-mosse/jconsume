package ar.uba.dc.analysis.memory.summary;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;

public interface MemReqSummarizer <M extends MemorySummary, P extends ParametricExpression> {

	public P getMemReq(M summary);
	public P getNonHTMLMemReq(M summary);
	
}