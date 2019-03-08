package ar.uba.dc.analysis.memory.impl;

import java.util.Set;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

public class BarvinokParametricExpression implements ParametricExpression {

	protected PiecewiseQuasipolynomial expr;
	
	public BarvinokParametricExpression(PiecewiseQuasipolynomial polynomials) {
		this.expr = polynomials;
	}
	
	public BarvinokParametricExpression(String polynomial)
	{
		this.expr = new PiecewiseQuasipolynomial(polynomial);
	}
	
	@Override
	public int hashCode() {
		return expr.hashCode(); 
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BarvinokParametricExpression)) return false;
		BarvinokParametricExpression bpe = (BarvinokParametricExpression) o;
		return expr.equals(bpe.expr);
	}
	
	public String asString() {
		return expr.asString();
	}
	
	public String toString() {
		return expr.toString(); 
	}
	
	public BarvinokParametricExpression clone() {
		return new BarvinokParametricExpression(expr.clone());
	}

	public PiecewiseQuasipolynomial getExpression() {
		return expr;
	}

	@Override
	public Set<String> getParameters() {
		return expr.getParameters();
	}
}
