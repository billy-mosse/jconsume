package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.invariantwriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CCReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CSReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CallSiteMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CreationSiteMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductiveVariablesInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductivesReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InvariantReader;

/*import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.SimpleGraph;*/

//import com.sun.org.apache.bcel.internal.generic.LoadClass;

// sun.awt.X11.XLayerProtocol;
import soot.Body;
import soot.BodyTransformer;
import soot.PackManager;
import soot.SootClass;
import soot.Transform;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import soot.tagkit.BytecodeOffsetTag;
import soot.tagkit.LineNumberTag;
import soot.tagkit.PositionTag;
import soot.tagkit.SourceLineNumberTag;
import soot.tagkit.SourceLnPosTag;
import soot.tagkit.Tag;
//import sun.text.normalizer.Replaceable;
//import sun.util.BuddhistCalendar;

public class SpecInvariantWriter {
	public static Map<String, Integer> offsets = new HashMap<String, Integer>();

	InvariantReader ir = new InvariantReader(true);
	CCReader ccr = new CCReader();
	CSReader csr = new CSReader();
	InductivesReader indr = new InductivesReader();

	String strClass;
	String destinationPath;
	PrintStream out;
	XMLSpecWriter xmlSpec;
	String fileName = "";
	private SootClass sootClass;

	private String currentMethod;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String[] optsDefault = { "-app", "-f", "n",
				// "-d","\.",
				"-allow-phantom-refs", "-src-prec", "class", "-keep-line-number", "-keep-bytecode-offset", "-p", "jb",
				"use-original-names:true", "-p", "jb.a", "enabled:false", "-p", "jb.ne", "enabled:false", "-p",
				"jb.uce", "enabled:false", "-p", "jb.dae", "enabled:false", "-p", "jb.ule", "enabled:false", "-p",
				"jb.cp", "enabled:false", };
		String[] opts = new String[optsDefault.length + args.length];
		for (int i = 0; i < optsDefault.length; i++)
			opts[i] = optsDefault[i];
		// for (int i = 0; i < args.length; i++)
		// opts[i + optsDefault.length] = args[i];
		opts[optsDefault.length] = args[0].substring(args[0].lastIndexOf("/") + 1);

		PackManager.v().getPack("jtp").add(new Transform("jtp.lineNumberAdder", LineNumbers.v()));
		// soot.Main.main(opts);

		SpecInvariantWriter siw = new SpecInvariantWriter();

		int posClass = args[0].lastIndexOf("/");
		siw.strClass = args[0];

		if (posClass == -1) {
			siw.destinationPath = "";
		} else {
			siw.destinationPath = args[0].substring(0, posClass + 1);
			// siw.strClass=args[0].substring(posClass+1);
		}
		if (args.length > 1)
			siw.destinationPath = args[1];

		
		System.out.println(siw.destinationPath);
		siw.fetchInvariants();
		// TO-DO: sacar el nombre de la clase del string

