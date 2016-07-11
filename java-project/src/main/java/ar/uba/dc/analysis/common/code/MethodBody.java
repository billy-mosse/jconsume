package ar.uba.dc.analysis.common.code;

import java.util.LinkedList;
import java.util.List;

import soot.SootMethod;


public class MethodBody {

	private SootMethod belongsTo;
	
	private List<NewStatement> news = new LinkedList<NewStatement>();
	
	private List<CallStatement> calls = new LinkedList<CallStatement>();
	
	public MethodBody(SootMethod belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	public SootMethod belongsTo() {
		return belongsTo;
	}

	public void addStatement(NewStatement newStmt) {
		news.add(newStmt);
	}
	
	public List<NewStatement> getNewStatements() {
		return news;
	}
	
	public void addStatement(CallStatement callStmt) {
		calls.add(callStmt);
	}

	public	List<CallStatement> getCallStatements() {
		return calls;
	}

	public List<Statement> getStatements() {
		List<Statement> statements = new LinkedList<Statement>(news);
		statements.addAll(calls);

		return statements;
	}
}
