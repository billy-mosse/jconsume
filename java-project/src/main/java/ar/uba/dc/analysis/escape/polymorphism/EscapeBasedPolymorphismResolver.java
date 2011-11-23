package ar.uba.dc.analysis.escape.polymorphism;

import java.util.Set;

import soot.Local;
import soot.SootClass;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.PolymorphismResolver;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.SootUtils;

public class EscapeBasedPolymorphismResolver implements PolymorphismResolver {

	@Override
	public SootClass getTarget(Stmt callStmt, EscapeSummary context) {
		SootClass implementation = null;
		Local varTargetOfCall = SootUtils.getVariableTargetOfCall(callStmt.getInvokeExpr());
		Set<Node> callTargetNodes = context.nodesPointedBy(varTargetOfCall);
		if (callTargetNodes.size() == 1) {
			Node node = callTargetNodes.iterator().next();
			if (node.isInside()) {
				implementation = node.getType();
			}
		}
		
		return implementation;
	}

}
