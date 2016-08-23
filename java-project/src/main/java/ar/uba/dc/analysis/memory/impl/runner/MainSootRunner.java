package ar.uba.dc.analysis.memory.impl.runner;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Main;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;

/**
 * Clase que se encarga de correr el analisis de consumo sobre una clase y un metodo.
 * A diferencia del {@link StandAloneRunner} el analisis se realiza invocando al {@link Main} de soot.
 */
public class MainSootRunner {
	//El StandAloneRunner de memory llama genera, con soot, el callgraph. Este llama al main de soot y se cuelga en un momento (es mas facil)
	//Ojo que hay que pisar el ReachableMethods.class. Esto se hace modificando el classpath para que tome target/classes.
	//En eclipse, hay que agregarlo como user entries en run configurations (no basta con agregar el classpath como variable de entorno)
	
	private static Log log = LogFactory.getLog(MainSootRunner.class);
	
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
		
		log.info("Memory analysis was requested for [" + className + "] and method [" + methodSignature + "]");
		
		//aca hace el insertTransformer
		if (context.getBoolean(Context.RUN_ESCAPE_ANALYSIS)) {
			PhaseInitializer initializer = context.getFactory().getEscapePhaseInitializer();
			initializer.initialize(context, className);
		}
		
		//BILLY jtp: interprocedural
		//BILLY wjtp: callgraph
		//SootUtils.insertTransformer("wjtp", "wjtp.memory", MemorySceneTransformer.v(context, className));
		
		SootUtils.insertTransformer("wjtp", "wjtp.paperMemory", PaperMemorySceneTransformer.v(context, className));
		
		String[] opts = SootUtils.buildOptions(context, className, methodSignature).toArray(new String[] {});
		
		soot.Main.main(opts); 	
	}
}
