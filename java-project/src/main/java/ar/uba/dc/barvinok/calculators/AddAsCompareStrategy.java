package ar.uba.dc.barvinok.calculators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.barvinok.BarvinokSyntax;
import ar.uba.dc.barvinok.BarvinokUtils;
import ar.uba.dc.barvinok.ComparePolynomialStrategy;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

/**
 * Estrategia que aproxima el maximo de polinomios con la suma.
 * 
 * Esta estrategia garantiza que siempre que se compare polinomios 
 * se obtenga un polinomio como resultado.
 * 
 * @author testis
 */
public class AddAsCompareStrategy implements ComparePolynomialStrategy {

	private static Log log = LogFactory.getLog(AddAsCompareStrategy.class);
	



	//BILLY Aca no estamos quedando con e2 si no podemos comparar ambos?
	public PiecewiseQuasipolynomial compare(PiecewiseQuasipolynomial e1, PiecewiseQuasipolynomial e2, CommandLineCalculator calculator) {
		if (BarvinokUtils.isConstant(e1) 
				&& StringUtils.isEmpty(e1.getPieces().get(0).getConstraints()) 
				&& BarvinokUtils.isConstant(e2)
				&& StringUtils.isEmpty(e2.getPieces().get(0).getConstraints())) {
			Long l1 = BarvinokUtils.toConstant(e1);
			Long l2 = BarvinokUtils.toConstant(e2);
			
			if (l1 > l2) {
				return e1;
			}
			
			return e2;
		}

		return calculator.add(e1, e2);
	}

	public PiecewiseQuasipolynomial upperBound(PiecewiseQuasipolynomial expr, DomainSet invariant, CommandLineCalculator calculator) {
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial result = calculator.execOperationOverDomain(CommandLineCalculator.UPPER_BOUND_OPERATION, invariant, expr, mapping);
		
		// Procesamos el resultado por si algun polinomio retorna varios candidatos


		//BILLY: aca nos estamos quedando con la suma de los candidatos?
		for (Integer i = 0; i < result.getPieces().size(); i++) {
			QuasiPolynomial pq = result.getPieces().get(i);
			String max = pq.getPolynomial();
			// Si le pasamos para maximizar un 0, esto no devuelve una lista de candidatos sino que vacio.
			// En ese caso nos devuelve un 0 el parser y tenemos que manejar este caso
			String maxCandidatesPrefix = BarvinokSyntax.MAX_CANDIDATES + "("; 
			if (max.indexOf(maxCandidatesPrefix) >= 0) {
				max = max.substring(maxCandidatesPrefix.length(), max.length() - 1);
			}
			String[] candidates = max.split(",");
			
			if (candidates.length > 1) {
				if (log.isDebugEnabled()) {
					log.debug("    Too many candidates [" + max + "]. Start the selection strategy");
				}
				// Si hay varios candidatos, los sumamos para conseguir una cota para el verdadero maximo
				List<String> pols = new ArrayList<String>();
				for (String candidate : candidates) {
					candidate = candidate.trim();
					if (candidate.startsWith("(") && candidate.endsWith(")")) {
						candidate = candidate.substring(1, candidate.length() - 1);
					}
					pols.add(candidate);
				}
								
				result.getPieces().set(i, calculator.addAll(pols, result.getParameters(), pq.getConstraints()));				
			} else {
				// Si hay un unico candidato, lo ponemos como polinomio directo.
				if (max.startsWith("(") && max.endsWith(")")) {
					max = max.substring(1, max.length() - 1);
				}
				pq.setPolynomial(max);
			}
		}
		
		result = calculator.expand(result, mapping);
		
		if (log.isDebugEnabled()) {
			log.debug("    Result: " + mapping);
		}
		
		return result;
	}
	
	public void prepareFor(String operation, PiecewiseQuasipolynomial[] polynomials) {
		// No hay necesidad de preprocesar los polynomios previo a ninguna operacion.
	}

	public void cleanResult(PiecewiseQuasipolynomial result) {
		// No hay necesidad de limpiar el resultado. Esta estrategia no deja nada para limpiar
	}
}
