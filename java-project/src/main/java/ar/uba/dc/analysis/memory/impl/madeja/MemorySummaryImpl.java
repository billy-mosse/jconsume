package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import soot.SootMethod;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class MemorySummaryImpl implements MemorySummary {

	protected SootMethod target;
	
	protected Set<String> parameters;
	
	protected ParametricExpression temporal;
	
	protected Map<HeapPartition, ParametricExpression> residuals;
	
	public MemorySummaryImpl(SootMethod method, Set<String> methodParameters, ParametricExpression initialTemporal) {
		super();
		this.target = method;
		this.parameters = methodParameters;
		this.temporal = initialTemporal;
		this.residuals = new HashMap<HeapPartition, ParametricExpression>();
	}
	

	@Override
	public Set<String> getParameters() {
		return this.parameters;
	}

	@Override
	public ParametricExpression getResidual(HeapPartition aHeapPartition) {
		return this.residuals.get(aHeapPartition);
	}

	@Override
	public Set<HeapPartition> getResidualPartitions() {
		return new HashSet<HeapPartition>(residuals.keySet());
	}

	@Override
	public SootMethod getTarget() {
		return this.target;
	}

	@Override
	public ParametricExpression getTemporal() {
		return this.temporal;
	}

	@Override
	public void setResidual(HeapPartition aHeapPartition, ParametricExpression newValue) {
		residuals.put(aHeapPartition, newValue);	
	}

	@Override
	public void setTemporal(ParametricExpression newValue) {
		this.temporal = newValue;
		
	}
	

}
