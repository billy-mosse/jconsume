package ar.uba.dc.analysis.escape.runner;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.SceneTransformer;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.InterproceduralAnalysis;
import ar.uba.dc.config.Context;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;

public class EscapeSceneTransformer extends SceneTransformer {

	private static Log log = LogFactory.getLog(EscapeSceneTransformer.class);
	
	private Context context;
	private String mainClass;
	private String mainMethod;

	public static EscapeSceneTransformer v(Context context, String mainClass) {
		return v(context, mainClass, null);
	}
	
	public static EscapeSceneTransformer v(Context context, String mainClass, String mainMethod) {
		return new EscapeSceneTransformer(context, mainClass, mainMethod);
	}
	
	private EscapeSceneTransformer(Context context, String mainClass, String mainMethod) {
		super();
		this.context = context;
		this.mainClass = mainClass;
		this.mainMethod = mainMethod;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void internalTransform(String phaseName, Map options) {
		InterproceduralAnalysis analysis = context.getFactory().getEscapeAnalysis();
		
		String methodSignature = StringUtils.defaultString(mainMethod, context.getString(Context.DEFAULT_MAIN_METHOD)); 
		
		SootMethod main = SootUtils.getMethod(mainClass, methodSignature); 
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		Timer t = new Timer();
		t.start();
		
		log.info("Running escape analysis for [" + mainClass + "] and method [" + methodSignature + "]");
		
		analysis.run(Scene.v().getCallGraph(), context.getFactory().getDirectedGraphMethodFilter(), main);
		
		analysisTimer.stop();
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}
	
}
