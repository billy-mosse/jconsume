package ar.uba.dc.analysis.escape.graph.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import soot.SootClass;
import soot.SootMethod;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.NewExpr;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * A node created dynamically and attached to a statement Stmt.
 * Can be either an inside or a load node.
 * Two such nodes are equal if and only if they have the same inside / load
 * flag and are attached to the same statement (we use Stmt.equal here).
 */


//TODO: codear la clase
public class PaperStmtNode implements PaperNode {

	/** 
	 * gives a unique id, for pretty-printing purposes.
	 * Usamos el id y dejamos fuera el contexto para poder rastrear de donde sale un nodo cuando veo
	 * un grafo (si tengo el mismo ID que en el summary del callee es mas facil encontrar de donde salio un 
	 * nodo).  
	 */
    private static final Map<StatementId, Integer> nMap = new HashMap<StatementId, Integer>();
    private static int n = 0;

    private static final Set<SootMethod> used = new HashSet<SootMethod>();
	
    private static final Map<StatementId, Integer> new_nMap = new HashMap<StatementId, Integer>();
    private static int new_n = 0;
    
	/** Statement and method that created the node */
	private StatementId id;
	
	/** true if an inside node, false if an load node */
    private boolean inside;

    /** contexto del nodo. Es el stack con los {@link Stmt} de soot que representan los calls por los que fue pasando el nodo desde su creacion. */
    private CircularStack<Invocation> context;
    
    private PaperStmtNode(StatementId id, boolean inside, CircularStack<Invocation> context) {
    	this.id = id;
    	this.inside = inside;
    	this.context = context;
		if (!nMap.containsKey(id)) { 
			nMap.put(id, new Integer(n)); 
			n++; 
		}
		
		//Si estoy en un nuevo metodo reseteo el contador
		if(used.add(id.getMethodOfId()))
		{
			new_n = 0;
		}
		
		if (!new_nMap.containsKey(id)) { 
			new_nMap.put(id, new Integer(new_n));
			new_n++; 
		}
    }
    
    public PaperStmtNode(StatementId id, boolean inside, int sensitivity) {
    	this(id, inside, new CircularStack<Invocation>(sensitivity));
    }
    
    public PaperStmtNode(Node origNode) {
		// TODO Auto-generated constructor stub
	}

	public String toString() { 
    	if (inside) {
    		return "I_" + nMap.get(id); 
    	} else {
    		return "L_" + nMap.get(id);
    	}
    }
    
    public String toJsonString()
    {
    	if (inside) {
		return "I_" + new_nMap.get(id); 
		} else {
			return "L_" + new_nMap.get(id);
		}
    	
    }
    
    public int hashCode() { 
    	return id.hashCode() + context.hashCode(); 
    }
    
    public boolean equals(Object o) {
    	if (o instanceof PaperStmtNode) {
    		PaperStmtNode oo = (PaperStmtNode)o;
    		boolean first = id.equals(oo.id);
    		boolean second = inside == oo.inside ;
    		boolean third = context.equals(oo.context);
    		return first && second && third;
    	} else {
    		return false;
    	}
    }
    
    public boolean isInside() { 
    	return inside; 
    }

    public boolean isLoad() { 
    	return !inside; 
    }

    public boolean isParam() { 
    	return false; 
    }
    
    /*public void changeContext(StatementId callStmtId) {
		context.push(callStmtId);
	}*/

	@Override
	public PaperStmtNode clone() {
		return new PaperStmtNode(id, inside, context.clone());
	}

	public StatementId getId() {
		return id;
	}

	public CircularStack<Invocation> getContext() {
		return new CircularStack<Invocation>(context);
	}

	@Override
	public void changeContext(Invocation invocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntermediateRepresentationMethod belongsTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean accept(PaperNode escapeNode) {
		// TODO Auto-generated method stub
		return false;
	}

	/*@Override
	public SootClass getType() {
		NewExpr newExpr = null;
		
		if (id.getId() instanceof AssignStmt) {
			Value right = ((AssignStmt) id.getId()).getRightOp();
			if (right instanceof NewExpr) {
				newExpr = (NewExpr) right;
			}
		}
		
		if (id.getId() instanceof NewExpr) {
			newExpr = (NewExpr) id.getId();
		}
		
		if (newExpr != null) {
			return newExpr.getBaseType().getSootClass();
		}
		
		return null;
	}*/

	/*@Override
	public SootMethod belongsTo() {
		return id.getMethodOfId();
	}

	@Override
	public StatementId popContext() {
		return context.pop();
	}

	@Override
	public boolean accept(Node node) {
		return equals(node);
	}

	@Override
	public void changeContext(Invocation invocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntermediateRepresentationMethod belongsTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean accept(PaperNode escapeNode) {
		// TODO Auto-generated method stub
		return false;
	}*/
	
}
