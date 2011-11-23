package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

import java.util.Map;
import java.util.TreeMap;


public class MemoryHeap {
	
	public static final String TEMPORAL_PARTITION_NAME = "Temporal";
	
	public static final String GLOBAL_STATIC_PARTITION_NAME = "{static}";
	
	// hp -> size
	private Map<String, Integer> memory = new TreeMap<String, Integer>();
	
	public Integer size() {
		Integer total = 0;
		
		for (Integer size : memory.values()) {
			total += size;
		}
		
		return total;
	}

	public void allocate(String hp, Integer size) {
		Integer actualSize = memory.get(hp);
		
		if (actualSize == null) {
			actualSize = 0;
		}
		
		memory.put(hp, actualSize + size);
	}

	public Integer release(String hp) {
		return memory.remove(hp);
	}
	
	public void release(String hp, Integer size) {
		memory.put(hp, memory.get(hp) - size);
	}

	public Map<String, Integer> dump() {
		return this.memory;
	}

	public Integer size(String hp) {
		return memory.get(hp);
	}
	
	public boolean hasAllocations() {
		return !this.memory.isEmpty();
	}
	
	public MemoryHeap clone() {
		MemoryHeap clone = new MemoryHeap();
		clone.memory = new TreeMap<String, Integer>(this.memory);
		return clone;
	}	
}
