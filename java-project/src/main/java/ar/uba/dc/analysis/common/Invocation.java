package ar.uba.dc.analysis.common;

import java.util.Set;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.DefaultIntermediateRepresentationParameter;
import ar.uba.dc.soot.SootUtils;

import com.google.common.base.*;
import com.google.common.collect.Iterables;


public class Invocation {
	
	public Invocation()
	{
		
	}
	
	public Invocation(Statement stmt) {
		this.name = stmt.getIntermediateRepresentationName();
		
		this.parameters = stmt.getIntermediateRepresentationParameters();
	}
	
	
	private void setName(CallStatement callStmt)
	{
		this.name = callStmt.toString();
	}
	
	private String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}


	private Set<DefaultIntermediateRepresentationParameter> getParameters() {
		return parameters;
	}


	private void setArguments(Set<DefaultIntermediateRepresentationParameter> parameters) {
		this.parameters = parameters;
	}


	private String name;
	private Set<DefaultIntermediateRepresentationParameter> parameters;
	

	
	public String toHumanReadableString() {
		
		String s = (this.parameters != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.parameters, new Function<DefaultIntermediateRepresentationParameter, String >()
				{
					public String apply(DefaultIntermediateRepresentationParameter parameter) { return parameter.getName(); }
				}
				
				
				)) : "");
		
		return this.name + String.format("(%s)", s);
		
		
		//return "<invoke " + this.name + "(" + arguments. + ")>;"; //Falta el invariante
		//String pepito = "blabla"; return pepito; //usar expresiones lambda o algo aprecido a linq
		//return this.name + this.arguments
	}

}
