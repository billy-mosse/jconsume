package ar.uba.dc.tools.soot;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import ar.uba.dc.analysis.common.AcceptAllMethodFilter;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.soot.callgraph.directed.GraphvizDirectedCallGraphPrinter;

public class CallGraphDumper {

	private static Log log = LogFactory.getLog(CallGraphDumper.class);
	private static int CALL_GRAPH_SIZE_LIMITE = 250;
	
	public static void main(String[] args) {
		String propertiesFile = null;
		
		if (args.length >= 2) {
			if (StringUtils.isNotBlank(args[1])) {
				propertiesFile = args[1];
			}
		}
		
		Context context = ContextFactory.getContext(propertiesFile);
		
		String className = args[0];
		String methodSignature = context.getString(Context.DEFAULT_MAIN_METHOD);
		
		if (args.length >= 3 && StringUtils.isNotBlank(args[2])) {
			methodSignature = args[2];
		}

		GraphvizDirectedCallGraphPrinter dgPrinter = new GraphvizDirectedCallGraphPrinter();
		dgPrinter.setOutputDir(context.getString(Context.OUTPUT_FOLDER));
		
		log.info("Dumping callgraph for [" + className + "] and method [" + methodSignature + "]");
		log.info("Getting the call graph");
		
		CallGraph cg = SootUtils.getCallGraph(className, methodSignature, context);

		log.info("Dumping callgraph");
		
		SootMethod methodUnderAnalysis = SootUtils.getMethod(className, methodSignature);		
		
		if (cg.size() <= CALL_GRAPH_SIZE_LIMITE) {
			DirectedCallGraph dg = new DirectedCallGraph(cg, AcceptAllMethodFilter.INSTANCE, methodUnderAnalysis);
			dgPrinter.print("CallGraph", methodUnderAnalysis, dg);
		} else {
			log.info("CallGraph is too large, it has [" + cg.size() + "] methods. Could not generate the graph");
		}
		
		log.info("Building directed callgraph");
		
		SootMethodFilter filter = context.getFactory().getDirectedGraphMethodFilter();
		DirectedCallGraph dgFiltered = new DirectedCallGraph(cg, filter, methodUnderAnalysis);
		
		log.info("Dumping directed callgraph");
		
		if (dgFiltered.size() <= CALL_GRAPH_SIZE_LIMITE) {
			dgPrinter.print("DirectedCallGraph", methodUnderAnalysis, dgFiltered);
		} else {
			log.info("DirectedCallGraph is too large, it has [" + dgFiltered.size() + "] methods. Could not generate the graph");
		}
		
		log.info("Dump Finished");
	}

}
