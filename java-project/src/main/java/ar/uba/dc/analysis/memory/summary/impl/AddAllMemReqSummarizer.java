package ar.uba.dc.analysis.memory.summary.impl;

import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemReqSummarizer;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class AddAllMemReqSummarizer implements MemReqSummarizer<MemorySummary, ParametricExpression> {

protected ParametricExpressionFactory expressionFactory;
	
	protected SymbolicCalculator symbolicCalculator;
	
	@Override
	public ParametricExpression getMemReq(MemorySummary summary) {
		ParametricExpression[] residualExpressions = new ParametricExpression[summary.getResidualPartitions().size()];
		
		int idx = 0;
		for (HeapPartition residualPartition : summary.getResidualPartitions()) {
			residualExpressions[idx] = summary.getResidual(residualPartition);
			idx++;
		}
		
		ParametricExpression residual = null; 
		
		if (residualExpressions.length == 0) {
			residual = expressionFactory.constant(0L);
		} else {
			residual = symbolicCalculator.add(residualExpressions);
		}
		
		return this.symbolicCalculator.add(summary.getTemporal(), residual);
	}
	
	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public void setSymbolicCalculator(SymbolicCalculator symbolicCalculator) {
		this.symbolicCalculator = symbolicCalculator;
	}
}
