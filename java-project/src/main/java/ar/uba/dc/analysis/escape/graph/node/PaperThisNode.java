package ar.uba.dc.analysis.escape.graph.node;

import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;
import soot.SootClass;
import soot.SootMethod;

public class PaperThisNode extends PaperParamNode{
public static PaperThisNode node = new PaperThisNode();
	
	public PaperThisNode() { 
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
	public void changeContext(String belongsTo) {
		// No hacemos nada. No hay contexto para estos nodos
	}
	
	@Override
	public CircularStack<String> getContext() {
		return null;
	}

	@Override
	public PaperThisNode clone() {
		return node;
	}

	public boolean accept(StmtNode node) {
		return false;
	}
}
