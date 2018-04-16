package ar.uba.dc.analysis.memory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;

import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;

import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;


public class PaperCallSummaryInContext {

	//por que no puedo poner la interfaz?
	private Map<SimplePaperPointsToHeapPartition, ParametricExpression> residuals = new HashMap<SimplePaperPointsToHeapPartition, ParametricExpression>();
	
	
	//private ParametricExpression temporalCall;
	private ParametricExpression MAX_memoryRequirementMinusRsd;
	private ParametricExpression acumResiduals;
	private Set<String> unboundedBindingVariables;

	
	/*public ParametricExpression getTemporalCall() {
		return temporalCall;
	}*/
	
	public ParametricExpression getMAX_memoryRequirementMinusRsd() {
		return MAX_memoryRequirementMinusRsd;
	}
	
	public ParametricExpression getAcumResiduals() {
		return acumResiduals;
	}

	public List<SimplePaperPointsToHeapPartition> getResidualPartitions() {
		return new LinkedList<SimplePaperPointsToHeapPartition>(residuals.keySet());
	}

	public ParametricExpression getResidual(SimplePaperPointsToHeapPartition partition) {
		return residuals.get(partition);
	}

	/*public void setTemporalCall(ParametricExpression value) {
		this.temporalCall = value;
	}*/
	
	public void setMAX_memoryRequirementMinusRsd(ParametricExpression value) {
		this.MAX_memoryRequirementMinusRsd = value;
	}

	public void setResidual(SimplePaperPointsToHeapPartition partition, ParametricExpression value) {
		this.residuals.put(partition, value);
	}

	public Set<String> getUnboundedBindingVariables() {
		return unboundedBindingVariables;
	}

	public void setUnboundedBindingVariables(Set<String> unboundedBindingVariables) {
		this.unboundedBindingVariables = unboundedBindingVariables;
	}

	public void setAcumResiduals(ParametricExpression value) {
		this.acumResiduals = value;
	}
	
	
}
