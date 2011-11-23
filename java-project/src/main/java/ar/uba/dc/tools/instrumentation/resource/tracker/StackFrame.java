package ar.uba.dc.tools.instrumentation.resource.tracker;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Deprecated
public class StackFrame {

	private Logger logger;
	private MemoryHeap memory;
	private Map<Integer, Integer> alias;
	
	private Integer maxCall;
	
	private Set<Integer> temporal;
	private Set<Integer> residual;
	
	private MemoryHeap creationSnapshot;
	
	private boolean mainFrame;
	
	private String executingMethod;
	
	private boolean captureAll;

	public StackFrame(String executingMethod, MemoryHeap memory, Set<Integer> temporal, Set<Integer> residual, Map<Integer, Integer> residualAliases, Logger logger, boolean mainFrame, boolean captureAll) {
		this.executingMethod = executingMethod;
		this.logger = logger;
		this.memory = memory;
		this.alias = residualAliases;
		this.maxCall = 0;
		this.temporal = temporal;
		this.residual = residual;
		this.mainFrame = mainFrame;
		this.captureAll = captureAll;
		
		for (Integer hp : residual) {
			if (!alias.containsKey(hp)) {
				alias.put(hp, hp);
				memory.allocate(hp, 0);
			}
		}
	}

	/**
	 * Creo las particiones temporales que voy a usar
	 * @param residualAliases 
	 */
	public void create() {
		for (Integer hp : temporal) {
			memory.allocate(hp, 0);
		}
		creationSnapshot = memory.clone();
	}
	
	public void allocateObject(int partition) {
		allocate(partition, 1);
	}

	public void allocateArray(int partition, int size) {
		allocate(partition, size);
	}
	
	private void allocate(int partition, int size) {
		allocate(alias, partition, size);
	}
	
	private void allocate(Map<Integer, Integer> mapping, int partition, int size) {
		if (temporal.contains(partition)) {
			memory.allocate(partition, size);
		} else {
			Integer residualPartition = mapping.get(partition);
			memory.allocate(residualPartition, size);
		}
	}
	
	/**
	 * Libero la memoria reservada
	 */
	public Integer destroy() {
		Integer releasedMemory = 0; 
		for (Integer hp : temporal) {
			releasedMemory += memory.release(hp);
		}
		
		if (captureAll) {
			// Convertimos todo el residual generado por el metodo en temporal.
			Set<Integer> alreadyProcessed = new TreeSet<Integer>();
			for (Integer value : residual) {
				Integer hpAlias = alias.get(value);
				if (alreadyProcessed.add(hpAlias)) {
					Integer residualGenerated = (memory.size(hpAlias) - creationSnapshot.size(hpAlias)); 
					releasedMemory += residualGenerated;
					memory.release(hpAlias, residualGenerated);
				}
			}
		}
		
		// BugFix: Cuando el residual de un constructor estatico tiene un mapping es porque
		//         el residual es redirigido a otra particion. En JLayer no se estaba limpiando las particiones originales
		//         haciendo que el main tenga mas residual del que en realidad tenia.
		if (executingMethod.contains("void <clinit>()")) {
			for (Integer value : residual) {
				Integer hpAlias = alias.get(value);
				if (hpAlias != null) {
					Integer actualSize = memory.size(value);
					if (actualSize != null) {
						memory.release(value, actualSize);
					}
				}
			}
		}
		
		if (mainFrame) {
			logger.info("Residual [" + countMemory(residual) + "] - Temporal [" + (releasedMemory + maxCall) + "]");
			//logger.debug("Residual dump: " + residual);
			logger.info("Temporal dump: " + releasedMemory);
			logger.info("Maxcall dump: " + maxCall);
			//logger.debug("Used aliases dump: " + alias);
		} else {
			logger.debug("Residual [" + countMemory(residual) + "] - Temporal [" + (releasedMemory + maxCall) + "]");
			//logger.debug("Residual dump: " + residual);
			logger.debug("Temporal dump: " + releasedMemory);
			logger.debug("Maxcall dump: " + maxCall);
			//logger.debug("Used aliases dump: " + alias);
		}
		
		return releasedMemory + maxCall;
	}

	private Integer countMemory(Set<Integer> memoryStatistics) {
		Integer result = 0;
		
		Set<Integer> alreadyProcessed = new TreeSet<Integer>();
		for (Integer value : memoryStatistics) {
			Integer hpAlias = alias.get(value);
			if (alreadyProcessed.add(hpAlias)) {
				result += (memory.size(hpAlias) - creationSnapshot.size(hpAlias));
			}
		}
		
		return result;
	}

	public void updateMaxCall(Integer value) {
		if (this.maxCall < value) {
			this.maxCall = value; 
		}
	}

	public void updateResidual(Map<Integer, Integer> mapping, int partition, int size) {
		allocate(mapping, partition, size);
	}

	public boolean isMainFrame() {
		return mainFrame;
	}

	public String getExecutingMethod() {
		return executingMethod;
	}

	public void updateTemporal(int residual2) {
		
	}

}
