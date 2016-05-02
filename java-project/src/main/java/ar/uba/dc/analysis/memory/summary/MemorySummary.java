package ar.uba.dc.analysis.memory.summary;

import java.util.Set;

import soot.SootMethod;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;

/**
 * Summary de consumo.
 * 
 * Por simplicidad obviamos el tipo de datos de los parametros (los objetos que aparecen en las expresiones).
 * 
 * @author testis
 */
public interface MemorySummary {

	/**
	 * Metodo para el cual construimos el summary
	 */
	public SootMethod getTarget();
	
	/**
	 * Parametros relevantes para el analisis de consumo del metodo en cuestion.
	 * No hace falta que sean exactamente los mismos parametros del metodo para el cual construimos el summary.
	 * De hecho, pueden ser derivados de los mismos o construidos artificialmente para poder predicar sobre el consumo
	 */
	public Set<String> getParameters();
	
	/**
	 * 
	 */
	//public ParametricExpression getTemporal();
	
	/**
	 * Dado un HeapPartition se retorna una expresion que representa el consumo de los objetos incluidos
	 * en dicha particion. 
	 */
	public ParametricExpression getResidual(HeapPartition aHeapPartition);

	public void setResidual(HeapPartition aHeapPartition, ParametricExpression newValue);

	//public void setTemporal(ParametricExpression newValue);

	public Set<HeapPartition> getResidualPartitions();

	public void setMemoryRequirement(ParametricExpression newValue);

	public ParametricExpression getMemoryRequirement();




}
