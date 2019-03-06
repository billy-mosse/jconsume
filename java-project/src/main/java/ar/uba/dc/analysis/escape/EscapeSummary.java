package ar.uba.dc.analysis.escape;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Local;
import soot.SootMethod;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.graph.Cache;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ContainerNode;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.escape.graph.node.ThisNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.map.HashMultiMap;
import ar.uba.dc.util.collections.map.MultiMap;

public class EscapeSummary {

	private static Log log = LogFactory.getLog(EscapeSummary.class);
	
	/** Metodo al cual se refiere el summary **/
	protected SootMethod target;
	protected PointsToGraph ptg;
	protected MultiMap<Node, String> mutated;	// node -> field such that (node,field) is
	protected boolean isArtificial;
	protected int maxLive;
	protected int escape;
	
	public EscapeSummary() {
		this.target = null;
		this.ptg = new PointsToGraph();
		this.mutated = new HashMultiMap<Node, String>();
		this.isArtificial = false;
	}

	public boolean isArtificial() {
		return isArtificial;
	}

	public void setArtificial(boolean isArtificial) {
		this.isArtificial = isArtificial;
	}

	public EscapeSummary(SootMethod target) {
		this();
		this.target = target;
		this.isArtificial = false;
	}
	
	public EscapeSummary(EscapeSummary value) {
		target = value.target;
		ptg = new PointsToGraph(value.ptg);
		mutated = new HashMultiMap<Node, String>(value.mutated);
		isArtificial = value.isArtificial;
	}

	@Override
	public int hashCode() {
		return target.hashCode() + ptg.hashCode() + mutated.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (!(o instanceof EscapeSummary))
			return false;
		EscapeSummary es = (EscapeSummary) o;
		return target.equals(es.target)
				&& ptg.equals(es.ptg) 
				&& mutated.equals(es.mutated);
	}	

	public SootMethod getTarget() {
		return target;
	}
	
	//for debug purposes
	public String toString()
	{
		return this.target.toString();
	}

	/**
	 * Replace the current summary with its union with value. value is not modified.
	 */
	public void union(EscapeSummary value) {
		ptg.union(value.ptg);
		mutated.putAll(value.mutated);
	}

	public void removeLocals() {
		ptg.removeLocals();
	}

	/**
	 * throw variable
	 */
	public void localEscapes(Local variable) {
		ptg.localEscapes(variable);
	}
	
	/**
	 * return variable
	 */
	public void returnLocal(Local variable) {
		ptg.returnLocal(variable);	
	}
	
	/**
	 * variable = C.f or variable = exception
	 */
	public void localIsUnknown(Local variable) {
		log.debug(" | | |- " + variable.getName() + " is unknown");
		ptg.strongUpdate(variable, GlobalNode.node);
	}
	
	/**
	 * left = param[index]
	 */
	public void assignParamToLocal(int index, Local left) {
		log.debug(" | | |- assign param[" + index + "] to " + left.getName());
		// strong update on local
		ptg.strongUpdate(left, Cache.cacheNode(new ParamNode(index)));		
	}
	
	/**
	 * left = this
	 */
	public void assignThisToLocal(Local left) {
		log.debug(" | | |- assign This to " + left.getName());
		// strong update on local
		ptg.strongUpdate(left, ThisNode.node);
	}

	/**
	 * Store non-static: left.field = right, or left[?] = right if field is [].
	 */
	public void assignLocalToField(Local right, Local left, String field) {
		log.debug(" | | |- " + left.getName() + "." + field + " = " + right.getName());
		
		// Primero registramos que se cambio el campo. Lo hacemos antes de actualizar la variable
		// para no tener en cuenta los nodos que nos agrega el weakUpdate
		mutateField(left, field);
		ptg.weakUpdate(left, field, right);
	}
	
