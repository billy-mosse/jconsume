package ar.uba.dc.analysis.memory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;


public class CallSummary {

	private Map<HeapPartition, ParametricExpression> residuals = new HashMap<HeapPartition, ParametricExpression>();
	private ParametricExpression temporalCall;
	private ParametricExpression residualCapured;
	
	public ParametricExpression getTemporalCall() {
		return temporalCall;
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

	public void setTemporalCall(ParametricExpression value) {
		this.temporalCall = value;
	}

	public void setResidual(HeapPartition partition, ParametricExpression value) {
		this.residuals.put(partition, value);
	}

	public void setResidualCaptured(ParametricExpression value) {
		this.residualCapured = value;
	}

}
