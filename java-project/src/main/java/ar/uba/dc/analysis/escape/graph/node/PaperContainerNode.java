package ar.uba.dc.analysis.escape.graph.node;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * Nodo que contiene a otros nodos. Permite agrupar y simplificar el resultado
 * del analisis
 * 
 * @author testis
 */
public class PaperContainerNode implements PaperNode {

	/*private static final Map<PaperContainerNode, Integer> nMap = new HashMap<PaperContainerNode, Integer>();
    private static int n = 0;*/
	
    private Set<PaperNode> id;
    
    private boolean inside;
    
    private CircularStack<String> context;
    
    private String belongsTo;
    
    public PaperContainerNode(ContainerNode origNode, CircularStack<String> context, String belongsTo) {
    	
    	
    	this.id = new LinkedHashSet<PaperNode>();
    	
    	for(Node n: origNode.getId())
    	{
    		PaperNode paperNode = PaperNodeUtils.createPaperNodeFromNormalNode(n, context, belongsTo);
    		this.id.add(paperNode);    		
    	}
    	
    	this.context = context;
    	this.belongsTo = belongsTo;    	
    	this.inside = origNode.isInside();
    }
    
    
    //TODO: a veces estoy llamando isInside a inside. Decidirse por uno
    public PaperContainerNode(Set<PaperNode> id, boolean inside, CircularStack<String> context, String belongsTo) {
    	this.id = id;
    	this.context = context;
    	this.belongsTo = belongsTo;    	
    	this.inside = inside;
    }
    
    public String toString() {
    	LinkedList<String> ids = new LinkedList<String>();
    	for (PaperNode n : id) {
    		ids.add(n.toString());
    	}
    	return "[" + StringUtils.join(ids, ", ") + "]";
    	/*if (inside) {
    		return "C_I_" + nMap.get(this); 
    	} else {
    		return "C_L_" + nMap.get(this);
    	}*/
    }
    
    public String toJsonString()
    {
    	return this.toString();
    }
    
    public int hashCode() { 
    	return id.hashCode() + context.hashCode(); 
    }
    
    public boolean equals(Object o) {
    	if (o instanceof PaperContainerNode) {
    		PaperContainerNode oo = (PaperContainerNode) o;
    		return id.equals(oo.id) && inside == oo.inside && context.equals(oo.context) && belongsTo.equals(oo.belongsTo);
    	} else {
    		return false;
    	}
    }
    
    @Override
	public PaperContainerNode clone() {
		return new PaperContainerNode(id, inside, context.clone(), belongsTo);
	}

	@Override
	public void changeContext(String callStmtId) {
		context.push(callStmtId);
	}

	@Override
	public CircularStack<String> getContext() {
		return new CircularStack<String>(context);
	}

	@Override
	public boolean isInside() {
		return inside; 
	}

	@Override
	public boolean isLoad() {
		return !inside; 
	}

	@Override
	public boolean isParam() {
		return false;
	}
	
	public Set<PaperNode> getId() {
		return new HashSet<PaperNode>(id);
	}

	@Override
	public boolean accept(PaperNode node) {
		if (node.equals(this)) return true;
		
		boolean ret = false;
		
		for (PaperNode idNode : id) {
			if (idNode.accept(node)) {
				ret = true;
				break;
			}
		}
		
		return ret;
	}

}
