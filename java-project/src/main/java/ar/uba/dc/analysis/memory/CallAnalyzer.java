package ar.uba.dc.analysis.memory;

import ar.uba.dc.analysis.memory.code.CallStatement;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

/**
 * Interfaz que define como debe comportarse el algoritmo para analizar un call.
 * El protocolo define que el orden de invocacion de los metodos sera
 * 
 *  - init
 *  - Por c/implementacion del call (por si es un virutal call) habra una llamada a process
 *  - build summary
 */
public interface CallAnalyzer {

	public void init(CallStatement callStmt, LifeTimeOracle lifeTimeOracle, SymbolicCalculator symbolicCalculator, ParametricExpressionFactory expressionFactory);

	public void process(CallStatement virtualInvoke, MemorySummary calleeSummary);

	public CallSummary buildSummary(CallStatement callStmt);	
}
