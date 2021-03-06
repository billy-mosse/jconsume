package ar.uba.dc.barvinok.expression.operation.impl;

import java.util.HashSet;
import java.util.Set;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.operation.BindingException;
import ar.uba.dc.barvinok.expression.operation.BindingValidator;

public class ExceptionalBindingValidator implements BindingValidator {

	public void validate(PiecewiseQuasipolynomial polynomial, DomainSet invariant) throws BindingException {
		Set<String> params = new HashSet<String>(polynomial.getParameters());
		
		params.removeAll(invariant.getVariables());
		
		if (!params.isEmpty()) {
			BindingException e = new BindingException("Falta especificar el binding de los parametros [" + params + "]");
			e.setParameters(params);
			throw e;
		}
	}	
}
