package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;

public class CallInfo {
	
	public CallInfo(String insSite, ListDIParameters args, String caller, String callee)
	{
		this.insSite = insSite;
		this.args = args;
		this.setCaller(caller);
		this.setCallee(callee);
	}
	
	private String insSite;
	private String caller;
	private String callee;
	
	
	public String getInsSite() {
		return insSite;
	}
	public void setInsSite(String insSite) {
		this.insSite = insSite;
	}
	public ListDIParameters getArgs() {
		return args;
	}
	public void setArgs(ListDIParameters args) {
		this.args = args;
	}

	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCallee() {
		return callee;
	}
	public void setCallee(String callee) {
		this.callee = callee;
	}
	
	public String toString()
	{
		return this.caller + " --> " + this.callee;
	}

	private ListDIParameters args;
}
