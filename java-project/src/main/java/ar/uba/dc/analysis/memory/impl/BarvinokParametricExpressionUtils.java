package ar.uba.dc.analysis.memory.impl;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionUtils;

public class BarvinokParametricExpressionUtils implements ParametricExpressionUtils {

	//Primera forma boba de analizar infinitud
	@Override
	public boolean isInfinite(ParametricExpression expression) {
		BarvinokParametricExpression barvinokExpression = (BarvinokParametricExpression) expression;
		return barvinokExpression.asString().contains("infty");
		
	}

}
