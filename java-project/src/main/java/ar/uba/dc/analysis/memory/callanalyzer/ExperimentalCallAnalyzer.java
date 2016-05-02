package ar.uba.dc.analysis.memory.callanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.memory.CallAnalyzer;
import ar.uba.dc.analysis.memory.CallSummary;
import ar.uba.dc.analysis.memory.CallSummaryInContext;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.memory.code.CallStatement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

/**
 * 
 * El temporal y residual capturado se calcula al igual que {@link DefaultCallAnalyzer}. Para el residual, asignamos 
 * consumo a aquellas implementaciones que tienen el peor residual (aportan el peor residual al caller). Para esto nos basamos fuertemente
 * en que podemos calcular el supremo de forma exacta. 
 * 
 * Para aproximar en caso de no poder, si sabemos que aporto algo al supremo, dejamos la implementacion.
 */
public class ExperimentalCallAnalyzer implements CallAnalyzer {

	private static Log log = LogFactory.getLog(ExperimentalCallAnalyzer.class);

	protected LifeTimeOracle lifeTimeOracle;
	protected SymbolicCalculator symbolicCalculator;
	protected ParametricExpressionFactory expressionFactory;

	protected Map<HeapPartition, ParametricExpression> residuals;
		// Tiene el maximo temporal necesario para ejecutar el call
	protected ParametricExpression temps;
		// Tiene el temporal generado por capturar objetos del callee
	protected ParametricExpression resCap;
	
		// El supremo de los residuales entre todas las implementaciones
	protected ParametricExpression maxResidual;
	
	protected List<Map<HeapPartition, ParametricExpression>> acumResidualPerImplementation;
	protected List<ParametricExpression> totalResidualPerImplementation;
	
	public void init(CallStatement callStmt, LifeTimeOracle lifeTimeOracle, SymbolicCalculator symbolicCalculator, ParametricExpressionFactory expressionFactory) {
		log.debug("Analyse call with experimental analysis");
		this.lifeTimeOracle = lifeTimeOracle;
		this.symbolicCalculator = symbolicCalculator;
		this.expressionFactory = expressionFactory;		
		
		this.residuals = new HashMap<HeapPartition, ParametricExpression>();	 
		this.temps = expressionFactory.constant(0L);
		this.resCap = expressionFactory.constant(0L);
		this.maxResidual = expressionFactory.constant(0L);
		
		this.acumResidualPerImplementation = new ArrayList<Map<HeapPartition, ParametricExpression>>();
		this.totalResidualPerImplementation = new ArrayList<ParametricExpression>();
	}

	public void process(CallStatement virtualInvoke, MemorySummary calleeSummary) {
		// Calculamos cuanto residual del callee es capturado. Esta variable refleja eso
		ParametricExpression acumCaptured = expressionFactory.constant(0L);
			// Calculamos cuanto residual aporta el calle a las particiones de caller. Este map se encarga de eso
		Map<HeapPartition, ParametricExpression> acumResiduals = new HashMap<HeapPartition, ParametricExpression>();
			// Residual aportado por esta implementacion al caller sin discriminar por particion
		ParametricExpression sumResidual = expressionFactory.constant(0L);
			// Actualizamos el maximo temporal necesario para ejecutar el call en base a lo que ya teniamos y el temporal del callee.
		//temps = symbolicCalculator.supreme(temps, calleeSummary.getTemporal());
		
		for (HeapPartition calleePartition : calleeSummary.getResidualPartitions()) {
				// Calculamos el vinculo entre caller y callee. 
			HeapPartition callerPartition = lifeTimeOracle.bind(calleePartition, virtualInvoke);
				// El oraculo puede decidir que una particion no se vincule entre caller y callee (se pierda). Esto 
				// depende del analisis de tiempo de vida auxiliar que utilizemos. Por ejemplo, para el escape, las particiones
				// que representan parametros no se vinculan entre caller y callee.
			if (callerPartition != null) {
				ParametricExpression calleePartitionResidual = calleeSummary.getResidual(calleePartition);
				if (!callerPartition.isTemporal()) {
						// Actualizamos el aporte de esta implementacion al residual de la particion del caller
					ParametricExpression oldValue = acumResiduals.get(callerPartition);
					ParametricExpression newValue = calleePartitionResidual;
					if (oldValue != null) {
						newValue = symbolicCalculator.add(oldValue, newValue);
					}
					acumResiduals.put(callerPartition, newValue);
					sumResidual = symbolicCalculator.add(sumResidual, calleePartitionResidual);
				} else {
						// Actualizamos el temporal capturado para esta implementacion
					acumCaptured = symbolicCalculator.add(acumCaptured, calleePartitionResidual);
				}
			}
		}
		
		acumResidualPerImplementation.add(acumResiduals);
		totalResidualPerImplementation.add(sumResidual);
					
			// Hacemos lo mismo con el residual capturado. Nos quedamos con el valor mas grande (el supremo) entre todas las
			// implementaciones
		resCap = symbolicCalculator.supreme(resCap, acumCaptured);
	}

	public CallSummary buildSummary(CallStatement callStmt) {
		CallSummary result = new CallSummary();
		
		// Calculamos quienes son las implementaciones que deben aportar por ser aquellas que resultaron ser las que 
		// peor residual tienen
		List<Map<HeapPartition, ParametricExpression>> finalResidualHP = new LinkedList<Map<HeapPartition,ParametricExpression>>();
		
		Iterator<Map<HeapPartition, ParametricExpression>> itAcumResidual = acumResidualPerImplementation.iterator();
		for (ParametricExpression residual : totalResidualPerImplementation) {
			Map<HeapPartition, ParametricExpression> acumResiduals = itAcumResidual.next();
			
			ParametricExpression oldMax = maxResidual.clone();
			maxResidual = symbolicCalculator.supreme(maxResidual, residual);
			
			if (maxResidual.equals(residual)) {
				// Si la implementacion define un nuevo maximo, entonces solo ella debe aportar. Descartamos lo que ya habia
				finalResidualHP = new LinkedList<Map<HeapPartition,ParametricExpression>>();
				finalResidualHP.add(acumResiduals);
			} else if(!maxResidual.equals(oldMax)) {
				// Si la implementacion no define un maximo pero modifica el maximo existente significa que no pudimos determinar
				// el maximo entre el valor actual y el residual de esta implementacion. Para ser sound, agregamos el residual de la misma
				finalResidualHP.add(acumResiduals);
			}
		}
		
		for (Map<HeapPartition, ParametricExpression> acumResiduals : finalResidualHP) {
			for (HeapPartition callerPartition : acumResiduals.keySet()) {
				ParametricExpression oldValue = residuals.get(callerPartition);
				ParametricExpression newValue = acumResiduals.get(callerPartition);
				if (oldValue != null) {
					newValue = symbolicCalculator.supreme(oldValue, newValue);
				}
				residuals.put(callerPartition, newValue);
			}
		}
		
		ParametricExpression tempCall = symbolicCalculator.maximize(temps, callStmt);
		
		if (log.isDebugEnabled()) {
			log.debug(" | | |- Temporal call is: " + tempCall);
		}
		
		//result.setTemporalCall(tempCall);

		for (HeapPartition partition : residuals.keySet()) {
			result.setResidual(partition, symbolicCalculator.summate(residuals.get(partition), callStmt));
		}
		
		result.setResidualCaptured(symbolicCalculator.summate(resCap, callStmt));
		
		return result;
	}
	
	public CallSummaryInContext buildSummary2(CallStatement callStmt) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
