package ar.uba.dc.analysis.memory.impl.report.html;

import java.util.Map;

import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public interface MemorySummaryInterpreter<T> {

	//public String getTemporal(MemorySummary summary);

	public String getResidual(T summary);

	public String getNonHTMLResidual(T summary);
	
	public boolean hasEscapeDetail(T summary);

	public String getEscapeDetail(T summary);
	
	public boolean hasMemoryDetail(T summary);

	public String getMemoryDetail(T summary);
	
	public Map<String, String> getResidualPartitions(T summary);

	public String getMemReq(T summary);

	public String getNonHTMLMemReq(T summary);



}
