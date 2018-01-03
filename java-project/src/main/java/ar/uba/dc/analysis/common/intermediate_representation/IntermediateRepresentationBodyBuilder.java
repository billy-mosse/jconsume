package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.memory.IntraproceduralAnalysis;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedLifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.invariant.spec.SpecInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;
import decorations.Binding;
import decorations.BindingPair;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class IntermediateRepresentationBodyBuilder {
	private static Log log = LogFactory.getLog(IntraproceduralAnalysis.class);
	
	protected InvariantProvider invariantProvider;

	private CallGraph callGraph;

	protected LifeTimeOracle lifetimeOracle;
	public LifeTimeOracle getLifetimeOracle() {
		return lifetimeOracle;
	}


	public void setLifetimeOracle(LifeTimeOracle lifetimeOracle) {
		this.lifetimeOracle = lifetimeOracle;
	}


	public IntermediateRepresentationBodyBuilder(InvariantProvider invariantProvider, CallGraph callGraph, LifeTimeOracle lifetimeOracle)
	{
		this.invariantProvider = invariantProvider;
		this.callGraph = callGraph;
		this.lifetimeOracle = lifetimeOracle;
	}

	
	public Line buildLineFromStatement(Statement stmt, long counter,
			Set<IntermediateRepresentationMethod> ir_methods, Set<PaperPointsToHeapPartition> nodes, String fullName, Map<Node, Integer> numbers, SortedSet<SootMethod> queue )
	{
		Line line = new Line(stmt, this.callGraph, this.lifetimeOracle, ir_methods, nodes, fullName, numbers);
		
		//Esto no lo estoy escribiendo en el IR por ahora
		line.setLabel(counter);
		
		
		DomainSet inv = invariantProvider.getInvariant(stmt);
		
		log.debug("Line " + line.toString() + " has the following invariant: " + inv.toString());
		line.setInvariant(inv);
		
		
		Binding b = invariantProvider.getBinding(stmt);
		
		
		HashSet<BindingPair> newBindingPairs = new HashSet<BindingPair>();

		for(Invocation invocation : line.getInvocations())
		{
			for(IntermediateRepresentationMethod ir_method : ir_methods)
			{
				if(ir_method.toString().equals(IRUtils.key(invocation, line.getIrName()).toString()))
				{
					log.debug("Procesando el callee "+ ir_method.toString());
					log.debug("Propagando los new_parameters al binding caller-callee");
					
					for(DerivedVariable dv : ir_method.getNewRelevantParameters())
					{
						for(BindingPair bp : b.getBindingPairs())
						{
							if(bp.getCallee_parameter().equals(dv.getName()))
							{
								String new_caller_parameter = bp.getCaller_parameter() + "__f__" + dv.getField();
								BindingPair new_bp = new BindingPair(new_caller_parameter,dv.toString());
								newBindingPairs.add(new_bp);
							}
						}
					}
				}
			}
		}
		
		b.addBindingPairs(newBindingPairs);
		
		log.debug("Line " + line.toString() + " has the following invariant: " + inv.toString());
		line.setBinding(b);	

		
		
		return line;
	}

	public IntermediateRepresentationMethodBody build_body(BasicMethodBody methodBody, Set<IntermediateRepresentationMethod> ir_methods, 
			Set<PaperPointsToHeapPartition> nodes, String fullName, Map<Node, Integer> numbers, SortedSet<SootMethod> queue ) {
		IntermediateRepresentationMethodBody ir_body = new IntermediateRepresentationMethodBody();
		
		Set<Line> lines = new LinkedHashSet<Line>();
		long counter=0;
		for(Statement stmt : methodBody.getStatements())
		{
			counter++;
			log.debug("Processing statement: " + stmt.toString());
			
			Line line = buildLineFromStatement(stmt, counter, ir_methods, nodes, fullName, numbers, queue);
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
