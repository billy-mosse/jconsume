package ar.uba.dc.analysis.memory.callanalyzer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.memory.CallAnalyzer;
import ar.uba.dc.analysis.memory.CallSummary;
import ar.uba.dc.analysis.memory.CallSummaryInContext;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class LoopInvariantAddCallAnalyzer implements CallAnalyzer {

	private static Log log = LogFactory.getLog(LoopInvariantAddCallAnalyzer.class);

	protected LifeTimeOracle lifeTimeOracle;
	protected SymbolicCalculator symbolicCalculator;
	protected ParametricExpressionFactory expressionFactory;
	
	protected Map<HeapPartition, ParametricExpression> residuals;
	// Tiene el maximo temporal necesario para ejecutar el call 
	//protected ParametricExpression tempCall;
	// Tiene el temporal generado por capturar objetos del callee
	protected ParametricExpression resCap;

	public void init(CallStatement callStmt, LifeTimeOracle lifeTimeOracle, SymbolicCalculator symbolicCalculator, ParametricExpressionFactory expressionFactory) {
		log.debug("Analyse call with loop invariant");
		this.lifeTimeOracle = lifeTimeOracle;
		this.symbolicCalculator = symbolicCalculator;
		this.expressionFactory = expressionFactory;
		
		this.residuals = new HashMap<HeapPartition, ParametricExpression>();
		//this.tempCall = expressionFactory.constant(0L);
		this.resCap = expressionFactory.constant(0L);
	}

	@Override
	public void process(CallStatement virtualInvoke, MemorySummary calleeSummary) {
			// Calculamos cuanto residual del callee es capturado. Esta variable refleja eso
		ParametricExpression acumCaptured = expressionFactory.constant(0L);
			// Calculamos cuanto residual aporta el calle a las particiones de caller. Este map se encarga de eso
		Map<HeapPartition, ParametricExpression> acumResiduals = new HashMap<HeapPartition, ParametricExpression>();
			// Actualizamos el maximo temporal necesario para ejecutar el call en base a lo que ya teniamos y el temporal del callee.
		//tempCall = symbolicCalculator.supreme(tempCall, symbolicCalculator.maximize(calleeSummary.getTemporal(), virtualInvoke));
		
		for (HeapPartition calleePartition : calleeSummary.getResidualPartitions()) {
				// Calculamos el vinculo entre caller y callee. 
			HeapPartition callerPartition = lifeTimeOracle.bind(calleePartition, virtualInvoke);
				// El oraculo puede decidir que una particion no se vincule entre caller y callee (se pierda). Esto 
				// depende del analisis de tiempo de vida auxiliar que utilizemos. Por ejemplo, para el escape, las particiones
				// que representan parametros no se vinculan entre caller y callee.
			if (callerPartition != null) {
				ParametricExpression newValue = symbolicCalculator.summate(calleeSummary.getResidual(calleePartition), virtualInvoke);
				
				if (!callerPartition.isTemporal()) {
						// Actualizamos el aporte de esta implementacion al residual de la particion del caller
					ParametricExpression oldValue = acumResiduals.get(callerPartition);
					if (oldValue != null) {
						newValue = symbolicCalculator.add(oldValue, newValue);
					}
					acumResiduals.put(callerPartition, newValue);
				} else {
						// Actualizamos el temporal capturado para esta implementacion
					acumCaptured = symbolicCalculator.add(acumCaptured, newValue);
				}
			}
		}
		
			// Actualizamos el residual que aporta el call comparando esta implementacion con las otras procesadas.
			// nos quedamos con el supremo para c/particion del residual
		for (HeapPartition callerPartition : acumResiduals.keySet()) {
			ParametricExpression oldValue = residuals.get(callerPartition);
			ParametricExpression newValue = acumResiduals.get(callerPartition);
			if (oldValue != null) {
				newValue = symbolicCalculator.add(oldValue, newValue);
			}
			residuals.put(callerPartition, newValue);
		}
		
			// Hacemos lo mismo con el residual capturado. Nos quedamos con el valor mas grande (el supremo) entre todas las
			// implementaciones
		resCap = symbolicCalculator.supreme(resCap, acumCaptured);
	}
	
	public CallSummary buildSummary(CallStatement callStmt) {
		CallSummary result = new CallSummary();
		
		//result.setTemporalCall(tempCall);

		for (HeapPartition partition : residuals.keySet()) {
			result.setResidual(partition, residuals.get(partition));
		}
		
		result.setResidualCaptured(resCap);
		
		return result;
	}	
	public CallSummaryInContext buildSummary2(CallStatement callStmt) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	public void calculateCorrectTotalResiduals(CallStatement virtualInvoke)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
