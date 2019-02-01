package ar.uba.dc.tools.instrumentation.resource.tracker.madeja;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Scene;
import soot.SceneTransformer;
import soot.SootMethod;
import ar.uba.dc.config.Context;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.Timer;


public class LifeTimeOracleSceneTransformer extends SceneTransformer {

	private static Log log = LogFactory.getLog(LifeTimeOracleSceneTransformer.class);
	
	private Context context;
	private String mainClass;
	private String mainMethod;
	
	private LifeTimeSummaryRepository repository;
	
	LifeTimeOracleAnalysis analysis;

	public static LifeTimeOracleSceneTransformer v(Context context, String mainClass, LifeTimeSummaryRepository repository) {
		return v(context, mainClass, null, repository);
	}
	
	public static LifeTimeOracleSceneTransformer v(Context context, String mainClass, String mainMethod, LifeTimeSummaryRepository repository) {
		return new LifeTimeOracleSceneTransformer(context, mainClass, mainMethod, repository);
	}
	
	private LifeTimeOracleSceneTransformer(Context context, String mainClass, String mainMethod, LifeTimeSummaryRepository repository) {
		super();
		this.context = context;
		this.mainClass = mainClass;
		this.mainMethod = mainMethod;
		this.analysis = this.context.getFactory().getLifeTimeOracleAnalysis();
		this.analysis.setRepository(repository);
	}

	
	
	public LifeTimeSummaryRepository getRepository() {
		return repository;
	}

	public void setRepository(LifeTimeSummaryRepository repository) {
		this.repository = repository;
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
		
		log.info("Running memory analysis for [" + mainClass + "] and method [" + methodSignature + "]");
		
	
		analysis.run(Scene.v().getCallGraph(), context.getFactory().getDirectedGraphMethodFilter(), main, mainClass);
		
		analysisTimer.stop();
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
		
	}
	
	
}
