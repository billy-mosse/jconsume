package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.WeakHashMap;

import ar.uba.dc.tools.instrumentation.resource.tracker.madeja.RuntimeLifeTimeOracleFactory;


/**
 * @author martin
 *
 */
public class ResourceTrackerListener implements RuntimeListener {

	public static final String CLASS_INITIALIZER_PATTERN = "<clinit>";
	
	private static final String MAIN_SIGNATURE = "void main(java.lang.String[])";
	
	private boolean debug = false;
	
	private boolean allExecutions = true;
	
	private boolean assertStackTrace = false;
	
	private int trackMethodLimit = Integer.MAX_VALUE;
	
	private boolean printStat = true;
	
	private String fromMethod;
	
	private Set<String> methods = new HashSet<String>();
	
	private Set<String> excludeMethods = new HashSet<String>();
	
	private Set<String> excludeClasses = new HashSet<String>();
	
	private Set<String> classes = new HashSet<String>();
	
	private Set<String> classPrefixes = new HashSet<String>();
	
	private Stack<StackFrame> stackTrace;
	
	private StackFrame clinit;
	
	private StackFrame lastAnalyzableFrame = null;
	
	private Stack<String> callStmts;
	
	private Map<String, Set<StackFrame>> executions ;
	
	private RuntimeLifeTimeOracle lifeTimeOracle;

	private WeakHashMap<Object, Integer> allocations = new WeakHashMap<Object, Integer>();
	
	private boolean trackArraySizes = false;
	
	private boolean trackPreReachableObjects = false;
//	private StackFrame appPeak;

	private boolean trackStatics = false;

	private boolean forceCountAfterConstructor = false;
	
	private boolean trackResiduals = false;
	
	
	public ResourceTrackerListener() {
		super();
		this.lifeTimeOracle = (new RuntimeLifeTimeOracleFactory()).getLifeTimeOracle();
		this.executions = new LinkedHashMap<String, Set<StackFrame>>();
		this.callStmts =  new Stack<String>();
		this.stackTrace = new Stack<StackFrame>();
		this.clinit = new CLINITFrame();
		
		Properties props = new Properties();
		
		try {
			String trackerPropFile = System.getProperty("tracker.properties","tracker.properties");
			InputStream source = ClassLoader.getSystemClassLoader().getResourceAsStream(trackerPropFile);
		
			if(source == null)
				source = ClassLoader.getSystemClassLoader().getResourceAsStream("default.tracker.properties");
			
			if (source != null) {
				props.load(source);
			}
			
		} catch (IOException e) {
			// Si no se puede cargar, no pasa nada. A lo sumo tendra la configuracion default
		}
		
		this.debug = Boolean.valueOf(props.getProperty("tracker.log.mode.debug.enabled", "false"));
		this.allExecutions= Boolean.valueOf(props.getProperty("tracker.track.executions.all", "true"));
		this.assertStackTrace = Boolean.valueOf(props.getProperty("tracker.assert.stackTrace", "true"));
		this.printStat = Boolean.valueOf(props.getProperty("tracker.statistic.print", "true"));
		
		this.trackMethodLimit = Integer.valueOf(props.getProperty("tracker.track.methods.limit", "10000"));
		
		String methods = props.getProperty("tracker.track.methods", null);
		if(methods != null && !methods.isEmpty()) {
			String[] methodsToBeTracked = methods.split(";");
			for (String signature : methodsToBeTracked) {
				this.methods.add(signature);
			}
		}
		
		methods = props.getProperty("tracker.track.methods.exclude", null);
		if(methods != null && !methods.isEmpty()) {
			String[] methodsToBeExcluded = methods.split(";");
			for (String signature : methodsToBeExcluded) {
				this.excludeMethods.add(signature);
			}
		}
		
		String classes = props.getProperty("tracker.track.classes", null);
		if(classes != null && !classes.isEmpty()) {
			String[] classesToBeTracked = classes.split(";");
			for (String signature : classesToBeTracked) {
				this.classes.add(signature);
			}
		}
		
		classes = props.getProperty("tracker.track.classes.exclude", null);
		if(classes != null && !classes.isEmpty()) {
			String[] classesToBeExcluded = classes.split(";");
			for (String signature : classesToBeExcluded) {
				this.excludeClasses.add(signature);
			}
		}
		
		String prefixes = props.getProperty("tracker.track.class.prefix", null);
		if(prefixes != null && !prefixes.isEmpty()) {
			String[] classesToBeTracked = prefixes.split(";");
			for (String signature : classesToBeTracked) {
				this.classPrefixes.add(signature);
			}
		}
		
		this.fromMethod = props.getProperty("tracker.track.from.method", null);
		
		
	}
	
