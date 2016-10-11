package ar.uba.dc.soot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Body;
import soot.Local;
import soot.PackManager;
import soot.RefLikeType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Transformer;
import soot.Type;
import soot.Unit;
import soot.jimple.AssignStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.ClinitElimTransformer;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import soot.options.Options;
import soot.tagkit.BytecodeOffsetTag;
import soot.tagkit.LineNumberTag;
import soot.util.Chain;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedLifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartition;
import ar.uba.dc.config.Context;

public class SootUtils {

	private static final String SPARK_ALGORITHM = "spark";	
	
	private static Log log = LogFactory.getLog(SootUtils.class);

	
	/**
	 * Retorna el callgraph de los metodos invocados por un metodo de una clase dado
	 * @param className - Clase a la que pertenece el metodo
	 * @param methodSignature - Signatura del metodo para el que queremos recuperar el call graph
	 * @param context - contexto con el cual trabaja la aplicacion. De aqui se saca el classpath a utilizar
	 */
	public static CallGraph getCallGraph(final String className, final String methodSignature, final Context context) {
		if (!Options.v().whole_program()) {
			Options.v().set_whole_program(true);
		}
		Options.v().set_main_class(className);
		
		setClasspath(context);
		
		SootClass mainClass = getClass(className);
		SootMethod mainMethod = mainClass.getMethod(methodSignature);

		mainClass.setApplicationClass();
		Scene.v().setEntryPoints(Arrays.asList(new SootMethod[] { mainMethod }));
		
		Scene.v().loadNecessaryClasses();
		
		if (context.getBoolean(Context.SOOT_FILTER_CALL_GRAPH)) {
			EdgePredicate predicate = context.getFactory().getEdgePredicate();
			if (predicate != null) {
				ReachableMethods.setEdgePredicate(predicate);
			}
		}
		
		if (StringUtils.equalsIgnoreCase(SPARK_ALGORITHM, context.getString(Context.CALLGRAPH_ALGORITHM))) {
			Map<String, String> opt = buildCallGraphOptions(context, Context.SOOT_SPARK_OPTIONS);
			SparkTransformer.v().transform(StringUtils.EMPTY, opt);
		} else {
			Map<String, String> opt = buildCallGraphOptions(context, Context.SOOT_CHA_OPTIONS);
			CHATransformer.v().transform(StringUtils.EMPTY, opt);
		}
		
		CallGraph callgraph = Scene.v().getCallGraph();
		
		if (context.getBoolean(Context.SOOT_CG_TRIM_CLINIT)) {
			// Esto esta presente en el clase CallGraphPack de soot. Lo que hace es eliminar 
			// llamadas a constructores estaticos
			ClinitElimTransformer trimmer = new ClinitElimTransformer();
			for (SootClass cl : Scene.v().getClasses(SootClass.BODIES)) {
	            for( Iterator<SootMethod> mIt = cl.getMethods().iterator(); mIt.hasNext(); ) {
	                final SootMethod m = mIt.next();
	                if( m.isConcrete() && m.hasActiveBody() ) {
	                    trimmer.transform( m.getActiveBody() );
	                }
	            }
	        }
		}

		return callgraph;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> buildCallGraphOptions(Context context, String optionsKey) {
		Map<String, String> opts = new HashMap<String, String>();
		
		List<String> options = (List<String>) context.getList(optionsKey);
		if (options == null || options.isEmpty()) {
			opts.put("enabled", "true");
		} else {
			for (String option : options) {
				if (StringUtils.isNotBlank(option)) {
					String[] parts = option.split(":");
					
					if (parts.length >= 2 && StringUtils.isNotBlank(parts[0])) {
						opts.put(parts[0].trim(), parts[1].trim());
					}
				}
			}
		}
		
		return opts;
	}

	public static SootMethod getMethod(String className, String methodSignature) {
		return getClass(className).getMethod(methodSignature);
	}
	
	public static SootClass getClass(String className) {
		SootClass sootClass = null;
		
		if (!Scene.v().containsClass(className)) {
			sootClass = Scene.v().loadClassAndSupport(className);
		} else {
			sootClass = Scene.v().getSootClass(className);
		}
		
		return sootClass;
	}
	
	public static Local getVariableToAssignResult(Stmt stmt) {
		Local ret = null;
		if (stmt instanceof AssignStmt) {
			AssignStmt assignation = (AssignStmt) stmt;
		    Local v = (Local) assignation.getLeftOp();
		    if (v.getType() instanceof RefLikeType) {
		    	ret = v;
		    }
		}
		
		return ret;
	}

	public static Local getVariableTargetOfCall(InvokeExpr expr) {
		Local target = null;
		if (!(expr instanceof StaticInvokeExpr)) { 
			target = (Local) ((InstanceInvokeExpr) expr).getBase();
		}
		
		return target;
	}
	
	public static Stmt findStatementInMethod(SootMethod method, String statement, Integer lineNumber, Integer bytecodeOffset) {
		Stmt ret = null;
		
		Body body = method.retrieveActiveBody();
		Iterator<Unit> itUnits = body.getUnits().iterator();
		while (itUnits.hasNext() && ret == null) {
			Unit aux = itUnits.next();
			if (aux instanceof Stmt) {
				if (aux.toString().equals(statement)) {
					LineNumberTag lineNumbertag = (LineNumberTag) aux.getTag("LineNumberTag");
					
					if (lineNumbertag == null || lineNumbertag.getLineNumber() == lineNumber.intValue()) {
						BytecodeOffsetTag bytecodeOffsettag = (BytecodeOffsetTag) aux.getTag("BytecodeOffsetTag");
						if (bytecodeOffsettag == null || bytecodeOffsettag.getBytecodeOffset() == bytecodeOffset.intValue()) {
							ret = (Stmt) aux;
						}
					}
				}
			}
		}
			
		return ret;
	}

	public static Chain<Local> getLocals(SootMethod method) {
		return method.retrieveActiveBody().getLocals();
	}	
	
	public static void setClasspath(Context context) {
		String classpath = context.getString(Context.APPLICATION_CLASSPATH);

		if (classpath != null) {
			Scene.v().setSootClassPath(classpath);
		}
	}

	public static Local findLocal(SootMethod methodUnderConversion, String localName, Type localType) {
		Local ret = null;
		
		Chain<Local> locals = SootUtils.getLocals(methodUnderConversion);
		Iterator<Local> itLocal = locals.iterator();
		while (itLocal.hasNext() && ret == null) {
			Local local = itLocal.next();
			if (local.getName().equals(localName) && local.getType().equals(localType)) {
				ret = local;
			}
		}
		
		return ret;
	}

	public static void insertTransformer(String pack, String phaseName,Transformer transformer) {
		 PackManager.v().getPack(pack).add(new Transform(phaseName, transformer));
	}
	
	
	
	
	public static List<String> buildOptions(Context ctx, String mainClass, String mainMethod) {
		List<String> options = new LinkedList<String>();
		
		if (ctx.getBoolean(Context.SOOT_OPTION_KEEP_LINE_NUMBER)) {
			options.add("-keep-line-number");
		}
		
		if (ctx.getBoolean(Context.SOOT_OPTION_BYTECODE_OFFSET)) {
			options.add("-keep-offset");
			//options.add("-keep-bytecode-offset");
		}
		
		if (ctx.getBoolean(Context.SOOT_OPTION_APP)) {
			options.add("-app");
		}
		
		// No genera output propio de soot
		options.add("-f");
		options.add("none");
		
		// OutputFolder
		options.add("-d");
		options.add(ctx.getString(Context.OUTPUT_FOLDER));

		// Phase options
		for (Object opt : ctx.getList(Context.SOOT_PHASE_OPTIONS)) {
			String[] parts = opt.toString().split(":");
			options.add("-p");
			options.add(parts[0]);
			options.add(parts[1] + ":" + parts[2]);
		}
		
		String callgraphType = StringUtils.EMPTY;
		// Callgraph options
		Map<String, String> cgOpt = new HashMap<String, String>();
		if (StringUtils.equalsIgnoreCase(SPARK_ALGORITHM, ctx.getString(Context.CALLGRAPH_ALGORITHM))) {
			callgraphType = "spark";
			cgOpt = buildCallGraphOptions(ctx, Context.SOOT_SPARK_OPTIONS);
		} else {
			callgraphType = "cha";
			cgOpt = buildCallGraphOptions(ctx, Context.SOOT_CHA_OPTIONS);
		}

		if (cgOpt.size() > 0) {
			String cgCmdOptions = StringUtils.EMPTY;
			options.add("-p");
			options.add("cg." + callgraphType);
			for (Entry<String, String> entry : cgOpt.entrySet()) {
				cgCmdOptions += "," + entry.getKey() + ":" + entry.getValue();
			}
			options.add(cgCmdOptions.substring(1));
		}
		
		options.add("-w");
		
		options.add("-allow-phantom-refs");
		
		options.add("-soot-classpath");
		options.add(ctx.getString(Context.APPLICATION_CLASSPATH));
		
		options.add("-main-class");
		options.add(mainClass);
		options.add(mainClass);
		
		return options;
	}
	
	 static public void setPhaseOptions(String pack, String phaseName, String options) {
		 soot.PackManager.v().getPack(pack).get(phaseName).setDefaultOptions("on "+options);
	 }


	@SuppressWarnings("unchecked")
	public static Set<IntermediateRepresentationParameter> getParameters(SootMethod target, boolean withType) {

		List<Type> parameterTypes = new ArrayList(target.getParameterTypes());	
		
		Set<IntermediateRepresentationParameter> parameters = new LinkedHashSet<IntermediateRepresentationParameter>();
		
		Body body;
		
		try
		{
			body = target.getActiveBody();
		}
		catch(RuntimeException ex)
		{
			log.debug("Body is null");
			return parameters;
		}		
		
		String lines[] = body.toString().split("\\r?\\n");
		
		for (String line: lines)
		{
			for(int i = 0; i < parameterTypes.size(); i++)
			{
				String numbered_parameter ="@parameter" + i;
				if(line.contains(numbered_parameter))
				{
					String parameter = line.split(" := ")[0].trim();
					//TODO: tal vez podria ir sacando los que ya proceso y usar un map parameterType -> numero
					String parameterType = parameterTypes.get(i).toString();	
					boolean isRefLikeType = parameterTypes.get(i) instanceof RefLikeType ;					
					
					IntermediateRepresentationParameter p;
					if(withType)
					{
						p = new IntermediateRepresentationParameter(parameter, parameterType, isRefLikeType);
					}
					else
					{
						p = new IntermediateRepresentationParameter(parameter, isRefLikeType);
					}
					parameters.add(p);
					
				}
			}
		}
		
		return parameters;
	}

	public static List<Invocation> getInvocations(Statement stmt, boolean isCallStatement, CallGraph callGraph, LifeTimeOracle lifetimeOracle) {
		
		List<Invocation> invocations = new LinkedList<Invocation>();
		if(isCallStatement)
		{
			CallStatement callStmt = (CallStatement) stmt;
			Iterator<Edge> it = callGraph.edgesOutOf(callStmt.getStatement());
			
			while (it.hasNext()) {
				Edge edge = it.next();
				SootMethod m = edge.tgt();
				invocations.add(new Invocation(m));
			}
		}
		else
		{
			NewStatement newStmt = (NewStatement) stmt;
			
			HeapPartition hp = lifetimeOracle.getPartition(newStmt);
			//PointsToHeapPartition hp = new PointsToHeapPartition();
			
			invocations.add(new Invocation(newStmt, hp));
		}
		return invocations;		
		
	}
	
	
	/*@SuppressWarnings("unchecked")
	public Set<IntermediateRepresentationParameterWithType> getParametersWithType(SootMethod target, Class classname ) {

		return this.getParameters(target, classname);
	}*/
}
