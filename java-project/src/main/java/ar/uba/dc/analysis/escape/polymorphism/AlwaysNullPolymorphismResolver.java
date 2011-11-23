package ar.uba.dc.analysis.escape.polymorphism;

import soot.SootClass;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.PolymorphismResolver;

public class AlwaysNullPolymorphismResolver implements PolymorphismResolver {

	@Override
	public SootClass getTarget(Stmt callStmt, EscapeSummary context) {
		return null;
	}

}
