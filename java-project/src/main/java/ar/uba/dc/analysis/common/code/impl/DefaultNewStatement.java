package ar.uba.dc.analysis.common.code.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.StatementVisitor;
import ar.uba.dc.analysis.common.intermediate_representation.DefaultIntermediateRepresentationParameter;
import soot.SootMethod;
import soot.jimple.Stmt;

public class DefaultNewStatement extends AbstractStatement implements NewStatement {

	public DefaultNewStatement(SootMethod belongsTo, Stmt statement, Long counter) {
		super(belongsTo, statement, counter);
	}

	@Override
	public <T> T apply(StatementVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public String getIntermediateRepresentationName() {
		return "new";
	}

	@Override
	public Set<DefaultIntermediateRepresentationParameter> getIntermediateRepresentationParameters() {
		return new LinkedHashSet<DefaultIntermediateRepresentationParameter>();
	}
}
