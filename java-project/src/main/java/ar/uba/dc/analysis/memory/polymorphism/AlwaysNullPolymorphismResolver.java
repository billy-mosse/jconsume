package ar.uba.dc.analysis.memory.polymorphism;

import soot.SootClass;
import ar.uba.dc.analysis.memory.PolymorphismResolver;
import ar.uba.dc.analysis.common.code.CallStatement;

public class AlwaysNullPolymorphismResolver implements PolymorphismResolver {

	@Override
	public SootClass getTarget(CallStatement callStmt) {
		return null;
	}

}
