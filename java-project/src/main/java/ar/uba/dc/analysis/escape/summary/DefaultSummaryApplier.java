package ar.uba.dc.analysis.escape.summary;

import java.util.LinkedList;
import java.util.Set;

import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.SummaryApplier;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.Box;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.map.MultiMap;


public class DefaultSummaryApplier implements SummaryApplier {

	private InterproceduralMapper mapper;
	
	private SummaryCombiner combiner;
	
	private int sensitivity;
	
	/* Los nodos capturados por el callee deben figurar en el caller? */
	private boolean combineCapturedNodes;
	
	public void applySummary(Box<EscapeSummary> src, Stmt callStmt, EscapeSummary calleeSummary, Box<EscapeSummary> dest) {
		EscapeSummary summary = calleeSummary;
		if (sensitivity != 0) {
			// Cambiamos el contexto porque ahora todos los nodos del callee tienen que referenciar al call que se acaba de hacer.
			// Esto devuelve una copia del summary con todos los nodos con el contexto cambiado
			summary = calleeSummary.changeContext(new StatementId(src.getValue().getTarget(), callStmt));
		}
		
		EscapeSummary callerGraph = new EscapeSummary(src.getValue());
		
		// compute mapping relation g -> this
		// ///////////////////////////////////
		MultiMap<Node, Node> mu = mapper.buildMappingAndApplyRules(callerGraph, callStmt, summary);
		
		// extend mu into mu'
		
		//The algorithm next extends μ to μ 0 to ensure that all
		//nodes from the callee (except the parameter nodes) appear
		//in the new parallel interaction graph:
		for (Node n : summary.getNodes()) {
			if (!n.isParam()) {
				mu.put(n, n);
				callerGraph.add(n);		
			}
		}
		
		// combine g into this
		// ////////////////////
		combiner.combine(callerGraph, summary, mu, callStmt);

		
		// simplification
		// ///////////////

		Set<Node> escaping = callerGraph.getEscaping();
		for (Node n : new LinkedList<Node>(callerGraph.getNodes())) {
			if (!escaping.contains(n)) {
				if (n.isLoad()) {
					// remove captured load nodes
					callerGraph.remove(n);
				} else {
					// ... and outside edges from captured nodes
					for (Edge e : new LinkedList<Edge>(callerGraph.getEdgesOutOf(n))) {
						if (!e.isInside()) {
							callerGraph.remove(e);
						}
					}
				}
			}
		}				

 		if (!combineCapturedNodes) {
			escaping = summary.getEscaping();
			for (Node calleeNode : summary.getNodes()) {
				if (!escaping.contains(calleeNode)) {
					callerGraph.remove(calleeNode);
				}
			}
		}
		
		// update mutated
		// ///////////////

		for (Node n : summary.getMutatedNodes()) {
			for (Node nn : mu.get(n)) {
				
				//por que pide que no sea un inside no? porque si no no hace falta mutarlo?
				if (callerGraph.getNodes().contains(nn) && !nn.isInside()) {
					for (String f : summary.getMutatedFieldsOf(n)) {
						callerGraph.addMutated(nn, f);
					}
				}
			}
		}
				
		dest.setValue(callerGraph);
	}

	public void setMapper(InterproceduralMapper mapper) {
		this.mapper = mapper;
	}

	public void setCombiner(SummaryCombiner combiner) {
		this.combiner = combiner;
	}

	public void setCombineCapturedNodes(boolean combineCapturedNodes) {
		this.combineCapturedNodes = combineCapturedNodes;
	}
	
	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}
}
