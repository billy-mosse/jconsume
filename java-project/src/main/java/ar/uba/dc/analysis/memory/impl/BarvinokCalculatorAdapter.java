package ar.uba.dc.analysis.memory.impl;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;

import ar.uba.dc.analysis.memory.CountingTheory;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.memory.code.NewStatement;
import ar.uba.dc.analysis.memory.code.Statement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.BarvinokCalculator;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.invariant.InvariantProvider;

public class BarvinokCalculatorAdapter implements CountingTheory, SymbolicCalculator {

	private static Log log = LogFactory.getLog(BarvinokCalculatorAdapter.class);
	
	private InvariantProvider invariantProvider;
	
	protected BarvinokParametricExpressionFactory expressionFactory;
	
	private BarvinokCalculator calculator;
	
	@Override
	public ParametricExpression count(Statement stmt) {
		DomainSet inv = invariantProvider.getInvariant(stmt);
		
		PiecewiseQuasipolynomial result = calculator.countExecutions(inv);
		
		if (log.isDebugEnabled()) {
			log.debug("count(" + stmt + ", " + inv + ") = " + result);
		}
		
		return expressionFactory.polynomial(result);
	}

	@Override
	public ParametricExpression add(ParametricExpression... expressions) {
		PiecewiseQuasipolynomial[] pols = new PiecewiseQuasipolynomial[expressions.length];  
		
		int idx = 0;
		for (ParametricExpression expr : expressions) {
			pols[idx] = ((BarvinokParametricExpression) expr).expr;
			idx++;
		}
		
		PiecewiseQuasipolynomial result = calculator.add(pols);
		
		if (log.isDebugEnabled()) {
			log.debug("add(" + Arrays.toString(pols) + ") = " + result);
		}
		
		return expressionFactory.polynomial(result);
		
	}

	@Override
	public ParametricExpression maximize(ParametricExpression target, Statement stmt) {
		PiecewiseQuasipolynomial polynomial = ((BarvinokParametricExpression) target).expr;
		DomainSet inv = invariantProvider.getInvariant(stmt, InvariantProvider.Operation.MAXIMIZE);
		
		PiecewiseQuasipolynomial result = calculator.upperBound(polynomial, inv);
		
		if (log.isDebugEnabled()) {
			log.debug("maximize(" + polynomial + ", " + inv + ") = " + result);
		}
		
		return expressionFactory.polynomial(result);
	}

	@Override
	public ParametricExpression summate(ParametricExpression target, Statement stmt) {
		PiecewiseQuasipolynomial polynomial = ((BarvinokParametricExpression) target).expr;
		DomainSet inv = invariantProvider.getInvariant(stmt, InvariantProvider.Operation.SUMMATE);
		PiecewiseQuasipolynomial result = null;
		
		//FIXME esto es un hack para un ejemplo. Hacer un adapter con un file de rules para sobreescribir expresiones
		if(stmt.belongsTo().getDeclaringClass().getName().contains("ar.uba.dc.jolden.mst.Vertex") && stmt.belongsTo().getName().equals("<init>")) {
			result = new PiecewiseQuasipolynomial();
			// Parametros de la expresion
			for (String param: inv.getParameters()) {
				result.addParameter(param);
			}
			
			result.add(new QuasiPolynomial("1/4 * numvert","numvert >= 1" ));
		} else {
			 result = calculator.sumConsumtion(polynomial, inv);
			
			if (log.isDebugEnabled()) {
				log.debug("summate(" + polynomial + ", " + inv + ") = " + result);
			}
		}
		return expressionFactory.polynomial(result);
	}

	@Override
	public ParametricExpression supreme(ParametricExpression e1, ParametricExpression e2) {
		PiecewiseQuasipolynomial polynomial1 = ((BarvinokParametricExpression) e1).expr;
		PiecewiseQuasipolynomial polynomial2 = ((BarvinokParametricExpression) e2).expr;
		
		PiecewiseQuasipolynomial result = calculator.max(polynomial1, polynomial2);
		
		if (log.isDebugEnabled()) {
			log.debug("supreme(" + polynomial1 + ", " + polynomial2 + ") = " + result);
		}
		
		return expressionFactory.polynomial(result);
	}

	public void setInvariantProvider(InvariantProvider invariantProvider) {
		this.invariantProvider = invariantProvider;
	}

	public void setCalculator(BarvinokCalculator calculator) {
		this.calculator = calculator;
	}

	public void setExpressionFactory(
			BarvinokParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}
}
