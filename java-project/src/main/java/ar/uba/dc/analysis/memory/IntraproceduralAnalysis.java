package ar.uba.dc.analysis.memory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import ar.uba.dc.analysis.memory.code.CallStatement;
import ar.uba.dc.analysis.memory.code.MethodBody;
import ar.uba.dc.analysis.memory.code.MethodDecorator;
import ar.uba.dc.analysis.memory.code.NewStatement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummaryFactory;

public class IntraproceduralAnalysis {

	private static Log log = LogFactory.getLog(IntraproceduralAnalysis.class);
	
	protected InterproceduralAnalysis interprocedural;
	
	protected ParametricExpressionFactory expressionFactory;
	
	protected MemorySummaryFactory summaryFactory;
	
	protected MethodDecorator methodDecorator;
	
	protected CountingTheory ct;
	
	protected LifeTimeOracle ea;
	
	protected SymbolicCalculator sa;
	
	private long analyzedNews = 0;
	
	public IntraproceduralAnalysis(	InterproceduralAnalysis interprocedural, 
									ParametricExpressionFactory expressionFactory, 
									MemorySummaryFactory summaryFactory,
									MethodDecorator methodDecorator, 
									CountingTheory countingTheory,
									LifeTimeOracle lifeTimeOracle,
									SymbolicCalculator symbolicCalculator) {
		this.interprocedural = interprocedural;
		this.expressionFactory = expressionFactory; 
		this.summaryFactory = summaryFactory;
		this.methodDecorator = methodDecorator; 
		this.ct = countingTheory;
		this.ea = lifeTimeOracle;
		this.sa = symbolicCalculator;
	}

	
	
	public long getAnalyzedNews() {
		return analyzedNews;
	}



	public void setAnalyzedNews(long analyzedNews) {
		this.analyzedNews = analyzedNews;
	}



	public MemorySummary run(SootMethod method) {
		log.debug(" |- Intraprocedural Analysis for: " + method.toString());
		MemorySummary summary = summaryFactory.initialSummary(method);
		
		/*
		 * Acumula el consumo del metodo bajo analisis generado por los objetos creados cuyo tiempo de vida
		 * es el metodo bajo analisis. Es otras palabras, acumula el consumo de objetos creados que no escapan al 
		 * metodo. 
		 */
		ParametricExpression tempLocal = expressionFactory.constant(0L);
		
		/*
		 * Acumula la memoria temporal necesaria para realizar los calls ejecutados por el metodo bajo analisis. 
		 */
		ParametricExpression tempCalls = expressionFactory.constant(0L);
		
		/*
		 * Acumula la memoria generada por las llamadas a los metodos invocados. Esta variable acumula el consumo temporal
		 * generado a partir de los objetos generados por los metodos invocados que son capturados por el metodo bajo 
		 * analisis (su tiempo de vida finaliza con el metodo bajo analisis).
		 */
		ParametricExpression resCaptured = expressionFactory.constant(0L);
		
		log.debug(" |- Building Method abstraction");
		// Generamos una abstraccion del body de un metodo util para poder procesar el analisis.
		// De esta forma podemos decorar los statements con lo necesario para los analisis auxiliares
		MethodBody abstraction = methodDecorator.decorate(method);

		log.debug(" |- Processing new statements");
		for (NewStatement newStmt : abstraction.getNewStatements()) {
			
			this.analyzedNews++;
			
			if (log.isDebugEnabled()) {
				log.debug(" | |- Processing statement " + newStmt.toString());
			}
				
			ParametricExpression bound = ct.count(newStmt);
			if (log.isDebugEnabled()) {
				log.debug(" | | |- New is executed [" +  bound + "] times");
			}
			
			HeapPartition partition = ea.getPartition(newStmt);
			
			if (!partition.isTemporal()) {
				log.debug(" | | |- Partition escapes");
				ParametricExpression oldValue = summary.getResidual(partition);
				ParametricExpression newValue = bound;
				if (oldValue != null) {
					newValue = sa.add(bound, oldValue);
				}
				summary.setResidual(partition, newValue);
			} else {
				log.debug(" | | |- Partition is temporal");
				tempLocal = sa.add(tempLocal, bound);
			}
		}
		
		log.debug(" |- Processing call statements");
		for (CallStatement callStmt : abstraction.getCallStatements()) {
			if (log.isDebugEnabled()) {
				log.debug(" | |- Processing statement " + callStmt.toString());
			}
			
			CallSummary callSummary = interprocedural.analyseCall(callStmt);
			
			ParametricExpression newTempCalls = sa.supreme(tempCalls, callSummary.getTemporalCall());
			ParametricExpression newResCaptured = sa.add(resCaptured, callSummary.getResidualCaptured());
			
			if (log.isDebugEnabled()) {
				log.debug("Update tempCalls from [" + tempCalls + "] to [" + newTempCalls + "]");
				log.debug("Update tempCalls from [" + resCaptured + "] to [" + newResCaptured + "]");
				
			}
			
			tempCalls = newTempCalls;
			resCaptured = newResCaptured;
			
			for (HeapPartition partition : callSummary.getResidualPartitions()) {
				ParametricExpression oldValue = summary.getResidual(partition);
				ParametricExpression newValue = callSummary.getResidual(partition);
				if (oldValue != null) {
					newValue = sa.add(oldValue, newValue);
					if (log.isDebugEnabled()) {
						log.debug("Update partition residual with [" + callSummary.getResidual(partition) + "]. Partition [" + partition + "], old value [" + oldValue + "], new value [" + newValue + "]");
					}
				}
				summary.setResidual(partition, newValue);
			}
		}
		
		summary.setTemporal(sa.add(tempLocal, tempCalls, resCaptured));
	
		return summary;
	}
}