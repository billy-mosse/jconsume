package ar.uba.dc.analysis.escape;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.Edge;
import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis;
import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.common.code.MethodDecorator;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonIRWriter;
import ar.uba.dc.analysis.common.method.information.JsonBasedEscapeAnnotationsProvider;
import ar.uba.dc.analysis.escape.summary.EscapeAnnotation;
import ar.uba.dc.analysis.escape.summary.EscapeAnnotationsSummaryFactory;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;
import ar.uba.dc.analysis.memory.PaperInterproceduralAnalysis;
import ar.uba.dc.analysis.memory.impl.BarvinokCalculatorAdapter;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedLifeTimeOracle;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.soot.Box;
import ar.uba.dc.util.Timer;
import ar.uba.dc.util.location.MethodLocationStrategy;
import ar.uba.dc.util.location.method.ClassNameLocationStrategy;


public class InterproceduralAnalysis extends AbstractInterproceduralAnalysis implements CallAnalyzer {
	
	private static Log log = LogFactory.getLog(InterproceduralAnalysis.class);
	
	private static final int INFINITE = -1;
	
	protected ClassNameLocationStrategy locationStrategy;

	
	// Tiene los summaries que se fueron generando durante la corrida del analisis. No tiene los summaries
	// de los metodos no analizables que se fueron utilizando.
	protected RAMSummaryRepository data;					// SootMethod -> summary
	
	protected Set<IntermediateRepresentationMethod> ir_methods;
	
	// Tiene los summaries de aquellos metodos no analizados que se utilizaron durante
	// el analisis
	protected Map<SootMethod, EscapeSummary> unanalysed;	// SootMethod -> summary
	
	protected SummaryWriter<EscapeSummary> summaryWriter;

	protected SummaryWriter<IntermediateRepresentationMethod> irWriter;

	private SummaryWriter<IntermediateRepresentationMethod> ihrWriter;

	private SummaryWriter<IntermediateRepresentationMethod> jsonIrWriter;
	
	private JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProvider;
	
	private EscapeAnnotationsSummaryFactory escapeAnnotationsSummaryFactory;

	protected SummaryReader<IntermediateRepresentationMethod> irReader;	
	
	protected SummaryApplier summaryApplier;
	
	protected SummaryRepository<EscapeSummary, SootMethod> repository;
	
	protected MethodInformationProvider methodInformationProvider;
	
	protected boolean writeResults;
	
	protected boolean writeIntermediateLanguageRepresentation;
	
	protected boolean writeUnanalysedSummaries;
	
	protected boolean removeLocals;
	
	protected int sensitivity;
	
	protected boolean analyseKnownMethods;
	
	protected boolean cleanOutputFolder;
	
	protected String outputFolder;
	
	protected MethodDecorator methodDecorator;
	

	protected PolymorphismResolver polymorphismResolver;
	
	protected boolean trustInterfaceSummaries;
	
	private int recursionWatchDog;
	
	private boolean simplifySummary;
	
	private SummarySimplifier simplifier;
	
	
	protected InvariantProvider invariantProvider;
	
	protected PaperInterproceduralAnalysis paperInterproceduralAnalysis;
	
	protected BarvinokCalculatorAdapter barvinokCalculatorAdapter;
	
	
	/*protected LifeTimeOracle lifeTimeOracle ;
	
	
	public LifeTimeOracle getLifeTimeOracle() {
		return lifeTimeOracle;
	}

	public void setLifeTimeOracle(LifeTimeOracle lifeTimeOracle) {
		this.lifeTimeOracle = lifeTimeOracle;
	}*/

	public BarvinokCalculatorAdapter getBarvinokCalculatorAdapter() {
		return barvinokCalculatorAdapter;
	}

	public void setBarvinokCalculatorAdapter(
			BarvinokCalculatorAdapter barvinokCalculatorAdapter) {
		this.barvinokCalculatorAdapter = barvinokCalculatorAdapter;
	}

