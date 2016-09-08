package ar.uba.dc.analysis.memory.impl.summary;

import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;

public class PaperPointsToHeapPartition implements HeapPartition {

	
	protected boolean temporal;
	protected String node;
	
	
	public PaperPointsToHeapPartition(boolean temporal, String node)
	{
		this.temporal = temporal;
		this.node = node;
	}
	
	
	public PaperPointsToHeapPartition(HeapPartition heapPartition)
	{
		this.temporal = heapPartition.isTemporal();
		this.node = heapPartition.toString();
	}
	
	@Override
	public boolean isTemporal() {
		// TODO Auto-generated method stub
		return temporal;
	}

	@Override
	public HeapPartition clone() {
		return null;
	}

	@Override
	public <T> T apply(HeapPartitionVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toHumanReadableString() {
		// TODO Auto-generated method stub
		return null;
	}

}
