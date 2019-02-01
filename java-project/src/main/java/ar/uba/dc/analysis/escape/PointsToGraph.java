package ar.uba.dc.analysis.escape;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Local;
import ar.uba.dc.analysis.escape.graph.Cache;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.graph.DirectedGraph;
import ar.uba.dc.util.collections.map.HashMultiMap;
import ar.uba.dc.util.collections.map.MultiMap;

public class PointsToGraph {

	private static Log log = LogFactory.getLog(PointsToGraph.class);
	
	protected DirectedGraph<Node, Edge> graph;
	protected Set<Node> paramNodes; // only parameter & this nodes
	protected MultiMap<Local, Node> locals; // local -> nodes
	protected Set<Node> ret; // return -> nodes
	protected Set<Node> globEscape; // nodes escaping globally
	protected MultiMap<Node, Local> backLocals; // target node -> local node sources
	
	public PointsToGraph() {
		this.graph = new DirectedGraph<Node, Edge>();
		this.paramNodes = new HashSet<Node>();
		this.locals = new HashMultiMap<Local, Node>();
		this.ret = new HashSet<Node>();
		this.globEscape = new HashSet<Node>();
		this.backLocals = new HashMultiMap<Node, Local>();
	}
	
	public PointsToGraph(PointsToGraph value) {
		graph = new DirectedGraph<Node, Edge>(value.graph);
		paramNodes = new HashSet<Node>(value.paramNodes);
		locals = new HashMultiMap<Local, Node>(value.locals);
		ret = new HashSet<Node>(value.ret);
		globEscape = new HashSet<Node>(value.globEscape);
		backLocals = new HashMultiMap<Node, Local>(value.backLocals);
	}

