package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.HashMap;
import java.util.Map;

import madeja.analysis.tribes.Tribe;
import soot.SootMethod;
import ar.uba.dc.analysis.memory.HeapPartition;

public class MethodEscapeInfo {

	
	SootMethod sootMethod;
	
	/**
	 * Mapping de las tribus en particiones.
	 */
	Map<Tribe, HeapPartition> heapPartitions = new HashMap<Tribe, HeapPartition>();
	
	/**
	 * Mapping de las particiones de callees en una particion del caller.
	 */
	Map<HeapPartition, HeapPartition> mapPartitions = new HashMap<HeapPartition, HeapPartition>();
	
	public MethodEscapeInfo(SootMethod sootMethod) {
		this.sootMethod = sootMethod;
	}

	
	public HeapPartition getPartition(Tribe tribe) {
		HeapPartition heapPartition = (HeapPartition) this.heapPartitions.get(tribe);
		if(heapPartition == null) {
			heapPartition = new MadejaHeapPartition(tribe);
			this.heapPartitions.put(tribe, heapPartition);
		}
		return heapPartition;
	}
	
}
