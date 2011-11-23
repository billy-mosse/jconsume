package ar.uba.dc.tools.instrumentation.resource.tracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

@Deprecated
public class ResourceTrackerListener implements Logger {

	private static final String STATIC_CONSTRUCTOR_SIGNATURE = "void <clinit>()";
	private static final String MAIN_METHOD_SIGNATURE = "void main(java.lang.String[])";

	private static boolean DEBUG = true;
	
	private static boolean LOGGER_ENABLED = true;
	
	private static ResourceTrackerListener instance = new ResourceTrackerListener();
	
	private Stack<StackFrame> frames = new Stack<StackFrame>();
	
	private MemoryHeap memory = new MemoryHeap();

	// Informacion de contexto de las llamadas
	private Stack<CallContext> callingContext = new Stack<CallContext>();
	
	private Set<String> trackedMethods = new TreeSet<String>();
	
	public ResourceTrackerListener() {
		Properties props = new Properties();
		
		try {
			InputStream source = this.getClass().getResourceAsStream("tracker.properties");
			if (source != null) {
				props.load(source);
			}
		} catch (IOException e) {
			// Si no se puede cargar, no pasa nada. A lo sumo tendra la configuracion default
		}
		
		LOGGER_ENABLED = Boolean.valueOf(props.getProperty("tracker.log.enabled", "true"));
		DEBUG = Boolean.valueOf(props.getProperty("tracker.log.mode.debug.enabled", "false"));
		int i = 0;
		String trackMethodPrefix = "tracker.track.method."; 
		while (props.getProperty(trackMethodPrefix + i) != null) {
			trackedMethods.add(props.getProperty(trackMethodPrefix + i));
			i++;
		}
	}
	
	/**
	 * Entrada a un metodo
	 * @param temporalPartitions 
	 * @param residualPartitions 
	 */
	public void enterMethod(String methodName, String temporalPartitions, String residualPartitions) {
		checkLogEnabled(methodName);
				
		boolean isMainMethod = isMainMethod(methodName) || trackedMethods.size() > 0;
		
		if (isMainMethod) {
			info("enter: " + methodName);
		} else {
			debug("enter: " + methodName);
		}
		
		Boolean captureAll = false;
		Map<Integer, Integer> aliases = new TreeMap<Integer, Integer>();
		if (!callingContext.isEmpty()) {
			aliases = callingContext.peek().getResidualAliases();
			captureAll = callingContext.peek().getCaptureAll();
		}
		
		Set<Integer> residual = decodePartitions(residualPartitions);
		Set<Integer> temporal = decodePartitions(temporalPartitions);
		
		//debug("MEMORY BEFORE: " + memory.dump());
		frames.push(new StackFrame(methodName, memory, temporal, residual, aliases, this, isMainMethod, captureAll));
		frames.peek().create();
	}

	private void checkLogEnabled(String methodName) {
		if (trackedMethods.size() > 0) {
			LOGGER_ENABLED = trackedMethods.contains(methodName);
		}
	}
	
	private boolean isMainMethod(String methodName) {
		methodName = methodName.substring(methodName.indexOf(':') + 1, methodName.length() -1).trim();
		return methodName.equals(MAIN_METHOD_SIGNATURE);
	}
	
	private boolean isStaticConstructor(String methodName) {
		methodName = methodName.substring(methodName.indexOf(':') + 1, methodName.length() -1).trim();
		return methodName.equals(STATIC_CONSTRUCTOR_SIGNATURE);
	}

