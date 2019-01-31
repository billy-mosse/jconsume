package ar.uba.dc.analysis.escape.graph.node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jboss.util.NotImplementedException;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * Nodo que contiene a otros nodos. Permite agrupar y simplificar el resultado
 * del analisis
 * 
 * @author testis
 */
public class ContainerNode implements Node {

	/*private static final Map<ContainerNode, Integer> nMap = new HashMap<ContainerNode, Integer>();
    private static int n = 0;*/
	
    private Set<Node> id;

    private boolean inside;
    
    
    private CircularStack<StatementId> context;
    
    private SootMethod belongsTo;
    
    public ContainerNode(Set<Node> id, boolean inside, CircularStack<StatementId> context, SootMethod belongsTo) {
    	this.id = id;
    	this.inside = inside;
    	this.context = context;
    	this.belongsTo = belongsTo;
		/*if (!nMap.containsKey(id)) { 
			nMap.put(this, new Integer(n)); 
			n++; 
		}*/
    }
    
    public String toString() {
    	LinkedList<String> ids = new LinkedList<String>();
    	for (Node n : id) {
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
    	if (o instanceof ContainerNode) {
    		ContainerNode oo = (ContainerNode) o;
    		return id.equals(oo.id) && inside == oo.inside && context.equals(oo.context) && belongsTo.equals(oo.belongsTo);
    	} else {
    		return false;
    	}
    }
    
    @Override
	public ContainerNode clone() {
		return new ContainerNode(id, inside, context.clone(), belongsTo);
	}
    
	@Override
	public SootMethod belongsTo() {
		return belongsTo;
	}

	@Override
	public void changeContext(StatementId callStmtId) {
		context.push(callStmtId);
	}

	@Override
	public CircularStack<StatementId> getContext() {
		return new CircularStack<StatementId>(context);
	}

	@Override
	public SootClass getType() {
		return null;
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

	@Override
	public StatementId popContext() {
		return context.pop();
	}
	
	public Set<Node> getId() {
		return new HashSet<Node>(id);
	}

	@Override
	public boolean accept(Node node) {
		if (node.equals(this)) return true;
		
		boolean ret = false;
		
		for (Node idNode : id) {
			if (idNode.accept(node)) {
				ret = true;
				break;
			}
		}
		
		return ret;
	}
	
	@Override
	public int getNumber() {
		throw new Error("Esto jamas deberia ser llamado");
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
		//si alguno es omega, el container node es omega?
		throw new NotImplementedException();
	}

	@Override
	public boolean isFresh() {
		//si alguno es fresh, el container node es fresh? 
		throw new NotImplementedException();
	}
}
