package ar.uba.dc.barvinok.expression.operation.impl;

import java.util.HashSet;
import java.util.Set;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.operation.BindingException;
import ar.uba.dc.barvinok.expression.operation.BindingValidator;

public class OptimisticBindingValidator implements BindingValidator {

	public void validate(PiecewiseQuasipolynomial polynomial, DomainSet invariant) throws BindingException {
		Set<String> params = new HashSet<String>(polynomial.getParameters());
		
		params.removeAll(invariant.getVariables());
		
		if (!params.isEmpty()) {
			polynomial.removeAllParameters(params);
		}
	}
}
