package ar.uba.dc.barvinok;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

public interface BarvinokCalculator {

	PiecewiseQuasipolynomial max(PiecewiseQuasipolynomial expr1, PiecewiseQuasipolynomial expr2);

	PiecewiseQuasipolynomial add(PiecewiseQuasipolynomial... expressions);
	
	PiecewiseQuasipolynomial substract(PiecewiseQuasipolynomial expression1, PiecewiseQuasipolynomial expression2);
		
	PiecewiseQuasipolynomial countExecutions(DomainSet invariant);

	PiecewiseQuasipolynomial sumConsumtion(PiecewiseQuasipolynomial expr, DomainSet invariant);
	
	PiecewiseQuasipolynomial upperBound(PiecewiseQuasipolynomial value, DomainSet invariant);

	PiecewiseQuasipolynomial boundIfHasFold(PiecewiseQuasipolynomial expression);
}