	/**
	 * Store a primitive type into a non-static field left.field = v
	 */
	public void mutateField(Local left, String field) {
		log.debug(" | | |- field " + field + " of " + left.getName() + " has mutated");
		for (Node leftNode : ptg.getNodesPointedBy(left)) {
			mutateField(leftNode, field);			
		}
	}
	
	public void mutateField(Node leftNode, String field)
	{
		if (!leftNode.isInside()) {
			mutated.put(leftNode, field);
		}
		
		//Si nodeLeft apunta via "?" a otros nodos, se hace lo mismo con esos nodos
		for(Edge eFromNodeLeft : this.getEdgesOutOf(leftNode))
		{
			if(eFromNodeLeft.getField().equals("?"))
			{
				Node n = eFromNodeLeft.getTarget();
				if(!n.equals(leftNode))
					mutateField(n, field);
			}
		}
	}
	
	/** Copy assignment left = right. */
	public void assignLocalToLocal(Local right, Local left) {
		log.debug(" | | |- " + left.getName() + " = " + right.getName());
		
		// strong update on local
		ptg.strongUpdate(left, right);
	}

	/** variable = null */
	public void assignNullToLocal(Local variable) {
		log.debug(" | | |- " + variable.getName() + " = null");
		// strong update on local
		ptg.strongUpdate(variable);
	}
	
	/**
	 * Store a primitive type into a static field left.field = v
	 */
	public void mutateStaticField(String field) {
		log.debug(" | | |- " + field + " has mutated");  
		Node node = GlobalNode.node;
		mutateStaticField(node, field);		
	}
	
	public void mutateStaticField(Node node, String field)
	{
		mutated.put(node, field);
		ptg.add(node);
		
		//no se si GLB puede apuntar a otros nodos,...pero por las dudas...
		for(Edge eFromNodeLeft : this.getEdgesOutOf(node))
		{
			if(eFromNodeLeft.getField().equals("?"))
			{
				Node n = eFromNodeLeft.getTarget();
				if(!n.equals(node))
					mutateField(n, field);
			}
		}
	}
	
	/**
	 * Store static: C.field = right.
	 */
	public void assignLocalToStaticField(Local right, String field) {
		log.debug(" | | |- asign " + right.getName() + " to static field " + field);
		Node node = GlobalNode.node;
		localEscapes(right);
		ptg.add(node);
		mutated.put(node, field);
	}
	
	/**
	 * Load non-static: left = right.field, or left = right[?] if field is [].
	 */
	public void assignFieldToLocal(SootMethod methodUnderAnalysis, Stmt stmt, Local right, String field, Local left, int sensitivity) {
		log.debug(" | | |- " + left.getName() + " = " + right + "." + field);
		Set<Node> B = new HashSet<Node>();
		Set<Node> escaping = ptg.getEscaping();
		
		Set<Node> A = new HashSet<Node>();
		for (Node nodeRight : ptg.getNodesPointedBy(right)) {
			for (Edge edge : ptg.edgesOutOf(nodeRight)) {
				if (edge.isInside() && edge.getField().equals(field))
					A.add(edge.getTarget());
			}

			if (escaping.contains(nodeRight)) {
				B.add(nodeRight);
			}
		}

		if (!B.isEmpty()) {
			// right can escape

			// we add a label load node & outside edges
			Node loadNode = new StmtNode(new StatementId(methodUnderAnalysis, stmt), false, sensitivity);
			
			for (Node node : B) {
				ptg.relate(node, loadNode, field, false);
			}
			A.add(loadNode);
		}
		
		// strong update on local
		ptg.strongUpdate(left, A);
	}
	
	/**
	 * left = new Object or left = new Array
	 */
	public void assignNewToLocal(SootMethod methodUnderAnalysis, Stmt stmt, Local left, int sensitivity) {
		log.debug(" | | |- " + left.getName() + " = new Object");
		
		// strong update on local we add a label inside node
		Node node = new StmtNode(new StatementId(methodUnderAnalysis, stmt), true, sensitivity);
		ptg.strongUpdate(left, node);
	}

