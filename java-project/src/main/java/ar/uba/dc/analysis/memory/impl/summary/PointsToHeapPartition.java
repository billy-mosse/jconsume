package ar.uba.dc.analysis.memory.impl.summary;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;

public class PointsToHeapPartition implements HeapPartition {

	private Node node;
	private boolean temporal;
	
	
	public PointsToHeapPartition()
	{}
	
	public PointsToHeapPartition(Node node, boolean temporal) {
		this.node = node;
		this.temporal = temporal;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PointsToHeapPartition)) return false;
		PointsToHeapPartition hp = (PointsToHeapPartition) o;
    	return node.equals(hp.node) && temporal == hp.temporal;
	}

	@Override
	public int hashCode() {
		return node.hashCode() + (temporal ? 69 : 0); 
	}
	
	@Override
	public String toString() {
		return node.toString() + (temporal ? " (Temp)" : StringUtils.EMPTY);
	}

	@Override
	public boolean isTemporal() {
		return temporal;
	}

	public Node getNode() {
		return node;
	}
	
	public PointsToHeapPartition clone() {
		return new PointsToHeapPartition(node.clone(), temporal);
	}

	@Override
	public <T> T apply(HeapPartitionVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public String toHumanReadableString()
	{
		return "";
	}
}
