package ar.uba.dc.tools.instrumentation.resource.tracker;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import ar.uba.dc.analysis.common.RepositoryLoaderTransformer;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.runner.EscapeSceneTransformer;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;

@Deprecated
public class InstrumentationTool {
	
	private static Log log = LogFactory.getLog(InstrumentationTool.class);
	
	public static void main(final String[] args) throws IOException {
		String propertiesFile = null;
		boolean runEscapeBefore = false;
		
		if (args.length >= 2) {
			if (StringUtils.isNotBlank(args[1])) {
				propertiesFile = args[1];
			}
		}
		
		if (args.length >= 3) {
			if (StringUtils.isNotBlank(args[2])) {
				runEscapeBefore = Boolean.valueOf(args[2].trim().toLowerCase());
			}
		}

		log.info("Instrumentation for [" + args[0] + "] using [" + StringUtils.defaultString(propertiesFile, "null") + "] as configuration");
		
		final Context context = ContextFactory.getContext(propertiesFile, false);

		EdgePredicate predicate = context.getFactory().getEdgePredicate();
		if (predicate != null) {
			ReachableMethods.setEdgePredicate(predicate);
		}
		
		String[] opts = { 
			"-w",
			"-app",
			"-soot-classpath", context.getString(Context.APPLICATION_CLASSPATH),
			"-f", context.getString(Context.INSTRUMENTATION_OUTPUT_FORMAT),
			"-d", context.getString(Context.INSTRUMENTATION_OUTPUT_FOLDER),
			"-src-prec","class",
			"-keep-line-number", 
			"-keep-bytecode-offset",
			"-x", "gnu", 
			"-x", "spec.io",
			"-x", "java.lang.StringBuffer",
			"-p", "jb",	"use-original-names:true", 
			//"-p", "jb.dae", "enabled:false",
			//"-p", "jb.ule", "enabled:false",
			"-p", "jb.ulp", "enabled:true",
			"-p", "jj",	"use-original-names:true", 
			//"-p", "jj.dae", "enabled:false",
			//"-p", "jj.ule", "enabled:false",
			"-p", "jj.ulp", "enabled:true",
			"-p", "cg.spark", "enabled:true",
			"-allow-phantom-refs",
			"-main-class", args[0], 
			args[0]
		};
		
		SummaryRepository<EscapeSummary, SootMethod> escapeRepository = context.getFactory().getEscapeRepository();
		SummaryRepository<EscapeSummary, SootMethod> repository = escapeRepository;
		if (runEscapeBefore) {
			SootUtils.insertTransformer("wjtp", "wjtp.escape", EscapeSceneTransformer.v(context, args[0]));
		} else {
			repository = new RAMSummaryRepository();
			SootUtils.insertTransformer("wjtp", "wjtp.repository-loader", RepositoryLoaderTransformer.v((RAMSummaryRepository) repository, escapeRepository));
		}
		
		Instrumenter instrumenter = Instrumenter.v(args[0], repository, context.getInteger(Context.ESCAPE_SENSITIVITY), context.getFactory().getRuleInformationProvider(), context.getFactory().getInvariantProvider());
		
		SootUtils.insertTransformer("jtp", "jtp.instrumentation", instrumenter);	
		
		Scene.v().addBasicClass(Instrumenter.LISTENER_CLASS, SootClass.SIGNATURES);
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		
		soot.Main.main(opts);
		
		String relativePath = ClassUtils.getPackageCanonicalName(ResourceTrackerListener.class).replaceAll("\\.", "/") + "/tracker.properties";
		String srcDir = context.getString(Context.TOOL_CLASSPATH) + "/tracker.properties";
		String dstDir = context.getString(Context.INSTRUMENTATION_OUTPUT_FOLDER) + "/" + relativePath;
		FileUtils.copyFile(new File(srcDir), new File(dstDir));
		
		analysisTimer.stop();
		
		if (context.getBoolean(Context.INSTRUMENTATION_SHOW_MAPPING)) {
			log.info("HP MAPPING");
			log.info("==========");
			instrumenter.printAliases();
		}
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}	
}