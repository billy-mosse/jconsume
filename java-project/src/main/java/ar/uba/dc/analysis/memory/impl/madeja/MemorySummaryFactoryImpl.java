package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummaryFactory;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;

public class MemorySummaryFactoryImpl implements MemorySummaryFactory {
	
	private static Log log = LogFactory.getLog(MemorySummaryFactoryImpl.class);
	
	private InvariantProvider invariantProvider;

	private ParametricExpressionFactory expressionFactory;
	
	@Override
	public MemorySummary initialSummary(SootMethod method) {
		
		log.debug(" |- Building initial summary for method [" + method + "]");
		
		DomainSet methodRequirements = invariantProvider.getRequeriments(method);
		Set<String> methodParameters = invariantProvider.getRelevantParameters(method);
		ParametricExpression initialTemporal = expressionFactory.constant(0L, methodRequirements);
		ParametricExpression initialMemoryRequirement = expressionFactory.constant(0L, methodRequirements);
		MemorySummaryImpl summary =  new MemorySummaryImpl(method, methodParameters, initialTemporal, initialMemoryRequirement);
		
		log.debug(" |- Building process finished.");
		return summary;
	}

	public InvariantProvider getInvariantProvider() {
		return invariantProvider;
	}

	public void setInvariantProvider(InvariantProvider invariantProvider) {
		this.invariantProvider = invariantProvider;
	}

	public ParametricExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}

	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	
	
}
