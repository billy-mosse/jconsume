package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;

public class PaperMemorySummary {
	protected Set<String> parameters;
	
//	protected ParametricExpression temporal;
	
	protected ParametricExpression memoryRequirement;
	
	protected Map<SimplePaperPointsToHeapPartition, ParametricExpression> residuals;
	
	protected IntermediateRepresentationMethod target;
	
	protected Set<SimplePaperPointsToHeapPartition> globEscape; 				// partition escaping globally
	
	protected Set<SimplePaperPointsToHeapPartition> ret; 						// return -> partition
	
	
	public PaperMemorySummary(IntermediateRepresentationMethod target, Set<String> methodParameters, ParametricExpression initialTemporal, ParametricExpression initialMemoryRequirement) {
		this.target = target;
		this.parameters = methodParameters;
	//	this.temporal = initialTemporal;
		this.memoryRequirement = initialMemoryRequirement;
		this.residuals = new HashMap<SimplePaperPointsToHeapPartition, ParametricExpression>();
		this.globEscape = new HashSet<SimplePaperPointsToHeapPartition>();
		this.ret = new HashSet<SimplePaperPointsToHeapPartition>();
	}
	
	
	


	public PaperMemorySummary(IntermediateRepresentationMethod target,ParametricExpression temp, ParametricExpression mem) {
		this.target = target;
	//	this.temporal = temp;
		this.memoryRequirement = mem;
		this.parameters = mem.getParameters(); //esto esta horrible
		this.residuals = new HashMap<SimplePaperPointsToHeapPartition, ParametricExpression>();
		this.globEscape = new HashSet<SimplePaperPointsToHeapPartition>();
		this.ret = new HashSet<SimplePaperPointsToHeapPartition>();
		
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
	
	public ParametricExpression getResidual(SimplePaperPointsToHeapPartition aHeapPartition) {
		return this.residuals.get(aHeapPartition);
	}

	public Set<SimplePaperPointsToHeapPartition> getResidualPartitions() {
		return new HashSet<SimplePaperPointsToHeapPartition>(residuals.keySet());
	}

	public ParametricExpression getMemoryRequirement() {
		return this.memoryRequirement;
	}
		
	public void setResidual(PaperPointsToHeapPartition aHeapPartition, ParametricExpression newValue) {
		residuals.put((SimplePaperPointsToHeapPartition)aHeapPartition, newValue);	
	}
	
	public void setMemoryRequirement(ParametricExpression newValue) {
		this.memoryRequirement = newValue;
		
	}

	public void add(PaperPointsToHeapPartition paperGlobHp, ParametricExpression resMemory) {
		// TODO Auto-generated method stub		
		residuals.put((SimplePaperPointsToHeapPartition)paperGlobHp,  resMemory);		
	}
	
	public void partitionEscapeGlobaly(SimplePaperPointsToHeapPartition heapPartition) {
		globEscape.add(heapPartition);		
	}
	

	public void returnPartition(SimplePaperPointsToHeapPartition heapPartition) {
		ret.add(heapPartition);		
	}
}
