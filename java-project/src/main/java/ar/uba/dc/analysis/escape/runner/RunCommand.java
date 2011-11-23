package ar.uba.dc.analysis.escape.runner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import ar.uba.dc.analysis.memory.impl.runner.PhaseInitializer;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;

public class RunCommand {
	
	private static Log log = LogFactory.getLog(RunCommand.class);
	
	private String properties;
	
	private String className;
	
	
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




	public void execute() {

		final Context context = ContextFactory.getContext(this.properties, false);
		
		
		String methodSignature = context.getString(Context.DEFAULT_MAIN_METHOD);
		
	
		EdgePredicate predicate = context.getFactory().getEdgePredicate();
		if (predicate != null) {
			ReachableMethods.setEdgePredicate(predicate);
		}
		
		log.info("Escape analysis was requested for [" + className + "] and method [" + methodSignature + "]");
		
		PhaseInitializer initializer = context.getFactory().getEscapePhaseInitializer();
		initializer.initialize(context, className);
		
		String[] opts = SootUtils.buildOptions(context, className, methodSignature).toArray(new String[] {});
		
		soot.Main.main(opts);
	}
}
