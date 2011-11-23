package ar.uba.dc.tools.analysis.purity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.jimple.toolkits.annotation.purity.PurityAnalysis;
import soot.util.dot.DotGraph;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.ConsoleException;
import ar.uba.dc.util.ConsoleUtils;
import ar.uba.dc.util.Timer;

public class PurityRunner {
	
	private static Log log = LogFactory.getLog(PurityRunner.class);
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
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

		SootUtils.setClasspath(context);
		
		log.info("Purity Analysis for [" + className + "] and method [" + methodSignature + "] start");
		Scene.v().setCallGraph(SootUtils.getCallGraph(className, methodSignature, context));
		
		Map<String, String> options = new HashMap<String, String>();
		options.put("dump-summaries", "true");
		options.put("verbose", "true");
		options.put("enabled", "true");
		options.put("print", "true");
				
		Timer analysisTimer = new Timer();
		analysisTimer.start();

		PurityAnalysis.v().transform("purity", options);
		
		analysisTimer.stop();
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
		
		log.info("Converting dot to gif");
		Iterator<File> it = FileUtils.iterateFiles(new File(context.getString(Context.OUTPUT_FOLDER)), new String[] { "dot" }, false);
		while (it.hasNext()) {
			File srcFile = it.next();
			String location = srcFile.getAbsolutePath().replaceAll("<", "").replaceAll(">", "").replaceAll(":", "").replaceAll(" ", "_");
			FileUtils.moveFile(srcFile, new File(location));
			String command = "dot -Tgif " + location + " -o " + location.replace(DotGraph.DOT_EXTENSION, ".gif");
			try {
				ConsoleUtils.execCommand(command, false);
			} catch (ConsoleException e) {
				log.error("No fue posible generar el grafo [" + location + "]: " + e.getMessage());
			}
		}
		
		log.info("Finished");
	}
}
