package ar.uba.dc.analysis.escape.summary;

import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.util.collections.map.MultiMap;

public interface SummaryCombiner {

	void combine(EscapeSummary callerSummary, EscapeSummary calleeSummary, MultiMap<Node, Node> mu, Stmt callStmt);

}
