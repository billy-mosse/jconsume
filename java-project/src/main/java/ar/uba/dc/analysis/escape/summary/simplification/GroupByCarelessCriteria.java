package ar.uba.dc.analysis.escape.summary.simplification;

import java.util.Set;

import soot.SootMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.Box;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * Es el criterio de simplificacion de grafos mas poderoso y por lo tanto aquel que nos hace 
 * perder mayor presicion (aunque compactamos mas cosas).
 * 
 * Permite agrupar cosas de distintos contextos y no tiene en cuenta la implementacion
 * 
 * @author testis
 */
public class GroupByCarelessCriteria implements GroupByCriteria {

	@Override
	public SootMethod getBelongsTo(Box<EscapeSummary> box, Set<Node> targets) {
		return box.getValue().getTarget();
	}

	@Override
	public GroupId getGroup(Edge edge) {
		CircularStack<StatementId> context = edge.getTarget().getContext();
		if (context != null) {
			context = context.clone().clear();
		}
		return new GroupId(edge.getSource(), edge.getField(), edge.isInside(), edge.getTarget().isInside(), context);
	}

	
}
