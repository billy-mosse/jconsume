package ar.uba.dc.analysis.escape.graph;

/** 
 * An edge in a escape graph.
 * Each edge has a source Node, a target Node, and a field label (we use a String here).
 * To represent an array element, the convention is to use the [] field label.
 * Edges are immutables and hashable. They compare equal only if they link equal nodes and have equal labels.
 */
public class Edge implements ar.uba.dc.util.collections.graph.Edge<Node> {

	protected String field; 
	protected Node source, target;
	protected boolean inside;
    
    public Edge(Node source, String field, Node target, boolean inside) {
		this.source = source;
		this.field  = field;
		this.target = target;
		this.inside = inside;
    }
    
    public String getField() { 
    	return field; 
    }
    
    public Node getTarget() { 
    	return target; 
    }
    
    public Node getSource() { 
    	return source; 
    }
    
    public boolean isInside() { 
    	return inside; 
    }

    public int hashCode() {
    	return field.hashCode() + target.hashCode() + source.hashCode() + (inside ? 69 : 0); 
    }

    public boolean equals(Object o) {
    	if (!(o instanceof Edge)) return false;
    	Edge e = (Edge) o;
    	return source.equals(e.source) && field.equals(e.field) && target.equals(e.target) && inside == e.inside;
    }

    public String toString() {
    	if (inside) {
    		return source.toString() + " = " + field + " => " + target.toString();
    	} else {
    		return source.toString() + " - " + field + " -> " + target.toString();
    	}
    }
}
