package ar.uba.dc.analysis.escape.summary.simplification;

import java.util.Set;

import soot.SootMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.Box;

/**
 * Agrupa los nodos por contexto sin discriminar implementacion.
 */
public class GroupByContextCriteria implements GroupByCriteria {

	@Override
	public SootMethod getBelongsTo(Box<EscapeSummary> box, Set<Node> targets) {
		return box.getValue().getTarget();
	}

	@Override
	public GroupId getGroup(Edge edge) {
		return new GroupId(edge.getSource(), edge.getField(), edge.isInside(), edge.getTarget().isInside(), edge.getTarget().getContext());
	}
	
}
