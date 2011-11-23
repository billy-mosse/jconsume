package ar.uba.dc.util.collections.graph;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.util.collections.map.HashMultiMap;
import ar.uba.dc.util.collections.map.MultiMap;

/**
 * Implementacion de un grafo dirigido
 * 
 * @author testis
 *
 * @param <N> - Tipo de los nodos
 * @param <E> - Tipo de los ejes
 */
public class DirectedGraph<N, E extends Edge<N>> {

	protected Set<N> nodes; // all nodes
	protected MultiMap<N, E> edges; // source node -> edges
	protected MultiMap<N, E> backEdges; // target node -> edges
	
	public DirectedGraph() {
		nodes = new HashSet<N>();
		edges = new HashMultiMap<N, E>();
		backEdges = new HashMultiMap<N, E>();
	}
	
	public DirectedGraph(DirectedGraph<N, E> value) {
		nodes = new HashSet<N>(value.nodes);
		edges = new HashMultiMap<N, E>(value.edges);
		backEdges = new HashMultiMap<N, E>(value.backEdges);
	}
	
	@Override
	public int hashCode() {
		return nodes.hashCode()	+ edges.hashCode();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (!(o instanceof DirectedGraph)) {
			return false;
		}
		
		DirectedGraph<N, E> g = (DirectedGraph<N, E>) o;
		return nodes.equals(g.nodes) && edges.equals(g.edges);
	}
	
	@Override
	public String toString() {
		String result = "<{ " + StringUtils.join(nodes, ", ") + " }, { ";
		StringBuilder sbEdges = new StringBuilder();
		
		for (N node : edges.keySet()) {
			for (E edge : edges.get(node)) {
				if (sbEdges.length() > 0) {
					sbEdges.append(", ");
				}
				sbEdges.append(edge.toString());
			}
		}
		
		result += sbEdges.toString();
		result += " }>";
		return result;
	}

	public Set<N> getNodes() {
		return new HashSet<N>(nodes);
	}
	
	public Set<N> getEdgesSources() {
		return edges.keySet();
	}
	
	public Set<E> getEdgesOutOf(N src) {
		return new HashSet<E>(edges.get(src));
	}
	
	public Set<E> getEdgesInto(N target) {
		return new HashSet<E>(backEdges.get(target));
	}
	
	/**
	 * Replace the current graph with its union with arg. arg is not modified.
	 */
	public void union(DirectedGraph<N, E> arg) {
		nodes.addAll(arg.nodes);
		edges.putAll(arg.edges);
		backEdges.putAll(arg.backEdges);
	}

	public void add(N node) {
		nodes.add(node);
	}

	public void add(E edge) {
		if (edges.put(edge.getSource(), edge)) {
			backEdges.put(edge.getTarget(), edge);
		}
	}

	public boolean contains(N node) {
		return nodes.contains(node);
	}

	public Set<N> edgesSources() {
		return edges.keySet();
	}

	public void remove(N node) {
		for (E edge : edges.get(node)) {
			backEdges.remove(edge.getTarget(), edge);
		}
		
		for (E edge : backEdges.get(node)) {
			edges.remove(edge.getSource(), edge);
		}
		
		edges.remove(node);
		backEdges.remove(node);
		nodes.remove(node);
	}

	public void remove(E edge) {
		edges.remove(edge.getSource(), edge);
		backEdges.remove(edge.getTarget(), edge);
	}
}
