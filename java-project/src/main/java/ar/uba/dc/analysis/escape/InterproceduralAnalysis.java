package ar.uba.dc.analysis.escape;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;
import ar.uba.dc.soot.Box;
import ar.uba.dc.util.Timer;

public class InterproceduralAnalysis extends AbstractInterproceduralAnalysis implements CallAnalyzer {
	
	private static Log log = LogFactory.getLog(InterproceduralAnalysis.class);
	
	private static final int INFINITE = -1;
	
	// Tiene los summaries que se fueron generando durante la corrida del analisis. No tiene los summaries
	// de los metodos no analizables que se fueron utilizando.
	protected RAMSummaryRepository data;					// SootMethod -> summary
	
	// Tiene los summaries de aquellos metodos no analizados que se utilizaron durante
	// el analisis
	protected Map<SootMethod, EscapeSummary> unanalysed;	// SootMethod -> summary
	
	protected SummaryWriter<EscapeSummary> summaryWriter;
	
	protected SummaryApplier summaryApplier;
	
	protected SummaryRepository<EscapeSummary> repository;
	
	protected MethodInformationProvider methodInformationProvider;
	
	protected boolean writeResults;
	
	protected boolean writeUnanalysedSummaries;
	
	protected boolean removeLocals;
	
	protected int sensitivity;
	
	protected boolean analyseKnownMethods;
	
	protected boolean cleanOutputFolder;
	
	protected String outputFolder;
	
	protected PolymorphismResolver polymorphismResolver;
	
	protected boolean trustInterfaceSummaries;
	
	private int recursionWatchDog;
	
	private boolean simplifySummary;
	
	private SummarySimplifier simplifier;
	
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
		SortedSet<SootMethod> queue = new TreeSet<SootMethod>(getOrderComparator());

		// init
		for (SootMethod o : order.keySet()) { 
			if (analyseKnownMethods || !repository.contains(o)) {
				data.put(o, newInitialSummary(o));
				queue.add(o);
			}
		}

		Map<SootMethod, Integer> nb = new HashMap<SootMethod, Integer>(); // only for debug pretty-printing

		// fixpoint iterations
		while (!queue.isEmpty()) {
			SootMethod m = queue.first();
			queue.remove(m);
			if (analyseKnownMethods || !repository.contains(m)) {
				EscapeSummary newSummary = newInitialSummary(m);
				EscapeSummary oldSummary = data.get(m);
	
				if (nb.containsKey(m)) {
					nb.put(m, new Integer(nb.get(m).intValue() + 1));
				} else {
					nb.put(m, new Integer(1));
				}
				
				if (recursionWatchDog == INFINITE || nb.get(m) <= recursionWatchDog) {
					log.info(" |- processing " + m.toString() + " (" + nb.get(m) + "-st time)");
	
					Box<EscapeSummary> destBox = new Box<EscapeSummary>(newSummary);
					analyseMethod(m, destBox);
					if (!oldSummary.equals(destBox.getValue())) {
						// summary for m changed!
						data.put(m, destBox.getValue());
						queue.addAll(directedCallGraph.getPredsOf(m));
					}
				} else {
					log.info(" |- Avoid processing " + m.toString() + ". Recursion Watchdog is set to [" + recursionWatchDog + "] and the method has been processed [" + (nb.get(m) - 1) + "] times");
				}
			} else {
				log.info(" |- Avoid processing " + m.toString()  + ". Repository contains summary for it and analyseKnownMethods is set to false.");
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
		
		if (trustInterfaceSummaries && (callStmt.getInvokeExpr() instanceof InterfaceInvokeExpr)) {
			log.debug(" | | | |- Trust interface mode is enabled. Searching interface summary");
			
			EscapeSummary summary = repository.get(callStmt.getInvokeExpr().getMethod());
			
			if (summary != null) {
				log.debug(" | | | | |- Summary found. Retriving it");
				unanalysed.put(callStmt.getInvokeExpr().getMethod(), summary);
				Box<EscapeSummary> temp = new Box<EscapeSummary>(newInitialSummary(src.getValue().getTarget()));
				summaryApplier.applySummary(src, callStmt, summary, temp);
				dst.getValue().union(temp.getValue());
				return;
			} else {
				log.debug(" | | | | |- Escape summary not found. Continue with call analysis");
			}		
		}
		
		SootClass implementation = polymorphismResolver.getTarget(callStmt, src.getValue());
		
		Iterator<Edge> it = callGraph.edgesOutOf(callStmt);
		
		int mergedSummaries = 0;
		dst.setValue(newInitialSummary(src.getValue().getTarget()));
		while (it.hasNext()) {
			Edge edge = it.next();
			SootMethod m = edge.tgt();
			if (implementation == null || implementation.equals(m.getDeclaringClass())) {
				if (!methodInformationProvider.isExcluded(m)) {
					mergedSummaries++;
					log.debug(" | | | |- mergin with [" + m.getDeclaringClass().getName() + ": " + m.getSubSignature() + "]");
					Box<EscapeSummary> temp = new Box<EscapeSummary>(newInitialSummary(src.getValue().getTarget()));
					EscapeSummary summary = data.get(m);
		
					if (summary == null) {
						summary = repository.get(m);
						if (summary != null) {
							unanalysed.put(m, summary);
						}
					}
		
					if (summary == null) {
						throw new RuntimeException("Escape summary for method [" + m.getSignature() + "] not found. Is this method unanalizable?");
					}
		
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
			log.info(" |- Writing summary of analyzed method: " + summary.getTarget());
			summaryWriter.write(summary);
		}

		if (writeUnanalysedSummaries) {
			for (EscapeSummary summary : unanalysed.values()) {
				log.info(" |- Writing summary of unanalyzed method: " + summary.getTarget());
				summaryWriter.write(summary);
			}
		}
	}
	
	public void setSummaryWriter(SummaryWriter<EscapeSummary> writer) {
		this.summaryWriter = writer; 
	}

	public void setSummaryApplier(SummaryApplier summaryApplier) {
		this.summaryApplier = summaryApplier;
	}

	public void setRepository(SummaryRepository<EscapeSummary> repository) {
		this.repository = repository;
	}
	
	public void setWriteResults(boolean writeResults) {
		this.writeResults = writeResults;
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
}
