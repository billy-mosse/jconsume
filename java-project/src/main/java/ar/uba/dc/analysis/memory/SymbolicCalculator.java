package ar.uba.dc.analysis.memory;

import ar.uba.dc.analysis.memory.code.Statement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;


public interface SymbolicCalculator {

	public ParametricExpression maximize(ParametricExpression target, Statement stmt);
	
	public ParametricExpression add(ParametricExpression... expressions);
	
	public ParametricExpression supreme(ParametricExpression e1, ParametricExpression e2);

	public ParametricExpression summate(ParametricExpression target, Statement stmt);

}
