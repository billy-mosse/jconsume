package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.util.ArrayList;
import java.util.HashSet;

import ar.uba.dc.util.ListE;
import soot.SootClass;

public class InstrumentedMethodClass {
	public InstrumentedMethodClass(String packageName)
	{
		this.packageName = packageName;
		this.methods = new ArrayList<String>();
		this.usedClasses = new HashSet<String>();
	}
	public String packageName;
	public ArrayList<String> methods;
	public HashSet<String> usedClasses;
	public SootClass sootClass;
}
