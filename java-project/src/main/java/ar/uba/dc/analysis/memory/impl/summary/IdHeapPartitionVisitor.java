package ar.uba.dc.analysis.memory.impl.summary;

import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;

public class IdHeapPartitionVisitor implements HeapPartitionVisitor<String> {

	@Override
	public String visit(HeapPartition heapPartition) {
		return heapPartition.isTemporal()? "Temporal" : ((PointsToHeapPartition) heapPartition).getNode().toString();
	}

}
