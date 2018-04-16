package ar.uba.dc.analysis.memory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.LineWithParent;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.PaperStmtNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionUtils;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionUtils;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
//import heros.solver.Pair;
import ar.uba.dc.soot.StatementId;

public class PaperIntraproceduralAnalysis {

	
	public PaperIntraproceduralAnalysis(PaperInterproceduralAnalysis paperInterproceduralAnalysis,
			PaperMemorySummaryFactory summaryFactory, CountingTheory ct,
			ParametricExpressionFactory expressionFactory, SymbolicCalculator sa, List<LineWithParent> badLines, List<LineWithParent> badLinesCalls) {

		this.interprocedural = paperInterproceduralAnalysis;
		this.summaryFactory = summaryFactory;
		this.ct = ct;
		this.expressionFactory  = expressionFactory;
		this.sa = sa;
		this.badLines = badLines;
		this.badLinesCalls = badLinesCalls;
		
	}

	/*public PaperIntraproceduralAnalysis() {
		this.ct = null;
	}*/

	public PaperIntraproceduralAnalysis() {
		// TODO Auto-generated constructor stub
	}


	public PaperMemorySummaryFactory getSummaryFactory() {
		return summaryFactory;
	}

	public void setSummaryFactory(PaperMemorySummaryFactory summaryFactory) {
		this.summaryFactory = summaryFactory;
	}

	
	private static Log log = LogFactory.getLog(PaperIntraproceduralAnalysis.class);
	
	protected PaperMemorySummaryFactory summaryFactory;
	protected CountingTheory ct;
	protected ParametricExpressionFactory expressionFactory;		
	protected SymbolicCalculator sa;
	protected PaperInterproceduralAnalysis interprocedural;
	protected List<LineWithParent> badLines;
	protected List<LineWithParent> badLinesCalls;
	
	
	
	public SymbolicCalculator getSa() {
		return sa;
	}

	public void setSa(SymbolicCalculator sa) {
		this.sa = sa;
	}

