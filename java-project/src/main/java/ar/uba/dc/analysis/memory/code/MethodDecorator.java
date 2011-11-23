package ar.uba.dc.analysis.memory.code;

import soot.SootMethod;

public interface MethodDecorator {

	MethodBody decorate(SootMethod method);

}
