package ar.uba.dc.analysis.memory.impl.report.html;

import java.util.ArrayList;

import ar.uba.dc.util.List;

public class JsonClassSummary {
	protected String className;
	protected ArrayList<JsonMethodSummary> jsonMethodSummaries;
	
	
	public JsonClassSummary(String className) {
		this.className = className;
		this.jsonMethodSummaries = new ArrayList<JsonMethodSummary>();
	}
	
	public void addJsonMemorySummary(JsonMethodSummary jsonMethodSummary)
	{
		this.jsonMethodSummaries.add(jsonMethodSummary);
	}
	
	public ArrayList<JsonMethodSummary> getJsonMethodSummaries() {
		return jsonMethodSummaries;
	}
	public void setJsonMethodSummaries(ArrayList<JsonMethodSummary> jsonMemorySummaries) {
		this.jsonMethodSummaries = jsonMethodSummaries;
	}
	
}
