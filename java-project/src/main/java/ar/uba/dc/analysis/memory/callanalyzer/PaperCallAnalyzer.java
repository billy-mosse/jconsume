package ar.uba.dc.analysis.memory.callanalyzer;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
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
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.analysis.common.Line;


public class PaperCallAnalyzer {
	

	protected ParametricExpression resCap;
	

	protected ParametricExpression memReq;
	
	protected ParametricExpression MAX_memReqMinusRsd;

	protected ParametricExpression totalResiduals;
	
	protected ParametricExpression MAX_totalResidualsBeforeSummate;
	
	protected ParametricExpression summated_MAX_totalResiduals;
	

	protected Map<PaperPointsToHeapPartition, ParametricExpression> residuals;
	

	protected SymbolicCalculator symbolicCalculator;
	protected ParametricExpressionFactory expressionFactory;
	

	
	private static Log log = LogFactory.getLog(PaperCallAnalyzer.class);

	
	public void process(Invocation invocation, PaperMemorySummary invocationSummary, DomainSet invariant, Set<PaperPointsToHeapPartition> nodes, Set<PaperPointsToHeapPartition> escapeNodes) {
		ParametricExpression acumCaptured = expressionFactory.constant(0L);
		// Calculamos cuanto residual aporta el calle a las particiones de caller. Este map se encarga de eso
		Map<PaperPointsToHeapPartition, ParametricExpression> acumResiduals = new HashMap<PaperPointsToHeapPartition, ParametricExpression>();


		
		ParametricExpression acumTotalResiduals = expressionFactory.constant(0L); //porque tengo que inicializarla para cada implementacion

		
		for(PaperPointsToHeapPartition calleePartition : invocationSummary.getResidualPartitions())
		{
			//PaperPointsToHeapPartition callerPartition = new PaperPointsToHeapPartition(); //magia
			
			PaperPointsToHeapPartition callerPartition = this.bind(calleePartition, invocation, invocationSummary, nodes, escapeNodes);	
			
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
		for (PaperPointsToHeapPartition callerPartition : acumResiduals.keySet()) {
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
	
	
	//TODO:
	//Arreglar esto, esta horrible (para que uso HPs si puedo usar nodos directamente?
	//Los nomres tambien estan feos
	private PaperPointsToHeapPartition bind(PaperPointsToHeapPartition hpCallee, Invocation invocation, PaperMemorySummary invocationSummary, Set<PaperPointsToHeapPartition> nodes, Set<PaperPointsToHeapPartition> escapeNodes) {
		
		// Ignoramos los parametros
		if (hpCallee.getNode().isParam()) {
			return null;
		}
		

		
			// Construyo el nodo en el caller que basicamente es el mismo nodo pero cambiando el contexto (el escape era k-sensitivo)
		PaperNode escapeNode = hpCallee.getNode().clone();
		escapeNode.changeContext(invocation.getFullNameCalled());
		
		// La particion puede estar como nodo o bien contenida dentro del mismo 
		PaperNode nodeToBind = null;
		
		try
			{
			for (PaperPointsToHeapPartition hp_callerNode : nodes) {
				if (hp_callerNode.getNode().accept(escapeNode)) {
					nodeToBind = hp_callerNode.getNode();
					break;
				}
			}
		}
		catch (Exception e)
		{
			throw new Error();
		}
		
			// Podria ocurrir que el callstmt sea un virtual call. En este caso el escape podria resolver 
			// el polimorfismo y darse cuenta de que hay una implementacion que no es invocada pero para memoria
			// no darnos cuenta. Si esto ocurre, no consideramos las particiones de la implementacion que escape
			// ha ignorado. Esto es correcto porque si escape ignora implementaciones, en el summary de escape no 
			// figuraran estos nodos y por tanto en el summary de memoria tampoco.
		if (nodeToBind == null) {
			return null;
		}
		
			// A partir del summary me fijo si el nodo escapa o no.
		
		
		boolean isTemporal = false;
		
		for(PaperPointsToHeapPartition hp : escapeNodes)
		{
			if(hp.equals(nodeToBind))
				isTemporal = true;
		}
		
		//Para que sirve esto?
		//isTemporal = isTemporal || invariantProvider.captureAllPartitions(callStmt);
	
			// Con eso ya puedo armar el heapPartition del caller.
		PaperPointsToHeapPartition hpCaller = new PaperPointsToHeapPartition(nodeToBind, isTemporal);
		
		return hpCaller;
	}

	public void init(SymbolicCalculator symbolicCalculator, ParametricExpressionFactory expressionFactory) {
		log.debug("Analyse call with loop invariant");
		//this.lifeTimeOracle = lifeTimeOracle;
		this.symbolicCalculator = symbolicCalculator;
		this.expressionFactory = expressionFactory;
		
		this.residuals = new HashMap<PaperPointsToHeapPartition, ParametricExpression>();
		//this.tempCall = expressionFactory.constant(0L);
		this.resCap = expressionFactory.constant(0L);
		this.memReq = expressionFactory.constant(0L);
		this.MAX_memReqMinusRsd = expressionFactory.constant(0L);
		this.totalResiduals = expressionFactory.constant(0L);
		this.MAX_totalResidualsBeforeSummate = expressionFactory.constant(0L);
		this.summated_MAX_totalResiduals= expressionFactory.constant(0L);
		
		
	}
	

	//Totalmente al pedo pongo el parametro
	public PaperCallSummaryInContext buildSummary(Line callInvocation) {
		PaperCallSummaryInContext result = new PaperCallSummaryInContext();
		
		result.setAcumResiduals(totalResiduals);
		result.setMAX_memoryRequirementMinusRsd(MAX_memReqMinusRsd);
		
		for (PaperPointsToHeapPartition partition : residuals.keySet()) {
			result.setResidual(partition, residuals.get(partition));
		}	
		
		
		return result;
	}
	

}
