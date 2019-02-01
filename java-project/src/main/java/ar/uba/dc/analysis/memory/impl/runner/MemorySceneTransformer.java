package ar.uba.dc.analysis.memory.impl.runner;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.SceneTransformer;
import soot.SootMethod;
import ar.uba.dc.analysis.memory.InterproceduralAnalysis;
import ar.uba.dc.config.Context;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;

public class MemorySceneTransformer extends SceneTransformer {

	private static Log log = LogFactory.getLog(MemorySceneTransformer.class);
	
	private Context context;
	private String mainClass;
	private String mainMethod;

	public static MemorySceneTransformer v(Context context, String mainClass) {
		return v(context, mainClass, null);
	}
	
	public static MemorySceneTransformer v(Context context, String mainClass, String mainMethod) {
		return new MemorySceneTransformer(context, mainClass, mainMethod);
	}
	
	private MemorySceneTransformer(Context context, String mainClass, String mainMethod) {
		super();
		this.context = context;
		this.mainClass = mainClass;
		this.mainMethod = mainMethod;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void internalTransform(String phaseName, Map options) {
		InterproceduralAnalysis anMemoryAnalysis = context.getFactory().getMemoryAnalysis();
		
		String methodSignature = StringUtils.defaultString(mainMethod, context.getString(Context.DEFAULT_MAIN_METHOD)); 
		
		SootMethod main = SootUtils.getMethod(mainClass, methodSignature);
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		Timer t = new Timer();
		t.start();
		
		log.info("Running memory analysis for [" + mainClass + "] and method [" + methodSignature + "]");
		
		anMemoryAnalysis.run(Scene.v().getCallGraph(), context.getFactory().getDirectedGraphMethodFilter(), main, mainClass);
		
		analysisTimer.stop();
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}
}
