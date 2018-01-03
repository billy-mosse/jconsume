package ar.uba.dc.analysis.memory.impl.runner;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.sun.tools.internal.jxc.ap.Options;

import soot.G;
import soot.Main;
import soot.Scene;
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
	
	public static void main(CommandLineValues values, Context context) {
	    

		/*String propertiesFile = null;
		
		if (args.length >= 2) {
			if (StringUtils.isNotBlank(args[1])) {
				propertiesFile = args[1];
			}
		}*/
		
		 
		//String className = args[0];
		//String methodSignature = context.getString(Context.DEFAULT_MAIN_METHOD);
		
		/*if (args.length >= 3 && StringUtils.isNotBlank(args[2])) {
			methodSignature = args[2];
		}*/
		
		G.reset();
		
		EdgePredicate predicate = context.getFactory().getEdgePredicate();
		if (predicate != null) {
			ReachableMethods.setEdgePredicate(predicate);
		}
		
		//TODO: Esto es una mentira! El metodo main esta por default ya....ni me importa lo que pongan de parametro
		log.info("Escape analysis was requested for [" + values.getProgramName() + "] and method [" + values.getMain() + "]");
		
		//aca hace el insertTransformer
		//if (context.getBoolean(Context.RUN_ESCAPE_ANALYSIS)) {
		//if(values.runIr())
		//{
			PhaseInitializer initializer = context.getFactory().getEscapePhaseInitializer();
			initializer.initialize(context, values.getProgramName(), values.isDebugIR());
		//}
		
		//BILLY jtp: interprocedural
		//BILLY wjtp: callgraph
		
		/*if(values.runMemory())
		{
			//SootUtils.insertTransformer("wjtp", "wjtp.memory", MemorySceneTransformer.v(context, values.getProgramName()));
		
			SootUtils.insertTransformer("wjtp", "wjtp.paperMemory", PaperMemorySceneTransformer.v(context, values.getProgramName()));
		}*/
		String[] opts = SootUtils.buildOptions(context, values.getProgramName(), values.getMain()).toArray(new String[] {});

		
		soot.Main.main(opts);
	}
}
