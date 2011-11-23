package ar.uba.dc.tools.instrumentation.resource.tracker.madeja;

import java.util.LinkedHashMap;
import java.util.Map;

public class LifeTimeSummary {
	
	private Map<String, String> newStmtsMapping = new LinkedHashMap<String, String>();

	private Map<String, Map<String,String>> partitionMapping = new LinkedHashMap<String, Map<String,String>>();

	public Map<String, String> getNewStmtsMapping() {
		return newStmtsMapping;
	}

	public void setNewStmtsMapping(Map<String, String> newStmtsMapping) {
		this.newStmtsMapping = newStmtsMapping;
	}

	public Map<String, Map<String, String>> getPartitionMapping() {
		return partitionMapping;
	}

	public void setPartitionMapping(
			Map<String, Map<String, String>> partitionMapping) {
		this.partitionMapping = partitionMapping;
	}
	
	public void registerPartition(String newStmt, String partition) {
		this.newStmtsMapping.put(newStmt, partition);
	}

	public void registerPartition(String callStmt, Map<String, String> hpMapping) {
		this.partitionMapping.put(callStmt, hpMapping);
	}

}
