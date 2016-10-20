package ar.uba.dc.tools.instrumentation.resource.tracker;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Body;
import soot.BodyTransformer;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.CaughtExceptionRef;
import soot.jimple.IdentityStmt;
import soot.jimple.IntConstant;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.Jimple;
import soot.jimple.NewArrayExpr;
import soot.jimple.NewExpr;
import soot.jimple.NewMultiArrayExpr;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.jimple.toolkits.callgraph.Edge;
import soot.util.Chain;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.method.information.RuleBasedMethodInformationProvider;
import ar.uba.dc.analysis.common.method.information.rules.Rule;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.impl.DefaultMethodDecorator;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.map.HashMultiMap;
import ar.uba.dc.util.collections.map.MultiMap;

@Deprecated
public class Instrumenter  extends BodyTransformer {
	
	private static Log log = LogFactory.getLog(Instrumenter.class);
	private static final String MAIN_METHOD_SIGNATURE = "void main(java.lang.String[])";
	private static final String EXIT_PROGRAM_METHOD_SIGNATURE = "void exit(int)";
	private static final String EXIT_PROGRAM_SIGNATURE = "void exitProgramEvent(java.lang.String)";
	private static final String ENTER_METHOD_SIGNATURE = "void enter(java.lang.String,java.lang.String,java.lang.String)";
	private static final String EXIT_METHOD_SIGNATURE = "void exit(java.lang.String)";
	private static final String CAUGHT_EXCEPTION_SIGNATURE = "void caughtExceptionEvent(java.lang.String)";
	private static final String NEW_OBJECT_SIGNATURE = "void newObjectEvent(java.lang.String,int)";
	private static final String ANALIZABLE_CALL_SIGNATURE = "void analizableCallEvent(java.lang.String,java.lang.String,int)";
	private static final String UNANALIZABLE_CALL_SIGNATURE = "void unanalizableCallEvent(java.lang.String,java.lang.String,int,int,int,int)";
	public static final String LISTENER_CLASS = "ar.uba.dc.tools.instrumentation.resource.tracker.ResourceTrackerListener";
	
	private String mainClass;
	private SummaryRepository<EscapeSummary, SootMethod> repository;
	private Integer escapeSensitivity;
	private Map<Node, Integer> hpAlias = new HashMap<Node, Integer>();
	private Integer hpCounter = 0;
	private MultiMap<SootMethod, Node> hpByMethod = new HashMultiMap<SootMethod, Node>();
	
	private RuleBasedMethodInformationProvider informationProvider;
	
	private InvariantProvider invariantProvider;
	
	//private static final String JLAYER_HUFFMANCODETAB_STATIC_CONSTRUCTOR = "<javazoom.jl.decoder.huffcodetab: void <clinit>()>";
	//private Integer lastArrayFound = null;
	
	private Instrumenter(String mainClass, SummaryRepository<EscapeSummary, SootMethod> repository, Integer escapeSensitivity, RuleBasedMethodInformationProvider informationProvider, InvariantProvider invariantProvider) {
		this.mainClass = mainClass;
		this.repository = repository;
		this.escapeSensitivity = escapeSensitivity;
		this.informationProvider = informationProvider;
		this.invariantProvider = invariantProvider;
	}
	
	public static Instrumenter v(String mainClass, SummaryRepository<EscapeSummary, SootMethod> repository, Integer escapeSensitivity, RuleBasedMethodInformationProvider informationProvider, InvariantProvider invariantProvider) {
		return new Instrumenter(mainClass, repository, escapeSensitivity, informationProvider, invariantProvider);
	}
	
