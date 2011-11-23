package ar.uba.dc.analysis.common;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.SootMethod;
import ar.uba.dc.config.Context;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;

public class SceneTransformer extends soot.SceneTransformer {

	private static Log log = LogFactory.getLog(SceneTransformer.class);

	private Context context;
	private String mainClass;
	private String mainMethod;
	private AbstractInterproceduralAnalysis analysis;
	
	public static SceneTransformer v(Context context, String mainClass, AbstractInterproceduralAnalysis analysis) {
		return v(context, mainClass, null, analysis);
	}
	
	public static SceneTransformer v(Context context, String mainClass, String mainMethod, AbstractInterproceduralAnalysis analysis) {
		return new SceneTransformer(context, mainClass, mainMethod, analysis);
	}
	
	public SceneTransformer(Context context, String mainClass, String mainMethod, AbstractInterproceduralAnalysis analysis) {
		super();
		this.context = context;
		this.mainClass = mainClass;
		this.mainMethod = mainMethod;
		this.analysis = analysis;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void internalTransform(String phaseName, Map options) {
	
		String methodSignature = StringUtils.defaultString(mainMethod, context.getString(Context.DEFAULT_MAIN_METHOD)); 
	
		SootMethod main = SootUtils.getMethod(mainClass, methodSignature); 
	
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		Timer t = new Timer();
		t.start();
	
		log.info("Running " +  phaseName + " phase for [" + mainClass + "] and method [" + methodSignature + "]");
	
		this.analysis.run(Scene.v().getCallGraph(), context.getFactory().getDirectedGraphMethodFilter(), main);
	
		analysisTimer.stop();
	
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}

}
