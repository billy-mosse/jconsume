package ar.uba.dc.analysis.memory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import heros.solver.Pair;

public class PaperIntraproceduralAnalysis {

	
	public PaperMemorySummaryFactory getSummaryFactory() {
		return summaryFactory;
	}

	public void setSummaryFactory(PaperMemorySummaryFactory summaryFactory) {
		this.summaryFactory = summaryFactory;
	}

	protected PaperMemorySummaryFactory summaryFactory;
	private static Log log = LogFactory.getLog(PaperIntraproceduralAnalysis.class);

	protected CountingTheory ct;
	protected ParametricExpressionFactory expressionFactory;
		
	protected SymbolicCalculator sa;
	
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

	public PaperMemorySummary run(IntermediateRepresentationMethod ir_method) {
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
			
			log.debug(bound.toString());
			
			//TODO: preocuparse por polimorfismo
			
			if(newLine.getInvocations().size() != 1) throw new NotImplementedException("Hola! Ver que pasa aca");
			
			Invocation inv = newLine.getInvocations().get(0);
			PaperPointsToHeapPartition partition = inv.getHeapPartition();
			
			memReq = sa.add(memReq, bound);
			
			if (!partition.isTemporal()) {
				log.debug(" | | |- Partition escapes");
				
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
				//tempLocal = sa.add(tempLocal, bound);
			}
			
		}
		
		for(Line callInvocation : calls)
		{
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
		
		log.info("Memory of " + ir_method.getName() + " is " + memReq.toString());
		return summary;
	}


}
