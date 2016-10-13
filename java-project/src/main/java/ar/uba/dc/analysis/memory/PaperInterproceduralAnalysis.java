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
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.code.CallStatement;
//import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis.IntComparator;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.JsonReader;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.XMLReader;
import ar.uba.dc.analysis.escape.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.util.location.MethodLocationStrategy;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;







public class PaperInterproceduralAnalysis {
	
	protected MethodLocationStrategy locationStrategy;

	
	private static Log log = LogFactory.getLog(PaperInterproceduralAnalysis.class);

	
	
	protected SummaryReader<IntermediateRepresentationMethod> jsonReader;
	protected String mainClass;
	
	public SummaryReader<IntermediateRepresentationMethod> getJsonReader() {
		return jsonReader;
	}

	public void setJsonReader(SummaryReader<IntermediateRepresentationMethod> jsonReader) {
		this.jsonReader = jsonReader;
	}

	protected List<String> marked_temporarily;
	protected List<String> marked_permanently;
	protected List<IntermediateRepresentationMethod> ordered_methods;
	
	//protected PaperIntraproceduralAnalysis analysis;
	
	
	protected Map<String, PaperMemorySummary>  data;
	
	
	protected PaperMemorySummaryFactory summaryFactory;
	protected CountingTheory countingTheory;
	protected ParametricExpressionFactory expressionFactory;
	protected SymbolicCalculator symbolicCalculator;
	
	protected SummaryRepository<PaperMemorySummary, IntermediateRepresentationMethod> repository;

	

	//protected Map<IntermediateRepresentationMethod, List<IntermediateRepresentationMethod> > preds;
	//protected Map<IntermediateRepresentationMethod, List<IntermediateRepresentationMethod> > succs;
	
	
	public SummaryRepository<PaperMemorySummary, IntermediateRepresentationMethod> getRepository() {
		return repository;
	}

	public void setRepository(SummaryRepository<PaperMemorySummary, IntermediateRepresentationMethod> repository) {
		this.repository = repository;
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
				succesors.add(key(inv, line.getName()));
			}
		}
		
		return succesors;
	}
	
	public String key(Invocation invocation, String name)
	{
		return invocation.getClass_called() + "." + name; 
	}
	
	
	public String key(IntermediateRepresentationMethod ir_method)
	{
		return ir_method.getDeclaringClass() + "." + ir_method.getName(); 
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
		if(marked_temporarily.contains(key(ir_method)))
		{
			throw new NotDAGException("El grafo no es un DAG. Tiene ciclos");
		}
		
		if(!marked_permanently.contains(key(ir_method)))
		{
			marked_temporarily.add(key(ir_method));
			
			
			for(String s_name : getSuccesors(ir_method))
			{
				//log.debug("-------------" + s_name + " is succesor of " + key(ir_method));
				IntermediateRepresentationMethod succ = methods.get(s_name); 
				
				if(succ != null)
					this.visit(succ);
				
			}
			
			ordered_methods.add(ir_method);
			marked_temporarily.remove(key(ir_method));
			
			//log.debug("+++Adding to set " + key(ir_method));
			marked_permanently.add(key(ir_method));
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
			log.debug(key(ordered_methods.get(i)));
		}
	}

	public void doAnalysis(String mainClass)
	{
		this.mainClass = mainClass;
		getIrMethods();
		orderIrMethods();
		
		this.data = new HashMap<String, PaperMemorySummary>();
		
		for(IntermediateRepresentationMethod ir_method : ordered_methods)
		{
			try{
				log.debug("Processing " + key(ir_method) + "...");				
				
				PaperIntraproceduralAnalysis analysis = new PaperIntraproceduralAnalysis(this, summaryFactory, countingTheory, expressionFactory, symbolicCalculator);
				
				PaperMemorySummary summary = analysis.run(ir_method);
				data.put(key(ir_method), summary);				
			}
			catch(Error error)
			{
				log.debug(ir_method.getName());
			}
		}
		

		

		
		
		//irMethods = irMethods;
	}
	
	public PaperMemorySummary analyseCall(Line callInvocation)
	{
		
		
		ParametricExpression MAX_memreq = this.expressionFactory.constant(0L);
		
		for(Invocation invocation : callInvocation.getInvocations())
		{
			PaperMemorySummary invocationSummary = this.data.get(invocation.getCalled_implementation_signature()); //O algo asi
			
			MAX_memreq = symbolicCalculator.supreme(MAX_memreq, invocationSummary.getMemoryRequirement());
			
			
			for(PaperPointsToHeapPartition hp: invocationSummary.getResidualPartitions())
			{
				
			}
			

			
			
		}
		
		
		
		
		return null;
	}
	
	public void run(CallGraph cg, SootMethodFilter filter, String mainClass){
		
		doAnalysis(mainClass);
		
	}
	
	protected Map<String, IntermediateRepresentationMethod> methods;
	public void getIrMethods()
	{
		
		methods = new HashMap<String, IntermediateRepresentationMethod>();
		
		
		String loc = getLocationStrategy().getBasePath() + "json/" + mainClass + "/";
		
		
		
		
		
		List<File> files = new ArrayList<File>();
		
		listf(loc,files);
		
		for (final File fileEntry : files) {
			log.debug("Retriving location for summary: [" + fileEntry.toString() + "]");			
			
			if (fileEntry.exists()) {
				try {
					IntermediateRepresentationMethod method = jsonReader.read(new FileReader(fileEntry));
					methods.put(key(method), method);
					
				} catch (FileNotFoundException e) {
					log.error("Error al recuperar el summary de escape para el metodo [" + fileEntry.toString() + "] a xml: " + e.getMessage(), e);
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



	public SummaryReader<IntermediateRepresentationMethod> getReader() {
		return jsonReader;
	}


	public void setReader(SummaryReader<IntermediateRepresentationMethod>  jsonReader) {
		this.jsonReader = jsonReader;
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
	};
	
	

}
