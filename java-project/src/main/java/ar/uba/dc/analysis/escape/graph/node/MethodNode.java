package ar.uba.dc.analysis.escape.graph.node;

import java.util.HashMap;
import java.util.Map;

import org.jboss.util.NotImplementedException;

import soot.SootClass;
import soot.SootMethod;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * Kind of Stmt inside node, but global to the method.
 * Used for synthetic summary of unalysed methods returning a fresh object.
 */
public class MethodNode implements Node {

	/** 
	 * gives a unique id, for pretty-printing purposes.
	 * Usamos el id y dejamos fuera el contexto para poder rastrear de donde sale un nodo cuando veo
	 * un grafo (si tengo el mismo ID que en el summary del callee es mas facil encontrar de donde salio un 
	 * nodo).  
	 */
    private static final Map<SootMethod, Integer> nMap = new HashMap<SootMethod, Integer>();
    private static int n = 0;
	
    /** Method that created the node */
	private SootMethod id;
	
	public boolean omega;
	private boolean fresh;
	
	/** contexto del nodo. Es el stack con los {@link Stmt} de soot que representan los calls por los que fue pasando el nodo desde su creacion. */
    private CircularStack<StatementId> context;
	
    private MethodNode(SootMethod id, CircularStack<StatementId> context) {
    	this.id = id;
    	this.context = context;
    	if (!nMap.containsKey(id)) { 
    		nMap.put(id, new Integer(n)); 
    		n++; 
    	}
    }
    
    public MethodNode(SootMethod id, int sensitivity) {
    	this(id, new CircularStack<StatementId>(sensitivity));
    }
	
    public MethodNode(SootMethod id, int sensitivity, boolean omega) {
    	this(id, sensitivity);
    	
    	//If method is not fresh then return value is an omega node.
    	this.omega=omega;
	}

	public String toString() { 
    	return (omega? "[W] " : "") + "M_" + nMap.get(id);
    }
    
    public String toJsonString() { 
    	return this.toString();
    }
    
    

    public int hashCode() { 
    	return id.hashCode() + context.hashCode(); 
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o instanceof MethodNode) {
    		MethodNode oo = (MethodNode) o;
    		return id.equals(oo.id) && context.equals(oo.context);
    	} else { 
    		return false;
    	}
    }

    public boolean isInside() { 
    	return true; 
    }

    public boolean isLoad() { 
    	return false; 
    }

    public boolean isParam() { 
    	return false; 
    }

	public void changeContext(StatementId callStmtId) {
		context.push(callStmtId);
	}

	@Override
	public MethodNode clone() {
		return new MethodNode(id, context.clone());
	}

	public SootMethod getId() {
		return id;
	}

	public CircularStack<StatementId> getContext() {
		return new CircularStack<StatementId>(context);
	}

	@Override
	public SootClass getType() {
		return null;
	}

	@Override
	public SootMethod belongsTo() {
		return id;
	}

	@Override
	public StatementId popContext() {
		return context.pop();
	}

	@Override
	public boolean accept(Node node) {
		return this.equals(node);
	}
	
	@Override
	public int getNumber() {
		return nMap.get(id);
	}
	
	@Override
	public int compareTo(Node o) {
		return this.toString().compareTo(o.toString());
	}

	@Override
	public boolean isOmega() {
		return this.omega;
	}

	@Override
	public boolean isFresh() {
		return this.fresh;
	}

	@Override
	public void convertToOmegaNode() {
		this.omega = true;		
	}	
}