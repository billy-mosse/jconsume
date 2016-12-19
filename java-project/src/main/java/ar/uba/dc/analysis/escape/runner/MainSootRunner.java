package ar.uba.dc.analysis.escape.runner;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;

import soot.Main;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;

/**
 * Clase que se encarga de correr el analisis de escape sobre una clase y un metodo.
 * A diferencia del {@link StandAloneRunner} el analisis se realiza invocando al {@link Main} de soot.
 */
public class MainSootRunner {

	private static Log log = LogFactory.getLog(MainSootRunner.class);
	
	//TODO: Este main ya esta re obsoleto. Podria volarlo
	public static void main(String[] args) {
		String propertiesFile = null;
		
		if (args.length >= 2) {
			if (StringUtils.isNotBlank(args[1])) {
				propertiesFile = args[1];
			}
		}
		
		final Context context = ContextFactory.getContext(propertiesFile, false);
		
		String className = args[0];
		String methodSignature = context.getString(Context.DEFAULT_MAIN_METHOD);
		
		if (args.length >= 3 && StringUtils.isNotBlank(args[2])) {
			methodSignature = args[2];
		}
		
		EdgePredicate predicate = context.getFactory().getEdgePredicate();
		if (predicate != null) {
			ReachableMethods.setEdgePredicate(predicate);
		}
		
		log.info("Escape analysis was requested for [" + className + "] and method [" + methodSignature + "]");
		
		SootUtils.insertTransformer("wjtp", "wjtp.escape", EscapeSceneTransformer.v(context, className, false));
		
		String[] opts = SootUtils.buildOptions(context, className, methodSignature).toArray(new String[] {});
		
		soot.Main.main(opts);
	}
}
