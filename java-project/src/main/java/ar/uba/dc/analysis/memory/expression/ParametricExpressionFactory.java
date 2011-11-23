package ar.uba.dc.analysis.memory.expression;

import ar.uba.dc.barvinok.expression.DomainSet;

public interface ParametricExpressionFactory {

	public ParametricExpression constant(Long value);

	public ParametricExpression constant(Long value, DomainSet requirements);

}
