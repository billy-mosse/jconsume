package ar.uba.dc.soot;

import soot.SootMethod;
import soot.toolkits.graph.DirectedGraph;

public interface DirectedCallGraphPrinter {
	public void print(String name, SootMethod method, DirectedGraph<SootMethod> dg);
}
