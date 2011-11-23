package ar.uba.dc.analysis.escape.summary.simplification;

import java.util.Set;

import soot.SootMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.Box;

public interface GroupByCriteria {

	GroupId getGroup(Edge edge);
	
	SootMethod getBelongsTo(Box<EscapeSummary> box, Set<Node> targets);
	
}
