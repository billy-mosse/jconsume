package ar.uba.dc.analysis.memory.summary.impl;

import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.PaperResidualSummarizer;
import ar.uba.dc.analysis.memory.summary.ResidualSummarizer;

public class PaperAddAllResidualSummarizer implements PaperResidualSummarizer<PaperMemorySummary, ParametricExpression> {

	protected ParametricExpressionFactory expressionFactory;
	
	protected SymbolicCalculator symbolicCalculator;
	
	@Override
	public ParametricExpression getResidual(PaperMemorySummary summary) {
		ParametricExpression[] residualExpressions = new ParametricExpression[summary.getResidualPartitions().size()];
		
		int idx = 0;
		for (PaperPointsToHeapPartition residualPartition : summary.getResidualPartitions()) {
			residualExpressions[idx] = summary.getResidual(residualPartition);
			idx++;
		}
		
		ParametricExpression residual = null; 
		
		if (residualExpressions.length == 0) {
			residual = expressionFactory.constant(0L);
		} else {
			residual = symbolicCalculator.add(residualExpressions);
		}
		
		return residual;
	}
	
	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public void setSymbolicCalculator(SymbolicCalculator symbolicCalculator) {
		this.symbolicCalculator = symbolicCalculator;
	}
}
