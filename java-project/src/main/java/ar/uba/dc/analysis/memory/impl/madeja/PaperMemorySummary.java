package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartition;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class PaperMemorySummary {
	protected Set<String> parameters;
	
	protected ParametricExpression temporal;
	
	protected ParametricExpression memoryRequirement;
	
	protected Map<PaperPointsToHeapPartition, ParametricExpression> residuals;
	
	protected IntermediateRepresentationMethod target;
	
	protected Set<PaperPointsToHeapPartition> globEscape; 				// partition escaping globally
	
	protected Set<PaperPointsToHeapPartition> ret; 						// return -> partition
	
	
	public PaperMemorySummary(IntermediateRepresentationMethod target, Set<String> methodParameters, ParametricExpression initialTemporal, ParametricExpression initialMemoryRequirement) {
		this.target = target;
		this.parameters = methodParameters;
		this.temporal = initialTemporal;
		this.memoryRequirement = initialMemoryRequirement;
		this.residuals = new HashMap<PaperPointsToHeapPartition, ParametricExpression>();
		this.globEscape = new HashSet<PaperPointsToHeapPartition>();
		this.ret = new HashSet<PaperPointsToHeapPartition>();
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

	public void add(PaperPointsToHeapPartition paperGlobHp, ParametricExpression resMemory) {
		// TODO Auto-generated method stub
		
	}
	
	public void partitionEscapeGlobaly(PaperPointsToHeapPartition heapPartition) {
		globEscape.add(heapPartition);		
	}
	

	public void returnPartition(PaperPointsToHeapPartition heapPartition) {
		ret.add(heapPartition);		
	}
}
