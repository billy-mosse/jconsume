	package ar.uba.dc.analysis.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootClass;
import soot.SootMethod;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.toolkits.callgraph.Edge;
import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis;
import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.MethodDecorator;
import ar.uba.dc.analysis.common.method.information.JsonBasedEscapeAnnotationsProvider;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.ReportWriter;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummaryFactory;
import ar.uba.dc.util.Timer;

public class InterproceduralAnalysis extends AbstractInterproceduralAnalysis {

	private static Log log = LogFactory.getLog(InterproceduralAnalysis.class);
	
	// Tiene los summaries que se fueron generando durante la corrida del analisis. No tiene los summaries
	// de los metodos no analizables que se fueron utilizando.
	protected Map<SootMethod, MemorySummary> data; 			// SootMethod -> summary
	
	// Tiene los summaries de aquellos metodos no analizados que se utilizaron durante
	// el analisis
	protected Map<SootMethod, MemorySummary> unanalysed;	// SootMethod -> summary
	
	protected boolean analyseKnownMethods;

	protected Set<SootMethod> excludedMethods = new HashSet<SootMethod>();
	
	protected SummaryRepository<MemorySummary, SootMethod> repository;
	
	protected ParametricExpressionFactory expressionFactory;
	
	protected MemorySummaryFactory summaryFactory;
	
	protected MethodDecorator methodDecorator;
	
	protected CountingTheory countingTheory;
	
	protected LifeTimeOracle lifeTimeOracle;
	
	protected SymbolicCalculator symbolicCalculator;
	
	protected CallAnalyzer callAnalyzer;
	
	protected MethodInformationProvider methodInformationProvider;
	
	protected PolymorphismResolver polymorphismResolver;
	
	protected boolean trustInterfaceSummaries;
	
	private Set<SootMethod> clinit;
	
	private long analyzedNewStaments = 0;
	
	private long analyzedMethods = 0;
	
	protected ReportWriter<String, PaperMemorySummary> reportWriter;
	
	//esto al final no lo tenemos que usar...me parece
	protected JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProvider;
	
	public JsonBasedEscapeAnnotationsProvider getJsonBasedEscapeAnnotationsProvider() {
		return jsonBasedEscapeAnnotationsProvider;
	}

	public void setJsonBasedEscapeAnnotationsProvider(
			JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProvider) {
		this.jsonBasedEscapeAnnotationsProvider = jsonBasedEscapeAnnotationsProvider;
	}

	@Override
	protected void doAnalysis() {
		init();
		
		Timer t = new Timer();
		log.info("Memory Analysis began");
		t.start();
		
		internalDoAnalysis();
		
		t.stop();
		log.info("Memory Analysis finished. Took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds). Analyzed NewStmts: " + this.analyzedNewStaments + " Analyzed methods: " + this.analyzedMethods);
	}

	protected void init() {
		// Inicializamos los summaries que  obtuvimos en esta corrida
		this.data = new HashMap<SootMethod, MemorySummary>();
		// Inicializamos los summaries de metodos no analizados que utilizamos en esta corrida
		this.unanalysed = new HashMap<SootMethod, MemorySummary>();
		
		//Llamadas a inicializadores de clase
		this.clinit = new HashSet<SootMethod>();
		
		this.jsonBasedEscapeAnnotationsProvider.fetchAnnotations(mainClass);
		
	}
	
	protected void internalDoAnalysis() {		
		SortedSet<SootMethod> queue = new TreeSet<SootMethod>(getOrderComparator());
		queue.addAll(order.keySet());
		
		for (SootMethod methodUnderAnalysis : queue) {
			if (analyseKnownMethods || !repository.contains(methodUnderAnalysis)) {
				log.info(" |- processing " + methodUnderAnalysis.toString());

				
				//if(this.jsonBasedEscapeAnnotationsProvider.get(methodUnderAnalysis.getDeclaringClass().toString() + "." + methodUnderAnalysis.getName()))
				
				//Esto no podria estar afuera? Bueno, no, por lo de analyzedNews por ejemplo.
				IntraproceduralAnalysis analysis = new IntraproceduralAnalysis(this, expressionFactory, summaryFactory, methodDecorator, countingTheory, lifeTimeOracle, symbolicCalculator);
				MemorySummary summary = analysis.run(methodUnderAnalysis);
				this.analyzedNewStaments += analysis.getAnalyzedNews();
				this.analyzedMethods++;
				data.put(methodUnderAnalysis, summary);
			} else {
				log.info(" |- Avoid processing " + methodUnderAnalysis.toString()  + ". Repository contains summary for it and analyseKnownMethods is set to false.");
			}
		}
		
		log.info(SootMethod.staticInitializerName +" methods:");
		for (SootMethod sootMethod : this.clinit) {
			log.info("  " + sootMethod);
		}
		
		log.info("The following methods have been excluded from the analysis:");
		for (SootMethod sootMethod : this.excludedMethods) {
			log.info("  " + sootMethod);
		}
	}
	
