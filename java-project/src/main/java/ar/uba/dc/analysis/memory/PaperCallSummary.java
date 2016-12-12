package ar.uba.dc.analysis.memory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;


public class PaperCallSummary {

	private Map<RichPaperPointsToHeapPartition, ParametricExpression> residuals = new HashMap<RichPaperPointsToHeapPartition, ParametricExpression>();
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

	public List<RichPaperPointsToHeapPartition> getResidualPartitions() {
		return new LinkedList<RichPaperPointsToHeapPartition>(residuals.keySet());
	}

	public ParametricExpression getResidual(RichPaperPointsToHeapPartition partition) {
		return residuals.get(partition);
	}

	/*public void setTemporalCall(ParametricExpression value) {
		this.temporalCall = value;
	}*/
	
	public void setMemoryRequirement(ParametricExpression value) {
		this.memoryRequirement = value;
	}

	public void setResidual(RichPaperPointsToHeapPartition partition, ParametricExpression value) {
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