	/**
	 * Salida de un metodo
	 */
	public void exitMethod(String methodName) {		
		//debug("MEMORY PRE-AFTER: " + memory.dump());
		StackFrame finishedMethod = frames.peek();
		
		// La salida del main puede producirse por una invocacion al metodo system.exit. Cubrimos este caso analizando
		// si el frame que estabamos ejecutando es el main
		if (finishedMethod.isMainFrame()) {
			checkLogEnabled(finishedMethod.getExecutingMethod()); 
		} else {
			checkLogEnabled(methodName);
		}
		
		Integer temporal = finishedMethod.destroy();
		frames.pop();
		
		if (!frames.isEmpty()) {
			frames.peek().updateMaxCall(temporal);
		}
		
		//System.out.println("PRE-POP: " + residualAliases.size());
		if (!callingContext.isEmpty() && !isStaticConstructor(methodName)) {
			callingContext.pop();
		
		}
		//System.out.println("POST-POP: " + residualAliases.size());
		
		//debug("MEMORY AFTER: " + memory.dump());
		if (finishedMethod.isMainFrame()) {
			info("exit: " + finishedMethod.getExecutingMethod());
		} else {
			debug("exit: " + methodName);
		}
		//LOGGER_ENABLED = false;
	}
	
	public void exitProgram(String methodName) {
		LOGGER_ENABLED = true;
		if (!frames.isEmpty()) {
			this.info("Exit forced by " + methodName);
			while (!frames.isEmpty()) {
				exitMethod(frames.peek().getExecutingMethod());
			}
		}
		
		this.info("exit program! Residual memory [" + memory.size() + "]");
		this.info("Residual dump: " + memory.dump());
	}

	public void newObject(String creationSite, int partition) {
		//debug("new object: " + creationSite + " - " + partition);
		frames.peek().allocateObject(partition);
	}
	
	/**
	 * Creacion de un arreglo. Puede ser multidimencional
	 */
	public void newArray(String creationSite, int partition, int size) {
		//debug("new array: " + creationSite + " - " + partition + " - " + size);
		frames.peek().allocateArray(partition, size);
	}
	
	/**
	 * Creacion de un arreglo. Puede ser multidimencional
	 */
	public void newArray(String creationSite, int partition, int sizeFirstDimension, int sizeSecondDimension) {
		//debug("new array: " + creationSite + " - " + partition + " - " + size);
		frames.peek().allocateArray(partition, sizeFirstDimension * sizeSecondDimension);
	}
	
	public void newArray(String creationSite, int partition, int sizeFirstDimension, int sizeSecondDimension, int sizeThirdDimension) {
		//debug("new array: " + creationSite + " - " + partition + " - " + size);
		frames.peek().allocateArray(partition, sizeFirstDimension * sizeSecondDimension * sizeThirdDimension);
	}
	
	private void analizableCall(String methodName, String partitionMapping, int captureAll) {
		//debug("call: " + partitionMapping);
		Map<Integer, Integer> mapping = new TreeMap<Integer, Integer>();
		
		if (!callingContext.isEmpty()) {
			mapping.putAll(callingContext.peek().getResidualAliases());
		}
		
		extendMapping(mapping, partitionMapping);
		//System.out.println("PRE-PUSH: " + residualAliases.size());
		callingContext.push(new CallContext(mapping, captureAll == 1));
		//System.out.println("POST-PUSH: " + residualAliases.size());
	}

	private void extendMapping(Map<Integer, Integer> mapping, String partitionMapping) {
		Map<Integer, Integer> newMapping = decodeMapping(partitionMapping); 
		for (Integer hpCallee : newMapping.keySet()) {
			Integer hpCaller = mapping.get(newMapping.get(hpCallee));
			
			if (hpCaller == null) {
				hpCaller = newMapping.get(hpCallee);
			}
			
			mapping.put(hpCallee, hpCaller);
		}
	}
	
	public void unanalizableCall(String methodName, String partitionMapping, int partitionToAssignResidual, int temporal, int residual, int captureAll) {
		Map<Integer, Integer> mapping = new TreeMap<Integer, Integer>();
		
		if (!callingContext.isEmpty()) {
			mapping.putAll(callingContext.peek().getResidualAliases());
		}
		
		extendMapping(mapping, partitionMapping);
		
		frames.peek().updateMaxCall(temporal);
		if (partitionToAssignResidual != -1) {
			frames.peek().updateResidual(mapping, partitionToAssignResidual, residual);
		}
	}
	
	private void caughtException(String caughtInMethod) {
		debug("exception caught in " + caughtInMethod + ". Active execution was [" + frames.peek().getExecutingMethod() + "]");
		//LOGGER_ENABLED = false;
		while (!frames.peek().getExecutingMethod().equals(caughtInMethod)) {
			exitMethod(frames.peek().getExecutingMethod());
		}
	}
	
