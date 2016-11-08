package ar.uba.dc.analysis.memory.impl;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;

import ar.uba.dc.analysis.memory.CountingTheory;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
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
	public ParametricExpression count(Line newLine) {
		DomainSet inv = newLine.getInvariant();
		
		PiecewiseQuasipolynomial result = calculator.countExecutions(inv);
		
		if (log.isDebugEnabled()) {
			log.debug("count(" + newLine.toHumanReadableString() + ", " + inv + ") = " + result);
		}
		
		return expressionFactory.polynomial(result);
	}

	
	public ParametricExpression boundIfHasFold(ParametricExpression expression)
	{
		PiecewiseQuasipolynomial pol = new PiecewiseQuasipolynomial();
		pol = ((BarvinokParametricExpression)expression).expr;
		
		PiecewiseQuasipolynomial result = calculator.boundIfHasFold(pol);

		return expressionFactory.polynomial(result);
	}
	
	
	//BILLY
	//Todavia no lo implemento pero quiero debuguear	
	//A diferencia de add, substract solo tiene sentido aplicarselo a dos epxresiones
	//Hace falta pasar por el adapter? O esto solo se hace cuando no sabes cuantos son?
	@Override
	public ParametricExpression substract(ParametricExpression expression1, ParametricExpression expression2) {		
		
		PiecewiseQuasipolynomial pol1 = new PiecewiseQuasipolynomial();
		PiecewiseQuasipolynomial pol2 = new PiecewiseQuasipolynomial();

		pol1 = ((BarvinokParametricExpression)expression1).expr;
		pol2 = ((BarvinokParametricExpression)expression2).expr;		
		
		PiecewiseQuasipolynomial result = calculator.substract(pol1, pol2);
		
		if (log.isDebugEnabled()) {
			log.debug("substract(" + expression2.toString() + "from " + expression1.toString() + ") = " + result);
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
		DomainSet inv = invariantProvider.getInvariantWithBinding(stmt, InvariantProvider.Operation.MAXIMIZE);
		
		PiecewiseQuasipolynomial result = calculator.upperBound(polynomial, inv);
		
		if (log.isDebugEnabled()) {
			log.debug("maximize(" + polynomial + ", " + inv + ") = " + result);
		}
		
		return expressionFactory.polynomial(result);
	}
	
	@Override
	public ParametricExpression maximize(ParametricExpression target, Invocation invocation, DomainSet invariant) {
		PiecewiseQuasipolynomial polynomial = ((BarvinokParametricExpression) target).expr;
		
		PiecewiseQuasipolynomial result = calculator.upperBound(polynomial, invariant);
		
		if (log.isDebugEnabled()) {
			log.debug("maximize(" + polynomial + ", " + invariant + ") = " + result);
		}
		
		return expressionFactory.polynomial(result);
	}

	
	
	
	
	@Override
	public ParametricExpression summateIfClassCalledChangedDuringLoop(ParametricExpression target, ParametricExpression totalResiduals, Statement stmt) {
		PiecewiseQuasipolynomial polynomial = ((BarvinokParametricExpression) target).expr;
		DomainSet inv = invariantProvider.getInvariantWithBinding(stmt, InvariantProvider.Operation.SUMMATE);
		if(inv.checkIfClassCalledChangedDuringLoop())
			return summateWithInvariant(target, stmt, inv);
		else
			return totalResiduals;
	}
	
	@Override
	public ParametricExpression summateIfClassCalledChangedDuringLoop(ParametricExpression target, ParametricExpression totalResiduals, Invocation invocation, DomainSet invariant) {
		PiecewiseQuasipolynomial polynomial = ((BarvinokParametricExpression) target).expr;
				
		if(invariant.checkIfClassCalledChangedDuringLoop())
			return summateWithInvariant(target, invocation, invariant);
		else
			return totalResiduals;
		}
	
	
	
	@Override
	public ParametricExpression summate(ParametricExpression target, Statement stmt) {		
		DomainSet inv = invariantProvider.getInvariantWithBinding(stmt, InvariantProvider.Operation.SUMMATE);
		return summateWithInvariant(target,stmt, inv);
	}	
	
	@Override
	public ParametricExpression summate(ParametricExpression target, Invocation invocation, DomainSet invariant) {
		return summateWithInvariant(target, invocation, invariant);
		}
	
	
	public ParametricExpression summateWithInvariant(ParametricExpression target, Statement stmt, DomainSet inv) {
		PiecewiseQuasipolynomial polynomial = ((BarvinokParametricExpression) target).expr;
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
	
	
	public ParametricExpression summateWithInvariant(ParametricExpression target, Invocation invocation, DomainSet inv) {
		PiecewiseQuasipolynomial polynomial = ((BarvinokParametricExpression) target).expr;
		PiecewiseQuasipolynomial result = null;		
		//FIXME esto es un hack para un ejemplo. Hacer un adapter con un file de rules para sobreescribir expresiones
		
		 result = calculator.sumConsumtion(polynomial, inv);
		
		if (log.isDebugEnabled()) {
			log.debug("summate(" + polynomial + ", " + inv + ") = " + result);
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
