package ar.uba.dc.analysis.common;

import java.util.LinkedHashSet;
import java.util.Set;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.DefaultIntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.soot.SootUtils;
import soot.SootMethod;

import com.google.common.base.*;
import com.google.common.collect.Iterables;


public class Invocation {
	
	public Invocation()
	{
		
	}
	
	public Invocation(SootMethod m)
	{
		this.setName(m.getName());
		this.class_called = m.getDeclaringClass().toString();
		
		this.called_implementation_signature = m.getSignature();
		

		this.setParameters(new LinkedHashSet<DefaultIntermediateRepresentationParameter>());
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(m, DefaultIntermediateRepresentationParameter.class);
		for(IntermediateRepresentationParameter p : s)
		{
			this.getParameters().add((DefaultIntermediateRepresentationParameter)p);
		}
		
		
	}
	
	public Invocation(NewStatement newStmt) {
		this.setName(newStmt.getIntermediateRepresentationName());		
		this.setParameters(newStmt.getIntermediateRepresentationParameters());
		this.setClass_called("");
		this.called_implementation_signature = "";
	}
	
	
	public void setName(CallStatement callStmt)
	{
		this.name = callStmt.toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public Set<DefaultIntermediateRepresentationParameter> getParameters() {
		return parameters;
	}

	private String name;
	private Set<DefaultIntermediateRepresentationParameter> parameters;
	private String class_called;
	
	private String called_implementation_signature;
	

	
	public String toHumanReadableString() {
		
		String s = (this.getParameters() != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.getParameters(), new Function<DefaultIntermediateRepresentationParameter, String >()
				{
					public String apply(DefaultIntermediateRepresentationParameter parameter) { return parameter.getName(); }
				}
				
				
				)) : "");
		
		return (this.getName()!="new" ? this.class_called + "." : "") + this.getName() + String.format("(%s)", s);
		
		
		//return "<invoke " + this.name + "(" + arguments. + ")>;"; //Falta el invariante
		//String pepito = "blabla"; return pepito; //usar expresiones lambda o algo aprecido a linq
		//return this.name + this.arguments
	}

	public String getClass_called() {
		return class_called;
	}

	public void setClass_called(String class_called) {
		this.class_called = class_called;
	}

	public void setParameters(Set<DefaultIntermediateRepresentationParameter> parameters) {
		this.parameters = parameters;
	}

	public String getCalled_implementation_signature() {
		return called_implementation_signature;
	}

	public void setCalled_implementation_signature(String called_implementation_signature) {
		this.called_implementation_signature = called_implementation_signature;
	}

}
