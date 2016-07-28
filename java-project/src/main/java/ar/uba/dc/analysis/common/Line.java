package ar.uba.dc.analysis.common;

import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.expression.DomainSet;

public class Line {
	private long label;
	private DomainSet invariant;	
	private Invocation invocation;	
	
	public Line()
	{
		
	}
	
	public Line(Statement stmt) {
		this.label = stmt.getCounter();
		this.invocation = new Invocation(stmt);		
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
	public Invocation getInvocation() {
		return invocation;
	}
	public void setInvocation(Invocation invocation) {
		this.invocation = invocation;
	}

	public String toHumanReadableString() {
		String s =  this.label + ", " + this.invocation.toHumanReadableString() + ", " + this.invariant.toHumanReadableString();
		return String.format("<%s>", s);
	}
}
