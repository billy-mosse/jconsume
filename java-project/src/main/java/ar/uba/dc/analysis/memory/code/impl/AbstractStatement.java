package ar.uba.dc.analysis.memory.code.impl;

import ar.uba.dc.analysis.memory.code.Statement;
import soot.SootMethod;
import soot.jimple.Stmt;

public abstract class AbstractStatement implements Statement {

	protected SootMethod belongsTo;
	protected Stmt statement;
	protected Long counter;
	
	public AbstractStatement(SootMethod belongsTo, Stmt statement, Long counter) {
		this.belongsTo = belongsTo;
		this.statement = statement;
		this.counter = counter;
	}
	
	public SootMethod belongsTo() {
		return belongsTo;
	}
	
	public Long getCounter() {
		return counter;
	}

	public Stmt getStatement() {
		return statement;
	}
	
	public String toString() {
		return 	belongsTo.getDeclaringClass().getName() + " " + 
				belongsTo.getSubSignature() + ": " + 
				statement.toString() + " (" + counter + ")";
	}
}
