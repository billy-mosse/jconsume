package ar.uba.dc.analysis.common;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;


public class LineWithConsumption extends Line{
	protected ParametricExpression consumption;

	public ParametricExpression getConsumption() {
		return consumption;
	}

	public void setConsumption(ParametricExpression consumption) {
		this.consumption = consumption;
	}
	
}
