package ar.uba.dc.tools.instrumentation.resource.tracker;

import java.util.Map;
import java.util.TreeMap;

@Deprecated
public class MemoryHeap {
	 
	// hp -> size
	private Map<Integer, Integer> memory = new TreeMap<Integer, Integer>();
	
	public Integer size() {
		Integer total = 0;
		
		for (Integer size : memory.values()) {
			total += size;
		}
		
		return total;
	}

	public void allocate(Integer hp, Integer size) {
		Integer actualSize = memory.get(hp);
		
		if (actualSize == null) {
			actualSize = 0;
		}
		
		memory.put(hp, actualSize + size);
	}

	public Integer release(Integer hp) {
		return memory.remove(hp);
	}
	
	public void release(Integer hp, Integer size) {
		memory.put(hp, memory.get(hp) - size);
	}

	public Map<Integer, Integer> dump() {
		return new TreeMap<Integer, Integer>(memory);
	}

	public Integer size(Integer hp) {
		return memory.get(hp);
	}
	
	public MemoryHeap clone() {
		MemoryHeap clone = new MemoryHeap();
		clone.memory = new TreeMap<Integer, Integer>(this.memory);
		
		return clone;
	}	
}
