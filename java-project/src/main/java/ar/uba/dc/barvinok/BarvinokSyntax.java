package ar.uba.dc.barvinok;

import java.util.List;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

/**
 * Interfaz comun para poder manejar distintas sintaxis
 * de la calculadora.
 * 
 * @author testis
 */
public interface BarvinokSyntax {
	
	public static final String NaN = "NaN";
	public static final String INFINITE = "infty";
	public static final CharSequence MAX_CANDIDATES = "max";
	public static final CharSequence EXISTS = "exists";

	public List<PiecewiseQuasipolynomial> parseMultiplePiecewiseQuasipolynomial(String values);
	
	public PiecewiseQuasipolynomial parsePiecewiseQuasipolynomial(String value);
	
	public String toString(DomainSet value);
	
	public String toString(PiecewiseQuasipolynomial value);

	public String toString(PiecewiseQuasipolynomial value, boolean includeVariables);
}
