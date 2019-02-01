package ar.uba.dc.analysis.common;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.util.Timer;

public abstract class AbstractInterproceduralAnalysis {

	private static Log log = LogFactory.getLog(AbstractInterproceduralAnalysis.class);
	
	protected CallGraph callGraph;
	protected DirectedCallGraph directedCallGraph;
	protected Map<SootMethod, Integer> order;
	protected String mainClass; 
	protected boolean debugIR;
	
	public void run(CallGraph cg, SootMethodFilter filter, SootMethod head, String mainClass)
	{
		run(cg,filter,head,false, mainClass);
	}
	
	public void run(CallGraph cg, SootMethodFilter filter, SootMethod head, boolean debugIR, String mainClass) {
		Timer t = new Timer();
		t.start();
		log.debug("Building directed callgraph");
		this.callGraph = cg;
		this.directedCallGraph = new DirectedCallGraph(cg, filter, head, mainClass);
		this.debugIR = debugIR;
		t.stop();
		log.debug("Building directed callgraph took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
		this.mainClass = head.getDeclaringClass().toString();
		
		// construct reverse pseudo topological order on filtered methods
		buildOrder();
		doAnalysis();
	}
	
	protected void buildOrder() {
		this.order = new HashMap<SootMethod, Integer>();
		
		//BILLY linear ordering of its vertices such that for every directed edge uv from vertex u to vertex v, v comes before u in the ordering
		Orderer<SootMethod> o = new PseudoTopologicalOrderer<SootMethod>();
		Integer i = 0;
		
		
		
		for (SootMethod m : o.newList(directedCallGraph, true)) {
			log.debug(m.toString());

			this.order.put(m, new Integer(i));
			i++;
		}
	}
	
	protected Comparator<SootMethod> getOrderComparator() {
		return new IntComparator();
	}
	
	// queue class
	class IntComparator implements Comparator<SootMethod> {
		public int compare(SootMethod o1, SootMethod o2) {
			Integer v1 = order.get(o1);
			Integer v2 = order.get(o2);
			if(v1!=null && v2!=null)	return v1.intValue() - v2.intValue();
			else if(v1==null && v2!=null) return -v2.intValue();
			else if(v1!=null) return v1.intValue();
			else return 0;
		}
	};
	
	protected abstract void doAnalysis();

	public void setCallGraph(CallGraph callGraph) {
		this.callGraph = callGraph;
	}
}
