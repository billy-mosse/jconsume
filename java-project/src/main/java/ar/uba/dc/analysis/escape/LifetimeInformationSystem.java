package ar.uba.dc.analysis.escape;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.MethodDecorator;
import ar.uba.dc.analysis.memory.CallAnalyzer;
import ar.uba.dc.analysis.memory.CountingTheory;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummaryFactory;
import soot.SootMethod;


/**
 * Para ser llamada desde InterproceduralAnalysis
 * @author billy
 *
 */
public class LifetimeInformationSystem {
	
	protected Map<SootMethod, MemorySummary> data; 			// SootMethod -> summary
	
	
	//protected MethodDecorator methodDecorator;
	
	
	protected LifeTimeOracle lifeTimeOracle;
	
	protected CallStatement method;
	
	public void init(LifeTimeOracle lifeTimeOracle, Map<SootMethod, MemorySummary> data)
	{
		this.data = data;
		this.lifeTimeOracle = lifeTimeOracle;
		//this.method = m;
	}
	
	public Set<HeapPartition> getSubHeapDescriptors()
	{
		MemorySummary calleeSummary = data.get(method);
		return calleeSummary.getResidualPartitions();
	}
	

	
	
	/*, MethodDecorator methodDecorator, */
	private List<HeapPartition> getSubHeapDescriptorsEscapingFromCalleePartitionsThroughCallerPartitionsWithCalleeSummary(List<HeapPartition> caller_partitions, CallStatement caller, MemorySummary calleeSummary)
	{
		//MethodBody abstraction = methodDecorator.decorate(callee);
		List<HeapPartition> callee_partitions = new LinkedList<HeapPartition>();
		
		
		for (HeapPartition calleePartition : calleeSummary.getResidualPartitions()) {
			// Calculamos el vinculo entre caller y callee. 
			HeapPartition callerPartition = lifeTimeOracle.bind(calleePartition, caller);
			// El oraculo puede decidir que una particion no se vincule entre caller y callee (se pierda). Esto 
			// depende del analisis de tiempo de vida auxiliar que utilizemos. Por ejemplo, para el escape, las particiones
			// que representan parametros no se vinculan entre caller y callee.
			if (callerPartition != null) {
				
				// For each partition, get if exists the one from the callee that escapes 
				if (caller_partitions.contains(callerPartition))
				{
					callee_partitions.add(calleePartition);
				}
			}
		}	
		return callee_partitions;
		//TODO or not TODO? Assert partitions are from caller
		//Rta: NOT TO DO
		
	}
	
	
	public List<HeapPartition> getSubHeapDescriptorsEscapingFromCalleePartitionsThroughCallerPartitions(List<HeapPartition> caller_partitions, CallStatement caller, CallStatement callee)
	{
		//TODO: ver que pasa cuando no es tan simple (i.e. cuando hay polimorfismo)
		MemorySummary calleeSummary = data.get(callee);
		return getSubHeapDescriptorsEscapingFromCalleePartitionsThroughCallerPartitionsWithCalleeSummary(caller_partitions, caller, calleeSummary);
	}
	
	
	/*public void getSubHeapDescriptors()
	{
		
	}
	
	public void getSubHeapDescriptors()
	{
		
	}*/
	
	
	
}
