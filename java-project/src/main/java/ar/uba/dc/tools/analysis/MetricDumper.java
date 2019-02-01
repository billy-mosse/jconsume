package ar.uba.dc.tools.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Body;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.tagkit.BytecodeOffsetTag;
import soot.tagkit.LineNumberTag;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.soot.SootUtils;

/**
 * Dada una clase y metodo, devuelve la cantidad de clases, metodos, lineas de java, lineas de jimple, lineas de bytecode 
 * analizadas para poder dar un resultado al analisis de consumo 
 * 
 * @author testis
 */
public class MetricDumper {

	private static Log log = LogFactory.getLog(MetricDumper.class);
	
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

		log.info("Dumping metrics for [" + className + "] and method [" + methodSignature + "]");
		log.info("Getting the call graph");
		
		CallGraph cg = SootUtils.getCallGraph(className, methodSignature, context);

		SootMethod methodUnderAnalysis = SootUtils.getMethod(className, methodSignature);		
				
		log.info("Building directed callgraph");
		
		SootMethodFilter filter = context.getFactory().getDirectedGraphMethodFilter();
		DirectedCallGraph dgFiltered = new DirectedCallGraph(cg, filter, methodUnderAnalysis, className);
		
		MetricDumper instance = new MetricDumper();
		instance.doMetrics(dgFiltered);
	}

	private void doMetrics(DirectedCallGraph dgFiltered) {
		Set<SootClass> classes = new HashSet<SootClass>();
		Set<SootMethod> methods = new HashSet<SootMethod>();
		Long javaLines = 0L;
		Long bytecodeLines = 0L;
		Long jimpleLines = 0L;
		
		for (Iterator<SootMethod> itMethod = dgFiltered.iterator(); itMethod.hasNext(); ) {
			SootMethod m = itMethod.next();
			
			log.info("Analyzing method : " + m);
			classes.add(m.getDeclaringClass());
			methods.add(m);
			Result res = analyzeMethod(m);
			javaLines += res.getJavaLines();
			bytecodeLines += res.getBytecodeLines();
			jimpleLines += res.getJimpleLines();
		}
		
		log.info("METRICS");
		log.info("=======");
		log.info("Classes: " + classes.size());
		log.info("Methods: " + methods.size());
		log.info("Java Lines: " + javaLines);
		log.info("Bytecode Lines: " + bytecodeLines);
		log.info("Jimple Lines: " + jimpleLines);
		log.info("Dump Finished");
	}

	private Result analyzeMethod(SootMethod m) {
		Body b = m.retrieveActiveBody();
		
		int minLineNumber = Integer.MAX_VALUE;
		int maxLineNumber = 0;
		
		int minBytecodeNumber = Integer.MAX_VALUE;
		int maxBytecodeNumber = 0;
		for (Unit u : b.getUnits()) {
			LineNumberTag lineNumberTag = (LineNumberTag) u.getTag("LineNumberTag");
			BytecodeOffsetTag bytecodeOffsetTag = (BytecodeOffsetTag) u.getTag("BytecodeOffsetTag");
			
			if (lineNumberTag != null) {			
				if (lineNumberTag.getLineNumber() > maxLineNumber) {
					maxLineNumber = lineNumberTag.getLineNumber();
				}
				
				if (lineNumberTag.getLineNumber() < minLineNumber) {
					minLineNumber = lineNumberTag.getLineNumber();
				}
			}
			
			if (bytecodeOffsetTag != null) {
				if (bytecodeOffsetTag.getBytecodeOffset() > maxBytecodeNumber) {
					maxBytecodeNumber = bytecodeOffsetTag.getBytecodeOffset();
				}
				
				if (bytecodeOffsetTag.getBytecodeOffset() < minBytecodeNumber) {
					minBytecodeNumber = bytecodeOffsetTag.getBytecodeOffset();
				}
			}
		}
		
		log.info("Min Line: " + minLineNumber);
		log.info("Max Line: " + maxLineNumber);
		log.info("Min Bytecode: " + minBytecodeNumber);
		log.info("Max Bytecode: " + maxBytecodeNumber);
		return new Result(maxLineNumber-minLineNumber+1, maxBytecodeNumber-minBytecodeNumber+1, b.getUnits().size());
	}
	
	private class Result {
		private int javaLines = 0;
		private int bytecodeLines = 0;
		private int jimpleLines = 0;
		
		public Result(int javaLines, int bytecodeLines, int jimpleLines) {
			super();
			this.javaLines = javaLines;
			this.bytecodeLines = bytecodeLines;
			this.jimpleLines = jimpleLines;
		}
		public int getJavaLines() {
			return javaLines;
		}
		public int getBytecodeLines() {
			return bytecodeLines;
		}
		public int getJimpleLines() {
			return jimpleLines;
		}
	}
}
