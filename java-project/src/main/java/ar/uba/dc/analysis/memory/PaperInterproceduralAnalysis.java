package ar.uba.dc.analysis.memory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.LineWithParent;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.intermediate_representation.IRUtils;
//import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis.IntComparator;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.JsonIRReader;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.XMLReader;
import ar.uba.dc.analysis.common.method.information.Annotation;
import ar.uba.dc.analysis.common.method.information.AnnotationsContainer;
import ar.uba.dc.analysis.common.method.information.MethodAnnotationsHelper;
import ar.uba.dc.analysis.escape.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.callanalyzer.PaperCallAnalyzer;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionUtils;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionUtils;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.DomainSetUtils;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.util.location.MethodLocationStrategy;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;







public class PaperInterproceduralAnalysis {
	
	protected MethodLocationStrategy locationStrategy;
	
	private static Log log = LogFactory.getLog(PaperInterproceduralAnalysis.class);

	
	
	protected SummaryReader<IntermediateRepresentationMethod> jsonIRReader;
	private String mainClass;
	
	protected SummaryReader<AnnotationsContainer> jsonAnnotationsReader;
	
	public SummaryReader<AnnotationsContainer> getJsonAnnotationsReader() {
		return jsonAnnotationsReader;
	}

	public void setJsonAnnotationsReader(SummaryReader<AnnotationsContainer> jsonAnnotationsReader) {
		this.jsonAnnotationsReader = jsonAnnotationsReader;
	}
	
	

	protected List<String> marked_temporarily;
	protected List<String> marked_permanently;
	protected List<IntermediateRepresentationMethod> ordered_methods;
	
	//protected PaperIntraproceduralAnalysis analysis;
	
	
	protected Map<String, PaperMemorySummary>  data;
	
	//Lineas con invariante que hace que de infinito
	protected List<LineWithParent> badLines;
	protected List<LineWithParent> badLinesCalls;
	
	
	protected PaperMemorySummaryFactory summaryFactory;
	protected CountingTheory countingTheory;
	protected ParametricExpressionFactory expressionFactory;
	protected SymbolicCalculator symbolicCalculator;
	
	protected MethodAnnotationsHelper methodAnnotationsHelper;
	
	protected SummaryRepository<PaperMemorySummary, IntermediateRepresentationMethod> defaultSummaryRepository;
	protected SummaryRepository<PaperMemorySummary, IntermediateRepresentationMethod> annotationsSummaryRepository;

	
	protected PaperCallAnalyzer callAnalyzer;
	

	//protected Map<IntermediateRepresentationMethod, List<IntermediateRepresentationMethod> > preds;
	//protected Map<IntermediateRepresentationMethod, List<IntermediateRepresentationMethod> > succs;
	
	
	public PaperCallAnalyzer getCallAnalyzer() {
		return callAnalyzer;
	}

	public void setCallAnalyzer(PaperCallAnalyzer callAnalyzer) {
		this.callAnalyzer = callAnalyzer;
	}

	
	public SummaryRepository<PaperMemorySummary, IntermediateRepresentationMethod> getDefaultSummaryRepository() {
		return defaultSummaryRepository;
	}

	public void setDefaultSummaryRepository(SummaryRepository<PaperMemorySummary, IntermediateRepresentationMethod> defaultSummaryRepository) {
		this.defaultSummaryRepository = defaultSummaryRepository;
	}

	public PaperMemorySummaryFactory getSummaryFactory() {
		return summaryFactory;
	}

	public void setSummaryFactory(PaperMemorySummaryFactory summaryFactory) {
		this.summaryFactory = summaryFactory;
	}

	public CountingTheory getCountingTheory() {
		return countingTheory;
	}

	public void setCountingTheory(CountingTheory countingTheory) {
		this.countingTheory = countingTheory;
	}

