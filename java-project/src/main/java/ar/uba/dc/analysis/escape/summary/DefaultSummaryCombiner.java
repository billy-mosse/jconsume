package ar.uba.dc.analysis.escape.summary;

import soot.Local;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.collections.map.MultiMap;

public class DefaultSummaryCombiner implements SummaryCombiner {

	/**
	 * Combina el resumen del metodo invocado con el llamador en base al interprocedural mapping dado como parametro
	 */
	@Override
	public void combine(EscapeSummary callerSummary, EscapeSummary calleeSummary, MultiMap<Node, Node> mu, Stmt callStmt) {
		// project edges
		for (Node n1 : calleeSummary.getEdgesSources()) {
			for (Edge e12 : calleeSummary.getEdgesOutOf(n1)) {
				String f = e12.getField();
				Node n2 = e12.getTarget();
				
				for (Node mu1 : mu.get(n1)) {
					if (e12.isInside()) {
						for (Node mu2 : mu.get(n2)) {
							callerSummary.relate(mu1, f, mu2, true);
						}
					} else {
						callerSummary.relate(mu1, f, n2, false);
					}
				}
			}
		}

		// return value
		Local varToAssignedResult = SootUtils.getVariableToAssignResult(callStmt);
		if (varToAssignedResult != null) {
			// strong update on locals
			callerSummary.remove(varToAssignedResult);
			for (Node n : calleeSummary.getReturnedNodes()) {
				callerSummary.variablePointsTo(varToAssignedResult, mu.get(n));
			}
		}

		// global escape
		for (Node n : calleeSummary.getEscapeGlobaly()) {
			callerSummary.nodesEscapeGlobaly(mu.get(n));
		}
	}
	
}
