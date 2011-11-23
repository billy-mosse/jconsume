package ar.uba.dc.analysis.escape.summary.simplification;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import soot.Local;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.SummarySimplifier;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ContainerNode;
import ar.uba.dc.soot.Box;
import ar.uba.dc.util.collections.map.HashMultiMap;
import ar.uba.dc.util.collections.map.MultiMap;

public class EquivalentPathSummarySimplifier implements SummarySimplifier {

	private GroupByCriteria criteria;
	
	private boolean distinguishReturned;
	
	private boolean distinguishEscapeGlobaly;
	
	@Override
	public void simplify(Box<EscapeSummary> box) {
		EscapeSummary simplification = new EscapeSummary(box.getValue());
		
		boolean doOneMoreIteration = false;

		do {
			Set<Node> escapeGlobaly = simplification.getEscapeGlobaly();
			Set<Node> returnedNodes = simplification.getReturnedNodes();
			
			// Agrupamos los ejes por (src, field, inside, contexto, dst.isParam, dst.isInside)
			MultiMap<GroupId, Edge> groups = new HashMultiMap<GroupId, Edge>();
			for (Node src : simplification.getEdgesSources()) {
				for (Edge edge : simplification.getEdgesOutOf(src)) {
					// Los parametros no son tenidos en cuenta en la simplificacion
					if (!edge.getTarget().isParam()) {
						GroupId id = criteria.getGroup(edge);
						if (id != null) {
							if (distinguishReturned) {
								id = new BooleanGroupIdDecorator(id, returnedNodes.contains(edge.getTarget()));
							}
							
							if (distinguishEscapeGlobaly) {
								id = new BooleanGroupIdDecorator(id, escapeGlobaly.contains(edge.getTarget()));
							}
							
							groups.put(id, edge);
						}
					}
				}
			}
			
			// Buscamos algun conjunto que tenga mas de un elemento donde: !dst.isParam
			GroupId key = null;
			Set<Edge> agrupation = null;
			
			for (Entry<GroupId, Set<Edge>> entry : groups.entrySet()) {
				if (entry.getValue().size() > 1) {
					key = entry.getKey();
					agrupation = entry.getValue();
				}
			}
			
			doOneMoreIteration = false;
			if (agrupation != null) {
				// Creamos un nodo que represente a este grupo
				// *******************************************
				Set<Node> targets = new HashSet<Node>(agrupation.size());
				
				Set<Edge> edgesInto = new HashSet<Edge>();
				Set<Edge> edgesOutOf = new HashSet<Edge>();
				Set<String> mutatedFields = new HashSet<String>();
				Set<Local> localPointingToNode = new HashSet<Local>();
				boolean escape = false;
				boolean returned = false;
				for (Edge e : agrupation) {
					Node tgt = e.getTarget();
					targets.add(tgt);
					// El nuevo nodo escapa si alguno de los nodos agrupados escapaba globalmente
					escape = escape || escapeGlobaly.contains(tgt);
					returned = returned || returnedNodes.contains(tgt);
					edgesOutOf.addAll(simplification.getEdgesOutOf(tgt));
					edgesInto.addAll(simplification.getEdgesInto(tgt));
					mutatedFields.addAll(simplification.getMutatedFieldsOf(tgt));
					localPointingToNode.addAll(simplification.localsPointing(tgt));
					simplification.remove(tgt);
				}
				
				// Como todos los nodos tienen el mismo contexto, deben pertenecer al mismo metodo.
				ContainerNode node = new ContainerNode(targets, key.isTargetInside(), key.getTargetContext(), criteria.getBelongsTo(box, targets));

				simplification.add(node);
				// Actualizamos los ejes existentes (src y/o dst) en base a si la fusion del grupo en un sólo nodo los afecta.
				for (Edge e : edgesInto) {
					Node src = e.getSource();
					if (targets.contains(src)) {
						src = node;
					}
					simplification.relate(src, e.getField(), node, e.isInside());
				}
				
				for (Edge e : edgesOutOf) {
					Node tgt = e.getTarget();
					if (targets.contains(tgt)) {
						tgt = node;
					}
					simplification.relate(node, e.getField(), tgt, e.isInside());
				}				
				
				// Actualizamos los mutated fields
				simplification.mutateField(node, mutatedFields);
				
				// Actualizamos las variables que apuntan al nuevo nodo
				simplification.setLocalsPointingTo(node, localPointingToNode);
				
				if (escape) {
					simplification.addGlobEscape(node);
				}
				
				if (returned) {
					simplification.addReturned(node);
				}
				
				// Hacemos una iteracion mas por si todavia podemos seguir comprimiendo el grafo
				doOneMoreIteration = true;
			}
		} while (doOneMoreIteration);

		box.setValue(simplification);	
	}

	public void setCriteria(GroupByCriteria criteria) {
		this.criteria = criteria;
	}

	public void setDistinguishReturned(boolean distinguishReturned) {
		this.distinguishReturned = distinguishReturned;
	}

	public void setDistinguishEscapeGlobaly(boolean distinguishEscapeGlobaly) {
		this.distinguishEscapeGlobaly = distinguishEscapeGlobaly;
	}

}
