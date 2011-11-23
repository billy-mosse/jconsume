package ar.uba.dc.analysis.memory.impl.summary;

import ar.uba.dc.util.collections.graph.Edge;

public class PointsToHeapPartitionEdge implements Edge<PointsToHeapPartition> {

	protected String field; 
	protected PointsToHeapPartition source, target;
	protected boolean inside;
    
    public PointsToHeapPartitionEdge(PointsToHeapPartition source, String field, PointsToHeapPartition target, boolean inside) {
		this.source = source;
		this.field  = field;
		this.target = target;
		this.inside = inside;
    }
    
    public String getField() { 
    	return field; 
    }
    
    public PointsToHeapPartition getTarget() { 
    	return target; 
    }
    
    public PointsToHeapPartition getSource() { 
    	return source; 
    }
    
    public boolean isInside() { 
    	return inside; 
    }

    public int hashCode() { 
    	return field.hashCode() + target.hashCode() + source.hashCode() + (inside ? 69 : 0); 
    }

    public boolean equals(Object o) {
    	if (!(o instanceof PointsToHeapPartitionEdge)) return false;
    	PointsToHeapPartitionEdge e = (PointsToHeapPartitionEdge) o;
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
