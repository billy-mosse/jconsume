package ar.uba.dc.analysis.escape.graph.node;

import soot.SootClass;
import soot.SootMethod;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/** 
 * The GBL node.
 */
public class PaperGlobalNode implements PaperNode {
	
	public static PaperGlobalNode node = new PaperGlobalNode();

	public String toString() { 
    	return "GBL"; 
    }
	public String toJsonString() { 
    	return this.toString(); 
    }

    public int hashCode() { 
    	//wut.
    	return 69; 
    }
    
    public boolean equals(Object o) { 
    	return o instanceof PaperGlobalNode; 
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
    
	public PaperGlobalNode clone() {
    	return node;
    }

	/*@Override
	public SootClass getType() {
		return null;
	}*/

	@Override
	public CircularStack<String> getContext() {
		return null;
	}
/*
	@Override
	public IntermediateRepresentationMethod belongsTo() {
		return null;
	}*/

	/*@Override
	public StatementId popContext() {
		// No hacemos nada. No hay contexto para estos nodos
		return null;
	}*/

	@Override
	public boolean accept(PaperNode node) {
		return false;
	}
	
	@Override
	public void changeContext(String invocation) {
		// no hacemos nada
		
	}
}