	@Override
	protected void doAnalysis() {
		init();
		
		Timer t = new Timer();
		log.info("Escape Analysis began");
		t.start();
		
		internalDoAnalysis();
		
		t.stop();
		log.info("Escape Analysis finished. Took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
		
		if (writeResults) {
			
			log.info("Writing escape summaries");
			t.start();
			
			internalWriteSummaries();
	
			t.stop();
			log.info("Writing finished. Took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
		}
		
		if(writeIntermediateLanguageRepresentation)
		{
			log.info("Writing intermediate language representation");
			
			//TODO: esto se podria hacer con factory
			
			//TODO importante: saltearse los metodos que no tienen active body. Ver como esta implementado en el analisis de memoria
			
			
			//TODO: no podria usar el factory para esto? Creo que no.
			EscapeBasedLifeTimeOracle lifetimeOracle = new EscapeBasedLifeTimeOracle();
			lifetimeOracle.setEscapeSummaryProvider(data);
			lifetimeOracle.setInvariantProvider(invariantProvider);
			
			//La sensitivity es el maximo tamaño del stack {calls por donde fue pasando el nodo desde su creacion}
			//Lo pongo en -1 porque en el analisis original tambien esta puesto en -1
			//(Es decir, no estamos usando la informacion)
			//Y si no pusiera -1, no andaria bien la parte de obtener la hp asociada a un nodo
			//porque lo que hace es obtener toods los escaping nodes del metodo y los compara contra el nodo, comparando incluso ese stack
			lifetimeOracle.setSensitivity(-1);
			
			IntermediateLanguageRepresentationBuilder irBuilder = new IntermediateLanguageRepresentationBuilder(data, order, repository, methodInformationProvider, 
					methodDecorator,
																												invariantProvider, outputFolder, callGraph, mainClass, 
																												lifetimeOracle, jsonBasedEscapeAnnotationsProvider,
																												barvinokCalculatorAdapter);
			ir_methods = irBuilder.buildIntermediateLanguageRepresentation();
			
			internalWriteIntermediateRepresentation();
		}
		
		//this.paperInterproceduralAnalysis.doAnalysis(mainClass);
	}

	protected void init() {
		// Inicializamos los summaries q obtuvimos en esta corrida
		this.data.clear();
		// Inicializamos los summaries de metodos no analizados que utilizamos en esta corrida
		this.unanalysed = new HashMap<SootMethod, EscapeSummary>();
		
		if (cleanOutputFolder) {
			try {
				File outputDir = new File(outputFolder);
				if (outputDir.exists()) {
					FileUtils.cleanDirectory(outputDir);
				}
			} catch (IOException e) {
				log.warn("No fue posible limpiar el directorio de output para los escape summaries: " + e.getMessage(), e);
			}
		}
	}
	
	protected void internalDoAnalysis() {

		//top bottom analysis
		
		//esto deberia ir al factory config, pero bueno!
		escapeAnnotationsSummaryFactory = new EscapeAnnotationsSummaryFactory();
		
		this.jsonBasedEscapeAnnotationsProvider.fetchAnnotations(this.mainClass);
		
		
		
		SortedSet<SootMethod> queue = new TreeSet<SootMethod>(getOrderComparator());

		
		// init		
		for (SootMethod o : order.keySet()) { 
			if (analyseKnownMethods || !repository.contains(o)) {
				data.put(o, newInitialSummary(o));
				queue.add(o);
			}
		}

		Map<SootMethod, Integer> nb = new HashMap<SootMethod, Integer>(); // only for debug pretty-printing

		//OJO que procesa solo metodos no filtrados (no abstractos, no nativos, etc). Ver la clase DirectedCallGraph.
		
		// fixpoint iterations
		while (!queue.isEmpty()) {
			SootMethod m = queue.first();
			queue.remove(m);
			
			//if the method has an annotation then it is not analyzed
			EscapeAnnotation annotation = jsonBasedEscapeAnnotationsProvider.get(m.getDeclaringClass() + "." + m.getName());
			if(annotation != null)
			{
				EscapeSummary summary = escapeAnnotationsSummaryFactory.buildPTG(m, annotation);
				
				//so that it becomes available next time
				data.put(m, summary);
			}
			else
			{
				log.debug("Processing " + m.toString());
				if (analyseKnownMethods || !repository.contains(m)) {
					EscapeSummary newSummary = newInitialSummary(m);
					EscapeSummary oldSummary = data.get(m);
		
					if (nb.containsKey(m)) {
						nb.put(m, new Integer(nb.get(m).intValue() + 1)); //Le suma 1.
					} else {
						nb.put(m, new Integer(1));
					}
					
					if (recursionWatchDog == INFINITE || nb.get(m) <= recursionWatchDog) {
						log.info(" |- processing " + m.toString() + " (" + nb.get(m) + "-st time)");
		
						Box<EscapeSummary> destBox = new Box<EscapeSummary>(newSummary);
						
						//Hace un analisis intraprocedural del metodo
						//incluye combinar los análisis de los callees
						//Usa DefaultInterproceduralMapper, DefaultSummaryApplier, DefaultSummaryCombiner 
						analyseMethod(m, destBox);
						
						if (!oldSummary.equals(destBox.getValue())) {
							// summary for m changed!
							

							//en el fondo no esta recorriendo los metodos en orden topologico?
							//"cambiar" es que no es un summary trivial, no?
							//salvo que haya recursion
							
							//P_0 - n -> L_1, se lee como que P_0 apunta a L_1 via el field n.
							data.put(m, destBox.getValue());
							List<SootMethod> l = directedCallGraph.getPredsOf(m);
							queue.addAll(l);
						}
					} else {
						//esto es para salir del punto fijo.
						log.info(" |- Avoid processing " + m.toString() + ". Recursion Watchdog is set to [" + recursionWatchDog + "] and the method has been processed [" + (nb.get(m) - 1) + "] times");
					}
				} else {
					log.info(" |- Avoid processing " + m.toString()  + ". Repository contains summary for it and analyseKnownMethods is set to false.");
				}
			}
			
		}
	}
	
	protected void analyseMethod(SootMethod method, Box<EscapeSummary> box) {
		// cargamos la clase en soot si todavia no esta disponible
		if (!Scene.v().containsClass(method.getDeclaringClass().getName())) {
			Scene.v().loadClassAndSupport(method.getDeclaringClass().getName());
		}
		IntraproceduralAnalysis r = new IntraproceduralAnalysis(method, this, sensitivity);
		r.copyResult(box);
    	if (removeLocals) {
    		box.getValue().removeLocals();
    	}
    	if (simplifySummary) {
    		simplifier.simplify(box);
    	}
	}
	
	public void analyseCall(Box<EscapeSummary> src, Stmt callStmt, Box<EscapeSummary> dst) {
		log.debug(" | | |- Analyse call: " + callStmt);
		
		
		//trustInterfaceSummaries=true;
		if (trustInterfaceSummaries && (callStmt.getInvokeExpr() instanceof InterfaceInvokeExpr)) {
			log.debug(" | | | |- Trust interface mode is enabled. Searching interface summary");
			
			EscapeSummary summary = repository.get(callStmt.getInvokeExpr().getMethod());
			
			if (summary != null ) {
				log.debug(" | | | | |- Summary found. Retreiving it");
				unanalysed.put(callStmt.getInvokeExpr().getMethod(), summary);
				Box<EscapeSummary> temp = new Box<EscapeSummary>(newInitialSummary(src.getValue().getTarget()));
				summaryApplier.applySummary(src, callStmt, summary, temp);
				dst.getValue().union(temp.getValue());
				return;
			} else {
				
				
				/*EscapeAnnotation escapeAnnotation = jsonBasedEscapeAnnotationsProvider.get(callStmt.getInvokeExpr().getMethod().getName());
				if(escapeAnnotation != null)
				{
					
				}*/
				//else
				//{
					log.debug(" | | | | |- Escape summary not found. Continue with call analysis");
				//}
				
				//aca deberia usar anotaciones.
				//annotationRepository deberia tener cargados los summaries calculados en base a las anotaciones
				/*
				 *
				 * EscapeSummary annotatedSummary = annotationRepository.get(callStmt.getInvokeExpr().getMethod())
				 *  //OJO que EscapeSummary tiene que extenderse y permitir omega nodes
				 *  //
				 * 
				 * 
				 */
				
				
				
			}		
		}
		
		SootClass implementation = polymorphismResolver.getTarget(callStmt, src.getValue());
		
		
		//No lo hace en orden topologico porque soporta recursion - por eso es un analisis de punto fijo.
		Iterator<Edge> it = callGraph.edgesOutOf(callStmt);
		
		int mergedSummaries = 0;
		dst.setValue(newInitialSummary(src.getValue().getTarget()));
		
		
		//mergea los PTG de los callees con src
		while (it.hasNext()) {
			Edge edge = it.next();
			SootMethod m = edge.tgt();
			if (implementation == null || implementation.equals(m.getDeclaringClass())) {
				if (!methodInformationProvider.isExcluded(m)) {
					//TODO: ver que pasa con iterator!
					
					mergedSummaries++;
					log.debug(" | | | |- mergin with [" + m.getDeclaringClass().getName() + ": " + m.getSubSignature() + "]");
					Box<EscapeSummary> temp = new Box<EscapeSummary>(newInitialSummary(src.getValue().getTarget()));
					EscapeSummary summary = data.get(m);
					
					if (summary == null) {
						EscapeAnnotation annotation = jsonBasedEscapeAnnotationsProvider.get(m.getDeclaringClass() + "." + m.getName());
						if(annotation != null)
						{
							summary = escapeAnnotationsSummaryFactory.buildPTG(m, annotation);
							
							//so that it becomes available next time
							data.put(m, summary);
						}
						else
						{
							//Aca agarra de unanalizable methods.
							//por ejemplo, List add es anotado como fresh, asi que crea un PTG sin omega nodes.
							summary = repository.get(m);
							if (summary != null) {
								unanalysed.put(m, summary);
							}	
						}
					}
		
					if (summary == null) {
						throw new RuntimeException("Escape summary for method [" + m.getSignature() + "] not found. Is this method unanalizable?");
					}
		
					//supongo que mergea el summary del callee con src?
					summaryApplier.applySummary(src, callStmt, summary, temp);
					dst.getValue().union(temp.getValue());
					log.debug(" | | | |- Merge finished");
				} else {
					log.debug(" | | | |- Method [" + m + "] is excluded from the analysis. It won't be merged");
				}
			} else {
				log.debug(" | | | |- Method [" + m + "] is skipped from the analysis. It won't be merged because escape information show it's not the correct implementation of the polymorphic call");
			}
		}
		
		if (mergedSummaries == 0) {
			dst.getValue().union(src.getValue());
		}
	}

	protected EscapeSummary newInitialSummary(SootMethod forMethod) {
		return new EscapeSummary(forMethod);
	}
	
	protected void internalWriteSummaries() {
		
		for (EscapeSummary summary : data.values()) {
			log.debug(" |- Writing summary of analyzed method: " + summary.getTarget());
			summaryWriter.write(summary);
		}

		if (writeUnanalysedSummaries) {
			for (EscapeSummary summary : unanalysed.values()) {
				log.debug(" |- Writing summary of unanalyzed method: " + summary.getTarget());
				summaryWriter.write(summary);
			}
		}
	}
	
	
	protected void internalWriteIntermediateRepresentation() {
		
		
		/*log.debug("Writing XML...");
		irWriter.setMainClass(this.mainClass);
		//Si, ya se, podria meter todo en un solo FOR
		for (IntermediateRepresentationMethod ir_method : ir_methods) {
			//TODO: agregar los parametros o un mejor nombre para debug
			log.info(" |- Writing summary of analyzed method: " + ir_method.getName());
			irWriter.write(ir_method);
		}
		
		log.debug("Done");
		log.debug("Writing Human Readable...");
		
		ihrWriter.setMainClass(this.mainClass);
		for (IntermediateRepresentationMethod ir_method : ir_methods) {
			//TODO: agregar los parametros o un mejor nombre para debug
			log.info(" |- Writing summary of analyzed method: " + ir_method.getName());
			ihrWriter.write(ir_method);
		}
		
		log.debug("Done");*/
		log.info("Writing JSON Intermediate Representation...");

		((JsonIRWriter)jsonIrWriter).setLocationStrategy(locationStrategy);
		
		jsonIrWriter.setMainClass(this.mainClass);
		
		if(jsonIrWriter.getClass() == JsonIRWriter.class)
			((JsonIRWriter) jsonIrWriter).registerTypeAdapters(this.debugIR);
		
		for (IntermediateRepresentationMethod ir_method : ir_methods) {
			//TODO: agregar los parametros o un mejor nombre para debug
			log.debug(" |- Writing summary of analyzed method: " + ir_method.getName());
			jsonIrWriter.write(ir_method);
			
			
		}
		
		log.debug("Done");

	}
	
/*protected Set<IntermediateRepresentationMethod> internalReadIntermediateRepresentation() {
		Set<IntermediateRepresentationMethod> ir_methods = new LinkedHashSet<IntermediateRepresentationMethod>();
		for (IntermediateRepresentationMethod ir_method : ir_methods) {
			irReader.read(ir_method);
		}
		return ir_methods;
	}*/
	
	public void setSummaryWriter(SummaryWriter<EscapeSummary> writer) {
		this.summaryWriter = writer; 
	}

	public void setIrWriter(SummaryWriter<IntermediateRepresentationMethod> ir_writer) {
		this.irWriter = ir_writer; 
	}
	
	public void setSummaryApplier(SummaryApplier summaryApplier) {
		this.summaryApplier = summaryApplier;
	}

	public void setRepository(SummaryRepository<EscapeSummary, SootMethod> repository) {
		this.repository = repository;
	}
	
	public void setWriteResults(boolean writeResults) {
		this.writeResults = writeResults;
	}	
	
	public void setWriteIntermediateLanguageRepresentation(boolean writeIntermediateLanguageRepresentation) {
		this.writeIntermediateLanguageRepresentation = writeIntermediateLanguageRepresentation;
	}	

	public void setWriteUnanalysedSummaries(boolean writeUnanalysedSummaries) {
		this.writeUnanalysedSummaries = writeUnanalysedSummaries;
	}

	public void setRemoveLocals(boolean removeLocals) {
		this.removeLocals = removeLocals;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	public void setAnalyseKnownMethods(boolean analyseKnownMethods) {
		this.analyseKnownMethods = analyseKnownMethods;
	}

	public void setCleanOutputFolder(boolean cleanOutputFolder) {
		this.cleanOutputFolder = cleanOutputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void setMethodInformationProvider(MethodInformationProvider methodInformationProvider) {
		this.methodInformationProvider = methodInformationProvider;
	}

	public void setPolymorphismResolver(PolymorphismResolver polymorphismResolver) {
		this.polymorphismResolver = polymorphismResolver;
	}

	public void setTrustInterfaceSummaries(boolean trustInterfaceSummaries) {
		this.trustInterfaceSummaries = trustInterfaceSummaries;
	}

	public void setRecursionWatchDog(int recursionWatchDog) {
		this.recursionWatchDog = recursionWatchDog;
	}

	public void setSimplifySummary(boolean simplifySummary) {
		this.simplifySummary = simplifySummary;
	}

	public void setSimplifier(SummarySimplifier simplifier) {
		this.simplifier = simplifier;
	}

	public void setData(RAMSummaryRepository data) {
		this.data = data;
	}	

	public MethodDecorator getMethodDecorator() {
		return methodDecorator;
	}

	public void setMethodDecorator(MethodDecorator methodDecorator) {
		this.methodDecorator = methodDecorator;
	}

	public InvariantProvider getInvariantProvider() {
		return invariantProvider;
	}

	public void setInvariantProvider(InvariantProvider invariantProvider) {
		this.invariantProvider = invariantProvider;
	}

	public PaperInterproceduralAnalysis getPaperInterproceduralAnalysis() {
		return paperInterproceduralAnalysis;
	}

	public void setPaperInterproceduralAnalysis(PaperInterproceduralAnalysis paperInterproceduralAnalysis) {
		this.paperInterproceduralAnalysis = paperInterproceduralAnalysis;
	}

	public SummaryWriter<IntermediateRepresentationMethod> getIhrWriter() {
		return ihrWriter;
	}

	public void setIhrWriter(SummaryWriter<IntermediateRepresentationMethod> ihrWriter) {
		this.ihrWriter = ihrWriter;
	}

	public SummaryWriter<IntermediateRepresentationMethod> getJsonIRWriter() {
		return jsonIrWriter;
	}

	public void setJsonIRWriter(SummaryWriter<IntermediateRepresentationMethod> jsonIRWriter) {
		this.jsonIrWriter = jsonIRWriter;
	}

	public JsonBasedEscapeAnnotationsProvider getJsonBasedEscapeAnnotationsProvider() {
		return jsonBasedEscapeAnnotationsProvider;
	}

	public void setJsonBasedEscapeAnnotationsProvider(
			JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProvider) {
		this.jsonBasedEscapeAnnotationsProvider = jsonBasedEscapeAnnotationsProvider;
	}
	
	public EscapeAnnotationsSummaryFactory getEscapeAnnotationsSummaryFactory() {
		return escapeAnnotationsSummaryFactory;
	}

	public void setEscapeAnnotationsSummaryFactory(
			EscapeAnnotationsSummaryFactory escapeAnnotationsSummaryFactory) {
		this.escapeAnnotationsSummaryFactory = escapeAnnotationsSummaryFactory;
	}
	
	public ClassNameLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}


	public void setLocationStrategy(ClassNameLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
}
