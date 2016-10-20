package ar.uba.dc.analysis.memory.summary;

import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;

/**
 * Dado un resumen extrae una {@link ParametricExpression} que representa el residual del {@link MemorySummary}. 
 * Recordemos que el {@link MemorySummary} esta compuesto por varias {@link HeapPartition} que componen
 * la informacion de consumo residual del metodo al que pertenece el {@link MemorySummary} 
 * 
 * @author testis
 */
public interface ResidualSummarizer<M extends MemorySummary, P extends ParametricExpression> {

	public P getResidual(M summary);
	
}
