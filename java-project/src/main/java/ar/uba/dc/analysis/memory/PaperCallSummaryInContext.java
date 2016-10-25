package ar.uba.dc.analysis.memory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;


public class PaperCallSummaryInContext {

	private Map<PaperPointsToHeapPartition, ParametricExpression> residuals = new HashMap<PaperPointsToHeapPartition, ParametricExpression>();
	//private ParametricExpression temporalCall;
	private ParametricExpression MAX_memoryRequirementMinusRsd;
	private ParametricExpression acumResiduals;
	
	/*public ParametricExpression getTemporalCall() {
		return temporalCall;
	}*/
	
	public ParametricExpression getMAX_memoryRequirementMinusRsd() {
		return MAX_memoryRequirementMinusRsd;
	}
	
	public ParametricExpression getAcumResiduals() {
		return acumResiduals;
	}

	public List<PaperPointsToHeapPartition> getResidualPartitions() {
		return new LinkedList<PaperPointsToHeapPartition>(residuals.keySet());
	}

	public ParametricExpression getResidual(PaperPointsToHeapPartition partition) {
		return residuals.get(partition);
	}

	/*public void setTemporalCall(ParametricExpression value) {
		this.temporalCall = value;
	}*/
	
	public void setMAX_memoryRequirementMinusRsd(ParametricExpression value) {
		this.MAX_memoryRequirementMinusRsd = value;
	}

	public void setResidual(PaperPointsToHeapPartition partition, ParametricExpression value) {
		this.residuals.put(partition, value);
	}

	public void setAcumResiduals(ParametricExpression value) {
		this.acumResiduals = value;
	}
}
