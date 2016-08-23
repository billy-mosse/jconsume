package ar.uba.dc.analysis.escape.graph;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * Interface shared by all kinds of nodes in a EscapeGraph.
 * Such nodes are immutables. They are hashable and two nodes are equal
 * only if they have the same kind and were constructed using the same
 * arguments (structural equality).
 */
public interface Node extends Cloneable { 

    /** Is it an inside node ? */
    public boolean isInside();

    /** Is it a load node ? */
    public boolean isLoad();

    /** Is it a parameter or this node ? */
    public boolean isParam();

	public void changeContext(StatementId callStmtId);
	
	public CircularStack<StatementId> getContext();

	public Node clone();

	/** if it's an inside node, return type of that node */
	public SootClass getType();

	/** 
	 * Method in which node was created (only apply for inside nodes). 
	 * If this information is not available, the method should return null  
	 * */
	public SootMethod belongsTo();

	public StatementId popContext();

	public boolean accept(Node escapeNode);
}
