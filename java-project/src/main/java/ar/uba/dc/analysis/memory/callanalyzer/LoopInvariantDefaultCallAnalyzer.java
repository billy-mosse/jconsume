package ar.uba.dc.analysis.memory.callanalyzer;

import java.util.HashMap;
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
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

import ar.uba.dc.barvinok.BarvinokUtils;







import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.BarvinokCalculator;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.invariant.InvariantProvider;



/**
 * Estamos ante invarianza de polimorfismo. Este es el mejor caso para el algoritmo:
 * Podemos operar (summate, maximize, etc) con c/expresion que va a parar a una misma particion del caller y luego buscar el supremo.
 * Esto, dependiendo del tipo de expresion simbolica que manejemos y la forma de resolver las operaciones
 * puede ser mas exacto (o bien mas facil de calcular y por lo tanto mejorar la performance del analisis) 
 */
public class LoopInvariantDefaultCallAnalyzer implements CallAnalyzer {

	private static Log log = LogFactory.getLog(LoopInvariantDefaultCallAnalyzer.class);
	
	protected LifeTimeOracle lifeTimeOracle;
	protected SymbolicCalculator symbolicCalculator;
	protected ParametricExpressionFactory expressionFactory;
	
	protected Map<HeapPartition, ParametricExpression> residuals;
	// Tiene el maximo temporal necesario para ejecutar el call 
	//protected ParametricExpression tempCall;
	// Tiene el temporal generado por capturar objetos del callee
	protected ParametricExpression resCap;
	

	protected ParametricExpression memReq;
	
	protected ParametricExpression MAX_memReqMinusRsd;

	protected ParametricExpression totalResiduals;
	
	protected ParametricExpression MAX_totalResidualsBeforeSummate;
	
	protected ParametricExpression summated_MAX_totalResiduals;

	public void init(CallStatement callStmt, LifeTimeOracle lifeTimeOracle, SymbolicCalculator symbolicCalculator, ParametricExpressionFactory expressionFactory) {
		log.debug("Analyse call with loop invariant");
		this.lifeTimeOracle = lifeTimeOracle;
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

	@Override
	public void process(CallStatement virtualInvoke, MemorySummary calleeSummary) {
			// Calculamos cuanto residual del callee es capturado. Esta variable refleja eso
		ParametricExpression acumCaptured = expressionFactory.constant(0L);
			// Calculamos cuanto residual aporta el calle a las particiones de caller. Este map se encarga de eso
		Map<HeapPartition, ParametricExpression> acumResiduals = new HashMap<HeapPartition, ParametricExpression>();
		
			// Actualizamos el maximo temporal necesario para ejecutar el call en base a lo que ya teniamos y el temporal del callee.
		//tempCall = symbolicCalculator.supreme(tempCall, symbolicCalculator.maximize(calleeSummary.getTemporal(), virtualInvoke));
		
		
		
		//memReq = symbolicCalculator.supreme(memReq,symbolicCalculator.maximize(calleeSummary.getMemoryRequirement(), virtualInvoke));
		
		
		ParametricExpression acumTotalResiduals = expressionFactory.constant(0L); //porque tengo que inicializarla para cada implementacion
		
		
		for (HeapPartition calleePartition : calleeSummary.getResidualPartitions()) {
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
		ParametricExpression memReqMinusRsd = symbolicCalculator.substract(symbolicCalculator.boundIfHasFold(calleeSummary.getMemoryRequirement()), acumTotalResiduals);
		
		MAX_memReqMinusRsd = symbolicCalculator.supreme(MAX_memReqMinusRsd, symbolicCalculator.maximize(memReqMinusRsd, virtualInvoke));
		
		totalResiduals = symbolicCalculator.supreme(totalResiduals, symbolicCalculator.summate(acumTotalResiduals, virtualInvoke));
		
		MAX_totalResidualsBeforeSummate = symbolicCalculator.supreme(MAX_totalResidualsBeforeSummate, acumTotalResiduals);

	}
	
	/*public CallSummary buildSummary(CallStatement callStmt) {
		CallSummary result = new CallSummary();
		
		//result.setTemporalCall(tempCall);

		for (HeapPartition partition : residuals.keySet()) {
			result.setResidual(partition, residuals.get(partition));
		}
		
		result.setResidualCaptured(resCap);
		
		result.setMemoryRequirement(MAX_memReqMinusRsd);
		
		result.setTotalResidualsIfCallee(totalResiduals);
		
		return result;
	}*/	
	
	
	
	public void calculateCorrectTotalResiduals(CallStatement virtualInvoke)
	{
		totalResiduals = symbolicCalculator.summateIfClassCalledChangedDuringLoop(MAX_totalResidualsBeforeSummate, totalResiduals, virtualInvoke);
	}
	
	public CallSummaryInContext buildSummary2(CallStatement callStmt) {
		CallSummaryInContext result = new CallSummaryInContext();
		
		//result.setTemporalCall(tempCall);

		for (HeapPartition partition : residuals.keySet()) {
			result.setResidual(partition, residuals.get(partition));
		}
		
		result.setAcumResiduals(totalResiduals);
		result.setMAX_memoryRequirementMinusRsd(MAX_memReqMinusRsd);
		
		return result;
	}

	public CallSummary buildSummary(CallStatement callStmt) {
		throw new UnsupportedOperationException("Will not be implemented. Fix");
	}	
	
}
