package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.invariantwriter;

import java.util.LinkedList;
import java.util.List;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CreationSiteMapInfo;

public class SpecMethod {
	private List<CreationSiteMapInfo> creationSites;
	private List<CreationSiteMapInfo> callSites;
	private String classAndMethod;
	private String method;
	private String className;
	
	public SpecMethod()
	{
		this.creationSites = new LinkedList<CreationSiteMapInfo>();
		this.callSites = new LinkedList<CreationSiteMapInfo>();
	}
	public List<CreationSiteMapInfo> getCreationSites() {
		return creationSites;
	}
	public void setCreationSites(List<CreationSiteMapInfo> creationSites) {
		this.creationSites = creationSites;
	}
	public List<CreationSiteMapInfo> getCallSites() {
		return callSites;
	}
	public void setCallSites(List<CreationSiteMapInfo> callSites) {
		this.callSites = callSites;
	}
	public String getClassAndMethod() {
		return classAndMethod;
	}
	public void setClassAndMethod(String classAndMethod) {
		this.classAndMethod = classAndMethod;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public void addCreationSite(CreationSiteMapInfo csInfo)
	{
		this.creationSites.add(csInfo);
	}
	public void addCallSite(CreationSiteMapInfo csInfo)
	{
		this.callSites.add(csInfo);
	}
	
	public String toString()
	{
		return this.method;
	}

}
