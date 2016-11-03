package ar.uba.dc.analysis.escape.graph.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import soot.SootClass;
import soot.SootMethod;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.NewExpr;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * A node created dynamically and attached to a statement Stmt.
 * Can be either an inside or a load node.
 * Two such nodes are equal if and only if they have the same inside / load
 * flag and are attached to the same statement (we use Stmt.equal here).
 */


//TODO: codear la clase
public class PaperStmtNode implements PaperNode {

	
	protected int id;
	protected boolean isInside;
    protected CircularStack<String> context;

	  
	public PaperStmtNode(Node origNode, CircularStack<String> context) {
		this.id = origNode.getNumber();
		this.isInside = origNode.isInside();
		this.context = context;
	}

	public PaperStmtNode(int id, boolean isInside, CircularStack<String> context) {
		this.id = id;
		this.isInside = isInside;
		this.context = context;		
	}

	@Override
	public boolean isInside() {
		// TODO Auto-generated method stub
		return isInside;
	}

	@Override
	public boolean isLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isParam() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void changeContext(String invocation) {
		this.context.push(invocation);
	}

	@Override
	public PaperNode clone() {
		return new PaperStmtNode(id, isInside, context.clone());
	}

	@Override
	public IntermediateRepresentationMethod belongsTo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean equals(Object o) {
    	if (o instanceof PaperStmtNode) {
    		PaperStmtNode oo = (PaperStmtNode)o;
    		boolean first = id== oo.id;
    		boolean second = isInside == oo.isInside ;
    		boolean third = context.equals(oo.context);
    		return first && second && third;
    	} else {
    		return false;
    	}
    }
	

	@Override
	public boolean accept(PaperNode node) {
		return this.equals(node);
	}

	@Override
	public CircularStack<String> getContext() {
		return new CircularStack<String>(context);
	}
	
	

}
