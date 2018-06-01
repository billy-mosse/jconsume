package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import ar.uba.dc.util.ListC;

public class SimpleDIParameter {
	public String name;
	public ListC derivedFields;
	
	public SimpleDIParameter(String name)
	{
		this.name = name;
		this.derivedFields = new ListC();
	}
	
	public String toString()
	{
		return name + ";" + derivedFields.toString();
	}
}
