package ar.uba.dc.analysis.memory.code.impl;

import ar.uba.dc.analysis.memory.code.CallStatement;
import ar.uba.dc.analysis.memory.code.StatementVisitor;
import soot.SootMethod;
import soot.jimple.Stmt;

public class DefaultCallStatement extends AbstractStatement implements CallStatement {

	private SootMethod calledMethod;
	private SootMethod calledImplementation;
	
	public DefaultCallStatement(SootMethod belongsTo, Stmt statement, Long counter) {
		this(belongsTo, statement, counter, statement.getInvokeExpr().getMethod(), statement.getInvokeExpr().getMethod());
	}
	
	public DefaultCallStatement(SootMethod belongsTo, Stmt statement, Long counter, SootMethod calledMethod, SootMethod calledImplementation) {
		super(belongsTo, statement, counter);
		this.calledMethod = calledMethod;
		this.calledImplementation = calledImplementation;
	}

	public SootMethod getCalledMethod() {
		return calledMethod;
	}

	@Override
	public SootMethod getCalledImplementation() {
		return calledImplementation;
	}

	@Override
	public <T> T apply(StatementVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public CallStatement virtualInvoke(SootMethod callee) {
		return new DefaultCallStatement(belongsTo, statement, counter, calledMethod, callee);
	}
}