	public int getAppHeapSize() {
		int size = 0;
		for (Object key : this.allocations.keySet()) {
			size += this.allocations.get(key);
		}
		return size;
	}
	public void newObjectRegistrationEvent(String newStmt, String partition) {
		this.lifeTimeOracle.registerPartition(newStmt, partition);
	}
	
	private int countFreshReachable(StackFrame frame) {
		return HeapTracker.countReachableObjects(frame.getFromObject(), frame.getToObject());
	}
	
	public void enter(String methodSignature) {
		this.debug("enter: " + methodSignature);
		
		
		if(this.fromMethod != null && this.fromMethod.equals(methodSignature))
			this.allExecutions = true;
		
		if(methodSignature.contains(CLASS_INITIALIZER_PATTERN)) {
			//Agregamos un callStmt explicitament
			this.callStmts.push("Invoke@CLINIT" + methodSignature);
		}
		
		StackFrame methodExecution = new StackFrame(methodSignature);
		methodExecution.setFromObject(HeapTracker.currentSeq());
		
		if(trackPreReachableObjects)
			methodExecution.setReachableObjectsPre(HeapTracker.countReachableObjects());

		
		
		this.stackTrace.push(methodExecution);
	}
	
	public void exit(String methodSignature) {
		
		this.debug("exit:" + methodSignature);
		
		StackFrame frame = this.stackTrace.pop();
		frame.setToObject(HeapTracker.currentSeq());
		
		
		//FIXMEframe.updatePeak(this.countFreshReachable(frame));
		
		this.debug(frame.toString());
		
		if(this.track(methodSignature)) {
			this.debug(frame.toString());
			this.register(frame);
		}
		
		if(methodSignature.contains(CLASS_INITIALIZER_PATTERN)) {
			//Sacamos el callStmt de la pila
			String stmt = this.callStmts.pop();
			if(this.assertStackTrace)
				assert(stmt.equals("Invoke@CLINIT" + methodSignature));
			
			
			
			if(this.assertStackTrace)
				assert(frame.getMethod().equals(methodSignature));
			
			//Hacemos el merge con el caller
			if(!this.stackTrace.isEmpty())
				//this.merge(stmt, this.stackTrace.peek(), frame);
				this.merge(stmt, this.clinit, frame); //Mantenemos todas las inicializaciones de clases aca
			else //Si no hay un caller solo puede ser un clinit
				assert(stmt.equals("Invoke@CLINIT" + methodSignature));
			
		} else if(methodSignature.contains(MAIN_SIGNATURE)) {
			
			if(this.assertStackTrace) {
				assert(this.callStmts.isEmpty());
				assert(this.stackTrace.isEmpty());
			}
			
		} else {
			this.lastAnalyzableFrame = frame;
		}
		
		if(this.fromMethod != null && this.fromMethod.equals(methodSignature))
			this.allExecutions = false;
		
		
	//	System.out.println("Peak: " + this.allocations.size());
	}
	
