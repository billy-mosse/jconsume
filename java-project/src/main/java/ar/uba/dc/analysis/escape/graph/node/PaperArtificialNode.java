package ar.uba.dc.analysis.escape.graph.node;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

public class PaperArtificialNode implements PaperNode{
	int id;
	String methodName;
	
	public PaperArtificialNode(int id, String methodName)
	{
		this.id = id;
		this.methodName = methodName;
	}

	 public int hashCode() { 
    	//wut.
    	return 69; 
     }
	
	@Override
	public PaperArtificialNode clone() {
		return new PaperArtificialNode(id, methodName);
	}
	
	public boolean equals(Object o) {
    	if (o instanceof PaperMethodNode) {
    		PaperArtificialNode oo = (PaperArtificialNode) o;
    		return id== oo.id && methodName.equals(oo.methodName);
    	} else { 
    		return false;
    	}
    }
	
	@Override
	public boolean accept(PaperNode escapeNode) {
		// TODO Auto-generated method stub
		if(escapeNode.getClass().equals(PaperArtificialNode.class))
		{
			PaperArtificialNode artificialEscapeNode = (PaperArtificialNode) escapeNode;
			if(artificialEscapeNode.id == this.id && artificialEscapeNode.methodName == this.methodName)
			{
				return true;
			}
		}
		return false;
	}


	public String toString()
	{
		return "A_" + this.id;
	}
	
	@Override
	public boolean isInside() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOmega() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFresh() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public CircularStack<String> getContext() {
		// TODO Auto-generated method stub
		return null;
	}
}
