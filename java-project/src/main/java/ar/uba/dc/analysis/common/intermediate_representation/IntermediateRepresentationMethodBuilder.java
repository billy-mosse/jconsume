package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

public class IntermediateRepresentationMethodBuilder {
	private static Log log = LogFactory.getLog(IntermediateRepresentationMethodBuilder.class);
	
	protected IntermediateRepresentationMethod irmethod;
	private IntermediateRepresentationBodyBuilder irbody_builder;
	
	private InvariantProvider invariantProvider;
	protected RAMSummaryRepository data;


	public IntermediateRepresentationMethodBuilder (InvariantProvider invariantProvider, RAMSummaryRepository data, CallGraph callGraph, LifeTimeOracle lifetimeOracle) {
		this.invariantProvider = invariantProvider;
		this.irbody_builder = new IntermediateRepresentationBodyBuilder(invariantProvider, callGraph, lifetimeOracle);
		this.data = data;
	}
	
	
	private void setBody(BasicMethodBody methodBody, Set<IntermediateRepresentationMethod> ir_methods, Set<PaperPointsToHeapPartition> nodes, String fullName, Map<Node, Integer> numbers)
	{	
		this.irmethod.body = irbody_builder.build_body(methodBody, ir_methods, nodes, fullName, numbers);
	}	
	
	public IntermediateRepresentationMethod buildMethod(BasicMethodBody methodBody, long order, Set<IntermediateRepresentationMethod> ir_methods)
	{
		
		SootMethod m = methodBody.belongsTo();
		
		log.debug("____Construyendo " + m.toString());

		
	
	//	SootClass clazz = m.getDeclaringClass();
	//	String s = clazz.getName();
		
		this.irmethod = new IntermediateRepresentationMethod(m, order);
		
		int n = m.getNumber();
		
		this.irmethod.setNumber(n);
		this.irmethod.setDeclaration(m.getDeclaration());
		this.irmethod.setSubSignature(m.getSubSignature());
		

		this.irmethod.setMethodRequirements(invariantProvider.getRequeriments(m));
		this.irmethod.setRelevant_parameters(invariantProvider.getRelevantParameters(m));
		
		Map<Node, Integer> numbers = new HashMap<Node, Integer>();

		this.irmethod.setNodesInfo(data.get(methodBody.belongsTo()), numbers);
		
		this.setBody(methodBody, ir_methods, this.irmethod.nodes, this.irmethod.getFullName(), numbers);
		
		return this.irmethod;
	}

	protected IntermediateRepresentationBodyBuilder getIrbody_builder() {
		return irbody_builder;
	}

	protected void setIrbody_builder(IntermediateRepresentationBodyBuilder irbody_builder) {
		this.irbody_builder = irbody_builder;
	}

	
}
