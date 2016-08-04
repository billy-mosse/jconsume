package ar.uba.dc.analysis.common;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.DefaultIntermediateRepresentationParameter;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.soot.SootUtils;
import soot.jimple.toolkits.callgraph.CallGraph;

public class Line {
	private long label;
	private DomainSet invariant;	
	private List<Invocation> invocations;	
	
	public Line()
	{
		
	}
	
	public Line(Statement stmt, CallGraph callGraph) {
		this.label = stmt.getCounter();	
		this.invocations = SootUtils.getInvocations(stmt, callGraph);
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
	public void setInvocation(List<Invocation> invocations) {
		this.invocations = invocations;
	}

	public String toHumanReadableString() {
		String s =  this.label + ", " + getInvocationsToHumanReadableString() + ", " + this.invariant.toHumanReadableString();
		return String.format("<%s>", s);
	}

	private String getInvocationsToHumanReadableString() {

		
		String s = (this.invocations != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.invocations, new Function<Invocation, String >()
		{
			public String apply(Invocation invocation) { return invocation.toHumanReadableString(); }
		}
		
		
		)) : "");

		return String.format("{%s}", s);
	}
}
