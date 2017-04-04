package ar.uba.dc.analysis.memory.impl;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionUtils;

public class BarvinokParametricExpressionUtils /*implements ParametricExpressionUtils */{

	//TODO: Estoy usando java 7, que no permite tener metodos estaticos en interfaces 
	//asi que voy a hacer que esta clase no implemente ParametricExpressionUtils
	//pero cuando deje de usar el viejo daikon y pase al nuevo voy a poder usar java 8
	//y voy a poder usar interfaces
	
	//Primera forma boba de analizar infinitud
	public static boolean isInfinite(ParametricExpression expression) {
		BarvinokParametricExpression barvinokExpression = (BarvinokParametricExpression) expression;
		return barvinokExpression.asString().contains("infty");
		
	}

}
