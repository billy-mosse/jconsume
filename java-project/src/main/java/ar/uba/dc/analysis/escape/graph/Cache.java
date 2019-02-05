package ar.uba.dc.analysis.escape.graph;

import java.util.HashMap;
import java.util.Map;

public class Cache {

	/**
	 * Caching: this seems to actually improve both speed and memory consumption!
	 */
	private static final Map<Edge, Edge> edgeCache = new HashMap<Edge, Edge>();
	private static final Map<Node, Node> nodeCache = new HashMap<Node, Node>();
	
	public static Edge cacheEdge(Edge e) {
		if (!edgeCache.containsKey(e)) {
			edgeCache.put(e, e);
		}
		
		return edgeCache.get(e);
	}

	public static Node cacheNode(Node n) {
		if (!nodeCache.containsKey(n)) {
			nodeCache.put(n, n);
		}
		
		return nodeCache.get(n);
	}
}
