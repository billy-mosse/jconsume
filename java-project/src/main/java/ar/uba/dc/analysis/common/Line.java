package ar.uba.dc.analysis.common;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.DefaultIntermediateRepresentationParameter;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedLifeTimeOracle;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.soot.SootUtils;
import decorations.Binding;
import soot.jimple.toolkits.callgraph.CallGraph;

public class Line {
	private long label;
	private DomainSet invariant;	
	private Binding binding;
	private List<Invocation> invocations;	
	private String name;
	
	private int lineNumber;
	
	public Line()
	{
		
	}
	
	public Line(Statement stmt, CallGraph callGraph, LifeTimeOracle lifetimeOracle) {
		
		this.setLineNumber(stmt.getStatement().getJavaSourceStartLineNumber());
		boolean isCallStatement = true;
		if(stmt instanceof CallStatement)
		{
			this.setName((CallStatement)stmt);
		}
		else
		{
			isCallStatement = false;
			this.setName("new");
		}
		
		
		this.label = stmt.getCounter();	
		this.invocations = SootUtils.getInvocations(stmt, isCallStatement, callGraph, lifetimeOracle);
	}
	
	public void setName(CallStatement callStmt)
	{
		this.name = callStmt.toString();
	}
	
	public long getLabel() {
		return label;
	}
	public void setLabel(long label) {
		this.label = label;
	}
	public DomainSet getInvariant() {
		return invariant;
	}
	public void setInvariant(DomainSet invariant) {
		this.invariant = invariant;
	}
	public List<Invocation> getInvocations() {
		return invocations;
	}
	public void setInvocations(List<Invocation> invocations) {
		this.invocations = invocations;
	}

	public String toHumanReadableString() {
		String s =  this.label + ", " + getInvocationsToHumanReadableString() + ", " + this.invariant.toHumanReadableString();
		return String.format("<%s>", s);
	}
	
	public boolean isNewStatement()
	{
		return this.name == "new";
	}

	private String getInvocationsToHumanReadableString() {

		
		String s = (this.invocations != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.invocations, new Function<Invocation, String >()
		{
			public String apply(Invocation invocation) { return invocation.toHumanReadableString(); }
		}
		
		
		)) : "");

		return this.name + " " + String.format("{%s}", s);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Binding getBinding() {
		return binding;
	}

	public void setBinding(Binding binding) {
		this.binding = binding;
	}
}
