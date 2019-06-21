package ar.uba.dc.analysis.escape.graph.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;

import soot.SootClass;
import soot.SootMethod;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.NewExpr;
import soot.jimple.Stmt;
import soot.tagkit.LineNumberTag;
import soot.tagkit.Tag;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * A node created dynamically and attached to a statement Stmt.
 * Can be either an inside or a load node.
 * Two such nodes are equal if and only if they have the same inside / load
 * flag and are attached to the same statement (we use Stmt.equal here).
 */
public class StmtNode implements Node {

	/** 
	 * gives a unique id, for pretty-printing purposes.
	 * Usamos el id y dejamos fuera el contexto para poder rastrear de donde sale un nodo cuando veo
	 * un grafo (si tengo el mismo ID que en el summary del callee es mas facil encontrar de donde salio un 
	 * nodo).  
	 */
    private static final Map<StatementId, Integer> nMap = new HashMap<StatementId, Integer>();
    private static int n = 0;

    private static final Map<SootMethod, Integer> method_index= new HashMap<SootMethod, Integer>();
	
    private static final Map<StatementId, Integer> new_nMap = new HashMap<StatementId, Integer>();
    private static int new_n = 0;
    
	/** Statement and method that created the node */
	private StatementId id;
	
	/** true if an inside node, false if an load node */
    private boolean inside;

    /** true if an omega node, false if it is not */
    public boolean omega;
    
    /** true if a fresh node, false if it is not */
    private boolean fresh;

    /** contexto del nodo. Es el stack con los {@link Stmt} de soot que representan los calls por los que fue pasando el nodo desde su creacion. */
    private CircularStack<StatementId> context;
    
    private int lineNumber;
    
    
    private StmtNode(StatementId id, boolean inside, CircularStack<StatementId> context) {
    	this.id = id;
    	this.inside = inside;
    	this.context = context;
    	
    	
    	int lineNumber = -1;
    	for (Iterator j = id.getId().getTags().iterator(); j.hasNext(); ) {
		    Tag tag = (Tag)j.next();
		    if (tag instanceof LineNumberTag) {		    	
		    	lineNumber = ((LineNumberTag) tag).getLineNumber();
		    }    	
    	}
    	
    	this.lineNumber = lineNumber;
    	
		if (!nMap.containsKey(id)) { 
			nMap.put(id, new Integer(n)); 
			n++; 
		}

		
		new_n = 0;
		
		if(method_index.containsKey(id.getMethodOfId()))
			new_n = method_index.get(id.getMethodOfId());	
		
		if (!new_nMap.containsKey(id)) { 
			new_nMap.put(id, new Integer(new_n)); 
			new_n++; 
		}		
		method_index.put(id.getMethodOfId(), new_n);
		
    }
    
    public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public StmtNode(StatementId id, boolean inside, int sensitivity) {
    	this(id, inside, new CircularStack<StatementId>(sensitivity));
    }
    
    public String toString() { 
    	if (inside) {
    		return "I_" + new_nMap.get(id); 
    	} else {
    		return "L_" + new_nMap.get(id);
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
    	if (o instanceof StmtNode) {
    		StmtNode oo = (StmtNode)o;
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
    
    public void changeContext(StatementId callStmtId) {
		context.push(callStmtId);
	}

	@Override
	public StmtNode clone() {
		return new StmtNode(id, inside, context.clone());
	}

	public StatementId getId() {
		return id;
	}

	public CircularStack<StatementId> getContext() {
		return new CircularStack<StatementId>(context);
	}

	@Override
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
	}

	@Override
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
	public int getNumber() {
		return new_nMap.get(id);
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
			if(this.context != null && o.getContext() == null)
			{
				ret = 1;
			}
			else
			{
				if(this.context == null && o.getContext() != null)
				{
					ret = -1;
				}
				else
				{
					if(this.context != null && o.getContext() != null)
					{
						ret = this.context.toString().compareTo(o.getContext().toString());
					}
				}
			}
		}
		return ret;
	}
	
	@Override
	public boolean isOmega() {
		return omega;
	}

	
	//que uso para representar un inside node correspondiente a un metodo no analizable?
	@Override
	public boolean isFresh() {
		return false;
		//return fresh;
	}

	@Override
	public void convertToOmegaNode() {
		this.omega = true;		
	}
}
