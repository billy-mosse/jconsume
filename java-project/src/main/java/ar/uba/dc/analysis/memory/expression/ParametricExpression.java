package ar.uba.dc.analysis.memory.expression;

import java.util.Set;

public interface ParametricExpression {

	Set<String> getParameters();
	
	ParametricExpression clone();

}
