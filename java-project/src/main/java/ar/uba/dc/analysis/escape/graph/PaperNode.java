package ar.uba.dc.analysis.escape.graph;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;
import soot.SootClass;
import soot.SootMethod;

public interface PaperNode extends Cloneable { 

    /** Is it an inside node ? */
    public boolean isInside();

    /** Is it a load node ? */
    public boolean isLoad();

    /** Is it a parameter or this node ? */
    public boolean isParam();

    //O es line?
    public void changeContext(Invocation invocation);
	
	public CircularStack<Invocation> getContext();

	public PaperNode clone();

	/** if it's an inside node, return type of that node */
	//public SootClass getType();

	/** 
	 * Method in which node was created (only apply for inside nodes). 
	 * If this information is not available, the method should return null  
	 * */
	public IntermediateRepresentationMethod belongsTo();

	//public StatementId popContext();
	
	//public String toJsonString();

	public boolean accept(PaperNode escapeNode);
}

