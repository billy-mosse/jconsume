package ar.uba.dc.barvinok.expression.operation.impl;

import org.nfunk.jep.function.PostfixMathCommand;

public class MaxMathCommand extends PostfixMathCommand {

	public MaxMathCommand() {
		
		//por que en Floor es -1?
		this.numberOfParameters = -1;
	}
	
}
