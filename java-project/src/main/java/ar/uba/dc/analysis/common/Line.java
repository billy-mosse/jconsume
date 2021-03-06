package ar.uba.dc.analysis.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedLifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
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
	
	private String irName;
	
	public String getIrName() {
		return irName;
	}

	public void setIrName(String irName) {
		this.irName = irName;
	}

	public Line()
	{
		
	}
	
	public String getFullNameCalled()
	{
		return this.irClass + "." + this.irName;
	}
	
	private String irClass;
	
	
	public Line clone()
	{
		Line l = new Line();
		l.binding = this.binding;
		l.invariant = this.invariant;
		l.invocations = this.invocations;
		l.irClass = this.irClass;
		l.irName = this.irName;
		l.name = this.name;
		return l;
	}
	public String getIrClass() {
		return irClass;
	}

	public void setIrClass(String irClass) {
		this.irClass = irClass;
	}
	
	public String magicalStmtName;
	
	public Line(Statement stmt, CallGraph callGraph, LifeTimeOracle lifetimeOracle,
			Set<IntermediateRepresentationMethod> ir_methods, Set<PaperPointsToHeapPartition> nodes, 
			String fullName, Map<Node, Integer> numbers) {
		
		//TODO: descomentar esta linea
		//this.setLineNumber(stmt.getStatement().getJavaSourceStartLineNumber());
		this.setLineNumber(0);
		boolean isCallStatement = true;
		if(stmt instanceof CallStatement)
		{
			CallStatement callStmt = (CallStatement)stmt;
			this.setName(callStmt);
			this.setIrName(callStmt.getStatement().getInvokeExpr().getMethod().getName());
			this.setIrClass(callStmt.getStatement().getInvokeExpr().getMethod().getDeclaringClass().getName());
			
			//TODO: volar cuando hagamos los heap partitions
			this.magicalStmtName = stmt.getStatement().toString();
		}
		else
		{
			isCallStatement = false;
			this.setName("new");
			this.setIrName("new");
			this.setIrClass(""); //I dont care
			this.magicalStmtName = "";
		}
		
		
		this.label = stmt.getCounter();	
		this.invocations = SootUtils.getInvocations(this, stmt, isCallStatement, callGraph, 
				lifetimeOracle, ir_methods, nodes, fullName, numbers);
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
		return this.irName.equals("new");
	}

	public String toString()
	{
		return this.irName;
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
