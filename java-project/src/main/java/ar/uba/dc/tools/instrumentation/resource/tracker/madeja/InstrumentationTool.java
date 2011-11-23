package ar.uba.dc.tools.instrumentation.resource.tracker.madeja;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.SootClass;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import ar.uba.dc.analysis.common.RepositoryLoaderTransformer;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.runner.EscapeSceneTransformer;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;
import ar.uba.dc.analysis.memory.impl.runner.PhaseInitializer;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.tools.instrumentation.resource.tracker.runtime.ResourceTrackerListener;
import ar.uba.dc.util.Timer;

@SuppressWarnings("unused")
public class InstrumentationTool {
	
	private static Log log = LogFactory.getLog(InstrumentationTool.class);
	
	public static void main(final String[] args) throws IOException {
		String propertiesFile = null;
		boolean runEscapeBefore = true;
		
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
			//"-f", "j",
			
			"-d", context.getString(Context.INSTRUMENTATION_OUTPUT_FOLDER),
			"-src-prec","class",
			"-keep-line-number", 
			"-keep-bytecode-offset",
			"-x", "gnu", 
			"-x", "spec.io",
			"-x", "java.lang.StringBuffer",
			"-p", "jb",	"use-original-names:true", 
			
			//Agregadas
			"-p","jb.a","enabled:false",
			"-p","jb.uce","enabled:false",
			"-p","jb.ne","enabled:false",
			"-p", "jb.dae", "enabled:false",
			"-p", "jb.ule", "enabled:false",
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
		
		//Escape analysis
		if (runEscapeBefore) {
			PhaseInitializer initializer = context.getFactory().getEscapePhaseInitializer();
			initializer.initialize(context, args[0]);
		} 
		
		
		LifeTimeSummaryRepository repository = new LifeTimeSummaryRepository();
		/**
		 * LifeTimeOracle Analysis
		 * Genera un resumen de todos los metodos a instrumentar en funcion del CG. Al finalizar hace un write de las propiedas
		 * usada luego por el Runtime para el binding de particiones
		 */
		LifeTimeOracleSceneTransformer transformer = LifeTimeOracleSceneTransformer.v(context, args[0], repository); //Configurar el repo
		transformer.setRepository(repository);
		SootUtils.insertTransformer("wjtp", "wjtp.la", transformer);	
		
		Scene.v().addBasicClass(Instrumenter.LISTENER_CLASS, SootClass.SIGNATURES);
		SootUtils.insertTransformer("jtp", "jtp.instrumentation", new Instrumenter(args[0], repository));	
		
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		
		soot.Main.main(opts);
		
		//String relativePath = ClassUtils.getPackageCanonicalName(ResourceTrackerListener.class).replaceAll("\\.", "/") + "/tracker.properties";
		//String srcDir = context.getString(Context.TOOL_CLASSPATH) + "/tracker.properties";
		
		
		String dstDir = context.getString(Context.INSTRUMENTATION_OUTPUT_FOLDER) + "/" + LifeTimeOracleAnalysis.OUTPUT_RELATIVE_PATH;		
		FileUtils.copyFile(new File(LifeTimeOracleAnalysis.OUTPUT_RELATIVE_PATH), new File(dstDir));
		
		analysisTimer.stop();
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}	
	
}