		// siw.fileName = siw.destinationPath+siw.strClass+".spec";
		//
		// try {
		// siw.out = new PrintStream(siw.fileName);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// siw.out = System.out;
		siw.writeSpecs();

	}

	private void writeSpecs() {
		xmlSpec = new XMLSpecWriter();
		// xmlSpec.writeXmlHeader();
		String currentClass = "";
		String currentMethod = "";
		boolean hasMethods = false;

		Set ms = csr.getMethods();
		for (Object o : ms) {
			String classAndMethod = (String) o;

			String className = extractClassName(classAndMethod);
			String method = extractFullMethodSignature(classAndMethod);

			if (!className.equals(currentClass)) {
				if (currentClass.length() != 0) {
					if (hasMethods) {
						endProcessMethod(currentMethod);
						hasMethods = false;
					}
					endProcessClass(className);

				}

				processNewClass(className);
				currentClass = className;
				processNewMethod(method);
				hasMethods = true;
				currentMethod = method;
			} else if (!classAndMethod.equals(currentMethod)) {
				if (currentMethod.length() != 0)
					endProcessMethod(currentMethod);
				processNewMethod(method);
				hasMethods = true;
				currentMethod = method;
			}

			// System.out.println(classAndMethod);
			Set css = new TreeSet(csr.getCSs(classAndMethod, ccr));
			if (css != null) {
				int newOffset = 0;
				int callOffset = 0;
				for (Object o2 : css) {
					CreationSiteMapInfo cs = (CreationSiteMapInfo) o2;
					// System.out.println("-- cs:"+cs);

					boolean isCall = cs.isCall();
					if (isCall) {
						if (cs.getOrder() == -1)
							cs.setOffset(newOffset);
						processCall(cs);
						newOffset++;
					} else {
						if (cs.getOrder() == -1)
							cs.setOffset(callOffset);
						processCreationSite(cs);
						callOffset++;
					}

				}
			}
		}
		if (currentMethod.length() != 0)
			endProcessMethod(currentMethod);
		if (currentClass.length() != 0)
			endProcessClass(currentClass);

		// xmlSpec.writeXmlFooter();
	}

	private void endProcessMethod(String methodName) {
		xmlSpec.writeMethodFooter(methodName);

	}

	private void endProcessClass(String className) {
		xmlSpec.writeClassFooter(className);
		xmlSpec.writeXmlFooter();
		out.close();
	}

	private void processNewClass(String className) {
		// sootClass = soot.Scene.v().loadClassAndSupport(className);

		int pos = className.lastIndexOf(".");
		String packagePath = pos != -1 ? className.substring(0, pos) : "";
		String onlyClass = className.substring(pos + 1);

		// fileName = destinationPath+className+".spec";
		
		
		fileName = destinationPath + "/" + packagePath.replace(".", "/") + "/" + onlyClass + ".spec";
		
		
		File srcFile = new File(fileName);
		
		if (!srcFile.getParentFile().exists()) {
			srcFile.getParentFile().mkdirs();
		}

		//boolean status = new File(destinationPath + "/" + packagePath).mkdir();
		try {
			out = new PrintStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		xmlSpec.writeXmlHeader();
		// System.out.println("Class:"+className);
		xmlSpec.writeClassHeader(className);
	}

	private void processNewMethod(String methodName) {
		// System.out.println("- Method:"+methodName);
		// Usa los "init"
		List params = ccr.getMethodParams(methodName);
		// System.out.println(params);
		xmlSpec.writeMethodHeader(methodName, params);

		currentMethod = methodName;

		// String mn = methodName.substring(methodName.indexOf(":")+1);
		// mn = mn.substring(0,mn.length()-1).trim();
		//
		// SootMethod sm = sootClass.getMethod(mn);
		// decorate(sm);
	}

	private String extractClassName(String m) {
		return m.substring(0, m.lastIndexOf("_"));
	}

	private String extractFullMethodSignature(String m) {
		return m.substring(m.lastIndexOf("_") + 1);
	}

	private String extractMethodSignature(String fms) {
		String res = fms.substring(fms.lastIndexOf(":") + 1);
		res = res.substring(1, res.length() - 1);
		return res;
	}

	private int extactLineNumberFromInsSite(String is) {
		//System.out.println("entrando");
		int pos_c  = is.lastIndexOf("c");
		int pos_dash  = is.lastIndexOf("_");
		String sln = null;
		if(pos_c!= -1 && pos_c > pos_dash)
			sln = is.substring(pos_dash + 1, pos_c);
		else
			sln = is.substring(pos_dash + 1);
		//System.out.println("saliendo");
		return Integer.parseInt(sln);
		
	}

	private void processCreationSite(CreationSiteMapInfo cs) {
		xmlSpec.writeCreationSite(cs);
	}

	private void processCall(CreationSiteMapInfo cs) {

		CallSiteMapInfo ccInfo = ccr.getCallSiteInfo(cs.getInsSite());
		xmlSpec.writeCallSite(cs, ccInfo);

	}

	void fetchInvariants() {
		try {
			ccr.analyze(strClass + ".cc");
			csr.analyze(strClass + ".cs");
			ir.analyze(strClass + ".txt");
			indr.analyze(strClass + ".ind");
		} catch (Exception e) {
			System.out.println("error:" + e.toString());
		}

	}

	class XMLSpecWriter {
		int level = 0;

		private void indent() {
			out.print(sIndent(level));
		}

		private String sIndent(int c) {
			StringBuffer res = new StringBuffer();
			for (int i = 0; i < level; i++)
				res.append("\t");
			return res.toString();
		}

		private void writeXmlHeader() {
			indent();
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<spec>");
			level++;
		}

		private void writeXmlFooter() {
			level--;
			indent();
			out.println("</spec>");
		}

		private void writeClassHeader(String className) {
			indent();
			out.print(" <class decl=");
			out.print("\"");
			out.print(className);
			out.print("\"");
			out.println(">");
			level++;
		}

		private void writeClassFooter(String className) {
			level--;
			indent();
			out.println("</class>");
		}

		private void writeMethodHeader(String methodName, List params) {
			indent();
			out.print(" <method decl=");
			out.print("\"");
			out.print(XMLizar(extractMethodSignature(methodName)));
			out.print("\"");
			out.println(">");
			level++;

			indent();
			out.print("<relevant-parameters>");
			out.print(extractStringVarList(params));
			out.println("</relevant-parameters>");
		}

		private void writeMethodFooter(String methodName) {
			level--;
			indent();
			out.println("</method>");
		}

		private void writeCreationSiteHeader(CreationSiteMapInfo cs) {
			int order = getOrder(cs);
			indent();
			out.print(" <creation-site ");
			out.print("offset=" + "\"" + order + "\"");
			out.print(" srccode-offset=" + "\"" + extactLineNumberFromInsSite(cs.getInsSite()) + "\"");
			out.println(">");
			level++;
		}

		private void writeCreationSiteFooter(CreationSiteMapInfo cs) {
			level--;
			indent();
			out.println(" </creation-site>");

		}

		private void writeCallSiteHeader(CreationSiteMapInfo cs) {

			indent();
			out.print(" <call-site ");
			out.print("offset=" + "\"" + getOrder(cs) + "\"");
			out.print(" srccode-offset=" + "\"" + extactLineNumberFromInsSite(cs.getInsSite()) + "\"");
			out.println(">");
			level++;
		}

		private int getOrder(CreationSiteMapInfo cs) {
			String query = currentMethod + "@" + cs.getInsSite().substring(cs.getInsSite().indexOf("_") + 1);
			Integer offSet = SpecInvariantWriter.offsets.get(query);
			int order = offSet != null ? offSet.intValue() : cs.getOrder();
			return order;
		}

		private void writeCallSiteFooter(CreationSiteMapInfo cs) {
			level--;
			indent();
			out.println(" </call-site>");

		}

		private void writeCallSite(CreationSiteMapInfo cs, CallSiteMapInfo ccInfo) {
			String l = cs.getInsSite();

			writeCallSiteHeader(cs);
			writeRevelantVariables(cs.getVars());

			// Lo calculo antes para tener los bindinds
			String inv = getInvariantForSite(l, cs.getVars());
			if (inv == null)
				inv = "";
			Set newInductives = new HashSet();
			String callInv = generateCallInvariant(ccInfo, inv, newInductives);

			InductiveVariablesInfo inductives = indr.getiInfo(cs.getInsSite());
			List inductivas = new Vector();
			if (inductives != null) {
				inductivas = InductiveVariablesInfo.filterInductives(cs.getVars(), inductives, "");
			} else
				inductivas.addAll(cs.getVars());
			// inductivas.addAll(newInductives);
			writeInductiveVariables(inductivas, newInductives);

			// writeInductiveVariables(inductives);

			writeCallee(ccInfo);

			writeInvariant(callInv);

			writeCallSiteFooter(cs);
		}

		
		//Algo importante: a partir del archivo .cc genera el binding
		//en el .cc deberian aparecer las relevantes, de alguna manera, en el orden correcto
		//uh esto no va andar
		private String generateCallInvariant(CallSiteMapInfo ccInfo, String inv, Set newInductives) {
			StringBuffer callInvariant = new StringBuffer(inv);
			if (ccInfo != null) // && ccInfo.methodCallee.indexOf("add(")!=-1)
			{
				int count = 0;
				// System.out.println("\t cc:"+ccInfo);
				List args = ccInfo.getArgs();
				List params = ccInfo.getParams();
				List paramsInit = ccInfo.getParamsInit();
				for (int i = 0; i < args.size(); i++) {
					String arg = (String) args.get(i);
					String paramInit = (String) paramsInit.get(i);
					String binderParam = "$t." + removeNonJavaSymbols(paramInit);

					if (paramInit.trim().length() > 0) {
						if ((i == 0 && inv.length() > 0) || count > 0) {
							callInvariant.append(", ");
						}
						callInvariant.append(binderParam + " == " + removeNonJavaSymbols(arg));
						count++;
						newInductives.add(binderParam);
					}

				}
			}
			String callInv = callInvariant.toString();
			return callInv;
		}
		
	/*	
		public String[] get_vars_s(String s, List vars, String separator)
		{
			String[] vars_s = s.split(separator);
			
			//assert(vars_s.length == 2); //TODO: podria ser mas? SI
			
			
			//TODO: trim te modifica el string directamente o te crea una copia?
			
			for(int i = 0; i < vars_s.length; i++)
			{
				vars_s[i] = vars_s[i].trim();
			}
			return vars_s;
		}
		

		
		//Creo que voy a armar un solo grafo con NODOS = VARIABLES, EQUALITY_RELATION, o NOT_EQUALITY_RELATION
		
		//no, tengo que hacer Abstract Syntax Tree
		public RichConstraint filterByQuotient(List vars, List inductiveVars, String inv)
		{
			//Igual daikon no se si te devuelve ORs. Deberia!
			if (inv.contains("||"))
				return null;
			
			
			List<String> constraints = Arrays.asList(inv.split("&&"));

			//List<String> equalities = new ArrayList<String>();
			
			//no es lo mismo que inequalities
			List<String> not_equalities = new ArrayList<String>();
			
			//podria hacer que la key sea un INT pero ya fue
			//Prefiero gastar mas memoria que tiempo
			
			UndirectedGraph<String, RelationshipEdge> g =
				      new SimpleGraph<String, RelationshipEdge>(
				    		  new ClassBasedEdgeFactory<String, RelationshipEdge>(RelationshipEdge.class));
			
			
			//TODO: cambiar los assert por throw exception
			Map<String, List<String>> constraints_affecting_variable = new HashMap<String, List<String>>();
			for(String s : constraints)
			{
				//Me armo el grafo
				//TODO grande: ojo que podria ser una igualdad con suma. a == b +4
				//Bah, igual ahi esta todo bien, pero si fueran cosas al cuadrado no.
				//Igual por suerte daikon te genera solo invariantes lineales ^^
				
				
				//MULTIEJE entre varios nodos....?
				if (s.contains("=="))
				{
					//creo que esto lo va a separar por igualdad, operaciones y numeros.
					//asi me quedo solo con las variables
					String[] vars_s = get_vars_s(s, vars, "==|\\+|\\-|\\*|\\/|\\[0-9]");
					g.addEdge(vars_s[0], vars_s[1],
							new RelationshipEdge<String>("James", "John", "hol"));
				}
				else
				{
					//Me armo el mapa v |--> inequalities que tienen a v
					
					
					//TODO: claramente esto podria estar mejor
					String[] vars_s = null;
					vars_s = get_vars_s(s,vars, "<=|\\>=|\\<|\\>|\\+|\\-|\\*|\\/|\\[0-9]");

					String var_1 = vars_s[0];
					String var_2 = vars_s[1];
					
					
					List<String> l1 = constraints_affecting_variable.get(var_1);
					List<String> l2 = constraints_affecting_variable.get(var_2);

					
					//TODO: Te lo devuelve por referencia?
					if(l1==null)
					{
						List<String> l1_ = new ArrayList<String>();
						l1 = l1_;
					}
					l1.add(s);
					
					if(l2==null)
					{
						List<String> l2_ = new ArrayList<String>();
						l2 = l2_;
					}
					l2.add(s);
				}
			}
			
			
			
			List<Set<String>> connected_components = get_connected_components(g);
			
			
			//Ahora, por cada connected component, tengo que elegir un representante
			//y filtrar tambien las constraints!
			//Si borro de constraints_affecting_variable las keys que no voy a usar mas
			//Las constraints que me quedan van a ser el JOIN con un and de las constraitns sobrevivientes
			
			
			//OJO: estoy filtrando las variables. Las inductivas son variables asi que cuando filtre variables
			//Despues tambien filtro las inductivas en base a si estan en las nuevas variables
			
			
			List<String> new_variables = new ArrayList<String>();
			List<String> new_inductives = new ArrayList<String>();			
			List<String> new_constraints = new ArrayList<String>();
			
			for(Set<String> connected_component : connected_components)
			{
				//ya aca puedo tomar un representante siempre, de ultima en las desigualdades
				String repr = get_canonical_repr(connected_component);
				
				//TODO: ojo que aca si a == b + 4,   y tengo 1 <= b <= n, y elegi a como canonico,
				//tengo el laburo de cambiar el invariante oir 1 <= a-4 <= n
				//O sea, tengo que invertir la relacion
				//seguro eso sale con una libreria matematica
				//nop.
				RichConstraint r = filter_not_equalities_of_cc_by_repr(connected_component, repr, constraints_affecting_variable);
				new_variables.addAll(r.getVariables());
				new_inductives.addAll(r.getInductives());
				new_constraints.add(r.getConstraint());
				
			}
			
			return null;
			
		}
		
		private RichConstraint filter_not_equalities_of_cc_by_repr(Set<String> connected_component,
				String repr, Map<String, List<String>> constraints_affecting_variable) {

			for(Map.Entry<String, List<String>> entry : constraints_affecting_variable.entrySet())
			{
				if (connected_component.contains(repr))
				{
					for(String constraint: entry.getValue())
					{
						//reemplazo las variables de la componente conexa por el representante?
						//No, porque podrian no ser iguales pero diferir por algo
					}
				}
			}
			
			
			return null;
		}

		//Me intento conseguir uno que no sea ni temp ni generado por la instrumentacion
		//Si no hay, intento conseguir uno que no sea temp
		//Si no hay, es que son todos temp
		//Agarro el primero
		private String get_canonical_repr(Set<String> connected_component) {
			
			String the_chosen_one = null;
			int level_of_awesomeness = 0;
			for(String var : connected_component)
			{
				if (level_of_awesomeness == 0)
				{
					the_chosen_one = var;
					level_of_awesomeness = 1;
				}
				if (level_of_awesomeness == 1 && !var.startsWith("__"))
				{
					the_chosen_one = var;
					level_of_awesomeness = 2;
				}
				
				if (level_of_awesomeness == 2 && !var.startsWith("size") && !var.startsWith("b_"))
				{
					the_chosen_one = var;
					level_of_awesomeness = 3;
				}
			}
			
			
			//seguro que no es null...si la componente conexa no es vacia, pero nunca va a ser vacia
			return the_chosen_one;
		}

		//TODO: esto es feo
		protected Set<String> marked  = new HashSet<String>();
		protected Set<String> temporarily_marked  = new HashSet<String>();
		
		
		
		
		private List<Set<String>> get_connected_components(UndirectedGraph<String, RelationshipEdge> g) {
			List<Set<String>> connected_components = new ArrayList<Set<String>>();

			
			Set<String> vertices = g.vertexSet();
			
			for(String v : vertices)
			{
				if(marked.contains(v))
					continue;
				
				//lo inicializo de nuevo
				temporarily_marked = new HashSet<String>();
				
				Set<String> connected_component = new HashSet<String>();
				
				//si estoy aca estoy en una nueva componente conexa

				connected_component.addAll(visit(v,g));
				
				connected_components.add(connected_component);
			}
			
			return connected_components;

		}

		private Set<String> visit(String v, UndirectedGraph<String, RelationshipEdge> g) {		
			
			//uso un temporarily porque si el grafo es grande quiero evitar lo mas posible leer el marked,
			//porque va a terminar teniendo todos los nodos
			temporarily_marked.add(v);
			Set<String> c = new HashSet<String>();
			for(RelationshipEdge e: g.edgesOf(v))
			{
				String source = g.getEdgeSource(e);
				if(source != v && temporarily_marked.contains(source))
					//pregunto si esta en marked porque tal vez ya lo procese...dentro del connected component
					//TODO: optimizacion: en grafos grandes podria mantener 
				{
					c.add(source);
					visit(source, g);
				}
				else
				{				
					String target = g.getEdgeTarget(e);
					if(target != v && temporarily_marked.contains(target))
					{
						c.add(target);
						visit(target, g);
					}
				}
			}			
			return c;
		}*/

		private void writeCreationSite(CreationSiteMapInfo cs) {
			String l = cs.getInsSite();
			String inv = getInvariantForSite(l, cs.getVars());
			writeCreationSiteHeader(cs);
			List vars = cs.getVars();
			Set paramsArr = new HashSet();
			int cont = 0;
			String invArray = "";
			for (Iterator iter = cs.getArrayParams().iterator(); iter.hasNext();) {
				cont++;
				String parameterArray = "aux" + "_" + Integer.toString(cont);
				String value = (String) iter.next();
				if (cont > 1)
					invArray = invArray + " && ";
				invArray += "1<=" + parameterArray;
				if (!Character.isDigit(value.charAt(0))) {
					// value =
					// l.replaceAll("\\.","_")+"_"+removeNonJavaSymbols(value);
					value = removeNonJavaSymbols(value);

				}
				invArray = invArray + " && " + parameterArray + "<=" + value;
				paramsArr.add(parameterArray);
				vars.add(parameterArray); //esto esta bien?
			}

			

			

			InductiveVariablesInfo inductives = indr.getiInfo(cs.getInsSite());
			List inductiveVars = new Vector();
			if (inductives != null) {
				inductiveVars = InductiveVariablesInfo.filterInductives(cs.getVars(), inductives, "");
			} else
				inductiveVars.addAll(cs.getVars());
			inductiveVars.addAll(paramsArr);

			// writeInductiveVariables(inductives);
			if (!invArray.isEmpty()) {
				if (inv == null || inv.isEmpty())
					inv = invArray;
				else
					inv = inv + " && " + invArray;
			}


			//filterByQuotient(vars, inductiveVars, inv);
			
			writeRevelantVariables(vars);
			writeInductiveVariables(inductiveVars);
			
			writeInvariant(inv);
			writeCreationSiteFooter(cs);
		}

		private void writeCallee(CallSiteMapInfo ccInfo) {
			if (ccInfo != null) {
				indent();
				out.print("<callee>");
				String callee = ccInfo.getMethodCallee();
				callee = callee.substring(1, callee.length() - 1);
				out.print(XMLizar(callee));
				out.println("</callee>");
			}

		}

		private void writeRevelantVariables(List vars) {
			indent();
			out.print("<variables>");

			// Sacar las que tienen $$$$.

			String s = extractStringVarList(vars);
			s = filterBindingVariables(s);

			out.print(s);
			out.println("</variables>");
		}

		private String filterBindingVariables(String s) {

			String result = "";
			if (s != "") {
				String[] variables = s.split(",");

				for (int i = 0; i < variables.length; i++) {
					String v = variables[i].trim();
					if (!v.startsWith("$t.")) {
						result += v;
						if (i < variables.length - 1)
							result += ", ";
					}

				}

			}
			return result;

		}

		private String extractStringVarList(List vars) {
			if (vars != null) {
				String sVars = vars.toString().replaceAll("\\[", "").replaceAll("\\]", "");
				return removeNonJavaSymbols(sVars);
			}
			return "";
		}

		// Ojo esta no cambia los $ por __
		private String extractStringParSet(Set vars) {
			if (vars != null) {
				String sVars = vars.toString().replaceAll("\\[", "").replaceAll("\\]", "");
				// return removeNonJavaSymbols(sVars);
				return sVars;
			}
			return "";
		}

		private void writeInductiveVariables(List inds) {
			writeInductiveVariables(inds, new HashSet());
		}

		private void writeInductiveVariables(List inds, Set newInd) {
			indent();
			out.print("<inductives>");
			// String sInds=
			// inds.toString().replaceAll("\\[","").replaceAll("\\]","");
			out.print(extractStringVarList(inds));
			if (!newInd.isEmpty()) {
				if (!inds.isEmpty())
					out.print(", ");

				String s = extractStringParSet(newInd);
				s = filterBindingVariables(s);

				out.print(s);
			}
			out.println("</inductives>");
		}

		private void writeInductiveVariables(InductiveVariablesInfo ind) {
			indent();
			if (ind != null) {
				out.print("<inductives>");
				String sInd = ind.getInductiveInfo().toString().replaceAll("\\[", "").replaceAll("\\]", "");
				String sExc = ind.getExcludeInfo().toString().replaceAll("\\[", "").replaceAll("\\]", "");
				out.print("I:" + sInd + ',');
				out.print("O:" + sExc);
				out.println("</inductives>");
			}
		}

		private String getInvariantForSite(String l, List vars) {
			String l2 = l.replaceAll("\\.", "_");
			// Este es para eliminar prefijos de los invariant
			String prefijo = l2.replaceAll("\\$", "__");
			// Esto es porque la cabeza de los inv usa _ en vez de __, raro...
			l2 = l2.replaceAll("\\$", "_");
			if (l2.indexOf("HG") != -1) {
				System.out.print("");
			}
			
			String inv = ir.getCreationSiteInv(l2);
			// Se saco el prefijo de clase y nro de linea que no
			// me hace falta
			if (inv != null) {
				inv = removePrefix(prefijo, inv);
			}
			
			/*String[] constraints = inv.split(",");
			
			List new_constraints = new ArrayList<String>();
			
			
			//hack asqueroso
			//leccion aprendida: 
			//hay que hacerlo bien. Los terminos pueden ser mezcla de numeros y variables de .indFake
			//hay que hacerlo con cuidado
			for (int i = 0; i < constraints.length; i++)
			{
				String constraint = constraints[i];
				String[] sides = constraint.split("<=|>=|==");
				if(sides.length < 2) continue;
				String s0 = sides[0].trim();
				String s1 = sides[1].trim();
				if (sides.length == 2 && 
						(StringUtils.isNumeric(s0) || vars.contains(s0)) &&
								(StringUtils.isNumeric(s1) || vars.contains(s1)))
					new_constraints.add(constraint);
			}
			
			inv = StringUtils.join(new_constraints, ",");*/
					
			
			
			
			
			return inv;
		}

		//dejo este metodo aparte porque seguro voy a tener que hacer cosas con las _f_
		private String removePrefix(String prefijo, String inv) {

			inv = inv.replaceAll(prefijo + "_","");
			
			inv = inv.replaceAll("\\[\\]","");
			
			//TODO: cambiar _f_ por __ para los fields
			/*String[] invList = inv.trim().split(prefijo + "_");
			inv = "";
			for (int i = 0; i < invList.length; i++) {
				String myInv = invList[i].trim();
				if (myInv.length() > 0) {
					if (myInv.startsWith("f_"))
						myInv = "_" + myInv;
					inv += myInv;
				}
			}*/
			return inv;
		}

		private void writeInvariant(String inv) {
			indent();
			out.println("<constraints>");
			level++;

			String[] constBind = adaptInvariant(inv);

			String constraints = constBind[0];
			String binding = constBind[1];

			if (binding != null && binding.length() > 0)
				binding = binding.trim();

			
			if(constraints!="null" && constraints != null)
			{	
				indent();
				out.print("<![CDATA[");
				out.print(constraints);
				out.println("]]>");
			}
			// out.println(newInv);
			level--;
			indent();
			out.println("</constraints>");

			// Este es un hack feo porque los creation sites no tienen binding
			// asi que directamente aprovecho y pregunto
			// si el binding es vacio....pero no deberia "intentar calcularlo"
			// si estamos en un creation site
			if (binding != null && binding.length() > 0) {
				indent();
				out.print("<binding>");
				out.print(binding);
				out.println("</binding>");
			}
		}

		String[] adaptInvariant(String inv) {
			if (inv == null)
				return new String[2];

			String[] constBind = new String[2];

			// inv = inv.replaceAll("=", "==");
			String[] invList = inv.split(",");
			StringBuffer resCo = new StringBuffer();
			StringBuffer resBi = new StringBuffer();
			for (int i = 0; i < invList.length; i++) {
				String s = invList[i];
				
				if(s.contains("inargs"))
				{
					System.out.println("Hola!");
				}

				if(!s.contains("$t."))
					s = s.replace(".", "__");
				
				
				/*if(s.contains("$t"))
				{
					System.out.println("hola");
				}*/
				
				
				//TODO revisar que no haya invariantes con ")" que no provengan de size!
				//Tal vez si si la relacion es lineal pero con cuentas con parentesis??
				s = s.replace("size(", "size_");
				s = s.replace(")", "");
				
				
				//s = s.replace("[", "__");

				//s = s.replace("]", "__");

				if (s.contains("$t.")) {
					if (resBi.length() > 0)
						resBi.append(" and ");
					resBi.append(s.trim());

				} else {
					if (resCo.length() > 0) {
						// res.append('\n');
						// res.append(sIndent(level));
						// res.append("and ");
						resCo.append(" && ");

					}
					resCo.append(s.trim());
				}

			}

			constBind[0] = resCo.toString();
			constBind[1] = resBi.toString();
			return constBind;
		}

		String XMLizar(String s) {
			String res = s.replaceAll("<", "&lt;");
			res = res.replaceAll(">", "&gt;");
			return res;
		}

	}

	private static String removeNonJavaSymbols(String s) {
		return s.replaceAll("\\$", "__");
	}

}

