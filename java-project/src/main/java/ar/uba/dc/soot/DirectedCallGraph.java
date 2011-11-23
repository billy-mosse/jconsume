package ar.uba.dc.soot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.toolkits.graph.DirectedGraph;
import ar.uba.dc.util.collections.map.HashMultiMap;
import ar.uba.dc.util.collections.map.MultiMap;

/**
 * Builds a DirectedGraph from a CallGraph and SootMethodFilter.
 * 
 * This is used in AbstractInterproceduralAnalysis to construct a reverse pseudo
 * topological order on which to iterate. You can specify a SootMethodFilter to
 * trim the graph by cutting call edges strarting
 * 
 * Methods filtered-out by the SootMethodFilter will not appear in the
 * DirectedGraph!
 */
public class DirectedCallGraph implements DirectedGraph<SootMethod> {
	
	private static Log log = LogFactory.getLog(DirectedCallGraph.class);
	
	protected Set<SootMethod> nodes;
	protected Map<SootMethod, List<SootMethod>> succ;
	protected Map<SootMethod, List<SootMethod>> pred;
	protected List<SootMethod> heads;
	protected List<SootMethod> tails;
	protected int size;

	/**
	 * The constructor does all the work here. After constructed, you can safely
	 * use all interface methods. Moreover, these methods should perform very
	 * fastly...
	 * 
	 * The DirectedGraph will only contain methods in call paths from a method
	 * in head and comprising only methods wanted by filter. Moreover, only
	 * concrete methods are put in the graph...
	 * 
	 * @param heads - is a List of SootMethod
	 */
	public DirectedCallGraph(CallGraph cg, SootMethodFilter filter, SootMethod head) {
		// filter heads by filter
		List<SootMethod> filteredHeads = new LinkedList<SootMethod>();
		if (head.isConcrete() && filter.want(head)) {
			filteredHeads.add(head);
		}
		
		
		nodes = new HashSet<SootMethod>(filteredHeads);
		
		MultiMap<SootMethod, SootMethod> s = new HashMultiMap<SootMethod, SootMethod>();
		MultiMap<SootMethod, SootMethod> p = new HashMultiMap<SootMethod, SootMethod>();

		// simple breadth-first visit
		Set<SootMethod> remain = new HashSet<SootMethod>(filteredHeads);
		int nb = 0;
		log.debug("Dumping method dependencies");
		while (!remain.isEmpty()) {
			Set<SootMethod> newRemain = new HashSet<SootMethod>();
			Iterator<SootMethod> it = remain.iterator();
			while (it.hasNext()) {
				SootMethod m = it.next();
				Iterator<Edge> itt = cg.edgesOutOf(m);
				log.debug(" |- " + m.toString() + " calls");
				while (itt.hasNext()) {
					Edge edge = itt.next();
					SootMethod mm = edge.tgt();
					boolean keep = mm.isConcrete() && filter.want(mm);
					log.debug(" |  |- " + mm.toString() + (keep ? "" : " (filtered out)"));
					if (keep) {
						if (this.nodes.add(mm)) {
							newRemain.add(mm);
						}
						s.put(m, mm);
						p.put(mm, m);
					}
				}
				nb++;
			}
			remain = newRemain;
		}
		log.info("Number of methods to be analysed: " + nb);

		// MultiMap -> Map of List
		succ = new HashMap<SootMethod, List<SootMethod>>();
		pred = new HashMap<SootMethod, List<SootMethod>>();
		tails = new LinkedList<SootMethod>();
		heads = new LinkedList<SootMethod>();
		Iterator<SootMethod> it = nodes.iterator();
		while (it.hasNext()) {
			SootMethod x = it.next();
			Set<SootMethod> ss = s.get(x);
			Set<SootMethod> pp = p.get(x);
			succ.put(x, new LinkedList<SootMethod>(ss));
			pred.put(x, new LinkedList<SootMethod>(pp));
			if (ss.isEmpty())
				tails.add(x);
			if (pp.isEmpty())
				heads.add(x);
		}

		size = nodes.size();
	}
	
	/** You get a List of SootMethod. */
	public List<SootMethod> getHeads() {
		return heads;
	}

	/** You get a List of SootMethod. */
	public List<SootMethod> getTails() {
		return tails;
	}

	/** You get an Iterator on SootMethod. */
	public Iterator<SootMethod> iterator() {
		return nodes.iterator();
	}

	public int size() {
		return size;
	}

	/** You get a List of SootMethod. */
	public List<SootMethod> getSuccsOf(SootMethod s) {
		return succ.get(s);
	}

	/** You get a List of SootMethod. */
	public List<SootMethod> getPredsOf(SootMethod s) {
		return pred.get(s);
	}
}
