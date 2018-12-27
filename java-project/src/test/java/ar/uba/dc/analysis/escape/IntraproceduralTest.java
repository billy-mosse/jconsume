package ar.uba.dc.analysis.escape;

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
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.Box;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootUtils;

@RunWith(Theories.class)
@SuppressWarnings("unused")
@Ignore

//TODO no estan los ejemplos? por alguna razon no anda.
public class IntraproceduralTest {

	private Context ctx;
	private Map<SootMethod, String> location = new HashMap<SootMethod, String>();
	
	@Before
	public void setUp() {
		Options.v().set_whole_program(true);
		ctx = ContextFactory.getContext("test.properties");
		SootUtils.setClasspath(ctx);
		//hack choto:
		//String basePath = ctx.getString("project.classes.classpath") + "/escape/summary/";
		String basePath = "/home/billy/Projects/git/jconsume/java-project/target/test-classes/escape/summary/";
		location.put(SootUtils.getMethod("ar.uba.dc.simple.EjemploSimple01", "void main(java.lang.String[])"), basePath + "ejemplosimple01");
		location.put(SootUtils.getMethod("ar.uba.dc.rinard.BasicTest", "void main(java.lang.String[])"), basePath + "rinard");
		location.put(SootUtils.getMethod("ar.uba.dc.jolden.mst.MST", "void main(java.lang.String[])"), basePath + "mst");
		location.put(SootUtils.getMethod("ar.uba.dc.tacas.Snippet01", "void main(java.lang.String[])"), basePath + "tacas");
		location.put(SootUtils.getMethod("ar.uba.dc.basic.polymorphism.expectation.BasicTest", "void main(java.lang.String[])"), basePath + "algoritmo");
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
	public void escapesRegression() {
		int from = -1;
		int to = 0;
		
		Map<SootMethod, List<Integer>> sensitivitiesForMethod = new HashMap<SootMethod, List<Integer>>();
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
		}
		
		System.setProperty("escape.summary.repository.layout", "Folder");
		for (SootMethod method : methods()) {
			for (Integer sensitivity : sensitivitiesForMethod.get(method)) {
				System.setProperty("escape.summary.repository.dir", location.get(method) + "-k-" + Integer.toString(sensitivity, 10));
				System.setProperty("escape.sensitivity", Integer.toString(sensitivity, 10));
				System.setProperty("escape.summary.output.layout", "Folder");
				System.setProperty("escape.summary.remove-variables", "false");
				System.setProperty("escape.analyse.known.methods", "true");
				System.setProperty("escape.summary.unanalized.enabled", "false");
				System.setProperty("escape.interprocedural.include.captured.nodes.from.callee.into.caller", "true");
				ctx = ContextFactory.getContext("test.properties");
				CallGraph cg = SootUtils.getCallGraph(method.getDeclaringClass().getName(), method.getSubSignature(), ctx);
				
				SummaryRepository<EscapeSummary, SootMethod> repository = ctx.getFactory().getEscapeRepository();
				InterproceduralAnalysis inter = ctx.getFactory().getEscapeAnalysis();
				inter.setSensitivity(sensitivity);
				inter.setRemoveLocals(false);
				inter.setWriteResults(false);
				inter.setCallGraph(cg);
				inter.init();
				
				DirectedCallGraph dg = new DirectedCallGraph(cg, ctx.getFactory().getDirectedGraphMethodFilter(), method);
				
				Orderer<SootMethod> o = new PseudoTopologicalOrderer<SootMethod>();
				for (SootMethod m : o.newList(dg, true)) {
					System.out.println("Test method: " + m.toString() + " - sensitivity: " + Integer.toString(sensitivity, 10));
					EscapeSummary savedSummary = repository.get(m); 
					if (savedSummary != null) {
						IntraproceduralAnalysis intra = new IntraproceduralAnalysis(m, inter, sensitivity);
						Box<EscapeSummary> result = new Box<EscapeSummary>(new EscapeSummary());
						intra.copyResult(result);
						boolean v = savedSummary.equals(result.getValue());
						assertThat(savedSummary, is(equalTo(result.getValue())));
					}
				}
			}
		}
	}
}
