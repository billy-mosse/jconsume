package ar.uba.dc.analysis.common;

import java.util.Set;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;

public class Invocation {
	public Invocation(Statement stmt) {
		this.name = stmt.getIntermediateRepresentationName();
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


	private Set<IntermediateRepresentationParameter> getArguments() {
		return arguments;
	}


	private void setArguments(Set<IntermediateRepresentationParameter> arguments) {
		this.arguments = arguments;
	}


	private String name;
	private Set<IntermediateRepresentationParameter> arguments;
}
