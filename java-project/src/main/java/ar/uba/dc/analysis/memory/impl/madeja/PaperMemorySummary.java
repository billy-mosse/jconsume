package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;

public class PaperMemorySummary {
protected Set<String> parameters;
	
	protected ParametricExpression temporal;
	
	protected ParametricExpression memoryRequirement;
	
	protected Map<PaperPointsToHeapPartition, ParametricExpression> residuals;
	
	protected IntermediateRepresentationMethod target;
	
	public PaperMemorySummary(IntermediateRepresentationMethod target, Set<String> methodParameters, ParametricExpression initialTemporal, ParametricExpression initialMemoryRequirement) {
		this.target = target;
		this.parameters = methodParameters;
		this.temporal = initialTemporal;
		this.memoryRequirement = initialMemoryRequirement;
		this.residuals = new HashMap<PaperPointsToHeapPartition, ParametricExpression>();
	}
	

	public IntermediateRepresentationMethod getTarget() {
		return target;
	}


	public void setTarget(IntermediateRepresentationMethod target) {
		this.target = target;
	}


	public Set<String> getParameters() {
		return this.parameters;
	}

	public ParametricExpression getResidual(HeapPartition aHeapPartition) {
		return this.residuals.get(aHeapPartition);
	}

	public Set<PaperPointsToHeapPartition> getResidualPartitions() {
		return new HashSet<PaperPointsToHeapPartition>(residuals.keySet());
	}

	public ParametricExpression getMemoryRequirement() {
		return this.memoryRequirement;
	}
		
	public void setResidual(PaperPointsToHeapPartition aHeapPartition, ParametricExpression newValue) {
		residuals.put(aHeapPartition, newValue);	
	}
	
	public void setMemoryRequirement(ParametricExpression newValue) {
		this.memoryRequirement = newValue;
		
	}
}
