package ar.uba.dc.analysis.escape.graph.node;

import org.jboss.util.NotImplementedException;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * A node representing a method parameter.
 * Each method parameter has a number, starting from 0.
 * 
 */
public class PaperParamNode implements PaperNode {

	private int id;
	
	private boolean omega;
    
    public PaperParamNode(int id) { 
    	this.id = id; 
    }

    public PaperParamNode(Node origNode) {
		this.id = origNode.getNumber();
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
    	if (o instanceof PaperParamNode) {
    		return ((PaperParamNode) o).id == id;
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

	public PaperParamNode clone() {
		return this;
	}
	
	@Override
	public void changeContext(String invocation) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean accept(PaperNode escapeNode) {
		// TODO Auto-generated method stub
		return false;
	}

	/*@Override
	public IntermediateRepresentationMethod belongsTo() {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public CircularStack<String> getContext() {
		// TODO Auto-generated method stub
		return null;
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
