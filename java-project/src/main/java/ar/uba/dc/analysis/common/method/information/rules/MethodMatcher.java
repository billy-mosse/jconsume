package ar.uba.dc.analysis.common.method.information.rules;

import soot.SootMethod;

public class MethodMatcher extends AbstractMatcher {

	public boolean match(SootMethod method) {
		return super.match(method.getName());
	}
	
}
