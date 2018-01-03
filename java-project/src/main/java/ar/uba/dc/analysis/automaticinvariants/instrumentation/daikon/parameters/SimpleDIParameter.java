package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.ArrayList;
import java.util.Vector;

import ar.uba.dc.util.List;

public class SimpleDIParameter {
	public String name;
	public List derivedFields;
	
	public SimpleDIParameter(String name)
	{
		this.name = name;
		this.derivedFields = new List();
	}
	
	public String toString()
	{
		return name + ";" + derivedFields.toString();
	}
}
