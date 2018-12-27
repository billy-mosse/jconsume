package ar.uba.dc.analysis.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootUtils;

@Ignore
@SuppressWarnings("unused")
public class IntraproceduralTest {

	private Context ctx;
	private Map<SootMethod, String> escapeLocation = new HashMap<SootMethod, String>();
	private Map<SootMethod, String> memoryLocation = new HashMap<SootMethod, String>();
	
	@Before
	public void setUp() {
		Options.v().set_whole_program(true);
		ctx = ContextFactory.getContext("test.properties");
		SootUtils.setClasspath(ctx);
		String basePath = ctx.getString("project.classes.classpath") + "/escape/summary/";
		escapeLocation.put(SootUtils.getMethod("ar.uba.dc.simple.EjemploSimple01", "void main(java.lang.String[])"), basePath + "ejemplosimple01");
		escapeLocation.put(SootUtils.getMethod("ar.uba.dc.rinard.BasicTest", "void main(java.lang.String[])"), basePath + "rinard");
		escapeLocation.put(SootUtils.getMethod("ar.uba.dc.jolden.mst.MST", "void main(java.lang.String[])"), basePath + "mst");
		escapeLocation.put(SootUtils.getMethod("ar.uba.dc.tacas.Snippet01", "void main(java.lang.String[])"), basePath + "tacas");
		escapeLocation.put(SootUtils.getMethod("ar.uba.dc.basic.polymorphism.expectation.BasicTest", "void main(java.lang.String[])"), basePath + "algoritmo");
		
		basePath = ctx.getString("project.classes.classpath") + "/memory/summary/";
		memoryLocation.put(SootUtils.getMethod("ar.uba.dc.simple.EjemploSimple01", "void main(java.lang.String[])"), basePath + "ejemplosimple01");
		memoryLocation.put(SootUtils.getMethod("ar.uba.dc.rinard.BasicTest", "void main(java.lang.String[])"), basePath + "rinard");
		memoryLocation.put(SootUtils.getMethod("ar.uba.dc.jolden.mst.MST", "void main(java.lang.String[])"), basePath + "mst");
		memoryLocation.put(SootUtils.getMethod("ar.uba.dc.tacas.Snippet01", "void main(java.lang.String[])"), basePath + "tacas");
		memoryLocation.put(SootUtils.getMethod("ar.uba.dc.basic.polymorphism.expectation.BasicTest", "void main(java.lang.String[])"), basePath + "algoritmo");
	}
	
	public static SootMethod[] methods() {
		return new SootMethod[] {
			SootUtils.getMethod("ar.uba.dc.simple.EjemploSimple01", "void main(java.lang.String[])"),
			SootUtils.getMethod("ar.uba.dc.rinard.BasicTest", "void main(java.lang.String[])"),
			SootUtils.getMethod("ar.uba.dc.jolden.mst.MST", "void main(java.lang.String[])"),
			SootUtils.getMethod("ar.uba.dc.tacas.Snippet01", "void main(java.lang.String[])"),
			SootUtils.getMethod("ar.uba.dc.basic.polymorphism.expectation.BasicTest", "void main(java.lang.String[])")
		};
	}
	
	@Test
	public void memoryRegression() {
		int from = -1;
		int to = 0;
		
		String[] compareStrategies = new String[] { "add", "lazy" };
		
		System.setProperty("escape.summary.repository.layout", "Folder");
		System.setProperty("escape.summary.output.layout", "Folder");
		System.setProperty("escape.summary.remove-variables", "false");
		System.setProperty("escape.analyse.known.methods", "true");
		System.setProperty("escape.summary.unanalized.enabled", "false");
		System.setProperty("escape.summary.all.enabled", "false");
		
		System.setProperty("memory.summary.repository.layout", "Folder");
		System.setProperty("memory.summary.output.layout", "Folder");
		System.setProperty("memory.summary.remove-variables", "false");
		System.setProperty("memory.analyse.known.methods", "true");
		System.setProperty("memory.summary.unanalized.enabled", "false");
		System.setProperty("memory.summary.all.enabled", "false");
		System.setProperty("memory.interprocedural.type", "default");
		System.setProperty("memory.interprocedural.loop.invariant", "infer");
		System.setProperty("callgraph.include.knwon.methods", "true");
		
		System.setProperty("invariants.provider", "spec.full-references");
		System.setProperty("invariants.repository", ctx.getString("project.classes.classpath") + "/../../invariants/arraysCountSize/spec/fullreferences");
		
		System.setProperty("calculator.type", "barvinok");

		Map<SootMethod, List<Integer>> sensitivitiesForMethod = new HashMap<SootMethod, List<Integer>>();
		Map<SootMethod, String[]> compareStrategiesForMethod = new HashMap<SootMethod, String[]>();
		for (SootMethod method : methods()) {
			if (!method.getDeclaringClass().getName().equals("ar.uba.dc.basic.polymorphism.expectation.BasicTest")) {
				List<Integer> l = new LinkedList<Integer>();
				for (int sensitivity = from; sensitivity <= to; sensitivity++) {
					l.add(sensitivity);
				}
				sensitivitiesForMethod.put(method, l);
			} else {
				sensitivitiesForMethod.put(method, Arrays.asList(new Integer[] { 1 }));
			}
			compareStrategiesForMethod.put(method, compareStrategies.clone());
		}
		
		for (SootMethod method : methods()) {
			for (Integer sensitivity : sensitivitiesForMethod.get(method)) {
				for (String compareStrategy : compareStrategiesForMethod.get(method)) {
					System.setProperty("escape.summary.repository.dir", escapeLocation.get(method) + "-k-" + Integer.toString(sensitivity, 10));
					System.setProperty("memory.summary.repository.dir", memoryLocation.get(method) + "-" + compareStrategy + "-k-" + Integer.toString(sensitivity, 10));
					System.setProperty("escape.sensitivity", Integer.toString(sensitivity, 10));
					System.setProperty("barvinok.calculator.compare-strategy", compareStrategy);
					
					ctx = ContextFactory.getContext("test.properties");
					CallGraph cg = SootUtils.getCallGraph(method.getDeclaringClass().getName(), method.getSubSignature(), ctx);
					
					SummaryRepository<MemorySummary, SootMethod>repository = ctx.getFactory().getMemoryRepository();
					InterproceduralAnalysis inter = ctx.getFactory().getMemoryAnalysis();
					inter.setCallGraph(cg);
					inter.init();
					
					DirectedCallGraph dg = new DirectedCallGraph(cg, ctx.getFactory().getDirectedGraphMethodFilter(), method);
					
					Orderer<SootMethod> o = new PseudoTopologicalOrderer<SootMethod>();
					for (SootMethod m : o.newList(dg, true)) {
						System.out.println("Test method: " + m.toString() + " - sensitivity: " + Integer.toString(sensitivity, 10) + " - compare strategy: " + compareStrategy);
						MemorySummary savedSummary = repository.get(m); 
						if (savedSummary != null) {
							IntraproceduralAnalysis intra = new IntraproceduralAnalysis(inter, inter.getExpressionFactory(), inter.getSummaryFactory(), inter.getMethodDecorator(), inter.getCountingTheory(), inter.getLifeTimeOracle(), inter.getSymbolicCalculator());
							MemorySummary result = intra.run(m);
							
							boolean v = savedSummary.equals(result);
							System.out.println("Compare summaries for method [" + m + "] - sensitivity: " + Integer.toString(sensitivity, 10) + " - compare strategy: " + compareStrategy);
							assertThat(savedSummary, is(equalTo(result)));
						}
					}
				}
			}
		}
	}
}