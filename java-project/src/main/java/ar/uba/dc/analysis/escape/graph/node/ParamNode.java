package ar.uba.dc.analysis.escape.graph.node;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * A node representing a method parameter.
 * Each method parameter has a number, starting from 0.
 * 
 */
public class ParamNode implements Node {

	private int id;
    
    public ParamNode(int id) { 
    	this.id = id; 
    }

    public String toString() { 
    	return "P_" + id; 
    }
    
    public String toJsonString()
    {
    	return this.toString();
    }

    public int hashCode() { 
    	return id; 
    }
    
    public boolean equals(Object o) {
    	if (o instanceof ParamNode) {
    		return ((ParamNode) o).id == id;
    	} else {
    		return false;
    	}
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
	
	public int getIndex() {
		return id;
	}

	public void changeContext(StatementId callStmtId) {
		// No hacemos nada. No hay contexto para estos nodos
	}

	public ParamNode clone() {
		return this;
	}

	@Override
	public SootClass getType() {
		return null;
	}

	@Override
	public CircularStack<StatementId> getContext() {
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

	@Override
	public boolean accept(Node node) {
		return false;
	}
	
}
