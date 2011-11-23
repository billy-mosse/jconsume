package ar.uba.dc.analysis.memory.impl.madeja;

import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;

public class KeyHeapPartitionVisitor implements HeapPartitionVisitor<String> {

	@Override
	public String visit(HeapPartition heapPartition) {
		return heapPartition.isTemporal()? "Temporal" : ((MadejaHeapPartition) heapPartition).key();
	}

}
