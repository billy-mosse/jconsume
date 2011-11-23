package ar.uba.dc.barvinok;

import java.util.Set;

import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

public class BarvinokFactory {

	public static PiecewiseQuasipolynomial constant(Long value) {
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.add(new QuasiPolynomial(value.toString()));
		
		return p;
	}
	
	public static PiecewiseQuasipolynomial polynomial(Set<String> parameters, QuasiPolynomial quasipolynomial) {
		PiecewiseQuasipolynomial pqp = new PiecewiseQuasipolynomial();
		
		for (String param : parameters) {
			pqp.addParameter(param);
		}
		
		pqp.add(quasipolynomial);
		
		return pqp;
	}
	
	public static PiecewiseQuasipolynomial infinite(Set<String> parameters) {
		PiecewiseQuasipolynomial pqp = new PiecewiseQuasipolynomial();
		
		pqp.addAllParameters(parameters);		
		pqp.add(new QuasiPolynomial(BarvinokSyntax.INFINITE));
		
		return pqp;
	}
}
