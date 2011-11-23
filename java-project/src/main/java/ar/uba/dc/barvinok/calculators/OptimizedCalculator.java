package ar.uba.dc.barvinok.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.barvinok.BarvinokCalculator;
import ar.uba.dc.barvinok.BarvinokFactory;
import ar.uba.dc.barvinok.BarvinokUtils;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

/**
 * Calculadora optimizada.
 * 
 * Cuando puede no hace las operaciones
 * 
 * @author testis
 */
public class OptimizedCalculator implements BarvinokCalculator {

	private static Log log = LogFactory.getLog(OptimizedCalculator.class);
	
	protected BarvinokCalculator target;
	
	public OptimizedCalculator() {
		super();
	}
	
	public OptimizedCalculator(BarvinokCalculator target) {
		this.target = target;
	}
	
	public PiecewiseQuasipolynomial add(PiecewiseQuasipolynomial... expressions) {
		List<PiecewiseQuasipolynomial> finalExpressions = new ArrayList<PiecewiseQuasipolynomial>(expressions.length);
		
		for (PiecewiseQuasipolynomial e : expressions) {
			PiecewiseQuasipolynomial expr = (PiecewiseQuasipolynomial) e;
			Long value = BarvinokUtils.toConstant(expr); 
			boolean esConstante = (value != null);
			boolean esCero = (esConstante && value.equals(0L));
			boolean tieneConstraints = StringUtils.isNotBlank(expr.getPieces().get(0).getConstraints());
			if (!esConstante || !esCero || tieneConstraints) {
				finalExpressions.add(e);
			}
		}
		
		if (log.isDebugEnabled()) {
			log.debug(" | | |- Antes: " + Arrays.toString(expressions));
			log.debug(" | | |- Despues: " + finalExpressions);
		}
		
		if (finalExpressions.size() < expressions.length) {
			log.debug(" | | |- + optimized");
		}
		
		PiecewiseQuasipolynomial result = null;
		
		if (finalExpressions.isEmpty()) {
			result = BarvinokFactory.constant(0L);
		} else if (finalExpressions.size() == 1) {
			result = finalExpressions.get(0).clone();
		} else {
			result = target.add(finalExpressions.toArray(new PiecewiseQuasipolynomial[] {}));
		}
		
		return result;
	}

	public PiecewiseQuasipolynomial countExecutions(DomainSet invariant) {
		return target.countExecutions(invariant);
	}

	public PiecewiseQuasipolynomial max(PiecewiseQuasipolynomial expr1, PiecewiseQuasipolynomial expr2) {
		if (BarvinokUtils.isConstant(expr1) 
				&& StringUtils.isEmpty(expr1.getPieces().get(0).getConstraints()) 
				&& BarvinokUtils.isConstant(expr2)
				&& StringUtils.isEmpty(expr2.getPieces().get(0).getConstraints())) {
			Long l1 = BarvinokUtils.toConstant(expr1);
			Long l2 = BarvinokUtils.toConstant(expr2);
			
			if (l1 > l2) {
				return expr1;
			}
			
			return expr2;
		}
		
		return target.max(expr1, expr2);
	}

	public PiecewiseQuasipolynomial sumConsumtion(PiecewiseQuasipolynomial expr, DomainSet invariant) {
		PiecewiseQuasipolynomial e = (PiecewiseQuasipolynomial) expr;
		PiecewiseQuasipolynomial result = null;
		
		Long value = BarvinokUtils.toConstant(e);
		if (value != null && value.equals(0L)) {
			//result =  e.clone();
			result = BarvinokFactory.polynomial(invariant.getParameters(), new QuasiPolynomial("0"));
			log.debug(" | | |- sum Optimized");
		} else {
			result = target.sumConsumtion(expr, invariant);
		}
		
		return result;
	}

	public PiecewiseQuasipolynomial upperBound(PiecewiseQuasipolynomial expr, DomainSet invariant) {
		PiecewiseQuasipolynomial e = (PiecewiseQuasipolynomial) expr;
		PiecewiseQuasipolynomial result = null;
		
		Long value = BarvinokUtils.toConstant(e);
		if (value != null && value.equals(0L)) {
			//result = e.clone();
			result = BarvinokFactory.polynomial(invariant.getParameters(), new QuasiPolynomial("0"));
			log.debug(" | | |- ub Optimized");
		} else {
			result = target.upperBound(expr, invariant);
		}
		
		return result;
	}

	public void setTarget(BarvinokCalculator target) {
		this.target = target;
	}

	public BarvinokCalculator getTarget() {
		return target;
	}
}