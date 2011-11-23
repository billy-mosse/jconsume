package ar.uba.dc.analysis.memory;

public interface HeapPartition {

	boolean isTemporal();

	HeapPartition clone();
	
	public <T> T apply(HeapPartitionVisitor<T> visitor);

}
