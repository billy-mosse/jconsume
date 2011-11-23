package ar.uba.dc.barvinok.expression.operation;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

/**
 * Interfaz que respeta un validador de binding. 
 * Esto nos permite definir distintos tipos de validaciones
 * y ser mas o menos flexibles a la hora de aplicar una operacion
 * que trabaje con un dominio y un polinomio
 * 
 * @author testis
 */
public interface BindingValidator {

	void validate(PiecewiseQuasipolynomial polynomial, DomainSet invariant) throws BindingException;
	
}
