package ar.uba.dc.analysis.memory;


public interface HeapPartitionVisitor <T> {

	T visit(HeapPartition heapPartition);
}
