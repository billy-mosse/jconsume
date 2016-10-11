package ar.uba.dc.analysis.common;

import java.util.LinkedHashSet;
import java.util.Set;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedLifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.soot.SootUtils;
import soot.SootMethod;

import com.google.common.base.*;
import com.google.common.collect.Iterables;


public class Invocation {
	
	private boolean isCallStatement;
	private PaperPointsToHeapPartition heapPartition;



	public Invocation()
	{
		
	}
	
	public Invocation(SootMethod m)
	{
		this.class_called = m.getDeclaringClass().toString();
		this.setCallStatement(true);
		this.called_implementation_signature = m.getSignature();
		

		this.setParameters(new LinkedHashSet<IntermediateRepresentationParameter>());
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(m, false);
		for(IntermediateRepresentationParameter p : s)
		{
			this.parameters.add((IntermediateRepresentationParameter)p);
		}		
		
	}
	
	public Invocation(NewStatement newStmt, HeapPartition heapPartition) {			
		this.setCallStatement(false);
		this.setParameters(newStmt.getIntermediateRepresentationParameters());
		this.setClass_called("");
		this.called_implementation_signature = "";
		this.heapPartition =  new PaperPointsToHeapPartition(heapPartition);
		
	}

	public PaperPointsToHeapPartition getHeapPartition() {
		return heapPartition;
	}	

	public void setHeapPartition(PaperPointsToHeapPartition escapePartition) {
		this.heapPartition = escapePartition;
	}

	public Set<IntermediateRepresentationParameter> getParameters() {
		return parameters;
	}

	protected Set<IntermediateRepresentationParameter> parameters;
	protected String class_called;
	
	protected String called_implementation_signature;
	

	
	public String toHumanReadableString() {
		
		String s = (this.getParameters() != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.getParameters(), new Function<IntermediateRepresentationParameter, String >()
				{
					public String apply(IntermediateRepresentationParameter parameter) { return parameter.getName(); }
				}
				
				
				)) : "");
		
		return (this.isCallStatement ? this.class_called: "") + String.format("(%s)", s);
		
		
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

	public void setParameters(Set<IntermediateRepresentationParameter> parameters) {
		this.parameters = parameters;
	}

	public String getCalled_implementation_signature() {
		return called_implementation_signature;
	}

	public void setCalled_implementation_signature(String called_implementation_signature) {
		this.called_implementation_signature = called_implementation_signature;
	}

	public boolean isCallStatement() {
		return isCallStatement;
	}

	public void setCallStatement(boolean isCallStatement) {
		this.isCallStatement = isCallStatement;
	}

}
