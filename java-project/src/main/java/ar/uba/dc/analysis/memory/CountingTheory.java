package ar.uba.dc.analysis.memory;

import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;

public interface CountingTheory {

	public ParametricExpression count(Statement stmt);

}
