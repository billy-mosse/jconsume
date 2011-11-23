package ar.uba.dc.tools.invariant;

import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.memory.code.MethodBody;
import ar.uba.dc.analysis.memory.code.Statement;
import ar.uba.dc.analysis.memory.code.impl.DefaultMethodDecorator;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;

import soot.SootMethod;
import soot.options.Options;

public class MethodDummper {

	public static void dump(String className, String subsignature) {
		dump(SootUtils.getMethod(className, subsignature), System.out);
	}
	
	public static void dump(String className, String subsignature, PrintStream stream) {
		dump(SootUtils.getMethod(className, subsignature), stream);
	}
	
	public static void dump(SootMethod method) {
		dump(method, System.out);
	}
	
	public static void dump(SootMethod method, PrintStream stream) {
		MethodBody body = new DefaultMethodDecorator().decorate(method);
		
		for (Statement stmt : body.getStatements()) {
			stream.println(stmt);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String className = args[0];
		String subsignature = args[1];
		
		String propertiesFile = null;
		
		if (args.length >= 3 && StringUtils.isNotBlank(args[2])) {
			propertiesFile = args[2];
		}
		
		Context context = ContextFactory.getContext(propertiesFile);
		
		Options.v().set_soot_classpath(context.getString(Context.APPLICATION_CLASSPATH));
		
		dump(className, subsignature);
	}
}