	public void exit() {
		
		if(this.printStat) {
			this.info("Printing estatistics");
			this.info("====================");
			for (String method : this.executions.keySet()) {
				for(StackFrame frame: this.executions.get(method)) {
					//if(frame.hasAllocations())
						this.info(frame.toString());
				}
					
			}
			this.info(this.clinit.toString());
			System.out.println("HeapTracker.count: " + HeapTracker.times + " times");
		}
	}
	
	
	private void updatePeak(boolean recursive) {
		//StackFrame frame = this.stackTrace.peek();
		//frame.updatePeak(this.countFreshReachable(frame));
		
		
		for (StackFrame frame : this.stackTrace) {
			if(frame.getMethod().equals("<javazoom.jl.player.jlp: void play()>"))
				frame.updatePeak(this.countFreshReachable(frame));
		}
	}
	
	public void newObjectEvent(Object object, String newStmt) {
		String partId = this.lifeTimeOracle.getPartition(newStmt);
		StackFrame frame = this.stackTrace.peek();
		frame.allocateObject(partId);
		//this.allocations.put(object, new Integer(1));
		
		if(this.trackStatics || !staticEnvironment() )
			HeapTracker.track(object);
		
		updatePeak(true);

	}
	

	private boolean staticEnvironment() {
		for (StackFrame stackFrame : this.stackTrace) {
			if(stackFrame.getMethod().contains(CLASS_INITIALIZER_PATTERN))
				return true;
		}
		return false;
	}

	public void newArrayEvent(Object object, String newStmt, int size) {
		String partId = this.lifeTimeOracle.getPartition(newStmt);
		int arrayLength = this.trackArraySizes ? size : 1;
		
		if(this.trackStatics || !staticEnvironment() )
			HeapTracker.track(object, arrayLength);
		this.newArray(partId, arrayLength);
		//this.allocations.put(object, );
		
		
	//	System.out.println("Reachable objects " + HeapTracker.countReachableObjects());
	}
	
	public void newArrayEvent(Object object, String newStmt, int firstDimension,int secondDimension) {
		String partId = this.lifeTimeOracle.getPartition(newStmt);
		int arrayLength = this.trackArraySizes ? firstDimension  * secondDimension : 1;
		
		if(this.trackStatics || !staticEnvironment() )
			HeapTracker.track(object, arrayLength);
		this.newArray(partId, arrayLength);
	//	this.allocations.put(object, this.trackArraySizes ? firstDimension  * secondDimension : 1);
		
	}
	
	public void newArrayEvent(Object object, String newStmt, int firstDimension,int secondDimension, int thirdDimension) {
		String partId = this.lifeTimeOracle.getPartition(newStmt);
		int arrayLength = this.trackArraySizes ? firstDimension  * secondDimension * thirdDimension : 1;
		
		if(this.trackStatics || !staticEnvironment() )
			HeapTracker.track(object, arrayLength);
		this.newArray(partId, arrayLength);	
		//this.allocations.put(object, this.trackArraySizes ? firstDimension  * secondDimension * thirdDimension : 1);
		
	}
	
	public void callEvent(String callStmt) {
		this.debug("Call --> " + callStmt );
		this.callStmts.push(callStmt);
	}
	
	public void endCallEvent(String callStmt) {
		this.debug("End call --> " + callStmt );
		this.callStmts.pop();
		StackFrame frame = this.popFrame();

		
		if(this.assertStackTrace)
			assert(this.isValidCall(callStmt, frame));
		
		
		if(callStmt.contains("<init>") && this.forceCountAfterConstructor ) {
			StackFrame caller = this.stackTrace.peek();
			int reachableAfterInit = HeapTracker.countReachableObjects(caller.getFromObject(), caller.getToObject());
			caller.updatePeak(reachableAfterInit);
		}
		
		//Analyzable
		if(frame != null) {
			//Residuales
			if(this.trackResiduals) {
				int reachable = HeapTracker.countReachableObjects(frame.getFromObject(), frame.getToObject());			
				frame.updatePeak(reachable);
				frame.setReachableObjectsPost(reachable);
			}
			
			this.merge(callStmt, this.stackTrace.peek(), frame);
		
		} else {
			this.debug(callStmt + " not analyzable");
		}
		
	}	
	
