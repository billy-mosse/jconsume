package ar.uba.dc.analysis.escape.runner;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.escape.InterproceduralAnalysis;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;
import soot.jimple.toolkits.callgraph.CallGraph;

/**
 * Clase que se encarga de correr el analisis de escape sobre una clase y un metodo.
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
		
		
		
		log.info("Hola!!");
		
		log.debug("Hola, soy un mensaje de debug!!");

		Context context = ContextFactory.getContext(propertiesFile);
		
		String className = args[0];
		String methodSignature = context.getString(Context.DEFAULT_MAIN_METHOD);
		
		if (args.length >= 3 && StringUtils.isNotBlank(args[2])) {
			methodSignature = args[2];
		}

		log.info("Running escape analysis for [" + className + "] and method [" + methodSignature + "]");
		log.info("APPLICATION_CLASSPATH[" + context.getString(Context.APPLICATION_CLASSPATH) + "]");
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		Timer t = new Timer();
		t.start();
		log.debug("Getting the call graph");
		CallGraph cg = SootUtils.getCallGraph(className, methodSignature, context);
		t.stop();
		log.info("Building Callgraph took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
		
		InterproceduralAnalysis anAnalysis = context.getFactory().getEscapeAnalysis();
		anAnalysis.run(cg, context.getFactory().getDirectedGraphMethodFilter(), SootUtils.getMethod(className, methodSignature));
		analysisTimer.stop();
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}
}