	public ParametricExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}

	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public SymbolicCalculator getSymbolicCalculator() {
		return symbolicCalculator;
	}

	public void setSymbolicCalculator(SymbolicCalculator symbolicCalculator) {
		this.symbolicCalculator = symbolicCalculator;
	}

	public Set<String> getSuccesors(IntermediateRepresentationMethod ir_method)
	{
		Set<String> succesors = new HashSet<String>();
		for(Line line : ir_method.getBody().getLines())
		{
			for(Invocation inv : line.getInvocations())
			{
				succesors.add(IRUtils.key(inv, line.getIrName()));
			}
		}
		
		return succesors;
	}
	
	
	public class NotDAGException extends Exception {
		public NotDAGException(String message)
		{
			super(message);				
		}
		
	}
	
	
	
	public void visit(IntermediateRepresentationMethod ir_method) throws NotDAGException
	{
		//log.debug("visiting " + key(ir_method) +  "...");
		if(marked_temporarily.contains(IRUtils.key(ir_method)))
		{
			throw new NotDAGException("El grafo no es un DAG. Tiene ciclos");
		}
		
		if(!marked_permanently.contains(IRUtils.key(ir_method)))
		{
			marked_temporarily.add(IRUtils.key(ir_method));
			
			
			for(String s_name : getSuccesors(ir_method))
			{
				//log.debug("-------------" + s_name + " is succesor of " + key(ir_method));
				IntermediateRepresentationMethod succ = methods.get(s_name); 
				
				if(succ != null)
					this.visit(succ);
				
			}
			
			ordered_methods.add(ir_method);
			marked_temporarily.remove(IRUtils.key(ir_method));
			
			//log.debug("+++Adding to set " + key(ir_method));
			marked_permanently.add(IRUtils.key(ir_method));
		}
	}
	
	public void orderIrMethods()
	{
		Stack<IntermediateRepresentationMethod> unordered_methods = new Stack<IntermediateRepresentationMethod>();
		for(IntermediateRepresentationMethod ir_method : methods.values())
		{
			unordered_methods.add(ir_method);
		}

		marked_temporarily = new ArrayList<String>();
		marked_permanently = new ArrayList<String>();
		ordered_methods = new LinkedList<IntermediateRepresentationMethod>();
		
		for(IntermediateRepresentationMethod ir_method : unordered_methods)
		{
			try
			{
				visit(ir_method);
			}
			catch(NotDAGException exc)
			{
				//Cuando haya recursion hay que cambiar el algoritmo de pseudo top order
				log.error("Graph not a DAG. Has recursion. Can't continue");
				System.exit(1);
			}
		}
		
		log.debug("Listo! Metodos ordenados: ");
		for(int i = 0; i < ordered_methods.size(); i++)
		{
			log.debug(IRUtils.key(ordered_methods.get(i)));
		}
	}

	public void doAnalysis(String mainClass)
	{
		this.setMainClass(mainClass);
		getIrMethods(getLocationStrategy().getBasePath());
		getAnnotations(getLocationStrategy().getBasePath() + "../");

		
		orderIrMethods();
		
		this.data = new HashMap<String, PaperMemorySummary>();
		this.badLines = new ArrayList<LineWithParent>();
		this.badLinesCalls = new ArrayList<LineWithParent>();
		
		ListIterator<IntermediateRepresentationMethod> li = ordered_methods.listIterator();
			
		while(li.hasNext())
		{
			IntermediateRepresentationMethod ir_method = (IntermediateRepresentationMethod) li.next();
			try{
				log.debug("Processing " + IRUtils.key(ir_method) + "...");				
				
				PaperIntraproceduralAnalysis analysis = new PaperIntraproceduralAnalysis(this, summaryFactory, countingTheory, expressionFactory, symbolicCalculator, badLines, badLinesCalls);
				//PaperIntraproceduralAnalysis analysis = new PaperIntraproceduralAnalysis();
				PaperMemorySummary summary = analysis.run(ir_method);
				data.put(IRUtils.key(ir_method), summary);				
			}
			catch(Error error)
			{
				//Tal vez deberia hacer que se rompa y fue
				log.warn("El metodo " + ir_method.getName() + " no pudo ser analizado.");
			}
		}
		
		log.info("Paper Analysis terminado");
		
		//irMethods = irMethods;
	}
	
	public PaperCallSummaryInContext analyseCall(Line callInvocation, IntermediateRepresentationMethod belongsTo)
	{
		
		callAnalyzer.init(symbolicCalculator, expressionFactory);

		//ParametricExpression MAX_memreq = this.expressionFactory.constant(0L);
		
		
		DomainSet lineInvariant_without_binding = callInvocation.getInvariant();	
		DomainSet lineInvariant = lineInvariant_without_binding.clone();
		DomainSetUtils.unify(lineInvariant, callInvocation.getBinding());
		Set<String> unboundedBindingVariables = new TreeSet<String>();
		
		
		IntermediateRepresentationMethod dummyMethod;
		if(callInvocation.getInvocations().isEmpty())
		{
			dummyMethod = new IntermediateRepresentationMethod();
			
			//TODO: el name no es el signature, es otra cosa
			dummyMethod.setName(callInvocation.getIrName());		
			
			dummyMethod.setDeclaringClass(callInvocation.getIrClass());
			

			
			PaperMemorySummary invocationSummary = this.methodAnnotationsHelper.get(dummyMethod);
			if(invocationSummary == null)
			{
				throw new RuntimeException("Memory summary for method [" + dummyMethod.getName() + "] not found. Is this method unanalizable? Is it in a recursive call chain?");
				
			} 
			else {
				Invocation dummyInvocation = new Invocation();
				callAnalyzer.process(dummyInvocation, callInvocation, invocationSummary, lineInvariant, belongsTo.getNodes(), belongsTo.getEscapeNodes(), belongsTo.getFullName());					
			}
			
			
		}
		
		for(Invocation invocation : callInvocation.getInvocations())
		{					
				
			//En realidad es sin los parametros, para que machee bien
			PaperMemorySummary invocationSummary = this.data.get(IRUtils.key(invocation, callInvocation.getIrName()));
			
			if(invocationSummary ==null)
			{
				
				dummyMethod = new IntermediateRepresentationMethod();
				//TODO: el name no es el signature, es otra cosa
				dummyMethod.setName(invocation.getNameCalled());		
				
				dummyMethod.setDeclaringClass(invocation.getClass_called());
				
				dummyMethod.setParameters(invocation.getParameters());
				
				dummyMethod.setIsReturnRefLikeType(invocation.isReturnRefLikeType());
				invocationSummary = this.defaultSummaryRepository.get(dummyMethod);
				
				if (invocationSummary == null) {
					// No hay se genero un summary en esta corrida ni existia en el repositorio. No podemos continuar. Informamos al usuario de esto 
					throw new RuntimeException("Memory summary for method [" + dummyMethod.getName() + "] not found. Is this method unanalizable? Is it in a recursive call chain?");
					
				} else {
				
					callAnalyzer.process(invocation, callInvocation, invocationSummary, lineInvariant, belongsTo.getNodes(), belongsTo.getEscapeNodes(), belongsTo.getFullName());					
				}
			}
			else
			{
				callAnalyzer.process(invocation, callInvocation, invocationSummary, lineInvariant, belongsTo.getNodes(), belongsTo.getEscapeNodes(), belongsTo.getFullName());
			}						
									
		}		
		
		//MAX_memreq = symbolicCalculator.supreme(MAX_memreq, invocationSummary.getMemoryRequirement());
		
		
		/*for(PaperPointsToHeapPartition hp: invocationSummary.getResidualPartitions())
		{
			
		}*/
		if(BarvinokParametricExpressionUtils.isInfinite(callAnalyzer.getTotalResiduals()) || BarvinokParametricExpressionUtils.isInfinite(callAnalyzer.getMAX_memReqMinusRsd()))
		{
			System.out.println("hola");
			Set<String> calleeVariables = callInvocation.getBinding().getCallees();
			//lineInvariant ya tiene de inductivas a las del binding?
			
			
			//Es lo mismo chequear los callees que los callers no?
			if(lineInvariant!=null && !lineInvariant.toString().equals(""))
			{					
				unboundedBindingVariables.addAll(countingTheory.getUnboundedBindingVariables(lineInvariant,calleeVariables));
			}	
		}
		
		if(callInvocation.getInvocations().size() > 0)
		{
			
			callAnalyzer.calculateCorrectTotalResiduals(callInvocation.getInvocations().get(0), callInvocation.getInvariant().clone());
		}
		
		PaperCallSummaryInContext result = callAnalyzer.buildSummary(callInvocation);
		result.setUnboundedBindingVariables(unboundedBindingVariables);
		
		return result;			

	}
	
	public void run(String mainClass){
		doAnalysis(mainClass);		
	}
	
	protected Map<String, IntermediateRepresentationMethod> methods;
	
	public Map<String, IntermediateRepresentationMethod> getMethods() {
		return methods;
	}

	public void getIrMethods(String basePath)
	{
		
		methods = new HashMap<String, IntermediateRepresentationMethod>();
		
		
		String loc = basePath + "json/" + getMainClass() + "/";
		
		
		
		
		
		List<File> files = new ArrayList<File>();
		
		listf(loc,files);
		
		ListIterator fileIt = files.listIterator();
		
		while (fileIt.hasNext()) {
			
			File fileEntry = (File) fileIt.next();
			log.debug("Retriving location for summary: [" + fileEntry.toString() + "]");			
			
			if (fileEntry.exists()) {
				try {
					IntermediateRepresentationMethod method = jsonIRReader.read(new FileReader(fileEntry));
					methods.put(IRUtils.key(method), method);
					//addDependentMethods(method, fileIt);
					
				} catch (FileNotFoundException e) {
					log.error("Error al recuperar el summary de escape para el metodo [" + fileEntry.toString() + "] a xml: " + e.getMessage(), e);
				}
			}
			
		}
	}
	
	protected HashMap<String, Annotation> annotations;
	
	public void getAnnotations(String basePath)
	{
		
		HashMap<String, Annotation> annotations = new HashMap<String, Annotation>();
				
		String loc = basePath + "annotations/" + getMainClass() + "/";
		
		
		
		
		
		List<File> files = new ArrayList<File>();
		AnnotationsContainer annContainer = new AnnotationsContainer();

		File directory = new File(loc);
		if(directory.exists())
		{
			listf(loc,files);		
			ListIterator fileIt = files.listIterator();
			log.debug("Retrieving annotations...");
			while (fileIt.hasNext()) {
				
				File fileEntry = (File) fileIt.next();
				log.debug("Retriving location for summary: [" + fileEntry.toString() + "]");			
				
				if (fileEntry.exists()) {
					try {
						
						annContainer = jsonAnnotationsReader.read(new FileReader(fileEntry));
						
						this.methodAnnotationsHelper = new MethodAnnotationsHelper(annContainer);
						
					} catch (FileNotFoundException e) {
						log.error("Error al recuperar el summary de escape para el metodo [" + fileEntry.toString() + "] a xml: " + e.getMessage(), e);
					}
				}
				
			}
		}
	}

	public void listf(String directoryPath, List<File> files) {

		File directory = new File(directoryPath);
	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
	}



	public SummaryReader<IntermediateRepresentationMethod> getJsonIrReader() {
		return jsonIRReader;
	}


	public void setJsonIRReader(SummaryReader<IntermediateRepresentationMethod>  jsonIRReader) {
		this.jsonIRReader = jsonIRReader;
	}


	protected void init() {
		// TODO Auto-generated method stub
		
	}


	protected void doAnalysis() {
		// TODO Auto-generated method stub
		
	}
	
	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}


	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
	
	
	
	
	
	/*public IntermediateRepresentationMethod get(SootMethod method) {
		String location = locationStrategy.getLocation(method);
		log.debug("Retriving location for summary: [" + location + "]");
		
		if (lastFilePathUsed == null || !lastFilePathUsed.equals(location)) {
			lastFilePathUsed = location; 
			currentSummary = null;
			File srcFile = new File(location);
			
			if (srcFile.exists()) {
				try {
					currentSummary = reader.read(new FileReader(srcFile));
				} catch (FileNotFoundException e) {
					log.error("Error al recuperar el summary de escape para el metodo [" + method.toString() + "] a xml: " + e.getMessage(), e);
				}
			}
		}	
		
		return currentSummary;
	}*/
	
	
	protected Map<SootMethod, Integer> order;
	
	/*protected void buildOrder() {
		this.order = new HashMap<SootMethod, Integer>();
		
		//BILLY linear ordering of its vertices such that for every directed edge uv from vertex u to vertex v, v comes before u in the ordering
		Orderer<SootMethod> o = new PseudoTopologicalOrderer<SootMethod>();
		Integer i = 0;
		for (SootMethod m : o.newList(directedCallGraph, true)) {
			this.order.put(m, new Integer(i));
			i++;
		}
	}*/
	
	protected Comparator<SootMethod> getOrderComparator() {
		return new IntComparator();
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}


	// queue class
	class IntComparator implements Comparator<SootMethod> {
		public int compare(SootMethod o1, SootMethod o2) {
			Integer v1 = order.get(o1);
			Integer v2 = order.get(o2);
			if(v1!=null && v2!=null)	return v1.intValue() - v2.intValue();
			else if(v1==null && v2!=null) return -v2.intValue();
			else if(v1!=null) return v1.intValue();
			else return 0;
		}
	}

}