class LineNumbers extends BodyTransformer {
	private static LineNumbers instance = new LineNumbers();

	private LineNumbers() {
	}

	public static LineNumbers v() {
		return instance;
	}

	@Override
	protected void internalTransform(Body b, String phaseName, Map options) {
		decorate(b);
	}

	void decorate(Body body) {

		// Ya no iteramos mas en orden del flujo sino que en orden de jimple
		// ExceptionalUnitGraph flowGraph = new
		// ExceptionalUnitGraph(method.retrieveActiveBody());
		// List<Unit> topoOrder = new
		// PseudoTopologicalOrderer<Unit>().newList(flowGraph, false);

		int newCounter = 0;
		int callCounter = 0;
		for (Object u : body.getUnits()) {
			Stmt stmt = (Stmt) u;
			Boolean isCall = stmt.containsInvokeExpr();
			Boolean isNew = false;

			if (stmt instanceof AssignStmt) {
				AssignStmt assign = (AssignStmt) stmt;
				isNew = (assign.getRightOp() instanceof AnyNewExpr);
			}
			String s = getLineNumber(stmt);
			String ms = body.getMethod().getSignature();
			System.out.println(ms + "@" + s);

			if (isCall) {
				// body.addStatement(new DefaultCallStatement(method, stmt,
				// callCounter));
				SpecInvariantWriter.offsets.put(ms + "@" + s, new Integer(callCounter));
				callCounter++;
			} else if (isNew) {
				SpecInvariantWriter.offsets.put(ms + "@" + s, new Integer(newCounter));
				// body.addStatement(new DefaultNewStatement(method, stmt,
				// newCounter));
				newCounter++;
			}

		}

	}

