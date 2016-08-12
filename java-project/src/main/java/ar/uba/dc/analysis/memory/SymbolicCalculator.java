package ar.uba.dc.analysis.memory;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;


public interface SymbolicCalculator {

	public ParametricExpression maximize(ParametricExpression target, Statement stmt);

	public ParametricExpression add(ParametricExpression... expressions);
	
	public ParametricExpression substract(ParametricExpression expression1, ParametricExpression expression2);
	
	public ParametricExpression supreme(ParametricExpression e1, ParametricExpression e2);

	public ParametricExpression summate(ParametricExpression target, Statement stmt);

	public ParametricExpression boundIfHasFold(ParametricExpression acumTotalResiduals);

	public ParametricExpression summateIfClassCalledChangedDuringLoop(
			ParametricExpression MAX_totalResidualsBeforeSummate, ParametricExpression totalResiduals,
			Statement virtualInvoke);

}