	public Set<Node> nodesPointedBy(Local localArg) {
		return ptg.getNodesPointedBy(localArg);
	}

	public Set<Edge> getEdgesOutOf(Node node) {
		return ptg.edgesOutOf(node);
	}
	
	public Set<Edge> getEdgesInto(Node target) {
		return ptg.edgesInto(target);
	}

	public Set<Node> getEdgesSources() {
		return ptg.getEdgesSources();
	}

	public Set<Node> getEscaping() {
		return ptg.getEscaping();
	}

	public Set<Node> getNodes() {
		return ptg.getNodes();
	}

	public void add(Node node) {
		ptg.add(node);
	}

	public void relate(Node source, String field, Node target, boolean inside) {
		ptg.relate(source, target, field, inside);
	}

	public void remove(Local variable) {
		ptg.remove(variable);
	}

	public Set<Node> getReturnedNodes() {
		return ptg.getReturnedNodes();
	}

	public void variablePointsTo(Local variable, Set<Node> nodes) {
		ptg.localsPutAll(variable, nodes);
	}

	public Set<Node> getEscapeGlobaly() {
		return ptg.getEscapeGlobaly();
	}

	public void nodesEscapeGlobaly(Set<Node> nodes) {
		ptg.nodesEscapeGlobaly(nodes);
	}

	public void remove(Node node) {
		ptg.remove(node);
		mutated.remove(node);
	}

	public void remove(Edge edge) {
		ptg.remove(edge);
	}

	public Set<Node> getMutatedNodes() {
		return mutated.keySet();
	}

	public Set<String> getMutatedFieldsOf(Node node) {
		return mutated.get(node);
	}

	public void addMutated(Node node, String field) {
		mutated.put(node, field);
	}

	public boolean escapeGlobal(Node node) {
		return ptg.escapeGlobal(node);
	}

	public Set<Local> getLocals() {
		return ptg.getLocals();
	}

	public void addReturned(Node node) {
		ptg.addReturned(node);
	}

	public void addGlobEscape(Node node) {
		ptg.addGlobalEscape(node);
	}

	public EscapeSummary changeContext(StatementId callStmtId) {
		EscapeSummary ret = new EscapeSummary(target);
		Map<Node, Node> nodeMapping = new HashMap<Node, Node>();
		ret.ptg = ptg.changeContext(callStmtId, nodeMapping);

		// Modificamos el conjunto para que apunte a los nuevos nodos (con el contexto modificado)
		ret.mutated = new HashMultiMap<Node, String>();
		
		for (Node node : mutated.keySet()) {
			ret.mutated.putAll(nodeMapping.get(node), new HashSet<String>(mutated.get(node)));
		}
		
		return ret;
	}
	
	public PointsToGraph getPointsToGraph() {
		return ptg;
	}
	
	public MultiMap<Node, String> getMutatedFields() {
		return mutated;
	}

	public Set<Node> getParameterNodes() {
		return ptg.getParameterNodes();
	}

	public void setPointsToGraph(PointsToGraph pointsToGraph) {
		this.ptg = pointsToGraph;
	}

	public void mutateField(Node node, Set<String> fields) {
		mutated.putAll(node, fields);
	}

	public Set<Local> localsPointing(Node target) {
		return ptg.getLocalsPointingTo(target);
	}

	public void setLocalsPointingTo(ContainerNode node, Set<Local> localPointingToNode) {
		ptg.setLocalsPointingTo(node, localPointingToNode);		
	}
	
	public int getMaxLive()
	{
		return this.maxLive;
	}
	
	public int getEscape()
	{
		return this.escape;
	}
	
	public void setMaxLive(int maxLive)
	{
		this.maxLive = maxLive;
	}
	
	public void setEscape(int escape)
	{
		this.escape = escape;
	}
	
}
