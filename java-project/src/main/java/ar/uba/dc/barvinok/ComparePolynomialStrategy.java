package ar.uba.dc.barvinok;

import ar.uba.dc.barvinok.calculators.CommandLineCalculator;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

/**
 * Interfaz de las distintas estrategias para manejar
 * la comparacion entre polinomios.
 * 
 * @author testis
 */
public interface ComparePolynomialStrategy {

	PiecewiseQuasipolynomial compare(PiecewiseQuasipolynomial e1, PiecewiseQuasipolynomial e2, CommandLineCalculator barvinokCalculator);

	PiecewiseQuasipolynomial upperBound(PiecewiseQuasipolynomial expr, DomainSet invariant, CommandLineCalculator barvinokCalculator);

	/**
	 * Trabajar con la estrategia puede requerir que despues de sumar se limpie le resultado.
	 * Esto es por si al comparar devuelvo multiples candidatos.
	 * 
	 * Este metodo permite a la estrategia limpiar los resultados. El concepto de limpiar
	 * puede cambiar de acuerdo a al estrategia a utilizar
	 * 
	 * @param result
	 */
	void cleanResult(PiecewiseQuasipolynomial result);

	/**
	 * Dependiendo la estrategia, cuando aplicamos una operacion sobre un conjunto de polinomios
	 * quizas necesitemos prepreocesarlas. 
	 * 
	 * @param operator
	 * @param polynomials
	 */
	void prepareFor(String operator, PiecewiseQuasipolynomial[] polynomials);
}