	private boolean isValidCall(String callStmt, StackFrame frame) {
		//FIXME validar el call
		return true;
	}
	
	private StackFrame popFrame() {
		StackFrame frame = this.lastAnalyzableFrame;
		this.lastAnalyzableFrame = null;
		return frame;
	}
	
	private void newArray(String partId, int firstDimension) {
		//this.debug("New array [ " + firstDimension +" ] --> " + partId );
		StackFrame methodExecution = this.stackTrace.peek();
		methodExecution.allocateArray(partId, firstDimension);
		//methodExecution.updatePeak(this.countFreshReachable(methodExecution));
		updatePeak(true);
	}

	private void debug(String message) {
		if(this.debug)
			System.out.println(message);
	}
	
	private void info(String message) {
		System.out.println(message);
	}

	
	
	private void merge(String callStmt, StackFrame caller, StackFrame callee) {
		this.debug("Merging stacks frames:" + caller.getMethod() + "<--" + callee.getMethod());
		caller.updateMaxCall(callee.getTemporal());
		Map<String, Integer> heap = callee.memoryDump();
		for (String calleePartition : heap.keySet()) {
			if(!calleePartition.equals(MemoryHeap.TEMPORAL_PARTITION_NAME)) {
				String binding = this.lifeTimeOracle.bindPartition(callStmt, calleePartition);
				if(binding == null)
					throw new RuntimeException("Unable to bind partition " + calleePartition);
				if(caller == this.clinit && binding.equals(MemoryHeap.GLOBAL_STATIC_PARTITION_NAME))
					binding =  callee.getMethod();
				
				caller.allocate(binding, heap.get(calleePartition));
			}
		}
	}
	
	private void register(StackFrame frame) {
		if(this.track(frame.getMethod())) {
			Set<StackFrame> set;
			if(this.executions.containsKey(frame.getMethod())) {
				set = this.executions.get(frame.getMethod());
			} else {
				set = new HashSet<StackFrame>();
				this.executions.put(frame.getMethod(),set);
			}
			
			if(set.size() < this.trackMethodLimit)
				set.add(frame);
		}
	}
	
	public boolean track(String methodSignature) {
		
		if(this.excludeMethods.contains(methodSignature))
			return false;
		
		String declaringClass = methodSignature.substring(1, methodSignature.indexOf(":"));
		if(this.excludeClasses.contains(declaringClass))
			return false;
		
		if(this.allExecutions)
			return true;
		if(this.methods.contains(methodSignature) )
			return true;
		
		if(this.classes.contains(declaringClass))
			return true;
		
		for (String prefix: this.classPrefixes) {
			if(declaringClass.startsWith(prefix))
				return true;
		}
		
		return false;
	}

	@Override
	public void caughtException(String methodSignature) {
		StackFrame frame = this.stackTrace.peek();
		
		//La exception fue lanzada y capturada en otro metodo
		if(!frame.getMethod().equals(methodSignature)) {
			do {
				frame = this.stackTrace.pop();
				String callStmt = this.callStmts.pop();
				this.merge(callStmt, this.stackTrace.peek(), frame);
				this.register(frame);
				this.debug(frame.toString());
			} while(this.stackTrace.peek().getMethod() != methodSignature);
		}
			
	}

	public class Peak {
		
		int peak;
		
		String stackTrace;

		public Peak(int peak, String stackTrace) {
			super();
			this.peak = peak;
			this.stackTrace = stackTrace;
		}

		public int getPeak() {
			return peak;
		}

		public void setPeak(int peak) {
			this.peak = peak;
		}

		public String getStackTrace() {
			return stackTrace;
		}

		public void setStackTrace(String stackTrace) {
			this.stackTrace = stackTrace;
		}
	
		
	
	}
}
