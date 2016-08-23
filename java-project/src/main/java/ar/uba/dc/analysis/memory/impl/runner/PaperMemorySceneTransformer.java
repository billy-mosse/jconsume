package ar.uba.dc.analysis.memory.impl.runner;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.memory.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.PaperInterproceduralAnalysis;
import ar.uba.dc.config.Context;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootMethod;

public class PaperMemorySceneTransformer extends SceneTransformer{
private static Log log = LogFactory.getLog(PaperMemorySceneTransformer.class);
	
	private Context context;
	private String mainClass;
	private String mainMethod;

	public static PaperMemorySceneTransformer v(Context context, String mainClass) {
		return v(context, mainClass, null);
	}
	
	public static PaperMemorySceneTransformer v(Context context, String mainClass, String mainMethod) {
		return new PaperMemorySceneTransformer(context, mainClass, mainMethod);
	}
	
	private PaperMemorySceneTransformer(Context context, String mainClass, String mainMethod) {
		super();
		this.context = context;
		this.mainClass = mainClass;
		this.mainMethod = mainMethod;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void internalTransform(String phaseName, Map options) {
		PaperInterproceduralAnalysis aPaperMemoryAnalysis = context.getFactory().getPaperMemoryAnalysis();
		
		String methodSignature = StringUtils.defaultString(mainMethod, context.getString(Context.DEFAULT_MAIN_METHOD)); 
		
		SootMethod main = SootUtils.getMethod(mainClass, methodSignature);
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		Timer t = new Timer();
		t.start();
		
		log.info("Running memory analysis for [" + mainClass + "] and method [" + methodSignature + "]");
		
		aPaperMemoryAnalysis.run(Scene.v().getCallGraph(), context.getFactory().getDirectedGraphMethodFilter(), mainClass);
		
		analysisTimer.stop();
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}
}
