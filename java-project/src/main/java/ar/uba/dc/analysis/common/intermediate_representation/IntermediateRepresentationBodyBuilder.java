package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.memory.IntraproceduralAnalysis;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.invariant.spec.SpecInvariantProvider;

public class IntermediateRepresentationBodyBuilder {
	private static Log log = LogFactory.getLog(IntraproceduralAnalysis.class);
	
	protected InvariantProvider invariantProvider;
	public IntermediateRepresentationBodyBuilder(InvariantProvider invariantProvider)
	{
		this.invariantProvider = invariantProvider;
	}

	
	public Line buildLineFromStatement(Statement stmt)
	{
		Line line = new Line(stmt);
		DomainSet inv = invariantProvider.getInvariant(stmt);
		log.debug("Line " + line.toString() + " has the following invariant: " + inv.toString());
		line.setInvariant(inv);	
		
		return line;
	}
	//TODO: agregar al .spec lo que falta
	public IntermediateRepresentationMethodBody build_body(MethodBody methodBody) {
		IntermediateRepresentationMethodBody ir_body = new IntermediateRepresentationMethodBody();
		
		Set<Line> lines = new LinkedHashSet<Line>();
		for(Statement stmt : methodBody.getStatements())
		{
			Line line = buildLineFromStatement(stmt);
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
