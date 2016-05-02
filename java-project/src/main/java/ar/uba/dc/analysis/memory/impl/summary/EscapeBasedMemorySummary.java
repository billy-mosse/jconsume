package ar.uba.dc.analysis.memory.impl.summary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import soot.SootMethod;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.util.collections.graph.DirectedGraph;

public class EscapeBasedMemorySummary implements MemorySummary {

	protected SootMethod target;
	
	protected Set<String> parameters;
	protected DirectedGraph<PointsToHeapPartition, PointsToHeapPartitionEdge> graph; // heap representation
	protected Set<PointsToHeapPartition> ret; 						// return -> partition
	protected Set<PointsToHeapPartition> globEscape; 				// partition escaping globally
	protected ParametricExpression temporal;						// temporal
	protected ParametricExpression memoryRequirement;						// memoryRequirement (maxLive)
	protected Map<HeapPartition, ParametricExpression> residuals; 	// heapPartition -> residual
	
	private boolean doCheck = true;
	
	public EscapeBasedMemorySummary(SootMethod target, Set<String> parameters, ParametricExpression initialTemporal, ParametricExpression memoryRequirement) {
		this.target = target;
		this.parameters = parameters;
		this.temporal = initialTemporal;
		this.memoryRequirement = memoryRequirement;
		this.graph = new DirectedGraph<PointsToHeapPartition, PointsToHeapPartitionEdge>();
		this.ret = new HashSet<PointsToHeapPartition>();
		this.globEscape = new HashSet<PointsToHeapPartition>();
		this.residuals = new HashMap<HeapPartition, ParametricExpression>();
	}
	
	@Override
	public int hashCode() {
		return target.hashCode() 
				+ parameters.hashCode()
				+ graph.hashCode() 
				+ ret.hashCode()
				+ globEscape.hashCode()
				+ temporal.hashCode()
				+ residuals.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (!(o instanceof EscapeBasedMemorySummary))
			return false;
		EscapeBasedMemorySummary ms = (EscapeBasedMemorySummary) o;
		return target.equals(ms.target)
				&& parameters.equals(ms.parameters)
				&& graph.equals(ms.graph) 
				&& ret.equals(ms.ret)
				&& globEscape.equals(ms.globEscape)
				&& temporal.equals(ms.temporal)
				&& residuals.equals(ms.residuals);
	}

	public void add(PointsToHeapPartition heapPartition) {
		graph.add(heapPartition);
	}
	
	public void add(PointsToHeapPartition heapPartition, ParametricExpression residualValue) {
		graph.add(heapPartition);
		residuals.put(heapPartition, residualValue);
	}

	public void relate(PointsToHeapPartition source, String field, boolean inside, PointsToHeapPartition target) {
		graph.add(new PointsToHeapPartitionEdge(source, field, target, inside));
	}

	public void returnPartition(PointsToHeapPartition heapPartition) {
		ret.add(heapPartition);
	}

	public void partitionEscapeGlobaly(PointsToHeapPartition heapPartition) {
		globEscape.add(heapPartition);		
	}
	
	@Override
	public Set<String> getParameters() {
		return new TreeSet<String>(parameters);
	}

	@Override
	public ParametricExpression getResidual(HeapPartition aHeapPartition) {
		return residuals.get(aHeapPartition);
	}
	
	@Override
	public Set<HeapPartition> getResidualPartitions() {
		return new HashSet<HeapPartition>(residuals.keySet());
	}
	
	@Override
	public SootMethod getTarget() {
		return target;
	}

	/*@Override
	public ParametricExpression getTemporal() {
		return temporal;
	}*/

	@Override
	public ParametricExpression getMemoryRequirement() {
		return memoryRequirement;
	}

	@Override
	public void setResidual(HeapPartition aHeapPartition, ParametricExpression newValue) {
		if (doCheck && !graph.contains((PointsToHeapPartition) aHeapPartition)) {
			throw new RuntimeException("Intentamos asignar residual [" + newValue + "] a una particion que no existe en el grafo [" + aHeapPartition.toString() + "]");
		}
		residuals.put(aHeapPartition, newValue);
	}

	/*@Override
	public void setTemporal(ParametricExpression newValue) {
		this.temporal = newValue;
	}*/
	
	@Override
	public void setMemoryRequirement(ParametricExpression newValue) {
		this.memoryRequirement = newValue;
	}


	public Set<PointsToHeapPartition> getAllPartitions() {
		return graph.getNodes();
	}

	public boolean isEscapeGlobal(HeapPartition hp) {
		return globEscape.contains(hp);
	}

	public Set<PointsToHeapPartition> getReturnedPartitions() {
		return new HashSet<PointsToHeapPartition>(ret);
	}

	public Set<PointsToHeapPartition> getEdgesSources() {
		return graph.getEdgesSources();
	}

	public Set<PointsToHeapPartitionEdge> getEdgesOutOf(PointsToHeapPartition src) {
		return graph.getEdgesOutOf(src);
	}

	public Set<PointsToHeapPartition> getEscapeGlobalyPartitions() {
		return new HashSet<PointsToHeapPartition>(globEscape);
	}	
}
