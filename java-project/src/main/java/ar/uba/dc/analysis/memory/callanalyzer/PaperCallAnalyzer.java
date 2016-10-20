package ar.uba.dc.analysis.memory.callanalyzer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.memory.CallSummary;
import ar.uba.dc.analysis.memory.CallSummaryInContext;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;

public class PaperCallAnalyzer {
	

	protected ParametricExpression resCap;
	

	protected ParametricExpression memReq;
	
	protected ParametricExpression MAX_memReqMinusRsd;

	protected ParametricExpression totalResiduals;
	
	protected ParametricExpression MAX_totalResidualsBeforeSummate;
	
	protected ParametricExpression summated_MAX_totalResiduals;
	

	protected Map<HeapPartition, ParametricExpression> residuals;
	

	protected SymbolicCalculator symbolicCalculator;
	protected ParametricExpressionFactory expressionFactory;
	

	
	private static Log log = LogFactory.getLog(PaperCallAnalyzer.class);

	
	public void process(Invocation invocation, PaperMemorySummary invocationSummary) {
		//TODO: hacer
		
	}
	public void init(SymbolicCalculator symbolicCalculator, ParametricExpressionFactory expressionFactory) {
		log.debug("Analyse call with loop invariant");
		//this.lifeTimeOracle = lifeTimeOracle;
		this.symbolicCalculator = symbolicCalculator;
		this.expressionFactory = expressionFactory;
		
		this.residuals = new HashMap<HeapPartition, ParametricExpression>();
		//this.tempCall = expressionFactory.constant(0L);
		this.resCap = expressionFactory.constant(0L);
		this.memReq = expressionFactory.constant(0L);
		this.MAX_memReqMinusRsd = expressionFactory.constant(0L);
		this.totalResiduals = expressionFactory.constant(0L);
		this.MAX_totalResidualsBeforeSummate = expressionFactory.constant(0L);
		this.summated_MAX_totalResiduals= expressionFactory.constant(0L);
	}
	

	public CallSummaryInContext buildSummary(Invocation invocation) {
		CallSummaryInContext result = new CallSummaryInContext();
		
		result.setAcumResiduals(totalResiduals);
		result.setMAX_memoryRequirementMinusRsd(MAX_memReqMinusRsd);
		
		return result;
	}
	

}
