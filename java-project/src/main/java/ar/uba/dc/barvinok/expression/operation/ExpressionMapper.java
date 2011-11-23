package ar.uba.dc.barvinok.expression.operation;

import java.util.Map;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

public interface ExpressionMapper {

	/**
	 * Transforma el invariante en una version con parametros y variables renombrados como "a, b, c, d, ..., z, za, zb, ..."
	 * en orden es primero los parametros y luego las variables, c/u en orden alfabetico.
	 * 
	 * El mapeo (nueva clave -> parametro o variable original) es agregado a mapping. 
	 * 
	 * @return El invariante con los parametros y variables reemplazados, asi como sus constraints
	 */
	public DomainSet simplify(DomainSet invariant, Map<String, String> mapping);
	
	/**
	 * Revierte el proceso hecho por simplify. Es decir, en base a el mapping, vuelve 
	 * los parametros, variables y constraints al estado que tenian previo aplicar el simplify.
	 */
	public DomainSet expand(DomainSet invariant, Map<String, String> mapping);
	
	/**
	 * Transforma el invariante en una version con parametros y variables renombrados como "a, b, c, d, ..., z, za, zb, ..."
	 * en orden es primero los parametros y luego las variables en el orden de las partes del polinomio, c/u en orden alfabetico.
	 * 
	 * El mapeo (nueva clave -> parametro o variable original) es agregado a mapping. 
	 * 
	 * @return El invariante con los parametros y variables reemplazados, asi como sus constraints
	 */
	public PiecewiseQuasipolynomial simplify(PiecewiseQuasipolynomial pwq, Map<String, String> mapping, String prefixForParameters);
	
	/**
	 * Revierte el proceso hecho por simplify. Es decir, en base a el mapping, vuelve 
	 * los parametros, variables y constraints al estado que tenian previo aplicar el simplify.
	 */
	public PiecewiseQuasipolynomial expand(PiecewiseQuasipolynomial target, Map<String, String> mapping);
}
