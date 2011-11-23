package ar.uba.dc.analysis.memory.impl;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

public class BarvinokParametricExpressionFactory implements ParametricExpressionFactory {

	@Override
	public ParametricExpression constant(Long value) {
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.add(new QuasiPolynomial(value.toString()));
		
		return new BarvinokParametricExpression(p);
	}
	
	@Override
	public ParametricExpression constant(Long value, DomainSet requirements) {
		if (requirements == null) {
			return constant(value);
		}
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.add(new QuasiPolynomial(value.toString(), requirements.getConstraints()));
		p.addAllParameters(requirements.getParameters());
		
		return new BarvinokParametricExpression(p);
	}

	public BarvinokParametricExpression polynomial(PiecewiseQuasipolynomial piecewiseQuasipolynomial) {
		return new BarvinokParametricExpression(piecewiseQuasipolynomial);
	}
}
