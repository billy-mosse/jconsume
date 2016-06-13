package ar.uba.dc.barvinok.calculators;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.barvinok.BarvinokUtils;
import ar.uba.dc.barvinok.ComparePolynomialStrategy;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

/**
 * Estrategia no aproxima los maximos sino que intenta resolverlos. Cuando no puede, deja una expresion
 * indicando los candidatos
 * 
 * Esta estrategia no garantiza que siempre que se compare polinomios 
 * se obtenga un polinomio como resultado.
 * 
 * @author testis
 */
public class LazyAsCompareStrategy implements ComparePolynomialStrategy {

	private static final Log log = LogFactory.getLog(LazyAsCompareStrategy.class);
	
	public PiecewiseQuasipolynomial compare(PiecewiseQuasipolynomial e1, PiecewiseQuasipolynomial e2, CommandLineCalculator calculator) {
		PiecewiseQuasipolynomial p = e1.clone();		
		
		PiecewiseQuasipolynomial q = e2.clone();
		
		BarvinokUtils.surroundWithMax(p);
		BarvinokUtils.surroundWithMax(q);
		
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial result = calculator.execOperationOverExpressions(CommandLineCalculator.COMPARE_OPERATOR, mapping, new PiecewiseQuasipolynomial[] { p, q });
		
		result = calculator.expand(result, mapping);
		
		if (log.isDebugEnabled()) {
			log.debug("    mapping: " + mapping);
			log.debug("    expanded result: " + result);
		}
		
		return result;
	}

	public PiecewiseQuasipolynomial upperBound(PiecewiseQuasipolynomial expr, DomainSet invariant, CommandLineCalculator calculator) {
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial result = null;
		
		if (BarvinokUtils.hasFoldPiece(expr)) {
			PiecewiseQuasipolynomial clone = expr.clone();
			BarvinokUtils.surroundWithMax(clone);
			result = calculator.combineDomainWithPolynomial(CommandLineCalculator.COMPARE_OPERATOR, invariant, clone, mapping);
		} else {
			result = calculator.execOperationOverDomain(CommandLineCalculator.UPPER_BOUND_OPERATION, invariant, expr, mapping);
		}
		
		cleanResult(result);
		
		result = calculator.expand(result, mapping);
		
		if (log.isDebugEnabled()) {
			log.debug("    mapping: " + mapping);
			log.debug("    expanded result: " + result);
		}
		
		return result;
	}
		
	public void prepareFor(String operator, PiecewiseQuasipolynomial[] polynomials) {
		if (CommandLineCalculator.ADD_OPERATOR.equals(operator) || CommandLineCalculator.SUBSTRACT_OPERATOR.equals(operator)) {
			for (PiecewiseQuasipolynomial p : polynomials) {
				if (BarvinokUtils.hasFoldPiece(p)) {
					BarvinokUtils.surroundWithMax(p);
				}
			}
		}
	}

	public void cleanResult(PiecewiseQuasipolynomial polynomial) {
		BarvinokUtils.cleanMaxIfPossible(polynomial);
	}
}
