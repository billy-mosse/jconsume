package ar.uba.dc.analysis.memory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.jimple.AssignStmt;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.MethodDecorator;
import ar.uba.dc.analysis.common.code.NewStatement;
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


	private int computeSize(NewStatement newStmt)
	{
		int res = 1;
		AssignStmt as = (AssignStmt) newStmt.getStatement();
		soot.Type t = as.getRightOp().getType();
		res = computeSize(t);
		return res;
	}



	private int computeSize(soot.Type t) {
		int res = 1;
		if(isTypeObject(t)) {
			SootClass sc = Scene.v().loadClass(t.toString(), SootClass.DANGLING);
			if(sc!=null)
			{
				res = 0;
				if(sc.getFieldCount()>0) {
					for (SootField f : sc.getFields()) {
						if(isTypeObject(f.getType())) res += 4;
						else res += computeSize(f.getType());
					}
				}
				else res = 4;
			}
		}
		else
		{
			String st = t.toString();
			if(st.equals("int")) res = 4;
			else if(st.equals("long")) res = 4;
			else if(st.equals("float")) res = 4;
			else if(st.equals("double")) res = 8;
			else if(st.equals("char")) res = 2;
			else if(st.equals("byte")) res = 1;
			else if(st.endsWith("[]")) res = 4;
			else res = 1;
		}
		return res;
	}



	private boolean isTypeObject(soot.Type t) {
		boolean ok = false;
		try {
			Class c = Class.forName(t.toString());
			if (Object.class.isAssignableFrom(c)) {
				ok = true;
			}
		} catch (ClassNotFoundException e) {
			ok=false;
		}
		return ok;
	}
		
	
	public MemorySummary run(SootMethod method) {
		log.debug(" |- Intraprocedural Analysis for: " + method.toString());
		
		MemorySummary summary = summaryFactory.initialSummary(method);
		
		/*
		 * Acumula el consumo del metodo bajo analisis generado por los objetos creados cuyo tiempo de vida
		 * es el metodo bajo analisis. Es otras palabras, acumula el consumo de objetos creados que no escapan al 
		 * metodo. 
		 */
		//ParametricExpression tempLocal = expressionFactory.constant(0L);
		
		/*
		 * Acumula la memoria temporal necesaria para realizar los calls ejecutados por el metodo bajo analisis. 
		 */
		//ParametricExpression tempCalls = expressionFactory.constant(0L);
		
		/*
		 * Acumula la memoria generada por las llamadas a los metodos invocados. Esta variable acumula el consumo temporal
		 * generado a partir de los objetos generados por los metodos invocados que son capturados por el metodo bajo 
		 * analisis (su tiempo de vida finaliza con el metodo bajo analisis).
		 */
		//ParametricExpression resCaptured = expressionFactory.constant(0L);
		
		
		/*
		 * Acumula la memoria que escapa de los metodos invocados, para sumarselos al final a MemReq
		 * 
		 * 
		 * 
		 * */
		//ParametricExpression residualForMemReq = expressionFactory.constant(0L);
		
		ParametricExpression MAX_memReqFromCallees = expressionFactory.constant(0L);
		
		ParametricExpression acumResidualsFromCallees = expressionFactory.constant(0L);


		
		/* Acumula (sobreaproximando) el consumo total del metodo, 
		 * incluyendo  tanto a los objetos creados cuyo tiempo de vida es el metodo bajo analisis,
		 * como a la maxima cantidad de objetos creados por un metodo invocado que NO escapan
		 *  y son capturados por el metodo original.
		 *  En otras palabras, MemReq (=MaxLive) = tempLocal + tempCalls + resCaptured. 
		 *  
		 */		
		ParametricExpression memReq = expressionFactory.constant(0L);
		
		log.debug(" |- Building Method abstraction");
		// Generamos una abstraccion del body de un metodo util para poder procesar el analisis.
		// De esta forma podemos decorar los statements con lo necesario para los analisis auxiliares
		
		//Creo que por cada new agrega una instruccion extra de "new" para el contador de objetos. Idem con las calls
		MethodBody abstraction = methodDecorator.decorate(method);

		log.debug(" |- Processing new statements");
		for (NewStatement newStmt : abstraction.getNewStatements()) {
			
			this.analyzedNews++;
			
			if (log.isDebugEnabled()) {
				log.debug(" | |- Processing statement " + newStmt.toString());
			}
				
			//BILLY: aca llama a barvinok para armar el invariante del statement? O algo parecido
			//en los new esta dando 1.
			//en los new de array esta dando n
			
			
			ParametricExpression bound = ct.count(newStmt);
			
//			ParametricExpression sizeType = expressionFactory.constant((long) computeSize(newStmt));
//			ParametricExpression bound = sa.summate(sizeType, newStmt);
			
			
			
			if (log.isDebugEnabled()) {
				log.debug(" | | |- New is executed [" +  bound + "] times");
			}
			
			HeapPartition partition = ea.getPartition(newStmt);
			
			
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
		
		log.debug(" |- Processing call statements");
		for (CallStatement callStmt : abstraction.getCallStatements()) {
			if (log.isDebugEnabled()) {
				log.debug(" | |- Processing statement " + callStmt.toString());
			}

			/*if(callStmt.toString()== "ar.uba.dc.paper.Program3 ar.uba.dc.util.List map(ar.uba.dc.util.List,ar.uba.dc.paper.Op): o = virtualinvoke op.<ar.uba.dc.paper.Op: java.lang.Object apply(java.lang.Object)>(li) (3)")
			{
				log.debug("Hola soy apply!");
			}*/
			
			
			String s = callStmt.toString();
			//Boolean frenar = (s.equals("ar.uba.dc.basic.SubstractWithFolds java.lang.Integer f(int): virtualinvoke op.<ar.uba.dc.basic.IntContainer: java.lang.Integer apply(int)>(n) (3)"));

			
			CallSummaryInContext callSummary = interprocedural.analyseCall(callStmt);
			
			
			//BILLY: Actualiza tempCalls y resCaptured a partir de los del callee
			
			//Esto lo dejo asi para debuguear y ver que tienen adentro
			//ParametricExpression tempCallsForUpdate = callSummary.getTemporalCall();
			ParametricExpression memReqFromCallee = callSummary.getMAX_memoryRequirementMinusRsd();
			ParametricExpression acumResidualsFromCallee = callSummary.getAcumResiduals();
					
			
			MAX_memReqFromCallees = sa.supreme(MAX_memReqFromCallees, memReqFromCallee);
			
			
			acumResidualsFromCallees = sa.add(acumResidualsFromCallees, acumResidualsFromCallee);
			//BILLY Trata de tomar el supremo
			//Porque para contar el maxlive hay que contar la maxima cantidad de variables temporales vivas tambien!
			//ParametricExpression newTempCalls = sa.supreme(tempCalls, tempCallsForUpdate);
			
			
			//Trata de sumar las expresiones
			//Porque los objetos que escapan y son capturados por la funcion que llama tienen que ser contados para el maxlive!
			//ParametricExpression newResCaptured = sa.add(resCaptured, resCapturedForUpdate);
			
			
			
			
			
			
			
			//BILLY: es esto lo que tengo que cambiar?
			//Mirar arriba tempLocal, tempCalls, resCaptured
			
			//tempCalls = newTempCalls;
			//resCaptured = newResCaptured;
			
			
			
			//BILLY: temporal es los objetos que no escapan
			//Residual es los que escapan
			
			
			//ParametricExpression callSummaryRes = expressionFactory.constant(0L);
			

			//log.debug("Update tempCalls from [" + tempCalls + "] to [" + newTempCalls + "]");
			//log.debug("Update resCaptured from [" + resCaptured + "] to [" + newResCaptured + "]");		

			
			
			for (HeapPartition partition : callSummary.getResidualPartitions()) {
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
			

			// memReq = MAX (Maxlive_callee - Esc_callee) + SUM(ESC_callee)
			
			

			//Hay que cambiar el diseño, porque callSummaryRes ya lo estoy calculando en LoopInvariantDefault
			//residualForMemReq = sa.add(residualForMemReq, totalResiduals);
			
//			log.debug("Adding " + acumResiduals + " and " + memReqForUpdate + " to " + memReq);			
			
			//memReqFromCallees = sa.supreme(memReqFromCallees, memReqForUpdate);
			
			


			
		}

		memReq = sa.add(memReq, acumResidualsFromCallees);
		memReq = sa.add(memReq, MAX_memReqFromCallees);
		
		
		//summary.setTemporal(sa.add(tempLocal, tempCalls, resCaptured));
		
		summary.setMemoryRequirement(memReq);
	
		return summary;
	}
}