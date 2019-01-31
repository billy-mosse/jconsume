package ar.uba.dc.analysis.escape.summary;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import soot.Local;
import soot.RefLikeType;
import soot.Value;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Cache;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.ThisNode;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.collections.map.HashMultiMap;
import ar.uba.dc.util.collections.map.MultiMap;

/**
 * This class follows the notation from the paper: Pointer and Escape Analysis for Multithreaded Programs
 * @author billy
 *
 */
public class DefaultInterproceduralMapper implements InterproceduralMapper {

	/**
	 * Crea un interprocedural mapping el cual permite relacionar los nodos de un summary llamador con los del summary invocado.
	 * 
	 * @author testis
	 */
	@Override
	public MultiMap<Node, Node> buildMapping(EscapeSummary callerSummary, Stmt callStmt, EscapeSummary calleeSummary) {
		MultiMap<Node, Node> mu = new HashMultiMap<Node, Node>();

		// compute mapping relation g -> this
		// ///////////////////////////////////
		rule_1(callStmt, callerSummary, mu);
		
		// COULD BE OPTIMIZED!
		// many times, we need to copy sets cause we mutate them within
		// iterators
		boolean hasChanged = true;
		//fixed point algorithm
		while (hasChanged) { // (2) & (3) rules fixpoint
			hasChanged = false;
			hasChanged = rule_2(callerSummary, calleeSummary, mu) || hasChanged;
			hasChanged = rule_3(calleeSummary, mu) || hasChanged;
			
			
			//aca deberia agregar las reglas nuevas.
		}
		
		return mu;
	}

	//Constraint 1 maps each parameter node from the
	//callee to the nodes from the caller that represent the actual
	//parameters at the call site.
	@SuppressWarnings("unchecked")
	protected void rule_1(Stmt callStmt, EscapeSummary callerGraph, MultiMap<Node, Node> mu) {
		Local varCallOn = SootUtils.getVariableTargetOfCall(callStmt.getInvokeExpr());
		
		List<Value> args = callStmt.getInvokeExpr().getArgs(); // (1) rule
		int nb = 0;
		for (Value arg : args) {
			if (arg instanceof Local) {
				Local localArg = (Local) arg;
				if (localArg.getType() instanceof RefLikeType) {
					
					//hace la regla 1, de hacer que los parametros aputnen a lo que correspondan.
					mu.putAll(Cache.cacheNode(new ParamNode(nb)), callerGraph.nodesPointedBy(localArg));
				}
			}
			nb++;
		}
		
		if (varCallOn != null) { // (1) rule for "this" argument
			mu.putAll(ThisNode.node, callerGraph.nodesPointedBy(varCallOn));
		}
	}
	
	//Constraint 2 matches outside
	//edges read by the callee against corresponding inside edges
	//from the caller.
	protected boolean rule_2(EscapeSummary callerGraph, EscapeSummary calleeGraph, MultiMap<Node, Node> mu) {
		boolean hasChanged = false;
		// (2)
		
		//for all callee nodes n1...
		for (Node n1 : new LinkedList<Node>(mu.keySet())) {
			
			//and for all caller nodes n3=mu(n1),
			//so, for all nodes in the caller that come from a node in the callee
			for (Node n3 : new LinkedList<Node>(mu.get(n1))) {
				
				//we check the edges n1->n2 out of the nodes n1 in the callee
				for (Edge e12 : calleeGraph.getEdgesOutOf(n1)) {
					
					//but only the outside edges
					if (!e12.isInside()) {
						
						//for those inside edges, we get edges n3->n4 in the caller,
						//that is, edges mu(n1) -> n4 in the caller
						for (Edge e34 : callerGraph.getEdgesOutOf(n3)) {
							
							//but only the inside edges
							if (e34.isInside() && e12.getField().equals(e34.getField())) {
								
								//this is actually rule (5) of the whaley paper.
								if (mu.put(e12.getTarget(), e34.getTarget())) {
									hasChanged = true;
								}
							}
						}
					}
				}
			}
		}
		
		return hasChanged;
	}
	
	//Constraint 3 matches outside edges from the
	//callee against inside edges from the callee to model aliasing
	//between callee nodes.
	protected boolean rule_3(EscapeSummary calleeGraph, MultiMap<Node, Node> mu) {
		boolean hasChanged = false;

		// (3)
		for (Node n1 : calleeGraph.getEdgesSources()) {
			for (Node n3 : calleeGraph.getEdgesSources()) {
				// ((mu(n1) U {n1}) inter (mu(n3) U {n3})) not empty
				Set<Node> mu1 = new HashSet<Node>(mu.get(n1));
				Set<Node> mu3 = new HashSet<Node>(mu.get(n3));
				boolean cond = n1.equals(n3) || mu1.contains(n3) || mu3.contains(n1);
				
				Iterator<Node> itt = mu1.iterator();
				while (!cond && itt.hasNext()) {
					cond = cond || mu3.contains(itt.next());
				}
			
				// add (mu(n4) U ({n4} inter PNodes)) to mu(n2)
				if (cond && (!n1.equals(n3) || n1.isLoad())) {
					for (Edge e12 : calleeGraph.getEdgesOutOf(n1)) {
						if (!e12.isInside()) {
							for (Edge e34 : calleeGraph.getEdgesOutOf(n3)) {
								if (e34.isInside()) {
									if (e12.getField().equals(e34.getField())) {
										Node n2 = e12.getTarget();
										Node n4 = e34.getTarget();

										// add n4 (if not param node) to
										// mu(n2)
										if (!n4.isParam() && mu.put(n2, n4)) {
											hasChanged = true;
										}

										// add mu(n4) to mu(n2)
										if (mu.putAll(n2, mu.get(n4))) {
											hasChanged = true;	
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return hasChanged;
	}
}
