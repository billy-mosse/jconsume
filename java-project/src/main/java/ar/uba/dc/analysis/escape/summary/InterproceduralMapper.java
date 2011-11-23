package ar.uba.dc.analysis.escape.summary;

import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.util.collections.map.MultiMap;

public interface InterproceduralMapper {

	MultiMap<Node, Node> buildMapping(EscapeSummary callerSummary, Stmt callStmt, EscapeSummary calleeSummary);

}
