package ar.uba.dc.analysis.memory.impl.runner;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

/**
 * Clase que se encarga de correr el analisis de consumo sobre una clase y un metodo.
 * No invoca al Main de soot sino que instrumenta invocaciones a las clases que hacen falta invocar.
 * En otras palabras, corre por fuera de soot pero utilizando el framework a demanda.
 */
public class StandAloneRunner {

	private static Log log = LogFactory.getLog(StandAloneRunner.class);
	
	public static void main(String[] args) {
		String propertiesFile = null;
		
		if (args.length >= 2) {
			if (StringUtils.isNotBlank(args[1])) {
				propertiesFile = args[1];
			}
		}
		
		Context context = ContextFactory.getContext(propertiesFile);
		
		String className = args[0];
		String methodSignature = context.getString(Context.DEFAULT_MAIN_METHOD);
		
		if (args.length >= 3 && StringUtils.isNotBlank(args[2])) {
			methodSignature = args[2];
		}
		
		log.info("Running memory analysis for [" + className + "] and method [" + methodSignature + "]");
		
		Timer globalTimer = new Timer();
		globalTimer.start();
		
		Timer t = new Timer();
		t.start();
		log.debug("Getting the call graph");
		CallGraph cg = SootUtils.getCallGraph(className, methodSignature, context);
		t.stop();
		log.info("Building Callgraph took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
		
		SootMethod methodUnderAnalysis = SootUtils.getMethod(className, methodSignature);
		SootMethodFilter filter = context.getFactory().getDirectedGraphMethodFilter();
		
		if (context.getBoolean(Context.RUN_ESCAPE_ANALYSIS)) {
			log.info("Running escape analysis");
			t.start();
			ar.uba.dc.analysis.escape.InterproceduralAnalysis anEscapeAnalysis = context.getFactory().getEscapeAnalysis();
			anEscapeAnalysis.run(cg, filter, methodUnderAnalysis, className);
			t.stop();
			log.info("Escape analysis took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
		}
		
		ar.uba.dc.analysis.memory.InterproceduralAnalysis anMemoryAnalysis = context.getFactory().getMemoryAnalysis();
		anMemoryAnalysis.run(cg, filter, methodUnderAnalysis, className);
		
		globalTimer.stop();
		
		log.info("Finish. Memory analysis took " + globalTimer.getElapsedTime() + " (" + globalTimer.getElapsedSeconds() + " seconds)");
	}
}
