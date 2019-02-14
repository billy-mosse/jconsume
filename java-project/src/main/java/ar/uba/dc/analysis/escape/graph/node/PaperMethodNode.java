package ar.uba.dc.analysis.escape.graph.node;

import java.util.HashMap;
import java.util.Map;

import soot.SootClass;
import soot.SootMethod;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * Kind of Stmt inside node, but global to the method.
 * Used for synthetic summary of unalysed methods returning a fresh object.
 */
public class PaperMethodNode implements PaperNode {

	/** 
	 * gives a unique id, for pretty-printing purposes.
	 * Usamos el id y dejamos fuera el contexto para poder rastrear de donde sale un nodo cuando veo
	 * un grafo (si tengo el mismo ID que en el summary del callee es mas facil encontrar de donde salio un 
	 * nodo).  
	 */
	
	
    /*private static final Map<String, Integer> nMap = new HashMap<String, Integer>();
    private static int n = 0;*/
	
	
	//ME LA ESTOY JUGANDO A QUE HAY UN SOLO METHOD_NODE POR METODO. DEBERIA ENTENDER MEJOR ESTO....
	
    /** Method that created the node */
	private int id;
	private boolean omega;
	private boolean fresh;
	
	/** contexto del nodo. Es el stack con los {@link Stmt} de soot que representan los calls por los que fue pasando el nodo desde su creacion. */
    private CircularStack<String> context;
	
    
    //El map habria que tirarlo a la mierda
    public PaperMethodNode(MethodNode mNode, CircularStack<String> context, String belongsTo) {
    	this.id = mNode.getNumber();
    	this.context = context;    	
    	
    	/*if (!nMap.containsKey(id)) { 
    		nMap.put(belongsTo, new Integer(id));
    	}*/
    }
    
    public PaperMethodNode(int id, CircularStack<String> context) {
    	this.id = id;
    	this.context = context;
    }
	
   /* public PaperMethodNode(Node origNode, CircularStack<String> context, String belongsTo) {
    	this.context = context;
    	this.id = 
    	//this.id = origNode.
	}*/

	public PaperMethodNode(IntermediateRepresentationMethod ir_method, int sensitivity) {
		
		this.context = new CircularStack<String>(sensitivity);
		this.id = 0;
		
	}

	public String toString() { 
    	return "M_" + id;
    }
    
    public String toJsonString() { 
    	return this.toString();
    }
    
    
/*
    public int hashCode() { 
    	return id.hashCode() + context.hashCode(); 
    }*/
    
    public boolean equals(Object o) {
    	if (o instanceof PaperMethodNode) {
    		PaperMethodNode oo = (PaperMethodNode) o;
    		return id== oo.id && context.equals(oo.context);
    	} else { 
    		return false;
    	}
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
	public void changeContext(String invocation) {
		this.context.push(invocation);
	}

	@Override
	public CircularStack<String> getContext() {
		return new CircularStack<String>(context);
	}

	@Override
	public PaperNode clone() {
		return new PaperMethodNode(id, context.clone());
	}

	/*@Override
	public IntermediateRepresentationMethod belongsTo() {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public boolean accept(PaperNode escapeNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOmega() {
		return omega;
	}

	@Override
	public boolean isFresh() {
		return fresh;
	}
	
}