package ar.uba.dc.tools.analysis.memory.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.memory.impl.ReportWriter;
import ar.uba.dc.analysis.memory.impl.report.datasource.RepositoryReportDataSource;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;

public class ReportWriterTool {

	private static Log log = LogFactory.getLog(ReportWriterTool.class);
	
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

		SootUtils.setClasspath(context);
		
		log.info("Writing report of memory analysis for [" + className + "] and method [" + methodSignature + "]");
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		/*Timer t = new Timer();
		t.start();
		log.debug("Getting the call graph");
		CallGraph cg = SootUtils.getCallGraph(className, methodSignature, context);
		t.stop();
		log.info("Building Callgraph took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");*/
		
		ReportWriter writer = context.getFactory().getMemoryReportWriter();
		
		RepositoryReportDataSource ds = context.getFactory().getRepositoryReportDataSource();
		ds.setMainClass(className);
		writer.write(ds);
		
		analysisTimer.stop();
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}
}
