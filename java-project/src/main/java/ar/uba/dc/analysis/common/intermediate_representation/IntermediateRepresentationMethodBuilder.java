package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
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
	
	
	private void setBody(BasicMethodBody methodBody)
	{	
		this.irmethod.body = irbody_builder.build_body(methodBody);
	}	
	
	public IntermediateRepresentationMethod buildMethod(BasicMethodBody methodBody, long order)
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
		this.irmethod.setEscapeInfo(data.get(methodBody.belongsTo()));		
		
		this.setBody(methodBody);
		
		return this.irmethod;
	}

	protected IntermediateRepresentationBodyBuilder getIrbody_builder() {
		return irbody_builder;
	}

	protected void setIrbody_builder(IntermediateRepresentationBodyBuilder irbody_builder) {
		this.irbody_builder = irbody_builder;
	}

	
}