	public CallSummaryInContext analyseCall(CallStatement callStmt) {
		log.debug(" | | |- Analyse call: " + callStmt);
		
		if (trustInterfaceSummaries && (callStmt.getStatement().getInvokeExpr() instanceof InterfaceInvokeExpr)) {
			log.debug(" | | | |- Trust interface mode is enabled. Searching interface summary");
			
			MemorySummary calleeSummary = repository.get(callStmt.getStatement().getInvokeExpr().getMethod());
			
			if (calleeSummary != null) {
				log.debug(" | | | | |- Summary found. Retriving it");
				unanalysed.put(callStmt.getStatement().getInvokeExpr().getMethod(), calleeSummary);
				
				// Dejamos que se inicialize el callAnalyzer. Debe setear todo el entorno para un nuevo analisis
				callAnalyzer.init(callStmt, lifeTimeOracle, symbolicCalculator, expressionFactory);				
				
				callAnalyzer.process(callStmt, calleeSummary);
				return callAnalyzer.buildSummary2(callStmt);
			} else {
				log.debug(" | | | | |- Summary not found. Continue with call analysis");
			}		
		}
		
		
		SootClass virtualInvokeImplementation = polymorphismResolver.getTarget(callStmt);
		
		// Dejamos que se inicialize el callAnalyzer. Debe setear todo el entorno para un nuevo analisis
		callAnalyzer.init(callStmt, lifeTimeOracle, symbolicCalculator, expressionFactory);
		
		// Iteramos por c/implementacion posible (si no es un virtual call, habra un solo eje).
		Iterator<Edge> it = callGraph.edgesOutOf(callStmt.getStatement());
		
		List<CallStatement> virtualInvokes = new ArrayList<CallStatement>();
		
		
		while (it.hasNext()) {
			Edge edge = it.next();
			SootMethod callee = edge.tgt(); 
			if(callee.isStatic() && callee.getName().equals(SootMethod.staticInitializerName)) {
				this.clinit.add(callee);
				log.info(" | | | Adding " + callee.getSignature() + " to " +  SootMethod.staticInitializerName + "list");
			} else {
				if (virtualInvokeImplementation == null || virtualInvokeImplementation.equals(callee.getDeclaringClass())) {
					if (!methodInformationProvider.isExcluded(callee)) {
						log.debug(" | | | |- mergin with [" + callee.getDeclaringClass().getName() + ": " + callee.getSubSignature() + "]");
						// Recuperamos el summary del calle de la informacion procesada en esta corrida del algoritmo
						MemorySummary calleeSummary = data.get(callee);
						
						if (calleeSummary == null) {
							// Si no procesamos el metodo, buscamos en el repositorio a ver si tenemos una implementacion
							calleeSummary = repository.get(callee);
							if (calleeSummary != null) {
								unanalysed.put(callee, calleeSummary);
							}
						}
						
						if (calleeSummary == null) {
							// No hay se genero un summary en esta corrida ni existia en el repositorio. No podemos continuar. Informamos al usuario de esto 
							//throw new RuntimeException("Memory summary for method [" + callee.getSignature() + "] not found. Is this method unanalizable? Is it in a recursive call chain?");
							this.excludedMethods.add(callee);
					
						} else {
						
							callAnalyzer.process(callStmt.virtualInvoke(callee), calleeSummary);
							virtualInvokes.add(callStmt.virtualInvoke(callee));
						}
						log.debug(" | | | |- Merge finished");
					} else {
						log.debug(" | | | |- Method [" + callee + "] is excluded from the analysis. It won't be merged");
					}
				} else {
					log.debug(" | | | |- Method [" + callee + "] is skipped from the analysis. It won't be merged because polymorphism resolver decided it's not the correct implementation of the polymorphic call");
				}
			}
		}
				
		//TODO: que pasa cuando hay cero?
		if(virtualInvokes.size() > 0)
		{
			callAnalyzer.calculateCorrectTotalResiduals(virtualInvokes.get(0));
		}
		
		
		CallSummaryInContext result = callAnalyzer.buildSummary2(callStmt);
		
	
		log.debug("Call analysis finished");
		return result;
	}
	
	public Set<MemorySummary> getResults(boolean includeUnanalyzed) {
		Set<MemorySummary> summaries = new HashSet<MemorySummary>(data.values());
		
		if (includeUnanalyzed) {
			summaries.addAll(unanalysed.values());
		}
		
		return summaries;
	}

	public void setRepository(SummaryRepository<MemorySummary, SootMethod>repository) {
		this.repository = repository;
	}

	public void setAnalyseKnownMethods(boolean analyseKnownMethods) {
		this.analyseKnownMethods = analyseKnownMethods;
	}

	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public void setSummaryFactory(MemorySummaryFactory summaryFactory) {
		this.summaryFactory = summaryFactory;
	}

	public void setMethodDecorator(MethodDecorator methodDecorator) {
		this.methodDecorator = methodDecorator;
	}

	public void setCountingTheory(CountingTheory countingTheory) {
		this.countingTheory = countingTheory;
	}

	public void setLifeTimeOracle(LifeTimeOracle lifeTimeOracle) {
		this.lifeTimeOracle = lifeTimeOracle;
	}

	public void setSymbolicCalculator(SymbolicCalculator symbolicCalculator) {
		this.symbolicCalculator = symbolicCalculator;
	}
	
	public SummaryRepository<MemorySummary, SootMethod>getRepository() {
		return repository;
	}

	public ParametricExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}

	public MemorySummaryFactory getSummaryFactory() {
		return summaryFactory;
	}

	public MethodDecorator getMethodDecorator() {
		return methodDecorator;
	}

	public CountingTheory getCountingTheory() {
		return countingTheory;
	}

	public LifeTimeOracle getLifeTimeOracle() {
		return lifeTimeOracle;
	}

	public SymbolicCalculator getSymbolicCalculator() {
		return symbolicCalculator;
	}

	public void setMethodInformationProvider(MethodInformationProvider methodInformationProvider) {
		this.methodInformationProvider = methodInformationProvider;
	}

	public void setCallAnalyzer(CallAnalyzer callAnalyzer) {
		this.callAnalyzer = callAnalyzer;
	}
	
	public void setPolymorphismResolver(PolymorphismResolver polymorphismResolver) {
		this.polymorphismResolver = polymorphismResolver;
	}

	public void setTrustInterfaceSummaries(boolean trustInterfaceSummaries) {
		this.trustInterfaceSummaries = trustInterfaceSummaries;
	}

}
