package ar.uba.dc.analysis.escape.graph.node;

import org.jboss.util.NotImplementedException;

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
	
	private boolean omega;
    
    public ParamNode(int id) { 
    	this.id = id; 
    }
    
    public ParamNode(int id, boolean omega) { 
    	this(id);
    	this.omega = omega;
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
    
    @Override
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
	
	@Override
	public int getNumber() {
		return this.id;
	}
	
	@Override
	public int compareTo(Node o) {
		return this.toString().compareTo(o.toString());
	}
	
	
	@Override
	public boolean isOmega() {
		return omega;
	}

	@Override
	public boolean isFresh() {
		return false;
	}
}
