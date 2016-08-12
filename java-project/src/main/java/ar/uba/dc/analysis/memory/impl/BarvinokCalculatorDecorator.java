package ar.uba.dc.analysis.memory.impl;

import java.util.HashMap;
import java.util.Map;

import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.code.StatementVisitor;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

public class BarvinokCalculatorDecorator extends BarvinokCalculatorAdapter {

	protected BarvinokCalculatorAdapter calculatorAdapter;
	
	protected StatementVisitor<String> statementVisitor;
	
	private Map<String, ParametricExpression> summateOverride;

	
	public BarvinokCalculatorAdapter getCalculatorAdapter() {
		return calculatorAdapter;
	}

	public void setCalculatorAdapter(BarvinokCalculatorAdapter calculatorAdapter) {
		this.calculatorAdapter = calculatorAdapter;
	}

	public StatementVisitor<String> getStatementVisitor() {
		return statementVisitor;
	}

	public void setStatementVisitor(StatementVisitor<String> statementVisitor) {
		this.statementVisitor = statementVisitor;
	}

	public ParametricExpression add(ParametricExpression... expressions) {
		return calculatorAdapter.add(expressions);
	}

	public ParametricExpression count(Statement stmt) {
		return calculatorAdapter.count(stmt);
	}

	public ParametricExpression maximize(ParametricExpression target,
			Statement stmt) {
		return calculatorAdapter.maximize(target, stmt);
	}

	
	public ParametricExpression summateIfClassCalledChangedDuringLoop(ParametricExpression target, ParametricExpression totalResiduals, Statement stmt) {

		String stamentId = stmt.apply(this.statementVisitor);
		if(this.override(stamentId))
			return this.summateOverride.get(stamentId);

		return calculatorAdapter.summateIfClassCalledChangedDuringLoop(target, totalResiduals, stmt);
	}

	public ParametricExpression summate(ParametricExpression target, Statement stmt) {

		String stamentId = stmt.apply(this.statementVisitor);
		if(this.override(stamentId))
			return this.summateOverride.get(stamentId);

		return calculatorAdapter.summate(target, stmt);
	}

	private boolean override(String stamentId) {
		if(this.summateOverride == null)
			init(); //FIXME
		
		return this.summateOverride.containsKey(stamentId);
	}

	private void init() {
		this.summateOverride = new HashMap<String, ParametricExpression>();		
		PiecewiseQuasipolynomial result = new PiecewiseQuasipolynomial();
		// Parametros de la expresion
		result.addParameter("numvert");
		result.add(new QuasiPolynomial("1/4 * numvert","numvert >= 1" ));
		this.summateOverride.put(
				"Invoke@<ar.uba.dc.jolden.mst.Vertex: void <init>(ar.uba.dc.jolden.mst.Vertex,int)>:1 --> <ar.uba.dc.jolden.mst.Hashtable: void <init>(int)>",
				this.calculatorAdapter.expressionFactory.polynomial(result));
	}

	public ParametricExpression supreme(ParametricExpression e1,
			ParametricExpression e2) {
		return calculatorAdapter.supreme(e1, e2);
	}
	
	public ParametricExpression substract(ParametricExpression e1,
			ParametricExpression e2) {
		return calculatorAdapter.substract(e1, e2);
	}
	
	
	public ParametricExpression boundIfHasFold(ParametricExpression expression)
	{
		return calculatorAdapter.boundIfHasFold(expression);
	}


	
	
}