	/**
	 * Entrada a un metodo
	 * 
	 * * @param requiredPartitions - Ids separados por "," de las particiones necesaria el analisis
	 */
	public static void enter(String methodName, String temporalPartitions, String residualPartitions) {
		instance.enterMethod(methodName, temporalPartitions, residualPartitions);
	}
	
	/**
	 * Salida de un metodo
	 */
	public static void exit(String methodName) {
		instance.exitMethod(methodName);
	}
	
	/**
	 * Salida del programa
	 */
	public static void exitProgramEvent(String methodName) {
		instance.exitProgram(methodName);
	}

	/**
	 * Creacion de un objeto
	 */
	public static void newObjectEvent(String creationSite, int partition) {
		instance.newObject(creationSite, partition);
	}
	
	/**
	 * Creacion de un arreglo. Puede ser multidimencional
	 */
	public static void newArrayEvent(String creationSite, int partition, int size) {
		instance.newArray(creationSite, partition, size);
	}
	
	/**
	 * Creacion de un arreglo. Puede ser multidimencional
	 */
	public static void newArrayEvent(String creationSite, int partition, int sizeFirstDimension, int sizeSecondDimension) {
		instance.newArray(creationSite, partition, sizeFirstDimension, sizeSecondDimension);
	}
	
	public static void newArrayEvent(String creationSite, int partition, int sizeFirstDimension, int sizeSecondDimension, int sizeThirdDimension) {
		instance.newArray(creationSite, partition, sizeFirstDimension, sizeSecondDimension, sizeThirdDimension);
	}
	
	public static void caughtExceptionEvent(String caughtInMethod) {
		instance.caughtException(caughtInMethod);
	}

	public static void analizableCallEvent(String methodName, String partitionMapping, int captureAll) {
		instance.analizableCall(methodName, partitionMapping, captureAll);
	}
	
	public static void unanalizableCallEvent(String methodName, String partitionMapping, int partitionToAssignResidual, int temporal, int residual, int captureAll) {
		instance.unanalizableCall(methodName, partitionMapping, partitionToAssignResidual, temporal, residual, captureAll);
	}
	
	public void debug(String message) {
		if(DEBUG)
			print(message);
	}
	
	public void info(String message) {
		print(message);
	}
	
	protected void print(String message) {
		if (LOGGER_ENABLED) {
			System.out.println(repeat(" ", frames.size() * 2) + message);
		}
	}

	protected String repeat(String value, int times) {
		String result = "";
		
		for (int i = 0; i < times; i++) {
			result += value;
		}
		
		return result;
	}
	
	protected Set<Integer> decodePartitions(String partitions) {
		Set<Integer> ret = new TreeSet<Integer>();

		if (partitions.length() > 0) {
			for (String hp : partitions.split(",")) {
				ret.add(Integer.valueOf(hp, 10));
			}
		}
		
		return ret;
	}
	
	private Map<Integer, Integer> decodeMapping(String partitionMapping) {
		Map<Integer, Integer> ret = new TreeMap<Integer, Integer>();
		
		if (partitionMapping.length() > 0) {
			for (String mapping : partitionMapping.split(",")) {
				String[] parts = mapping.split("->", 2);
				ret.put(Integer.valueOf(parts[0], 10), Integer.valueOf(parts[1], 10));
			}
		}
		
		return ret;
	}
	
	private class CallContext {
		// [callee hp -> caller hp]
		private Map<Integer, Integer> residualAliases = new TreeMap<Integer, Integer>();
		// Sin importar lo que decia el EA, debemos considerar todo como temporal?
		private Boolean captureAll = false;
		
		public CallContext(Map<Integer, Integer> residualAliases, Boolean captureAll) {
			this.residualAliases = residualAliases;
			this.captureAll = captureAll;
		}

		public Map<Integer, Integer> getResidualAliases() {
			return residualAliases;
		}

		public Boolean getCaptureAll() {
			return captureAll;
		}
	}
}