	public int hashCode() {
		return graph.hashCode()
				// + paramNodes.hashCode() // redundant info
				+ locals.hashCode() 
				+ ret.hashCode()
				+ globEscape.hashCode();
				// + backLocals.hashCode() // redundant info
				
	}

	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (!(o instanceof PointsToGraph))
			return false;
		PointsToGraph ptg = (PointsToGraph) o;
		return graph.equals(ptg.graph)
				// && paramNodes.equals(ptg.paramNodes) // redundant info
				&& locals.equals(ptg.locals)
				&& ret.equals(ptg.ret)
				&& globEscape.equals(ptg.globEscape);
				// && backLocals.equals(g.backLocals) // redundant info
	}

	/**
	 * Replace the current ptg with its union with value. value is not modified.
	 */
	public void union(PointsToGraph value) {
		graph.union(value.graph);
		paramNodes.addAll(value.paramNodes);
		locals.putAll(value.locals);
		ret.addAll(value.ret);
		globEscape.addAll(value.globEscape);
		backLocals.putAll(value.backLocals);
	}
	
	public void add(Node node) {
		graph.add(node);
		if (node.isParam()) {
			paramNodes.add(node);
		}
	}
	
	public void removeLocals() {
		locals = new HashMultiMap<Local, Node>();
		backLocals = new HashMultiMap<Node, Local>();
	}

	public void localEscapes(Local variable) {
		// nodes escape globally
		globEscape.addAll(locals.get(variable));
	}

	public void returnLocal(Local variable) {
		log.debug(" | | |- return " + variable.getName());
		// strong update on ret
		ret.clear();
		ret.addAll(locals.get(variable));
	}

	public void strongUpdate(Local variable) {
		log.debug(" | | | |- strong update of [" + variable.getName() + "] with [null]");
		remove(variable);
	}
	
	public void strongUpdate(Local variable, Local with) {
		log.debug(" | | | |- strong update of [" + variable.getName() + "] with [" + with.getName() + "]");
		remove(variable);
		localsPutAll(variable, locals.get(with));
	}
	
	public void strongUpdate(Local variable, Node node) {
		log.debug(" | | | |- strong update of [" + variable.getName() + "] with [" + node + "]");
		remove(variable);
		localsPut(variable, node);
		graph.add(node);
		
		if (node.isParam()) {
			paramNodes.add(node);
		}
	}
	
	public void strongUpdate(Local variable, Set<Node> nodes) {
		log.debug(" | | | |- strong update of [" + variable.getName() + "] with [" + nodes + "]");
		
		remove(variable);
		localsPutAll(variable, nodes);
		
		for (Node node : nodes) {
			graph.add(node);
			
			if (node.isParam()) {
				paramNodes.add(node);
			}
		}
	}
	
	/**
	 * leftVar.field = rightVar or leftVar[] = rightVar
	 * 
	 * @param leftVar
	 * @param field
	 * @param rightVar
	 */
	public void weakUpdate(Local leftVar, String field, Local rightVar) {
		for (Node nodeLeft : locals.get(leftVar)) {
			for (Node nodeRight : locals.get(rightVar)) {
				graph.add(Cache.cacheEdge(new Edge(nodeLeft, field, nodeRight, true)));
			}
		}
	}
	
	public Set<Node> getNodesPointedBy(Local variable) {
		return new HashSet<Node>(locals.get(variable));
	}
	
	protected boolean remove(Local local) {
		for (Node node : locals.get(local)) {
			backLocals.remove(node, local);
		}
		
		return locals.remove(local);
	}
	
	protected boolean localsPut(Local local, Node node) {
		backLocals.put(node, local);
		return locals.put(local, node);
	}
	
	protected final boolean localsPutAll(Local local, Set<Node> nodes) {
		for (Node node : nodes) {
			backLocals.put(node, local);
		}
		return locals.putAll(local, nodes);
	}
	
	public final void setLocalsPointingTo(Node node, Set<Local> localsPointing) {
		for (Local local : localsPointing) {
			locals.put(local, node);
			backLocals.put(node, local);
		}
	}

	public void relate(Node source, Node target, String field, boolean inside) {
		graph.add(Cache.cacheEdge(new Edge(source, field, target, inside)));
	}

	public Set<Edge> edgesOutOf(Node src) {
		return graph.getEdgesOutOf(src);
	}
	
	public Set<Edge> edgesInto(Node target) {
		return graph.getEdgesInto(target);
	}

	public Set<Node> getEscaping() {
		Set<Node> escaping = new HashSet<Node>();
		internalPassNodes(ret, escaping, true);
		internalPassNodes(globEscape, escaping, true);
		if (graph.contains(GlobalNode.node)) {
			internalPassNode(GlobalNode.node, escaping, true);
		}
		internalPassNodes(paramNodes, escaping, true);
		return escaping;
	}
	
	protected void internalPassNodes(Set<Node> toColor, Set<Node> dest, boolean consider_inside) {
		for (Node n : toColor) {
			internalPassNode(n, dest, consider_inside);
		}			
	}
	
	protected void internalPassNode(Node node, Set<Node> dest, boolean consider_inside) {
		if (!dest.contains(node)) {
			dest.add(node);
			internalPassEdges(graph.getEdgesOutOf(node), dest, consider_inside);
		}
	}
	
	protected void internalPassEdges(Set<Edge> toColor, Set<Node> dest, boolean consider_inside) {
		for (Edge edge : toColor) {
			if (consider_inside || !edge.isInside()) {
				Node node = edge.getTarget();
				if (!dest.contains(node)) {
					dest.add(node);
					internalPassEdges(graph.getEdgesOutOf(node), dest, consider_inside);
				}
			}
		}
	}

	public Set<Node> getEdgesSources() {
		return graph.edgesSources();
	}

	public Set<Node> getNodes() {
		return graph.getNodes();
	}

	public Set<Node> getReturnedNodes() {
		return new HashSet<Node>(ret);
	}

	public Set<Node> getEscapeGlobaly() {
		return new HashSet<Node>(globEscape);
	}

	public void nodesEscapeGlobaly(Set<Node> nodes) {
		globEscape.addAll(nodes);
	}

	public void remove(Node node) {
		graph.remove(node);

		for (Local local : backLocals.get(node)) {
			locals.remove(local, node);
		}
		
		ret.remove(node);
		backLocals.remove(node);
		paramNodes.remove(node);
		globEscape.remove(node);		
	}

	public void remove(Edge edge) {
		graph.remove(edge);
	}

	public boolean escapeGlobal(Node node) {
		return globEscape.contains(node);
	}

	public Set<Local> getLocals() {
		return locals.keySet();
	}

	public void addReturned(Node node) {
		ret.add(node);
	}

	public void addGlobalEscape(Node node) {
		globEscape.add(node);
	}

	public PointsToGraph changeContext(StatementId callStmtId, Map<Node, Node> nodeMapping) {
		PointsToGraph ret = new PointsToGraph();
		
		ret.graph = new DirectedGraph<Node, Edge>();
		for (Node node : graph.getNodes()) {
			Node clone = node.clone();
			clone.changeContext(callStmtId);
			nodeMapping.put(node, clone);
			ret.graph.add(clone);
		}
		
		// Modificamos el conjunto para que apunte a los nuevos nodos (con el contexto modificado)
		for (Node src : graph.getEdgesSources()) {
			for (Edge edge : graph.getEdgesOutOf(src)) {
				ret.graph.add(Cache.cacheEdge(new Edge(nodeMapping.get(src), edge.getField(), nodeMapping.get(edge.getTarget()), edge.isInside())));
			}
		}
		
		ret.backLocals = new HashMultiMap<Node, Local>();
		for (Node node : backLocals.keySet()) {
			ret.backLocals.putAll(nodeMapping.get(node), new HashSet<Local>(backLocals.get(node)));
		}
		
		ret.globEscape = mapSet(nodeMapping, globEscape);
		
		ret.locals = new HashMultiMap<Local, Node>();
		for (Local local : locals.keySet()) {
			ret.locals.putAll(local, mapSet(nodeMapping, locals.get(local)));
		}
		
		ret.paramNodes = mapSet(nodeMapping, paramNodes);
		ret.ret = mapSet(nodeMapping, this.ret);
		
		return ret;
	}
	
	private Set<Node> mapSet(Map<Node, Node> nodeMapping, Set<Node> src) {
		Set<Node> ret = new HashSet<Node>();
		for (Node node : src) {
			ret.add(nodeMapping.get(node));
		}
		
		return ret;
	}

	public Set<Node> getParameterNodes() {
		return new HashSet<Node>(paramNodes);
	}
	
	
	public void returnNodes(Set<Node> nodes) {
		ret.addAll(nodes);
	}

	public void escapeGlobal(Set<Node> nodes) {
		globEscape.addAll(nodes);
	}

	public Set<Local> getLocalsPointingTo(Node target) {
		return new HashSet<Local>(backLocals.get(target));
	}
}
