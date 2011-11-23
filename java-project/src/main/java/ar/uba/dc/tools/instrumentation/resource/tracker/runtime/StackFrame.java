package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

import java.util.Map;

public class StackFrame {
	
	private String method;

	protected MemoryHeap memory;
	
	private Integer maxCall = 0;
	
	private int reachableObjectsPre;
	
	
	//Residuales fresh
	private int reachableObjectsPost;
	
	private int peak;
	
	//Object scope
	private long from = -1;
	
	private long to = -1;
	
	public StackFrame(String method) {
		super();
		this.memory = new MemoryHeap();
		this.method = method;
	}
	
	
	public void setFromObject(long from) {
		this.from =  from;
	}
	
	public void setToObject(long to) {
		this.to =  to;
	}
	
	public long getFromObject() {
		return this.from;
	}
	
	public long getToObject() {
		return this.to;
	}
	public String getMethod() {
		return method;
	}
	
	
	public int getReachableObjectsPre() {
		return reachableObjectsPre;
	}

	public void setReachableObjectsPre(int reachableObjectsPre) {
		this.reachableObjectsPre = reachableObjectsPre;
	}

	public int getReachableObjectsPost() {
		return reachableObjectsPost;
	}

	public void setReachableObjectsPost(int reachableObjectsPost) {
		this.reachableObjectsPost = reachableObjectsPost;
	}

	public void allocateObject(String partition) {
		this.allocate(partition, 1);
	}

	public void allocateArray(String partition, int size) {
		this.allocate(partition, size);
	}
	
	public void allocateArray(String partition, int size, int size2) {
		this.allocate(partition, size * size2);
	}

	public void allocate(String hp, Integer size) {
		this.memory.allocate(hp, size);
	}
	
	public void updateMaxCall(int size) {
		if(this.maxCall < size)
			this.maxCall = size;
	}
	
	public int getMaxCall() {
		return this.maxCall;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.method);
		buffer.append(" memreq=");
		buffer.append(this.memory.size() + this.getMaxCall());
		buffer.append(" ");
		buffer.append("rsd: ");
		
		Map<String, Integer> dump = this.memory.dump();
		int rsd = 0;
		for (String name : dump.keySet()) {
			if(!name.equals(MemoryHeap.TEMPORAL_PARTITION_NAME))
				rsd= rsd + dump.get(name);
			//buffer.append(name).append("=").append(dump.get(name));
			//buffer.append(" ");
		}
		buffer.append(rsd);
		buffer.append(" tmp: ");
		buffer.append(dump.get(MemoryHeap.TEMPORAL_PARTITION_NAME));
		buffer.append(" maxCall: " + this.getMaxCall());
		buffer.append(" reachability: {");
		buffer.append(" pre-alloc: " + this.getReachableObjectsPre());
		buffer.append(" fresh.rsd: " + this.getReachableObjectsPost());
		buffer.append(" fresh.peak: " + this.peak);
		//buffer.append(" max-rel: " + (this.peak - this.getReachableObjectsPre() ));
		buffer.append("}"); 
		return buffer.toString();
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof StackFrame) {
			StackFrame other = (StackFrame) obj;
			if(this.getMethod().equals(other.getMethod())) {
				return other.maxCall.equals(this.maxCall) && this.memory.size().equals(other.memory.size());
			}
				
		}
			//		
	//	} 
		return false;
	}
	
	/**
	 * No consumption at all
	 * @return
	 */
	public boolean hasAllocations() {
		return this.memory.hasAllocations();
	}

	public int getTemporal() {
		Integer temporal = this.memory.size(MemoryHeap.TEMPORAL_PARTITION_NAME);
		if(temporal == null)
			temporal = 0;
		return temporal + this.maxCall;
	}
	
	public Map<String, Integer> memoryDump() {
		return this.memory.dump();
	}
	
	public void updatePeak(int reachable) {
		//int freshReachable = reachable - reachableObjectsPre;
		int freshReachable = reachable;
		this.peak = this.peak < freshReachable ? freshReachable : this.peak;
	}
}