	@SuppressWarnings("unchecked")
	protected void internalTransform(Body body, String phaseName, Map options) {
		//lastArrayFound = null;
		
		SootMethod sootMethod = body.getMethod();
		
		EscapeSummary summary = repository.get(sootMethod);
		
		if (summary != null) {
			Chain<Unit> units = body.getUnits();
		
			log.info("Analizando..." + body.getMethod().toString());			
			Set<Node> escaping = summary.getEscaping();
			List<CallStatement> callStatements = new DefaultMethodDecorator().decorate(sootMethod).getCallStatements();
			Iterator<Unit> stmtIt = units.snapshotIterator();
			boolean enterEventInserted = false;
			while (stmtIt.hasNext()) {
				Stmt next = (Stmt) stmtIt.next();
				// Para insertar la llamada al evento de entrada debemos esperar a pasar las primeras asignaciones de tipo "var = @this" y "param1 = @param1"
				if(!(next instanceof IdentityStmt) && !enterEventInserted) {
					if (sootMethod.getSubSignature().equals(MAIN_METHOD_SIGNATURE) && sootMethod.getDeclaringClass().getName().equals(mainClass)) {
						units.insertBefore(addMainEnterEvent(sootMethod), next);
					} else {
						units.insertBefore(addMethodEnterEvent(sootMethod), next);
					}
					enterEventInserted = true;
				}
				processStmt(body, units, next, summary, escaping, callStatements);
			}
		} else {
			log.info("Ignorando..." + body.getMethod().toString());
		}
	}
	
	private List<Unit> addMainEnterEvent(SootMethod method) {
		return this.addMethodEnterEvent(method);
	}

	private List<Unit> addMethodEnterEvent(SootMethod method) {
		List<Integer> temporals = new LinkedList<Integer>();
		List<Integer> residuals = new LinkedList<Integer>();
		getPartitions(method, temporals, residuals);
		
		String temporal = StringUtils.join(temporals, ",");
		String residual = StringUtils.join(residuals, ",");
		
		return this.newStaticInvokeExpr(ENTER_METHOD_SIGNATURE, StringConstant.v(method.getSignature()), StringConstant.v(temporal), StringConstant.v(residual));
	}
	
