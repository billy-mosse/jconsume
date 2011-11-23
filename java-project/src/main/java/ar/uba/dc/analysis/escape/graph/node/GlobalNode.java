package ar.uba.dc.analysis.escape.graph.node;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/** 
 * The GBL node.
 */
public class GlobalNode implements Node {
	
	public static GlobalNode node = new GlobalNode();

	public String toString() { 
    	return "GBL"; 
    }

    public int hashCode() { 
    	return 69; 
    }
    
    public boolean equals(Object o) { 
    	return o instanceof GlobalNode; 
    }
    
    public boolean isInside() { 
    	return false; 
    }

    public boolean isLoad() { 
    	return false; 
    }

    public boolean isParam() { 
    	return false; 
    }

	public void changeContext(StatementId callStmtId) {
		// No hacemos nada. No hay contexto para estos nodos
	}
    
	public GlobalNode clone() {
    	return node;
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
		// No hacemos nada. No hay contexto para estos nodos
		return null;
	}

	@Override
	public boolean accept(Node node) {
		return false;
	}
	
}