	public static String getLineNumber(Stmt s) {
		String sLn = "", sColumn = "";
		List tags = s.getTags();
		// System.out.println("Tags de:"+s.toString()+" "+tags.toString());

		int ln = -1;
		int column = -1;
		int bytecode = -1;
		for (Iterator it = tags.iterator(); it
				.hasNext() /* && (ln == -1 || column==-1) */;) {
			Tag tag = (Tag) it.next();
			// System.out.println("Tipo: "+tag.getClass());

			if (tag instanceof LineNumberTag) {
				byte[] value = tag.getValue();
				ln = ((value[0] & 0xff) << 8) | (value[1] & 0xff);
			} else if (tag instanceof SourceLnPosTag) {
				ln = ((SourceLnPosTag) tag).startLn();
				column = ((SourceLnPosTag) tag).startPos();
			} else if (tag instanceof SourceLineNumberTag) {
				ln = ((SourceLineNumberTag) tag).getLineNumber();

			} else if (tag instanceof PositionTag) {
				column = ((PositionTag) tag).getStartOffset();
			}

			if (tag instanceof BytecodeOffsetTag) {
				byte[] value = tag.getValue();
				// bytecode = Integer.parseInt(tag.toString());
				BytecodeOffsetTag bcTag = (BytecodeOffsetTag) tag;
				bytecode = bcTag.getBytecodeOffset();
			}

		}
		if (ln != -1) {
			sLn = pad(Integer.toString(ln), 5, '0');
		}
		// if(column!=-1)
		// sLn +="c"+Integer.toString(column);
		// else
		if (bytecode != -1)
			sLn += "c" + pad(Integer.toString(bytecode), 5, '0');
		// System.out.println(ln+" "+column+" "+bytecode+"="+sLn);
		return sLn;

	}

	public static String pad(String s, int len, char c) {
		String res = "";
		for (int i = 0; i < len - s.length(); i++)
			res = res + c;
		return res + s;
	}

}