	private List<Unit> newStaticInvokeExpr(String methodDescription, Value... values) {
		List<Unit> code = new Vector<Unit>();
		SootMethod toCall = this.getTargetMethod(methodDescription);
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), Arrays.asList(values));
		Stmt invStmt = Jimple.v().newInvokeStmt(invExpr);
		code.add(invStmt);
		return code;
	}
	
	private SootMethod getTargetMethod(String methodDescription) {
		return SootUtils.getMethod(LISTENER_CLASS, methodDescription);
	}
	
	private void processStmt(Body body, Chain<Unit> units, Stmt s, EscapeSummary summary, Set<Node> escaping, List<CallStatement> callStatements) {
		SootMethod sootMethod = body.getMethod();

		//NewStmt
		if (s instanceof AssignStmt) {
			AssignStmt as = (AssignStmt) s;
			Value exp = as.getRightOp();

			// Si es un NEW se instrumenta como creation site
			if (exp instanceof AnyNewExpr) {
				StmtNode node = new StmtNode(new StatementId(sootMethod, s), true, escapeSensitivity);
				
				Integer hp = getPartition(node, summary);
				
				if (hp != null) {
					List<Unit> instrumentation = null;
					if (exp instanceof NewExpr) {
						instrumentation = this.addNewObjectEvent(node.toString(), hp, body, (NewExpr) exp);
					} else if (exp instanceof NewArrayExpr || exp instanceof NewMultiArrayExpr) {
						instrumentation = this.addNewArrayEvent(node.toString(), hp, body, exp);
					}
					if (instrumentation != null) {
						units.insertBefore(instrumentation, s);
					}
				}
			}
		}
		
		if (s instanceof IdentityStmt) {
			IdentityStmt is = (IdentityStmt) s;
			Value exp = is.getRightOp();
			if (exp instanceof CaughtExceptionRef) {
				units.insertAfter(this.addCaughtExceptionEvent(sootMethod.toString()), s);
			}
			
		}
		
		// Para los calls instrumentamos el mapping de particiones
		if (s.containsInvokeExpr()) {
			InvokeExpr expr = s.getInvokeExpr();
			SootMethod method = expr.getMethod();
			if (method.getDeclaringClass().getName().equals("java.lang.System") && EXIT_PROGRAM_METHOD_SIGNATURE.equals(method.getSubSignature())) {
				units.insertBefore(this.addMainMethodExitEvent(method.toString()), s);
			} else {
				if (expr instanceof InterfaceInvokeExpr || informationProvider.isAnalyzable(method)) {
					log.debug("Analizable call:" + s);
					units.insertBefore(this.addCallEvent(summary, body, s, callStatements), s);
				} else {
					if (!informationProvider.isExcluded(method)) {
						log.debug("Unanalizable call:" + s);
						units.insertBefore(this.addUnanalizableCallEvent(summary, body, s, callStatements), s);
					}
				}
			}
		}

		if (s instanceof ReturnStmt || s instanceof ReturnVoidStmt) {
			// Main => export result
			if(sootMethod.equals(Scene.v().getMainClass().getMethod(MAIN_METHOD_SIGNATURE))) {
				units.insertBefore(addMainMethodExitEvent(sootMethod.getSignature()), s);	
			} else {
				units.insertBefore(addMethodExitEvent(sootMethod.getSignature()), s);	
			}
		}
	}

	private List<Unit> addCaughtExceptionEvent(String caughtInMethod) {
		return this.newStaticInvokeExpr(CAUGHT_EXCEPTION_SIGNATURE, StringConstant.v(caughtInMethod));
	}

	private Integer getPartition(Node target, EscapeSummary summary) {	
		Node finalNode = null;
		
		// A partir del summary me fijo si el nodo escapa o no
		for (Node escapeNode : summary.getNodes()) {
			if (escapeNode.accept(target)) {
				finalNode = escapeNode;
				return getAlias(finalNode, summary.getTarget());
			}
		}
		
		//return lastArrayFound;
		return null;
	}
	
	private List<Unit> addCallEvent(EscapeSummary callerSummary, Body body, final Stmt callStmt, List<CallStatement> callStatements) {
		Set<String> mapping = new TreeSet<String>();
	
		Iterator<Edge> it = Scene.v().getCallGraph().edgesOutOf(callStmt);
		while (it.hasNext()) {
			Edge edge = it.next();
			SootMethod candidateCallee = edge.tgt();
			if (!informationProvider.isExcluded(candidateCallee)) {
				EscapeSummary calleeSummary = repository.get(candidateCallee);
				if (calleeSummary != null) {
					mapping.addAll(buildPartitionMapping(callerSummary, calleeSummary, callStmt));
				}
			}
		}	
	
		String mappingValue = StringUtils.join(mapping, ",");
		Integer captureAll = isCaptureAll(callStmt, callStatements);
		
		return this.newStaticInvokeExpr(ANALIZABLE_CALL_SIGNATURE, StringConstant.v(callStmt.getInvokeExpr().getMethod().toString()), StringConstant.v(mappingValue), IntConstant.v(captureAll));
	}

	private Integer isCaptureAll(final Stmt callStmt,
			List<CallStatement> callStatements) {
		Integer captureAll = 0;
		CallStatement statement = (CallStatement) CollectionUtils.find(callStatements, new Predicate() {
			
			@Override
			public boolean evaluate(Object object) {
				CallStatement call = (CallStatement) object;
				return call.getStatement().equals(callStmt);
			}
		});
		
		if (statement != null && invariantProvider.captureAllPartitions(statement)) {
			captureAll = 1;
		}
		return captureAll;
	}
	
	private List<String> buildPartitionMapping(EscapeSummary callerSummary, EscapeSummary calleeSummary, Stmt callStmt) {
		List<String> mappings = new LinkedList<String>();
		
		for (Node hpCallee : calleeSummary.getNodes()) {
			// El cuerpo del for es el operador bind de EA pero adaptado a no trabajar con partitions y directo con nodos
			
			String mapping = mapPartition(callerSummary, calleeSummary, callStmt, hpCallee);
			if (mapping != null) {
				 mappings.add(mapping);
			}
		}
		
		return mappings;
	}

	private String mapPartition(EscapeSummary callerSummary, EscapeSummary calleeSummary, Stmt callStmt, Node hpCallee) {
		if (!hpCallee.isParam() && !hpCallee.isLoad() && calleeSummary.getEscaping().contains(hpCallee)) {
			// Construyo el nodo en el caller que basicamente es el mismo nodo pero cambiando el contexto (el escape era k-sensitivo)
			Node nodeToBind = bindPartition(callerSummary, callStmt, hpCallee);
			
			if (nodeToBind != null) {
				return getAlias(hpCallee, calleeSummary.getTarget()) + "->" + getAlias(nodeToBind, callerSummary.getTarget());
			}
		}
		
		return null;
	}

	private Node bindPartition(EscapeSummary callerSummary, Stmt callStmt, Node hpCallee) {
		Node escapeNode = hpCallee.clone();
		escapeNode.changeContext(new StatementId(callerSummary.getTarget(), callStmt));
		
		// La particion puede estar como nodo o bien contenida dentro del mismo 
		Node nodeToBind = null;
		for (Node callerNode : callerSummary.getNodes()) {
			if (callerNode.accept(escapeNode)) {
				nodeToBind = callerNode;
				break;
			}
		}
		return nodeToBind;
	}

	private List<Unit> addUnanalizableCallEvent(EscapeSummary callerSummary, Body body, Stmt callStmt, List<CallStatement> callStatements) {
		InvokeExpr expr = callStmt.getInvokeExpr();
		SootMethod method = expr.getMethod();
		Integer captureAll = isCaptureAll(callStmt, callStatements);
		

		EscapeSummary calleeSummary = repository.get(method);
		List<String> mapping = buildPartitionMapping(callerSummary, calleeSummary, callStmt);
		
		Rule rule = informationProvider.findRule(method);
		Integer residual = Integer.valueOf(stripPart(rule.getResources().getResidual()), 10);
		Integer temporal = Integer.valueOf(stripPart(rule.getResources().getTemporal()), 10);
		
		Node residualPartition = null;
		if (informationProvider.hasFreshGraph(method)) {
			log.debug("FRESH GRAPH: " + callStmt);
			
			if (!calleeSummary.getReturnedNodes().isEmpty()) {
				residualPartition = calleeSummary.getReturnedNodes().iterator().next();
			}
		}
		
		if (informationProvider.hasConservativaGraph(method)) {
			log.debug("CONSERVATIVE GRAPH: " + callStmt);
			residualPartition = GlobalNode.node;
    	}
    	
    	if (informationProvider.hasNonConservativaGraph(method)) {
    		log.debug("NON CONSERVATIVE GRAPH: " + callStmt);
    		residualPartition = GlobalNode.node;
    	}  
    	
    	// Particion a la que se le asigna todo el residual		
		Integer residualPartitionAlias = -1;
		Node hpToBind = null;
		
		if (residualPartition != null) {
			bindPartition(callerSummary, callStmt, residualPartition);
		}
		
		// FIXME: Por alguna razon spark no siempre esta pudiendo detectar las llamadas a metodos no analizables. Por ejemplo
    	// no detecta la invocacion a getClass() en el metodo JavaLayerUtils.deserializeArray (linea 122: "Class cls = obj.getClass();"). 
    	// Hay que analizar un poco mas esto.
		// Es el unico caso en que tiene sentido que de null el hpToBind. En lineas generales, nunca deberia ocurrir esto
		if (hpToBind != null) {
			residualPartitionAlias = getAlias(residualPartition, calleeSummary.getTarget());
		}
		
		return this.newStaticInvokeExpr(UNANALIZABLE_CALL_SIGNATURE,
						StringConstant.v(callStmt.getInvokeExpr().getMethod().toString()),
						StringConstant.v(StringUtils.join(mapping, ",")),
						IntConstant.v(residualPartitionAlias),
						IntConstant.v(temporal),
						IntConstant.v(residual),
						IntConstant.v(captureAll));
	}

	private List<Unit> addNewObjectEvent(String insSite, int partition, Body body, NewExpr exp) {		
		return this.newStaticInvokeExpr(NEW_OBJECT_SIGNATURE, StringConstant.v(insSite), IntConstant.v(partition));
	}
	
	@SuppressWarnings("unchecked")
	private List<Unit> addNewArrayEvent(String insSite, Integer partition, Body body, Value exp) {
		// FIXME: Jlayer para el analisis de escape tenemos que comentar unos arreglos. para solucionar esto 
		// en el analisis de consumo especificamos los new de los arreglos con el tamaño total.
		// En la instrumentacion mandamos todos los subarreglos a la particion que contiene la primer dimension. 
		//if (body.getMethod().toString().equals(JLAYER_HUFFMANCODETAB_STATIC_CONSTRUCTOR)) {
		//	lastArrayFound = partition;
		//}
		
		List<Value> newArrayParam = new Vector<Value>();

		if (exp instanceof AnyNewExpr) {
			if (exp instanceof NewArrayExpr) { //Array
				NewArrayExpr newArrExp = (NewArrayExpr) exp;
				newArrayParam.add(newArrExp.getSize());
			} else if (exp instanceof NewMultiArrayExpr) { //MultiArray
				NewMultiArrayExpr newMArrayExp = (NewMultiArrayExpr) exp;
				for (Iterator<Value> iter = newMArrayExp.getSizes().iterator(); iter.hasNext();) {
					Value element = iter.next();
					newArrayParam.add(element);
				}
			}
		}
		
		//Generamos el codigo
		String sMethodtoCall = "void newArrayEvent(java.lang.String,int";
		for (int i = 0; i < newArrayParam.size(); i++)
			sMethodtoCall += ",int";
		sMethodtoCall += ")";
		
		List<Value> args = new Vector<Value>();
		args.add(StringConstant.v(insSite));
		args.add(IntConstant.v(partition));
		args.addAll(newArrayParam);
		return this.newStaticInvokeExpr(sMethodtoCall, args.toArray(new Value[] {}));
	}
	
	private List<Unit> addMainMethodExitEvent(String method) {
		List<Unit> code = new Vector<Unit>();
		code.addAll(this.newStaticInvokeExpr(EXIT_METHOD_SIGNATURE, StringConstant.v(method)));
		code.addAll(this.newStaticInvokeExpr(EXIT_PROGRAM_SIGNATURE, StringConstant.v(method)));
		return code;	
	}
	
	private List<Unit> addMethodExitEvent(String method) {
		return this.newStaticInvokeExpr(EXIT_METHOD_SIGNATURE, StringConstant.v(method));
	}

	private void getPartitions(SootMethod method, List<Integer> temporal, List<Integer> residual) {
		EscapeSummary summary = repository.get(method);
		
		Set<Node> escaping = summary.getEscaping();
		for (Node hp : summary.getNodes()) {
			if (!hp.isParam() && !hp.isLoad()) {
				Integer alias = getAlias(hp, method);
				if (escaping.contains(hp)) {
					residual.add(alias);
				} else {
					temporal.add(alias);
				}
			}
		}
	}
	
	private Integer getAlias(Node hp, SootMethod belongsTo) {
		if (!hpAlias.containsKey(hp)) {
			hpByMethod.put(belongsTo, hp);
			hpAlias.put(hp, nextAlias());
		}
		
		return hpAlias.get(hp);
	}
	
	public void printAliases() {
		Set<SootMethod> methods = new TreeSet<SootMethod>(new Comparator<SootMethod>() {
			public int compare(SootMethod arg0, SootMethod arg1) {
				return arg0.toString().compareTo(arg1.toString());
			}
		});
		methods.addAll(hpByMethod.keySet());
		
		for (SootMethod method : methods) {
			Map<Integer, Node> sortedNodes = new TreeMap<Integer, Node>();
			for (Node hp : hpByMethod.get(method)) {
				sortedNodes.put(hpAlias.get(hp), hp);
			}
			
			for (Entry<Integer, Node> entry : sortedNodes.entrySet()) {
				log.info("<" + method + ", " + entry.getValue().toString() + ">" + " -> " + entry.getKey());
			}
		}
	}
	
	private Integer nextAlias() {
		hpCounter++;
		return hpCounter;
	}
	
	private String stripPart(String value) {
		return value.substring(1, value.length() - 1).trim();
	}
}