package ar.uba.dc.analysis.memory.impl.summary;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;

public class PaperPointsToHeapPartition implements HeapPartition {

	
	protected boolean temporal;
	protected String node;
	
	
	public String getNode() {
		return node;
	}


	public void setNode(String node) {
		this.node = node;
	}


	public PaperPointsToHeapPartition(boolean temporal, String node)
	{
		this.temporal = temporal;
		this.node = node;
	}
	
	
	public PaperPointsToHeapPartition(HeapPartition heapPartition)
	{
		this.temporal = heapPartition.isTemporal();
		
		if(heapPartition.getClass() == PointsToHeapPartition.class)
		{
			this.node = ((PointsToHeapPartition) heapPartition).toJsonString();
		}
		else
		{
			throw new NotImplementedException("Not implemented for another type of Heap Partition");
		}		
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
