package ar.uba.dc.barvinok.calculators;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.barvinok.BarvinokFactory;
import ar.uba.dc.barvinok.BarvinokUtils;
import ar.uba.dc.barvinok.BarvinokCalculator;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

/**
 * Calculadora que para el {@link BarvinokCalculator#countExecutions(DomainSet)} devuelve simpre 1.
 * 
 * Para la opreacion {@link BarvinokCalculator#sumConsumtion(PiecewiseQuasipolynomial, DomainSet)} asi como la de {@link BarvinokCalculator#upperBound(PiecewiseQuasipolynomial, DomainSet)} 
 * devuelve la expresion recibida como parametro.
 * 
 * Los operadores de suma y maximo operan normalmente.
 * 
 * Esta implementacion permite contar cuantos news existen en el codigo y ademas medir los tiempos que insume el uso de otras
 * calculadoras en el analisis.
 * 
 * @author testis
 */
public class ConstantCalculator implements BarvinokCalculator {

	private static Log log = LogFactory.getLog(ConstantCalculator.class);
	
	private void throwNotConstantExpression(PiecewiseQuasipolynomial expression)
	{
		throw new RuntimeException("Intentamos sumar una expresion [" + expression + "] que no es una constante");
	}
	
	public PiecewiseQuasipolynomial substract(PiecewiseQuasipolynomial expression1, PiecewiseQuasipolynomial expression2) {
		if (log.isDebugEnabled()) {
			String operation = expression1 + " + " + expression2;
				
			if (StringUtils.isNotEmpty(operation)) {
				operation = operation.substring(3);
			}
			log.debug(operation);
		}

		Long result = 0L; //BILLY: Tengo que inicializarlo?
		
		
		if (BarvinokUtils.isConstant(expression1))
		{
			if (BarvinokUtils.isConstant(expression2))
			{				
				result = BarvinokUtils.toConstant(expression1) - BarvinokUtils.toConstant(expression2);
			}
			else
			{
				throwNotConstantExpression(expression2);
			}
		}		
		else
		{
			throwNotConstantExpression(expression1);
		}		
		
		return BarvinokFactory.constant(result);
	}
	
	
	
	public PiecewiseQuasipolynomial add(PiecewiseQuasipolynomial... expressions) {
		if (log.isDebugEnabled()) {
			String operation = StringUtils.EMPTY;
			for (PiecewiseQuasipolynomial e : expressions) {
				operation += " + " + e;
			}
				
			if (StringUtils.isNotEmpty(operation)) {
				operation = operation.substring(3);
			}
			log.debug(operation);
		}

		Long result = 0L;
		
		for (PiecewiseQuasipolynomial e : expressions) {
			if (BarvinokUtils.isConstant(e) 
					/*&& StringUtils.isEmpty(e.getPieces().get(0).getConstraints())*/) {
				result += BarvinokUtils.toConstant(e);
			} else {
				throw new RuntimeException("Intentamos sumar una expresion [" + e + "] que no es una constante");
			}
		}
		
		return BarvinokFactory.constant(result);
	}

	public PiecewiseQuasipolynomial countExecutions(DomainSet invariant) {
		return BarvinokFactory.constant(1L);
	}

	public PiecewiseQuasipolynomial max(PiecewiseQuasipolynomial expr1, PiecewiseQuasipolynomial expr2) {
		Long l1 = BarvinokUtils.toConstant(expr1);
		Long l2 = BarvinokUtils.toConstant(expr2);
		
		if (BarvinokUtils.isConstant(expr1) 
				/*&& StringUtils.isEmpty(expr1.getPieces().get(0).getConstraints())*/ 
				&& BarvinokUtils.isConstant(expr2)
				/*&& StringUtils.isEmpty(expr2.getPieces().get(0).getConstraints())*/) {
			
			
			if (l1 > l2) {
				log.debug("max(" + expr1 + ", " + expr2 + ") = " + expr1);
				return expr1;
			}
		
			log.debug("max(" + expr1 + ", " + expr2 + ") = " + expr2);
			return expr2;
		}
		
		log.debug("max(" + expr1 + ", " + expr2 + ")");
		throw new RuntimeException("Alguna de las expresiones no es una constante");
	}

	public PiecewiseQuasipolynomial sumConsumtion(PiecewiseQuasipolynomial expr, DomainSet invariant) {
		log.debug("sumConsumtion(" + expr + ", " + invariant + ") = " + expr);
		return expr.clone();
	}

	public PiecewiseQuasipolynomial upperBound(PiecewiseQuasipolynomial value, DomainSet invariant) {
		log.debug("upperBound(" + value + ", " + invariant + ") = " + value);
		return value.clone();
	}
}