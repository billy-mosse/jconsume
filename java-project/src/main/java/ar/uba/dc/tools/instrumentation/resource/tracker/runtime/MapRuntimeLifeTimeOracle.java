package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

import java.util.HashMap;
import java.util.Map;

public class MapRuntimeLifeTimeOracle implements RuntimeLifeTimeOracle {

	private Map<String, Map<String, String>> heapBinding = new HashMap<String, Map<String,String>>();
	
	private Map<String, String> newStmtMapping = new HashMap<String, String>();
	
	private boolean debug = false;
	
	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Map<String, Map<String, String>> getHeapBinding() {
		return heapBinding;
	}

	public void setHeapBinding(Map<String, Map<String, String>> heapBinding) {
		this.heapBinding = heapBinding;
	}

	public Map<String, String> getNewStmtMapping() {
		return newStmtMapping;
	}

	public void setNewStmtMapping(Map<String, String> newStmtMapping) {
		this.newStmtMapping = newStmtMapping;
	}

	/* (non-Javadoc)
	 * @see ar.uba.dc.tools.instrumentation.resource.tracker.madeja.RuntimeLifeTimeOracle#registerPartition(java.lang.String, java.lang.String)
	 */
	public void registerPartition(String newStmt, String partition) {
		this.debug("Registering " + newStmt + " at " + partition);
		this.newStmtMapping.put(newStmt, partition);
	}
	
	/* (non-Javadoc)
	 * @see ar.uba.dc.tools.instrumentation.resource.tracker.madeja.RuntimeLifeTimeOracle#registerPartition(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void registerPartition(String callStmt, String callerPartition, String calleePartition) {
		Map<String, String> mapsTo = this.heapBinding.get(callStmt);
		if(mapsTo == null) {
			mapsTo = new HashMap<String, String>();
			this.heapBinding.put(callStmt, mapsTo);
		}
		mapsTo.put(calleePartition, callerPartition);
	}
	
	
	/* (non-Javadoc)
	 * @see ar.uba.dc.tools.instrumentation.resource.tracker.runtime.RuntimeLifeTimeOracle#registerPartition(java.lang.String, java.util.Map)
	 */
	@Override
	public void registerPartition(String callStmt, Map<String, String> mapsTo) {
		this.heapBinding.put(callStmt, mapsTo);
	}
	
	/* (non-Javadoc)
	 * @see ar.uba.dc.tools.instrumentation.resource.tracker.madeja.RuntimeLifeTimeOracle#getPartition(java.lang.String)
	 */
	public String getPartition(String newStmt) {
		String partition = this.newStmtMapping.get(newStmt);
		if(partition == null) 
			throw new RuntimeException("Unable to find partitions for" + newStmt);
		return partition;
	}

	
	/* (non-Javadoc)
	 * @see ar.uba.dc.tools.instrumentation.resource.tracker.madeja.RuntimeLifeTimeOracle#bindPartition(java.lang.String, java.lang.String)
	 */
	public String bindPartition(String callStmt, String calleePartition) {
		//if(calleePartition.equals(MemoryHeap.GLOBAL_STATIC_PARTITION_NAME))
	//		return MemoryHeap.GLOBAL_STATIC_PARTITION_NAME;
		
		//String caller = callStmt.substring(7, callStmt.indexOf("@"));
		String partition = null;
		Map<String, String> binding = this.heapBinding.get(callStmt);
		if(binding != null) {
			partition = binding.get(calleePartition);
		}
		if(partition == null && (calleePartition.equals(MemoryHeap.GLOBAL_STATIC_PARTITION_NAME) || callStmt.startsWith("Invoke@CLINIT")))
				partition = MemoryHeap.GLOBAL_STATIC_PARTITION_NAME ;
			
		if(partition == null)
			throw new RuntimeException("Unable to bind partition " + calleePartition + " at " + callStmt);
		
		return partition;
	}

	private void debug(String message) {
		if(this.isDebug())
			System.out.println(message);
	}
	
}

