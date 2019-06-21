package ar.uba.dc.analysis.memory.callanalyzer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionUtils;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartitionBinding;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.DomainSetUtils;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.analysis.common.Line;


public class PaperCallAnalyzer {
	

	protected ParametricExpression resCap;
	

	protected ParametricExpression memReq;
	
	protected ParametricExpression MAX_memReqMinusRsd;

	protected ParametricExpression totalResiduals;
	
	public ParametricExpression getMAX_memReqMinusRsd() {
		return MAX_memReqMinusRsd;
	}



	public ParametricExpression getTotalResiduals() {
		return totalResiduals;
	}


	protected ParametricExpression MAX_totalResidualsBeforeSummate;
	
	protected ParametricExpression summated_MAX_totalResiduals;
	

	protected Map<SimplePaperPointsToHeapPartition, ParametricExpression> residuals;
	

	protected SymbolicCalculator symbolicCalculator;
	protected ParametricExpressionFactory expressionFactory;
	

	
	private static Log log = LogFactory.getLog(PaperCallAnalyzer.class);

	
	public void process(Invocation invocation, Line line, PaperMemorySummary invocationSummary, DomainSet invariant, Set<PaperPointsToHeapPartition> nodes, Set<PaperPointsToHeapPartition> escapeNodes, String fullName) {
		ParametricExpression acumCaptured = expressionFactory.constant(0L);
		// Calculamos cuanto residual aporta el calle a las particiones de caller. Este map se encarga de eso
		Map<SimplePaperPointsToHeapPartition, ParametricExpression> acumResiduals = new HashMap<SimplePaperPointsToHeapPartition, ParametricExpression>();


		
		ParametricExpression acumTotalResiduals = expressionFactory.constant(0L); //porque tengo que inicializarla para cada implementacion

		
		for(PaperPointsToHeapPartitionBinding hpBinding : invocation.getHpBindings())
		{
			SimplePaperPointsToHeapPartition calleePartition = (SimplePaperPointsToHeapPartition) hpBinding.getCallee_hp();
			SimplePaperPointsToHeapPartition callerPartition = (SimplePaperPointsToHeapPartition) hpBinding.getCaller_hp();
			
			boolean isTemporal = !escapeNodes.contains(callerPartition);
			
			
			if(callerPartition != null)
			{
				//Obtengo el rsd del callee asociado a ese nodo
				ParametricExpression rsdFromPartition = invocationSummary.getResidual(calleePartition);
				
				if(rsdFromPartition == null)
				{
					log.debug(invocation.toString());
					log.debug(calleePartition.number);
				}
				
				if(rsdFromPartition != null)
				{	
					Set s = DomainSetUtils.removePrefix(invariant.getVariables());
					Set params = rsdFromPartition.getParameters();
					
					Set<String> new_params = new TreeSet<String>();
					
					//hack, lo mas prolijo seria borrar los parametros vacios
					boolean containsAll = true;
					Iterator<String> it = params.iterator();
					while(it.hasNext())
					{
						String param = it.next();
						if(param.length() > 0)
						{
							new_params.add(param);
						}
					}
					params = new_params;
					containsAll = s.containsAll(params);
								
					if(!containsAll)
					{
						log.error("Falta el binding! " + invariant.toString());
						log.error(rsdFromPartition.toString());
						log.error(line.toString());
						log.error(invocation.toString());
						throw new Error("Falta el binding");
					}
					//hago un summate sobre el invariante
					
					
					//si el dominio no tiene inductivas, tiene sentido no hacer la suma? Y devolver el valor...
					

					ParametricExpression newValue;
					if(invariant.getInductives().isEmpty() || invariant.getInductives().toString().equals("[]"))
						newValue = symbolicCalculator.maximize(rsdFromPartition, invocation, invariant);
					else
						newValue = symbolicCalculator.summate(rsdFromPartition, invocation, invariant);
					
					//lo acumulo
					//rsdFromPartition > 0 => el nodo escapaba del callee
					//tengo que contarlo cuando hago max memReq - Esc + sum Esc
					acumTotalResiduals = symbolicCalculator.add(acumTotalResiduals, rsdFromPartition);
	
					
					if (!isTemporal) {
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
			
		}
		
		
		//Esto esta bien porque las residual partitions....no son temp. Pero habria que cambiarlo, creo
		/*for(SimplePaperPointsToHeapPartition calleePartition : invocationSummary.getResidualPartitions())
		{
			//PaperPointsToHeapPartition callerPartition = new PaperPointsToHeapPartition(); //magia
			
			
			//Al escapeNode del caller le agrego el contexto del callee
			//y me fijo si matchea con alguno de los nodos del callee
			//Y si engancha, me fijo si es temporal o no			
			RichPaperPointsToHeapPartition callerPartition = this.bind(invocation.getHpBindings(), nodes, escapeNodes);	
			
			if(callerPartition != null)
			{
				//Obtengo el rsd del callee asociado a ese nodo
				ParametricExpression rsdFromPartition = invocationSummary.getResidual(calleePartition);
				
				//hago un summate sobre el invariante
				ParametricExpression newValue = symbolicCalculator.summate(rsdFromPartition, invocation, invariant);
				
				//lo acumulo
				//rsdFromPartition > 0 => el nodo escapaba del callee
				//tengo que contarlo cuando hago max memReq - Esc + sum Esc
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
		
		
			// Actualizamos el residual que aporta el call comparando esta implementacion con las otras procesadas.
			// nos quedamos con el supremo para c/particion del residual
		for (PaperPointsToHeapPartition callerPartition : acumResiduals.keySet()) {
			
			SimplePaperPointsToHeapPartition simpleCallerPartition = (SimplePaperPointsToHeapPartition) callerPartition;
			
			ParametricExpression oldValue = residuals.get(simpleCallerPartition);
			ParametricExpression newValue = acumResiduals.get(simpleCallerPartition);
			if (oldValue != null) {
				newValue = symbolicCalculator.supreme(oldValue, newValue);
			}
			residuals.put(simpleCallerPartition, newValue);
		}
		
		resCap = symbolicCalculator.add(resCap, acumCaptured);
		
		// Deberiamos maximizar antes de restar?		
		//Eh no. 
		
		
		//Esto tal vez sea correcto
		acumTotalResiduals = symbolicCalculator.boundIfHasFold(acumTotalResiduals);
				
		
		TreeSet t1 = (TreeSet)DomainSetUtils.removePrefix(invariant.getVariables());
		TreeSet t2 = (TreeSet)invocationSummary.getMemoryRequirement().getParameters();
		if(!t1.containsAll(t2))
		{
			throw new Error("Falta el binding");
		}
		//Tampoco ojo que el invariante de cada linea puede ser distinto
		ParametricExpression memReqMinusRsd = symbolicCalculator.substract(symbolicCalculator.boundIfHasFold(invocationSummary.getMemoryRequirement()), acumTotalResiduals);
		
		
		
		MAX_memReqMinusRsd = symbolicCalculator.supreme(MAX_memReqMinusRsd, symbolicCalculator.maximize(memReqMinusRsd, invocation, invariant));
		
		
		ParametricExpression result;
		
		
		if(invariant.getInductives().isEmpty() || invariant.getInductives().toString().equals("[]"))
			result = symbolicCalculator.maximize(acumTotalResiduals, invocation, invariant);
		else
			result = symbolicCalculator.summate(acumTotalResiduals, invocation, invariant);
		
		totalResiduals = symbolicCalculator.supreme(totalResiduals, result);
		
		MAX_totalResidualsBeforeSummate = symbolicCalculator.supreme(MAX_totalResidualsBeforeSummate, acumTotalResiduals);	
		
	}
	

	
	public static SimplePaperPointsToHeapPartition bind(PaperPointsToHeapPartition hpCallee, Line line, Set<PaperPointsToHeapPartition> nodes, String fullName) {
		
		// Ignoramos los parametros

		
		RichPaperPointsToHeapPartition richHpCallee = (RichPaperPointsToHeapPartition) hpCallee;
		
		if (richHpCallee.getNode().isParam()) {
			//no entiendo
			//ver figura 5 del paper de iwako.
			return null;
		}
		

		
			// Construyo el nodo en el caller que basicamente es el mismo nodo pero cambiando el contexto (el escape era k-sensitivo)
		
		
		PaperNode escapeNode = richHpCallee.getNode().clone();
		escapeNode.changeContext(line.magicalStmtName);
		
		// La particion puede estar como nodo o bien contenida dentro del mismo 
		PaperNode nodeToBind = null;
		Integer number = 0;
		String belongsTo = "";
		
		
		try
			{
			for (PaperPointsToHeapPartition hp_callerNode : nodes) {
				RichPaperPointsToHeapPartition richHpCallerNode = (RichPaperPointsToHeapPartition)hp_callerNode;
				if (richHpCallerNode.getNode().accept(escapeNode)) {
					nodeToBind = (PaperNode) richHpCallerNode.getNode();
					number = richHpCallerNode.getNumber();
					belongsTo = richHpCallerNode.getBelongsTo();
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
		
		
		//boolean isTemporal = true;
		
		
		/*//Primero consigo el nodo y despues me fijo si es temporal!
		for(PaperPointsToHeapPartition hp : escapeNodes)
		{
			RichPaperPointsToHeapPartition richHpEscape = (RichPaperPointsToHeapPartition) hp;
			if(richHpEscape.getNode().equals(nodeToBind))
			{
				isTemporal = false;
				break;
			}
		}*/
		
		//Para que sirve esto?
		//isTemporal = isTemporal || invariantProvider.captureAllPartitions(callStmt);
	
			// Con eso ya puedo armar el heapPartition del caller.		
		SimplePaperPointsToHeapPartition returnHp = new SimplePaperPointsToHeapPartition(number);
		
		return returnHp;
	}

	public void init(SymbolicCalculator symbolicCalculator, ParametricExpressionFactory expressionFactory) {
		log.debug("Analyse call with loop invariant");
		//this.lifeTimeOracle = lifeTimeOracle;
		this.symbolicCalculator = symbolicCalculator;
		this.expressionFactory = expressionFactory;
		
		this.residuals = new HashMap<SimplePaperPointsToHeapPartition, ParametricExpression>();
		//this.tempCall = expressionFactory.constant(0L);
		this.resCap = expressionFactory.constant(0L);
		this.memReq = expressionFactory.constant(0L);
		this.MAX_memReqMinusRsd = expressionFactory.constant(0L);
		this.totalResiduals = expressionFactory.constant(0L);
		this.MAX_totalResidualsBeforeSummate = expressionFactory.constant(0L);
		this.summated_MAX_totalResiduals= expressionFactory.constant(0L);
		
		
	}
	
	public void calculateCorrectTotalResiduals(Invocation invocation, DomainSet invariant)
	{
		//TODO: no tengo la anotacion
		totalResiduals = symbolicCalculator.summateIfClassCalledChangedDuringLoop(MAX_totalResidualsBeforeSummate, totalResiduals, invocation, invariant);
	}
	

	//Totalmente al pedo pongo el parametro
	public PaperCallSummaryInContext buildSummary(Line callInvocation) {
		PaperCallSummaryInContext result = new PaperCallSummaryInContext();
		
		result.setAcumResiduals(totalResiduals);
		result.setMAX_memoryRequirementMinusRsd(MAX_memReqMinusRsd);
		
		for (PaperPointsToHeapPartition partition : residuals.keySet()) {			
			
			result.setResidual((SimplePaperPointsToHeapPartition) partition, residuals.get(partition));
		}
		
		
		return result;
	}
	

}
