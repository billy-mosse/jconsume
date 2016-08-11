package ar.uba.dc.analysis.common.code;

import java.util.LinkedList;
import java.util.List;

import soot.SootMethod;


public class BasicMethodBody {

	private SootMethod belongsTo;
	
	private List<Statement> statements = new LinkedList<Statement>();
	
	public BasicMethodBody(SootMethod belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	public SootMethod belongsTo() {
		return belongsTo;
	}

	public void addStatement(Statement newStmt) {
		statements.add(newStmt);
	}
	
	public List<Statement> getStatements() {
		return this.statements;
	}
}
