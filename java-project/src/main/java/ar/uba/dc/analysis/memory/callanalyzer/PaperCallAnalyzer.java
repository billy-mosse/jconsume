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
import ar.uba.dc.analysis.memory.PaperCallSummaryInContext;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;

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

	
	public void process(Invocation invocation, PaperMemorySummary invocationSummary, DomainSet invariant) {
		ParametricExpression acumCaptured = expressionFactory.constant(0L);
		// Calculamos cuanto residual aporta el calle a las particiones de caller. Este map se encarga de eso
		Map<PaperPointsToHeapPartition, ParametricExpression> acumResiduals = new HashMap<PaperPointsToHeapPartition, ParametricExpression>();


		
		ParametricExpression acumTotalResiduals = expressionFactory.constant(0L); //porque tengo que inicializarla para cada implementacion

		
		for(PaperPointsToHeapPartition calleePartition : invocationSummary.getResidualPartitions())
		{
			PaperPointsToHeapPartition callerPartition = new PaperPointsToHeapPartition(); //magia
			
			if(callerPartition != null)
			{
				ParametricExpression rsdFromPartition = invocationSummary.getResidual(calleePartition);
				ParametricExpression newValue = symbolicCalculator.summate(rsdFromPartition, invocation, invariant);
				
				//no va a tener mas de un fold piece
				acumTotalResiduals = symbolicCalculator.add(acumTotalResiduals, rsdFromPartition);

				
				if (!callerPartition.isTemporal()) {
					acumCaptured = symbolicCalculator.add(acumCaptured, rsdFromPartition);

						// Actualizamos el aporte de esta implementacion al residual de la particion del caller
					ParametricExpression oldValue = acumResiduals.get(callerPartition);
					if (oldValue != null) {
						newValue = symbolicCalculator.add(oldValue, newValue);
					}
					acumResiduals.put(callerPartition, newValue);					
				}
				
			}
		
		}
		
		
			// Actualizamos el residual que aporta el call comparando esta implementacion con las otras procesadas.
			// nos quedamos con el supremo para c/particion del residual
		for (HeapPartition callerPartition : acumResiduals.keySet()) {
			ParametricExpression oldValue = residuals.get(callerPartition);
			ParametricExpression newValue = acumResiduals.get(callerPartition);
			if (oldValue != null) {
				newValue = symbolicCalculator.supreme(oldValue, newValue);
			}
			residuals.put(callerPartition, newValue);
		}
		
		resCap = symbolicCalculator.add(resCap, acumCaptured);
		
		// Deberiamos maximizar antes de restar?		
		//Eh no. 
		
		
		//Esto tal vez sea correcto
		acumTotalResiduals = symbolicCalculator.boundIfHasFold(acumTotalResiduals);
				
		//Tampoco ojo que el invariante de cada linea puede ser distinto
		ParametricExpression memReqMinusRsd = symbolicCalculator.substract(symbolicCalculator.boundIfHasFold(invocationSummary.getMemoryRequirement()), acumTotalResiduals);
		
		MAX_memReqMinusRsd = symbolicCalculator.supreme(MAX_memReqMinusRsd, symbolicCalculator.maximize(memReqMinusRsd, invocation, invariant));
		
		totalResiduals = symbolicCalculator.supreme(totalResiduals, symbolicCalculator.summate(acumTotalResiduals, invocation, invariant));
		
		MAX_totalResidualsBeforeSummate = symbolicCalculator.supreme(MAX_totalResidualsBeforeSummate, acumTotalResiduals);	
		
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
	

	public PaperCallSummaryInContext buildSummary(Invocation invocation) {
		PaperCallSummaryInContext result = new PaperCallSummaryInContext();
		
		result.setAcumResiduals(totalResiduals);
		result.setMAX_memoryRequirementMinusRsd(MAX_memReqMinusRsd);
		
		return result;
	}
	

}
