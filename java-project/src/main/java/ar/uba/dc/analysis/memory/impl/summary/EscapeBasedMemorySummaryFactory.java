package ar.uba.dc.analysis.memory.impl.summary;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummaryFactory;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;

public class EscapeBasedMemorySummaryFactory implements MemorySummaryFactory {

	private static Log log = LogFactory.getLog(EscapeBasedMemorySummaryFactory.class);
	
	private SummaryRepository<EscapeSummary, SootMethod> escapeSummaryProvider;
	
	private ParametricExpressionFactory expressionFactory;
	
	private InvariantProvider invariantProvider;
	
	@Override
	public MemorySummary initialSummary(SootMethod method) {
		log.debug(" |- Building initial summary for method [" + method + "]");
		
		EscapeSummary escapeSummary = escapeSummaryProvider.get(method);
		
		if (escapeSummary == null) {
			throw new RuntimeException("No escape summary found for method [" + method + "]. Could not build memory's summary.");
		}
		
		DomainSet methodRequirements = invariantProvider.getRequeriments(method);
		
		//BILLY: agregue el ultimo parametro.
		EscapeBasedMemorySummary summary = new EscapeBasedMemorySummary(method, invariantProvider.getRelevantParameters(method), expressionFactory.constant(0L, methodRequirements), expressionFactory.constant(0L, methodRequirements));
		
		Set<Node> escaping = escapeSummary.getEscaping();
		
		for (Node n : escaping) {
			PointsToHeapPartition hp = new PointsToHeapPartition(n, false); 
			summary.add(hp, expressionFactory.constant(0L, methodRequirements));
			
			for (Edge edge : escapeSummary.getEdgesOutOf(n)) {
				if (escaping.contains(edge.getTarget())) {
					summary.relate(hp, edge.getField(), edge.isInside(), new PointsToHeapPartition(edge.getTarget(), false));
				}
			}
		}

		Set<Node> returned = new HashSet<Node>(escapeSummary.getReturnedNodes());
		returned.retainAll(escaping);
		
		//Hay dos particiones para esc. Por el return value, y globally
		for (Node n : returned) {
			summary.returnPartition(new PointsToHeapPartition(n, false));
		}
		
		Set<Node> escapeGlobaly = new HashSet<Node>(escapeSummary.getEscapeGlobaly());
		escapeGlobaly.retainAll(escaping);
		for (Node n : escapeGlobaly) {
			summary.partitionEscapeGlobaly(new PointsToHeapPartition(n, false));
		}

		log.debug(" |- Building process finished.");
		return summary;
	}

	public void setEscapeSummaryProvider(SummaryRepository<EscapeSummary, SootMethod> escapeSummaryProvider) {
		this.escapeSummaryProvider = escapeSummaryProvider;
	}

	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public void setInvariantProvider(InvariantProvider invariantProvider) {
		this.invariantProvider = invariantProvider;
	}
}
