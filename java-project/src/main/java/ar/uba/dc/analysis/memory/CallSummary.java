package ar.uba.dc.analysis.memory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;


public class CallSummary {

	private Map<HeapPartition, ParametricExpression> residuals = new HashMap<HeapPartition, ParametricExpression>();
	//private ParametricExpression temporalCall;
	private ParametricExpression memoryRequirement;
	private ParametricExpression residualCapured;
	private ParametricExpression totalResidualsIfCallee;
	
	/*public ParametricExpression getTemporalCall() {
		return temporalCall;
	}*/
	
	public ParametricExpression getMemoryRequirement() {
		return memoryRequirement;
	}
	
	public ParametricExpression getResidualCaptured() {
		return residualCapured;
	}

	public List<HeapPartition> getResidualPartitions() {
		return new LinkedList<HeapPartition>(residuals.keySet());
	}

	public ParametricExpression getResidual(HeapPartition partition) {
		return residuals.get(partition);
	}

	/*public void setTemporalCall(ParametricExpression value) {
		this.temporalCall = value;
	}*/
	
	public void setMemoryRequirement(ParametricExpression value) {
		this.memoryRequirement = value;
	}

	public void setResidual(HeapPartition partition, ParametricExpression value) {
		this.residuals.put(partition, value);
	}

	public void setResidualCaptured(ParametricExpression value) {
		this.residualCapured = value;
	}

	public void setTotalResidualsIfCallee(ParametricExpression value) {
		this.totalResidualsIfCallee = value;
		
	}
	
	public ParametricExpression getTotalResidualsIfCallee()
	{
		return totalResidualsIfCallee;
	}

}
