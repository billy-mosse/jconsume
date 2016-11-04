package ar.uba.dc.analysis.memory.impl.summary;

import ar.uba.dc.util.collections.graph.Edge;


//Clase no usada y que voy a tirar
public class PaperPointsToHeapPartitionEdge implements Edge<PaperPointsToHeapPartition> {

	protected String field; 
	protected PaperPointsToHeapPartition source, target;
	protected boolean inside;
    
    public PaperPointsToHeapPartitionEdge(PaperPointsToHeapPartition source, String field, PaperPointsToHeapPartition target, boolean inside) {
		this.source = source;
		this.field  = field;
		this.target = target;
		this.inside = inside;
    }
    
    public String getField() { 
    	return field; 
    }
    
    public PaperPointsToHeapPartition getTarget() { 
    	return target; 
    }
    
    public PaperPointsToHeapPartition getSource() { 
    	return source; 
    }
    
    public boolean isInside() { 
    	return inside; 
    }

    public int hashCode() { 
    	return field.hashCode() + target.hashCode() + source.hashCode() + (inside ? 69 : 0); 
    }

    public boolean equals(Object o) {
    	if (!(o instanceof PaperPointsToHeapPartitionEdge)) return false;
    	PaperPointsToHeapPartitionEdge e = (PaperPointsToHeapPartitionEdge) o;
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