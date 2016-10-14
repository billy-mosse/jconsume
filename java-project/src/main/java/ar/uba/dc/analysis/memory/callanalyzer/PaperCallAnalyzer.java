package ar.uba.dc.analysis.memory.callanalyzer;

import java.util.HashMap;
import java.util.Map;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class PaperCallAnalyzer {

	
	protected ParametricExpressionFactory expressionFactory;

	
	public void process(Invocation invocation, PaperMemorySummary calleeSummary) {		
		ParametricExpression acumCaptured = expressionFactory.constant(0L);
		// Calculamos cuanto residual aporta el calle a las particiones de caller. Este map se encarga de eso
		Map<HeapPartition, ParametricExpression> acumResiduals = new HashMap<HeapPartition, ParametricExpression>();
		
			// Actualizamos el maximo temporal necesario para ejecutar el call en base a lo que ya teniamos y el temporal del callee.
		//tempCall = symbolicCalculator.supreme(tempCall, symbolicCalculator.maximize(calleeSummary.getTemporal(), virtualInvoke));
		
		
		
		//memReq = symbolicCalculator.supreme(memReq,symbolicCalculator.maximize(calleeSummary.getMemoryRequirement(), virtualInvoke));
		
		
		ParametricExpression acumTotalResiduals = expressionFactory.constant(0L); //porque tengo que inicializarla para cada implementacion
		
		/*for (HeapPartition calleePartition : calleeSummary.getResidualPartitions()) {
			// Calculamos el vinculo entre caller y callee. 
			HeapPartition callerPartition = lifeTimeOracle.bind(calleePartition, virtualInvoke);			
				// El oraculo puede decidir que una particion no se vincule entre caller y callee (se pierda). Esto 
				// depende del analisis de tiempo de vida auxiliar que utilizemos. Por ejemplo, para el escape, las particiones
				// que representan parametros no se vinculan entre caller y callee.
			if (callerPartition != null) {
				ParametricExpression rsdFromPartition = calleeSummary.getResidual(calleePartition);
				ParametricExpression newValue = symbolicCalculator.summate(rsdFromPartition, virtualInvoke);
				
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
		}*/
	
	}
	
	
}
