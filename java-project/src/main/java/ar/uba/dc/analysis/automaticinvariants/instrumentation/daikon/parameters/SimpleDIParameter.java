package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import ar.uba.dc.util.ListE;

public class SimpleDIParameter {
	public String name;
	public ListE derivedFields;
	
	public SimpleDIParameter(String name)
	{
		this.name = name;
		this.derivedFields = new ListE();
	}
	
	public String toString()
	{
		return name + ";" + derivedFields.toString();
	}
}
