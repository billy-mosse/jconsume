package ar.uba.dc.barvinok.syntax;

import java.util.ArrayList;
import java.util.List;

import ar.uba.dc.barvinok.BarvinokSyntax;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

public abstract class AbstractBarvinokSyntax implements BarvinokSyntax {

	protected String stripPart(String part) {
		return part.substring(1, part.length() - 1).trim();
	}
	
	protected String extractPartFromTuple(String value) {
		// El resultado de la operacion podria ser un ub que a partir de la version 0.31 de la calculadora devuelve
		// una tupla con un boolean indicando si el bound encontrado es exacto o no.
		// Nuestra aplicacion asume que devolveremos la primer parte del polinomio. Por eso procesamos la primer componente
		// de la tupla.
		if (value.startsWith("(") && value.endsWith(")")) {
			value = value.substring(1, value.lastIndexOf(","));
		}
		
		return value;
	}

	public List<PiecewiseQuasipolynomial> parseMultiplePiecewiseQuasipolynomial(String values) {
		String[] pieces = values.split(System.getProperty("line.separator"));
		List<PiecewiseQuasipolynomial> result = new ArrayList<PiecewiseQuasipolynomial>(pieces.length);
		
		for (String value : pieces) {
			result.add(parsePiecewiseQuasipolynomial(value));
		}
		
		return result;
	}
}
