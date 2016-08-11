package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.memory.IntraproceduralAnalysis;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.invariant.spec.SpecInvariantProvider;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class IntermediateRepresentationBodyBuilder {
	private static Log log = LogFactory.getLog(IntraproceduralAnalysis.class);
	
	protected InvariantProvider invariantProvider;

	private CallGraph callGraph;
	public IntermediateRepresentationBodyBuilder(InvariantProvider invariantProvider, CallGraph callGraph)
	{
		this.invariantProvider = invariantProvider;
		this.callGraph = callGraph;
	}

	
	public Line buildLineFromStatement(Statement stmt, long counter)
	{
		Line line = new Line(stmt, this.callGraph);
		line.setLabel(counter);		
		
		
		DomainSet inv = invariantProvider.getInvariant(stmt);
		
		log.debug("Line " + line.toString() + " has the following invariant: " + inv.toString());
		line.setInvariant(inv);	
		
		return line;
	}
	//TODO: agregar al .spec lo que falta
	public IntermediateRepresentationMethodBody build_body(BasicMethodBody methodBody) {
		IntermediateRepresentationMethodBody ir_body = new IntermediateRepresentationMethodBody();
		
		Set<Line> lines = new LinkedHashSet<Line>();
		long counter=0;
		for(Statement stmt : methodBody.getStatements())
		{
			counter++;
			log.debug("Processing statement: " + stmt.toString());
			//ar.uba.dc.paper.Program3 ar.uba.dc.util.List map(ar.uba.dc.util.List,ar.uba.dc.paper.Op): li = interfaceinvoke it.<java.util.Iterator: java.lang.Object next()>() (2)
			Line line = buildLineFromStatement(stmt, counter);
			lines.add(line);
		}		
		ir_body.setLines(lines);
		
		log.debug("New and Call statements finished");		
		
		return ir_body;
	}
	
	//TODO: InvariantProvider, no Spec. Cuales son los otros?
	public void init(InvariantProvider invariantProvider) {
		this.invariantProvider = invariantProvider;
		
	}

}
