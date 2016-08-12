package ar.uba.dc.analysis.memory.callanalyzer;

import ar.uba.dc.analysis.memory.CallAnalyzer;
import ar.uba.dc.analysis.memory.CallSummary;
import ar.uba.dc.analysis.memory.CallSummaryInContext;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.LoopInvariantOracle;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class LoopInvariantOracleBasedCallAnalyzer implements CallAnalyzer {

	protected LoopInvariantOracle loopInvariantOracle;
	
	protected CallAnalyzer withLoopInvariant;
	protected CallAnalyzer withoutLoopInvariant;
	
	protected CallAnalyzer target;
	
	public void init(CallStatement callStmt, LifeTimeOracle lifeTimeOracle, SymbolicCalculator symbolicCalculator, ParametricExpressionFactory expressionFactory) {
		target = withoutLoopInvariant;
		if (loopInvariantOracle.isLoopInvariant(callStmt)) {
			target = withLoopInvariant;
		}
		
		target.init(callStmt, lifeTimeOracle, symbolicCalculator, expressionFactory);
	}

	public void process(CallStatement virtualInvoke, MemorySummary calleeSummary) {
		target.process(virtualInvoke, calleeSummary);
	}

	public CallSummary buildSummary(CallStatement callStmt) {
		return target.buildSummary(callStmt);
	}

	public CallSummaryInContext buildSummary2(CallStatement callStmt) {
		throw new UnsupportedOperationException("Not implemented yet");
	}	
	
	public void setLoopInvariantOracle(LoopInvariantOracle loopInvariantOracle) {
		this.loopInvariantOracle = loopInvariantOracle;
	}

	public void setWithLoopInvariant(CallAnalyzer withLoopInvariant) {
		this.withLoopInvariant = withLoopInvariant;
	}

	public void setWithoutLoopInvariant(CallAnalyzer withoutLoopInvariant) {
		this.withoutLoopInvariant = withoutLoopInvariant;
	}
	
	public void calculateCorrectTotalResiduals(CallStatement virtualInvoke)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
