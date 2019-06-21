package ar.uba.dc.analysis.escape.graph.node;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

public class ArtificialNode implements Node{
	int id;
	String methodName;
	
	public ArtificialNode(int id, String methodName)
	{
		this.id = id;
		this.methodName = methodName;
	}
	

    @Override
    public boolean equals(Object o) {
    	if (o instanceof ArtificialNode) {
    		ArtificialNode oo = (ArtificialNode) o;
    		return id == oo.id && methodName.equals(oo.methodName);
    	} else { 
    		return false;
    	}
    }
    
    public String toString()
    {
    	return "A_" + id;
    }

	@Override
	public int compareTo(Node o) {
		int ret = this.toString().compareTo(o.toString());
		if (ret != 0)
		{
			return ret;
		}
		else
		{
			if(o.getClass().equals(ArtificialNode.class))
			{
				ArtificialNode oArtificialNode = (ArtificialNode) o;
				if(this.id == oArtificialNode.id && this.methodName == oArtificialNode.methodName)
					return 1;
			}
		}
		return 0;
	}

	@Override
	public boolean isInside() {
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
	public void changeContext(StatementId callStmtId) {
		// TODO Auto-generated method stub
		
	}
	
	 public int hashCode() { 
    	//wut.
    	return 69; 
     }
	
	@Override
	public ArtificialNode clone() {
		return new ArtificialNode(id, methodName);
	}

	@Override
	public CircularStack<StatementId> getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SootClass getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SootMethod belongsTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementId popContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJsonString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean accept(Node escapeNode) {
		// TODO Auto-generated method stub
		if(escapeNode.getClass().equals(ArtificialNode.class))
		{
			ArtificialNode artificialEscapeNode = (ArtificialNode) escapeNode;
			if(artificialEscapeNode.id == this.id && artificialEscapeNode.methodName == this.methodName)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void convertToOmegaNode() {
		// TODO Auto-generated method stub
		
	}
}
