package ar.uba.dc.analysis.memory.impl.runner;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;

public class RunCommand {

	private static Log log = LogFactory.getLog(RunCommand.class);
	
	private String properties;
	
	private String className;
	
	private String specJar;
	
	
	public String getProperties() {
		return properties;
	}



	public void setProperties(String properties) {
		this.properties = properties;
	}



	public String getClassName() {
		return className;
	}



	public void setClassName(String className) {
		this.className = className;
	}

	


	public String getSpecJar() {
		return specJar;
	}



	public void setSpecJar(String specJar) {
		this.specJar = specJar;
	}



	public void execute() { 

		Map<String, String> prop = new HashMap<String, String>();
		prop.put("spec.jar", this.specJar);
		prop.put("barvinok.cmd.path","./barvinok.sh");
		
		final Context context = ContextFactory.getContext(this.properties, prop, false);
		
		String methodSignature = context.getString(Context.DEFAULT_MAIN_METHOD);
		
		
		EdgePredicate predicate = context.getFactory().getEdgePredicate();
		if (predicate != null) {
			ReachableMethods.setEdgePredicate(predicate);
		}
		
		log.info("Memory analysis was requested for [" + className + "] and method [" + methodSignature + "]");
		
		if (context.getBoolean(Context.RUN_ESCAPE_ANALYSIS)) {
			PhaseInitializer initializer = context.getFactory().getEscapePhaseInitializer();
			initializer.initialize(context, className);
		}
		
		SootUtils.insertTransformer("wjtp", "wjtp.memory", MemorySceneTransformer.v(context, className));
		
		String[] opts = SootUtils.buildOptions(context, className, methodSignature).toArray(new String[] {});
		
		soot.Main.main(opts);
	}
}