	public ParametricExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}

	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public CountingTheory getCt() {
		return ct;
	}

	public void setCt(CountingTheory ct) {
		this.ct = ct;
	}
	
	/*public HeapPartition getPartition(Line newLine) {
		StmtNode node = new StmtNode(new StatementId(newStmt.belongsTo(), newStmt.getStatement()), true, sensitivity);
		
		PaperStmtNode node = new PaperStmtNode();
		
			// Obtengo el escape summary del metodo bajo analisis
		EscapeSummary escapeSummary = escapeSummaryProvider.get(newStmt.belongsTo());
		
			// Hay nodos que pueden representar a muchos news. Vemos si alguno de ellos escapa
		boolean isTemporal = true;
		Node finalNode = node;
		
		// A partir del summary me fijo si el nodo escapa o no
		for (Node escapeNode : escapeSummary.getEscaping()) {
			if (escapeNode.accept(node)) {
				isTemporal = false;
				finalNode = escapeNode;
				break;
			}
		}
		
		// El invariantProvider puede pisar la decision tomada en base al summary
		isTemporal = isTemporal || invariantProvider.captureAllPartitions(newStmt);
		
		PointsToHeapPartition hp = new PointsToHeapPartition(finalNode, isTemporal);
		
		return hp;
	}	*/

	public PaperMemorySummary run(IntermediateRepresentationMethod ir_method) {
		
		ParametricExpression MAX_memReqMinusRsdFromCallees = expressionFactory.constant(0L);		
		ParametricExpression acumResidualsFromCallees = expressionFactory.constant(0L);
		
		log.debug(" |- Intraprocedural Analysis for: " + ir_method.getName());
		
		PaperMemorySummary summary = summaryFactory.initialSummary(ir_method);
		

		List<Line> news = new ArrayList<Line>();
		List<Line> calls = new ArrayList<Line>();

		ParametricExpression memReq = expressionFactory.constant(0L);
		
		
		for(Line line: ir_method.getBody().getLines())
		{
			if(line.isNewStatement())
			{
				news.add(line);
			}
			else
			{
				calls.add(line);
			}
		}
				
		for(Line newLine : news)
		{
			//ParametricExpression bound = ct.count(newLine);
			
			/**
			 * Hago el bound, pregunto si escapa, y si escapa voy acumulando
			 */
			
			
			
			log.debug(" | |- Processing statement " + newLine.toHumanReadableString());
			
			ParametricExpression bound = ct.count(newLine);
			
			if (BarvinokParametricExpressionUtils.isInfinite(bound))
			{
				DomainSet inv = newLine.getInvariant();
				Set<String> unboundedInductives = ct.getUnboundedInductives(inv);
				
				
				
				LineWithParent newLineWithParent = new LineWithParent(newLine, ir_method.toString(), unboundedInductives);
				this.badLines.add(newLineWithParent);
			}
			
			log.debug(bound.toString());
			
			//TODO: preocuparse por polimorfismo
			
			if(newLine.getInvocations().size() != 1) throw new NotImplementedException("Hola! Ver que pasa aca");
			
			Invocation inv = newLine.getInvocations().get(0);
			
			//TODO: No tendre que hacer un getPartition() como el bind?
			SimplePaperPointsToHeapPartition partition = (SimplePaperPointsToHeapPartition) inv.getHeapPartition();
			
					
			
			if(ir_method.getEscapeNodes().contains(partition)) {
				log.debug(" | | |- Partition escapes");
				
				//Si escapa, MemReq = Esc = bound, entonces no tengo que tomar supremo entre (MemReq-Esc) y bound
				// Porque el maximo entre algo y cero es ese algo
				acumResidualsFromCallees = sa.add(acumResidualsFromCallees, bound);
				
				
				ParametricExpression oldValue = summary.getResidual(partition);
				
				//Bound es el invariante del new statement
				ParametricExpression newValue = bound;
				
				//Y voy acumulando los bounds
				if (oldValue != null) {
					newValue = sa.add(bound, oldValue);
				}
				
				log.debug(" | | |- Setting residual...");
				summary.setResidual(partition, newValue);
			} else {
				log.debug(" | | |- Partition is temporal");
				
				//Si es temporal, MemReq - Esc = MemReq y tomo el supremo con eso!
				MAX_memReqMinusRsdFromCallees = sa.add(MAX_memReqMinusRsdFromCallees, bound);
				
				//tempLocal = sa.add(tempLocal, bound);
			}
			
		}
		for(Line callInvocation : calls)
		{
			
			Line c = callInvocation.clone();
			log.debug(callInvocation.getName());
			
			PaperCallSummaryInContext callSummary = interprocedural.analyseCall(callInvocation, ir_method);
			
			ParametricExpression memReqMinusRsdFromCallee = callSummary.getMAX_memoryRequirementMinusRsd();
			ParametricExpression acumResidualsFromCallee = callSummary.getAcumResiduals();
			
			log.debug("_____________________" + callInvocation.getFullNameCalled());
			log.debug("_____________________" + "memReqMinusRsdFromCallee: " + memReqMinusRsdFromCallee.toString());
			log.debug("_____________________" + "acumResidualsFromCallee: " + acumResidualsFromCallee.toString());
					
			if (BarvinokParametricExpressionUtils.isInfinite(memReqMinusRsdFromCallee) || 
					BarvinokParametricExpressionUtils.isInfinite(acumResidualsFromCallee)
					)
			{
				
				DomainSet inv = callInvocation.getInvariant();
				Set<String> unboundedInductives = ct.getUnboundedInductives(inv);
				
				boolean isInfinite = !unboundedInductives.isEmpty();
				
				//El binding puede siempre ser con variables no acotadas del invariante asi que hay que revisarlo siempre
				
				//Ademas, tenemos los siguientes CASOS:
				
				//Consumo finito, invariante finito: no hago nada ams
				
				//Consumo infinito, invariante finito: si el consumo es infinito ya lo trate en el calleee asi que nada
				
				//Consumo finito, invariante infinito: tengo que ver que inductivas del invariante estan jodiendo
				
				//Consumo infinito, invariante infinito: tengo que ver que inductivas del invariante estan jodiendo
				
				//Asi que solo importa si el invariante es infinito. Vale la pena hacer un COUNT
				
				//esto es medio feo, pero estoy contando el invariante nomas
				ParametricExpression bound = ct.count(callInvocation);
				
				if (BarvinokParametricExpressionUtils.isInfinite(bound))
				{
					unboundedInductives = ct.getUnboundedInductives(inv);
				}
				
				LineWithParent lineWithParent = new LineWithParent(callInvocation, ir_method.toString(), unboundedInductives, callSummary.getUnboundedBindingVariables());
				this.badLinesCalls.add(lineWithParent);
			}
			
			//fruta para ver si anda
			MAX_memReqMinusRsdFromCallees = sa.supreme(MAX_memReqMinusRsdFromCallees, memReqMinusRsdFromCallee);
			
			
			acumResidualsFromCallees = sa.add(acumResidualsFromCallees, acumResidualsFromCallee);			
			
			
			for (SimplePaperPointsToHeapPartition partition : callSummary.getResidualPartitions()) {
				ParametricExpression oldValue = summary.getResidual(partition);
				ParametricExpression newValue = callSummary.getResidual(partition);
				//callSummaryRes = sa.add(callSummaryRes, newValue );
				if (oldValue != null) {
					newValue = sa.add(oldValue, newValue);
					if (log.isDebugEnabled()) {
						log.debug("Update partition residual with [" + callSummary.getResidual(partition) + "]. Partition [" + partition + "], old value [" + oldValue + "], new value [" + newValue + "]");
					}
				}
				summary.setResidual(partition, newValue);
			}
			
			
			
			
			/**
			 * Tengo que procesar cada posible invocacion y tomar el max del ML y RSD, enchufarle la info de invariante e ir acumulando eso
			 * 
			 * Pregunta: no podria en un futuro "para cada (a1...an)" ver que invocacion me da el maximo ML y maximo rsd? Seria una mejor cota.
			 * Respuesta: no, no podria, porque es parametrico. En realidad si, pero es un quilombo impracticable
			 * 
			 * Respuesta 2: no sirve porque puede haber cosas que "no estemos viendo"...los invariantes son una abstraccion - una sobreaproximacion? - 
			 * y creo que puede pasar que el parametro de la invocacion vaya cambiando o algo asi
			 */
		}
		memReq = sa.add(memReq, acumResidualsFromCallees);
		memReq = sa.add(memReq, MAX_memReqMinusRsdFromCallees);
		
		
		//summary.setTemporal(sa.add(tempLocal, tempCalls, resCaptured));
		
		summary.setMemoryRequirement(memReq);

		
		
		log.info("Memory of " + ir_method.getName() + " is " + memReq.toString());
		return summary;
	}


}
