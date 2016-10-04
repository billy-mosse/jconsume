package ar.uba.dc.analysis.memory.impl.summary;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;
import soot.SootMethod;

public class PaperPointsToHeapPartition implements HeapPartition {

	
	protected boolean temporal; //creo que lo voy a tirar
	protected String node;
	protected boolean isInside;
	protected String belongsTo;
	
	
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
	
	
	public PaperPointsToHeapPartition(boolean temporal, Node node, String belongsTo)
	{
		this.temporal = temporal;
		this.node = node.toJsonString();
		this.belongsTo = belongsTo;
		this.isInside = node.isInside();
	}	
	
	public PaperPointsToHeapPartition(HeapPartition heapPartition)
	{
		this.temporal = heapPartition.isTemporal();
		
		if(heapPartition.getClass() == PointsToHeapPartition.class)
		{
			PointsToHeapPartition hp = ((PointsToHeapPartition) heapPartition);
			this.node = hp.toJsonString();
			
			SootMethod m = hp.getNode().belongsTo();
			this.belongsTo = m.getDeclaringClass().toString() + "." +  m.getName();
			this.isInside = hp.getNode().isInside();
			
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
