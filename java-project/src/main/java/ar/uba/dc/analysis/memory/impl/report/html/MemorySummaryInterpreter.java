package ar.uba.dc.analysis.memory.impl.report.html;

import java.util.Map;

import ar.uba.dc.analysis.memory.summary.MemorySummary;

public interface MemorySummaryInterpreter {

	//public String getTemporal(MemorySummary summary);

	public String getResidual(MemorySummary summary);
	
	public boolean hasEscapeDetail(MemorySummary summary);

	public String getEscapeDetail(MemorySummary summary);
	
	public boolean hasMemoryDetail(MemorySummary summary);

	public String getMemoryDetail(MemorySummary summary);
	
	public Map<String, String> getResidualPartitions(MemorySummary summary);

	public String getMemReq(MemorySummary summary);


}
