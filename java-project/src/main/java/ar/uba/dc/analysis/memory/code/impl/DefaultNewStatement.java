package ar.uba.dc.analysis.memory.code.impl;

import soot.SootMethod;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.memory.code.NewStatement;
import ar.uba.dc.analysis.memory.code.StatementVisitor;

public class DefaultNewStatement extends AbstractStatement implements NewStatement {

	public DefaultNewStatement(SootMethod belongsTo, Stmt statement, Long counter) {
		super(belongsTo, statement, counter);
	}

	@Override
	public <T> T apply(StatementVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
