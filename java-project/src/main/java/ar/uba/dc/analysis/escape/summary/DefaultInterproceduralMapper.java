package ar.uba.dc.analysis.escape.summary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
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
	public MultiMap<Node, Node> buildMappingAndApplyRules(EscapeSummary callerSummary, Stmt callStmt, EscapeSummary calleeSummary) {
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
			
			
			//new rules. HACK: these rules also relate edges now, like in Whaley's paper (they don't just build MU).
			hasChanged = new_rule_1(calleeSummary, callerSummary, mu) || hasChanged;
			hasChanged = new_rule_2(calleeSummary, mu) || hasChanged;
			
			//Notar que esta regla es con el caller summary. No es un typo
			hasChanged = new_rule_3(callerSummary, mu) || hasChanged;
			
			hasChanged = new_rule_4(calleeSummary, callerSummary, mu) || hasChanged;
			hasChanged = new_rule_5(calleeSummary, callerSummary, mu) || hasChanged;
			
			
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
	
	/**
	 * For every ? edge to an omega node, we propagate that edge in the caller transitevely through the fields of the omega node 
	 * */
	private boolean new_rule_1(EscapeSummary calleeGraph, EscapeSummary callerGraph,
			MultiMap<Node, Node> mu) {
		
		
		

		boolean hasChanged = false;
		for(Node n1 : calleeGraph.getEdgesSources())
		{
			
			//n1.belongsTo();
			
			for(Edge e12: calleeGraph.getEdgesOutOf(n1))
			{
				Node n2 = e12.getTarget();
				if(e12.getField().equals("?") && n2.isOmega())
				{
					LinkedList<Node> sourceNodes = new LinkedList<Node>();
					ArrayList<Node> processedNodes = new ArrayList<Node>();
					ArrayList<Node> usedSourceNodes = new ArrayList<Node>();
					sourceNodes.addAll(mu.get(n2));
					for(Node n3: mu.get(n1))
					{						
						while(!sourceNodes.isEmpty())
						{
							//para meterse en profundidad.
							Node n4 = sourceNodes.pop();
							usedSourceNodes.add(n4);
							for(Edge e45 : callerGraph.getEdgesOutOf(n4))
							{
								//esta bien que sean outside edges tambien? creo que si.
								//Edge e35 = new Edge(n3, "?", e45.getTarget(), e45.isInside());
								
								boolean contains = callerGraph.getEdgesOutOf(n3).contains(new Edge(n3, "?", e45.getTarget(), e45.isInside()));
								
								if(!contains)
								{
									callerGraph.relate(n3, "?", e45.getTarget(), e45.isInside());
									processedNodes.add(e45.getTarget());
									
									if(!usedSourceNodes.contains(e45.getTarget()))
									{
										sourceNodes.add(e45.getTarget());
									}
									
									hasChanged = true;
								}
							}
						}
					}
				}
				
			}
				/*Node n1;
			calleeGraph.getEdgesOutOf(n1);
			
			for(Edge e: calleeGraph.getEdgesOutOf(n1))
			{
				Node n2 = e.getTarget();
				if(e.getField().equals("?") && 	)
				{
					
				}
			}*/
			
		}
		
		
		// TODO Auto-generated method stub
		return hasChanged;
	}
	
	private boolean new_rule_2(EscapeSummary calleeGraph,
			MultiMap<Node, Node> mu) {
		// TODO Auto-generated method stub
		
		/*
		 * This rule should copy the "?" edges to the caller.
		 * As for now these edges are not special, it does nothing (as another rules already takes care of the general case).
		 * */
		
		return false;
	}
	
	/**
	 * Si n1 -?-> n2, y ademas n1 -f-> n3, entonces n1 puede alcanzar a n2 a través un field de n3, así que se agrega el field n3 -?-> n2.
	 * OJO que si n1 -?-> n1, eso hace que todos sus fields puedan apuntar a n1.
	 */
	private boolean new_rule_3(EscapeSummary callerGraph,
			MultiMap<Node, Node> mu) {
		
		boolean hasChanged = false;
		for(Node n1: callerGraph.getEdgesSources())
		{
			if(n1.isOmega())
			{
				for(Edge n12 : callerGraph.getEdgesOutOf(n1))
				{
					if(n12.getField().equals("?"))
					{
						Node n2 = n12.getTarget();
						for(Edge n13 : callerGraph.getEdgesOutOf(n1))
						{
							Node n3 = n13.getTarget();
							
							boolean contains = callerGraph.getEdgesOutOf(n3).contains(new Edge(n3, "?", n2, n13.isInside()));
							if(!n2.equals(n3) && !contains)
							{
								callerGraph.relate(n3, "?", n2, n13.isInside());
								hasChanged = true;
							}
						}
					}
				}
			}
		}
	
		
		return hasChanged;
	}
	
	/**
	 * Converts outside nodes reached by omega nodes via "?" fields, to omega nodes.
	 * @param calleeGraph
	 * @param mu
	 * @return
	 */
	private boolean new_rule_4(EscapeSummary calleeGraph, EscapeSummary callerGraph,
			MultiMap<Node, Node> mu) {
		
		boolean hasChanged = false;
		for(Node n1: calleeGraph.getEdgesSources())
		{
			if(n1.isOmega())
			{
				for(Node n2 : mu.get(n1))
				{
					for(Edge n23 : callerGraph.getEdgesOutOf(n2))
					{
						if(n23.getField().equals("?"))
						{
							Node n3 = n23.getTarget();
							if(!n3.isInside())
							{
								n3.convertToOmegaNode();
								hasChanged = true;
							}
						}
						
						
					}
				}
			}
		}
		// TODO Auto-generated method stub
		return hasChanged;
	}
	
	private boolean new_rule_5(EscapeSummary calleeGraph, EscapeSummary callerGraph,
			MultiMap<Node, Node> mu) {
		boolean hasChanged = false;
		for(Node n1: calleeGraph.getEdgesSources())
		{
			for(Edge e12 : calleeGraph.getEdgesOutOf(n1))
			{
				if(!e12.isInside())
				{
					Node n2 = e12.getTarget();
					for(Node n3 : mu.get(n1))
					{
						if (n3.isFresh())
						{
							Node n4 = n2.clone();						
							
							boolean contains = callerGraph.getEdgesOutOf(n3).contains(new Edge(n3, e12.getField(), n4, true));							
							
							if(contains)
							{
								callerGraph.add(n4);
								callerGraph.relate(n3,  e12.getField(), n4, true);
								
								hasChanged = true;
							}
							
							//ACA FALTA UN CAMBIO DE CONTEXTO, supongo que al mismo contexto que n1.
							//me parece que deberia crear un nuevo metodo de los nodos que haga eso...
							
							//arreglar cuando tenga un ejemplo hecho.
							throw new NotImplementedException();
						}
					}
				}
			}
		}
		
		return hasChanged;
	}
}
