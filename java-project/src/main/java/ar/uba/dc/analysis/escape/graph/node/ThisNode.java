package ar.uba.dc.analysis.escape.graph.node;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * A node representing the this parameter.
 * (should we make it a singleton ?)
 */ 
public class ThisNode extends ParamNode {

	public static ThisNode node = new ThisNode();
	
	private ThisNode() { 
    	super(-1); 
    }

    public String toString() { 
    	return "this"; 
    }
    
    public boolean isInside() { 
    	return false; 
    }

    public boolean isLoad() { 
    	return false; 
    }

    public boolean isParam() { 
    	return true; 
    }

	@Override
	public void changeContext(StatementId callStmtId) {
		// No hacemos nada. No hay contexto para estos nodos
	}
	
	@Override
	public CircularStack<StatementId> getContext() {
		return null;
	}

	@Override
	public ThisNode clone() {
		return node;
	}

	@Override
	public SootClass getType() {
		return null;
	}

	@Override
	public SootMethod belongsTo() {
		return null;
	}

	@Override
	public StatementId popContext() {
		return null;
	}

	public boolean accept(StmtNode node) {
		return false;
	}
	
}
