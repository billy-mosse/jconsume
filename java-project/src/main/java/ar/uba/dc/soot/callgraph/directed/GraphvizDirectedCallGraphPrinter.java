package ar.uba.dc.soot.callgraph.directed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soot.SootMethod;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;
import soot.util.dot.DotGraph;
import soot.util.dot.DotGraphNode;
import ar.uba.dc.soot.DirectedCallGraphPrinter;
import ar.uba.dc.util.graphviz.AbstractGraphvizPrinter;

public class GraphvizDirectedCallGraphPrinter extends AbstractGraphvizPrinter implements DirectedCallGraphPrinter {

	public void print(String name, SootMethod method, DirectedGraph<SootMethod> dg) {
		DotGraph dot = new DotGraph(method.toString());
		dot.setGraphLabel(name + " for: " + method.toString());
		Integer id = 0;
		Map<SootMethod, Integer> idmap = new HashMap<SootMethod, Integer>();
	
		// Draw nodes
		Orderer<SootMethod> o = new PseudoTopologicalOrderer<SootMethod>();
		List<SootMethod> methods = o.newList(dg, true);
		for (SootMethod m : methods) {
			DotGraphNode label = dot.drawNode("N_" + id);
			idmap.put(m, id);
			label.setLabel("(" + id + ") " + m.toString());
			label.setAttribute("fontsize", "15");
			label.setShape("box");
			id++;
		}

		// connect edges
		for (SootMethod m : methods) {
			for (SootMethod mm : dg.getSuccsOf(m)) {
				dot.drawEdge("N_" + idmap.get(m), "N_" + idmap.get(mm));
		    }
		}
		
		plot(dot, name + ".dot");
	}
}
