package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.invariantwriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.BindingAnnotation;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CCReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CSReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CallSiteMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CreationSiteMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductiveVariablesInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductivesReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InstrumentationSiteInvariantsReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InvariantReader;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.intermediate_representation.IRUtils;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.PaperInterproceduralAnalysis.NotDAGException;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
import ar.uba.dc.annotations.InstrumentationSiteInvariant;
import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;

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
	InstrumentationSiteInvariantsReader anr = new InstrumentationSiteInvariantsReader();
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
	
	
	
	
	public class NotDAGException extends Exception {
		public NotDAGException(String message)
		{
			super(message);				
		}
		
	}
	
	
	/*
	public void visit(IntermediateRepresentationMethod ir_method) throws NotDAGException
	{
		//log.debug("visiting " + key(ir_method) +  "...");
		if(marked_temporarily.contains(IRUtils.key(ir_method)))
		{
			throw new NotDAGException("El grafo no es un DAG. Tiene ciclos");
		}
		
		if(!marked_permanently.contains(IRUtils.key(ir_method)))
		{
			marked_temporarily.add(IRUtils.key(ir_method));
			
			
			for(String s_name : getSuccesors(ir_method))
			{
				//log.debug("-------------" + s_name + " is succesor of " + key(ir_method));
				IntermediateRepresentationMethod succ = methods.get(s_name); 
				
				if(succ != null)
					this.visit(succ);
				
			}
			
			ordered_methods.add(ir_method);
			marked_temporarily.remove(IRUtils.key(ir_method));
			
			//log.debug("+++Adding to set " + key(ir_method));
			marked_permanently.add(IRUtils.key(ir_method));
		}
	}
	
	public void orderIrMethods()
	{
		Stack<IntermediateRepresentationMethod> unordered_methods = new Stack<IntermediateRepresentationMethod>();
		for(IntermediateRepresentationMethod ir_method : methods.values())
		{
			unordered_methods.add(ir_method);
		}

		marked_temporarily = new ArrayList<String>();
		marked_permanently = new ArrayList<String>();
		ordered_methods = new LinkedList<IntermediateRepresentationMethod>();
		
		for(IntermediateRepresentationMethod ir_method : unordered_methods)
		{
			try
			{
				visit(ir_method);
			}
			catch(NotDAGException exc)
			{
				//Cuando haya recursion hay que cambiar el algoritmo de pseudo top order
				log.error("Graph not a DAG. Has recursion. Can't continue");
				System.exit(1);
			}
		}
		
		log.debug("Listo! Metodos ordenados: ");
		for(int i = 0; i < ordered_methods.size(); i++)
		{
			log.debug(IRUtils.key(ordered_methods.get(i)));
		}
	}*/
	
	
	public void visit(SpecMethod m, List<String> markedTemporarily,
			List<String> markedPermanently, List<SpecMethod> orderedMethods, Map<String, SpecMethod> unorderedMethodMap) throws NotDAGException
	{
		//log.debug("visiting " + key(ir_method) +  "...");
		if(markedTemporarily.contains(m.getMethod()))
		{
			throw new NotDAGException("El grafo no es un DAG. Tiene ciclos");
		}
		
		if(!markedPermanently.contains(m.getMethod()))
		{
			markedTemporarily.add(m.getMethod());
			
			
			for(CreationSiteMapInfo cs_succ : m.getCallSites())
			{
				CallSiteMapInfo ccInfo = ccr.getCallSiteInfo(cs_succ.getInsSite());

				//log.debug("-------------" + s_name + " is succesor of " + key(ir_method));
				SpecMethod succ = unorderedMethodMap.get(ccInfo.getMethodCallee()); 
				
				if(succ != null)
					this.visit(succ, markedTemporarily, markedPermanently, orderedMethods, unorderedMethodMap);
				
			}
			
			orderedMethods.add(m);
			markedTemporarily.remove(m.getMethod());
			
			//log.debug("+++Adding to set " + key(ir_method));
			markedPermanently.add(m.getMethod());
		}
	}
	
	private List<SpecMethod> getTopologicallyOrderedMethods()
	{
		Set ms = csr.getMethods();
		Stack<SpecMethod> unordered_methods = new Stack<SpecMethod>();
		Map<String, SpecMethod> unorderedMethodMap = new HashMap<String, SpecMethod>();
		
		//Primero obtengo los metodos
		for (Object o : ms) {
			SpecMethod m = new SpecMethod();
			String classAndMethod = (String) o;
			m.setClassAndMethod(classAndMethod);
			String className = extractClassName(classAndMethod);
			m.setClassName(className);
			String method = extractFullMethodSignature(classAndMethod);
			m.setMethod(method);
			Set css = new TreeSet(csr.getCSs(classAndMethod, ccr));
			if (css != null) {
				int newOffset = 0;
				int callOffset = 0;
				for (Object o2 : css) {
					CreationSiteMapInfo csInfo = (CreationSiteMapInfo) o2;
					boolean isCall = csInfo.isCall();
					if (isCall) {
						m.addCallSite(csInfo);
					}
					else
					{
						m.addCreationSite(csInfo);
					}
				}
			}
			unordered_methods.add(m);
			unorderedMethodMap.put(method, m);
		}
		
		//Ahora los ordeno!
		List<SpecMethod> orderedMethods = new LinkedList<SpecMethod>();
		List<String> markedTemporarily = new ArrayList<String>();
		List<String> markedPermanently = new ArrayList<String>();
		for(SpecMethod m : unordered_methods)
		{
			try
			{
				visit(m, markedTemporarily, markedPermanently, orderedMethods, unorderedMethodMap);
			}
			catch(NotDAGException exc)
			{
				System.exit(1);
			}
		}
		//todo cambiar
		return orderedMethods;
		
	}

	private void writeSpecs() {
		xmlSpec = new XMLSpecWriter();
		// xmlSpec.writeXmlHeader();
		String currentClass = "";
		String currentMethod = "";
		boolean hasMethods = false;

		
		
		
		
		List<SpecMethod> orderedMethods = getTopologicallyOrderedMethods();
		
		//List ordered_methods = topologicalOrder(ms);
		for (SpecMethod m : orderedMethods) {
			List oldRelevantParameters = new ArrayList();
			
			StringBuilder headerToPrint = new StringBuilder();
			StringBuilder invariantsToPrint = new StringBuilder();
			//String classAndMethod = (String) o;

			//String className = extractClassName(m.getClassAndMethod());
			
			//asumir que la clase no tiene guion bajo rompe todo
			String method = extractFullMethodSignature(m.getClassAndMethod());

			//creo que asume que las clases van en orden
			if (!m.getClassName().equals(currentClass)) {
				if (currentClass.length() != 0) {
					if (hasMethods) {
						endProcessMethod(currentMethod);
						hasMethods = false;
					}
					//endProcessClass(m.getClassName());
					out.close();

				}

				processNewClass(headerToPrint, m.getClassName());
				currentClass = m.getClassName();
				
				
				processNewMethod(headerToPrint, method, currentClass, oldRelevantParameters);
				hasMethods = true;
				currentMethod = method;
			} else if (!m.getClassAndMethod().equals(currentMethod)) {
				if (currentMethod.length() != 0)
					endProcessMethod(currentMethod);
				processNewMethod(headerToPrint, method, currentClass, oldRelevantParameters);
				hasMethods = true;
				currentMethod = method;
			}

			// System.out.println(classAndMethod);
			
			//Obtengo todos los insSites asociados al metodo (los obtuve del archivo .cs)
			//y los proceso (pueden ser CALLs o Creation sites)
			
			//Si son call sites uso el archivo .cc para obtener la informacion asociada
			//Set css = new TreeSet(csr.getCSs(classAndMethod, ccr));
			
			int newOffset = 0;
			int callOffset = 0;
			for(CreationSiteMapInfo csInfo : m.getCreationSites())
			{
				if (csInfo.getOrder() == -1)
					csInfo.setOffset(newOffset);
				processCreationSite(invariantsToPrint, csInfo, method);
				newOffset++;
			}
			
			for(CreationSiteMapInfo csInfo : m.getCallSites())
			{
				if (csInfo.getOrder() == -1)
					csInfo.setOffset(callOffset);
				processCall(invariantsToPrint, csInfo, method, oldRelevantParameters);
				callOffset++;
			}
			
			addNewRelevantParametersFromCallees(headerToPrint, method);
			out.print(headerToPrint.toString());
			out.print(invariantsToPrint.toString());
			
			/*
			if (css != null) {
				int newOffset = 0;
				int callOffset = 0;
				for (Object o2 : css) {
					CreationSiteMapInfo cs = (CreationSiteMapInfo) o2;
					System.out.println(cs.toString());
					System.out.println(cs.getInsSite());
					System.out.println("____");
					// System.out.println("-- cs:"+cs);

					boolean isCall = cs.isCall();
					if (isCall) {
						if (cs.getOrder() == -1)
							cs.setOffset(callOffset);
						processCall(cs, method);
						callOffset++;
					} else {
						if (cs.getOrder() == -1)
							cs.setOffset(newOffset);
						processCreationSite(cs, method);
						newOffset++;
					}

				}
			}*/
		}
		if (currentMethod.length() != 0)
			endProcessMethod(currentMethod);
		if (currentClass.length() != 0)
			out.close();

		endProcessClasses();
		// xmlSpec.writeXmlFooter();
	}





	private void addNewRelevantParametersFromCallees(StringBuilder headerToPrint, String methodName) {
		
		
		if(anr.getAnnotations() != null)
		{
			for(AnnotationSiteInvariantForJson annotation : anr.getAnnotations())
			{
				if (annotation.getMethodName().equals(methodName))
				{
					int pos = headerToPrint.indexOf("</relevant-parameters>");
					
					String relevantParametersString = StringUtils.join(annotation.getNewRelevantParameters(), ", ").replaceAll("\\.", "__f__");
					//No tiene relevant parameters <-> tengo "</relevant-parameters></relevant-parameters>"
					if (headerToPrint.charAt(pos-1) == '>')
						headerToPrint.insert(pos, relevantParametersString);
					else
						headerToPrint.insert(pos, ", " + relevantParametersString);
				}	
			}
		}
		
	}

	private void endProcessMethod(String methodName) {
		xmlSpec.writeMethodFooter(methodName);

	}

	
	private void endProcessClasses()
	{
		for(String className : startedClasses)
		{
			
			
			try {
				out = new PrintStream(			
						new FileOutputStream(getFileName(className), true));
				
				
				xmlSpec.currentClassName = className;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			xmlSpec.writeClassFooter(className);
			xmlSpec.writeXmlFooter();
			out.close();
		}
	}
	
	String getFileName(String className)
	{
		int pos = className.lastIndexOf(".");
		String packagePath = pos != -1 ? className.substring(0, pos) : "";
		String onlyClass = className.substring(pos + 1);

		// fileName = destinationPath+className+".spec";
		
		
		fileName = destinationPath + "/" + packagePath.replace(".", "/") + "/" + onlyClass + ".spec";
		return fileName;
	}

	
	List<String> startedClasses = new ArrayList<String>();
	
	private void processNewClass(StringBuilder headerToPrint, String className) {
		// sootClass = soot.Scene.v().loadClassAndSupport(className);

		String fileName = getFileName(className);

		//boolean status = new File(destinationPath + "/" + packagePath).mkdir();
		
		xmlSpec.currentClassName = className;
		try {
			if(startedClasses.contains(className))
			{
				out = new PrintStream(			
					new FileOutputStream(fileName, true));
			}
			else
			{
				File srcFile = new File(fileName);
				
				if (!srcFile.getParentFile().exists()) {
					srcFile.getParentFile().mkdirs();
				}
				
				out = new PrintStream(fileName);
				startedClasses.add(className);
				xmlSpec.classesLevel.put(className, 0);
				xmlSpec.writeXmlHeader(headerToPrint);
				// System.out.println("Class:"+className);
				xmlSpec.writeClassHeader(headerToPrint, className);
			}
			
			
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	private void processNewMethod(StringBuilder headerToPrint, String methodName, String className, List oldRelevantParameters) {
		
		xmlSpec.currentClassName = className;
		// System.out.println("- Method:"+methodName);
		// Usa los "init"
		List params = ccr.getMethodParams(methodName);
		oldRelevantParameters = params;
		// System.out.println(params);
		xmlSpec.writeMethodHeader(headerToPrint, methodName, params);

		
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
	
	/*
	 * private String extactLineNumberFromInsSite(String is) {
		//System.out.println("entrando");
		int pos_c  = is.lastIndexOf("c");
		
	
		int pos_first_dash  = is.lastIndexOf("_");

		String s2 = is.substring(0,pos_first_dash-1);
		int pos_dash  = s2.lastIndexOf("_");

		String sln = null;
		if(pos_c!= -1 && pos_c > pos_dash)
			sln = is.substring(pos_dash + 1, pos_c);
		else
			sln = is.substring(pos_dash + 1);
		//System.out.println("saliendo");
		return sln;
		
	}
	 * */

	private void processCreationSite(StringBuilder invariantsToPrint, CreationSiteMapInfo cs, String methodName) {
		xmlSpec.writeCreationSite(invariantsToPrint, cs, methodName);
	}

	private void processCall(StringBuilder invariantsToPrint, CreationSiteMapInfo cs, String methodName, List oldRelevantParameters) {

		CallSiteMapInfo ccInfo = ccr.getCallSiteInfo(cs.getInsSite());
		
		xmlSpec.writeCallSite(invariantsToPrint, cs, ccInfo, methodName, oldRelevantParameters);

	}

	void fetchInvariants() {
		try {
			anr.analyze(strClass + ".ann");
			ccr.analyze(strClass + ".cc");
			csr.analyze(strClass + ".cs");
			ir.analyze(strClass + ".txt");
			indr.analyze(strClass + ".ind");
		} catch (Exception e) {
			System.out.println("error:" + e.toString());
		}

	}

	class XMLSpecWriter {
		public Map<String, Integer> classesLevel = new HashMap<String, Integer>();
		//int level = 0;

		public String currentClassName;
		private void indent(StringBuilder toPrint) {
			Integer i = (classesLevel.get(currentClassName));
			toPrint.append(sIndent(i));
		}
		
		private void indent() {
			Integer i = (classesLevel.get(currentClassName));
			out.print(sIndent(i));
		}

		private String sIndent(int c) {
			StringBuffer res = new StringBuffer();
			for (int i = 0; i < classesLevel.get(currentClassName); i++)
				res.append("\t");
			return res.toString();
		}

		
		void addLine(StringBuilder b, String line)
		{
			b.append(line);
			b.append(System.getProperty("line.separator"));
		}
		
		private void writeXmlHeader(StringBuilder headerToPrint) {
			indent(headerToPrint);
			
			addLine(headerToPrint, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			addLine(headerToPrint,"<spec>");			
			
			incrementIndentationLevelForCurrentClass();
		}

		private void writeXmlFooter() {
			decrementIndentationLevelForCurrentClass();
			indent();
			out.println("</spec>");
		}

		private void writeClassHeader(StringBuilder headerToPrint, String className) {
			indent(headerToPrint);
			
			headerToPrint.append(" <class decl=");
			headerToPrint.append("\"");
			headerToPrint.append(className);
			headerToPrint.append("\"");
			addLine(headerToPrint, ">");
			incrementIndentationLevelForCurrentClass();
		}

		public void incrementIndentationLevelForCurrentClass()
		{
			Integer i = classesLevel.get(currentClassName);
			classesLevel.put(currentClassName, i+1);
		}
		
		public void decrementIndentationLevelForCurrentClass()
		{
			Integer i = classesLevel.get(currentClassName);
			classesLevel.put(currentClassName, i-1);
		}
		
		
		private void writeClassFooter(String className) {
			decrementIndentationLevelForCurrentClass();
			indent();
			out.println("</class>");
		}

		private void writeMethodHeader(StringBuilder headerToPrint, String methodName, List params) {
			indent(headerToPrint);
			headerToPrint.append(" <method decl=");
			headerToPrint.append("\"");
			headerToPrint.append(XMLizar(extractMethodSignature(methodName)));
			headerToPrint.append("\"");
			addLine(headerToPrint, ">");
			incrementIndentationLevelForCurrentClass();

			indent(headerToPrint);
			headerToPrint.append("<relevant-parameters>");
			if(methodName.contains("void main(java.lang.String[])"))
			{
				if(params==null)
				{
					params = new ArrayList<String>();
				}
				params.add("args_init__f__size");
			}

			
			
			
			headerToPrint.append(extractStringVarList(params));
				
			addLine(headerToPrint, "</relevant-parameters>");
			
		}

		private void writeMethodFooter(String methodName) {
			decrementIndentationLevelForCurrentClass();
			indent();
			out.println("</method>");
		}

		private void writeCreationSiteHeader(StringBuilder invariantsToPrint, CreationSiteMapInfo cs) {
			int order = getOrder(cs);
			indent(invariantsToPrint);
			invariantsToPrint.append(" <creation-site ");
			invariantsToPrint.append("offset=" + "\"" + order + "\"");
			invariantsToPrint.append(" srccode-offset=" + "\"" + extactLineNumberFromInsSite(cs.getInsSite()) + "\"");
			addLine(invariantsToPrint, ">");
			incrementIndentationLevelForCurrentClass();
		}

		private void writeCreationSiteFooter(StringBuilder invariantsToPrint, CreationSiteMapInfo cs) {
			decrementIndentationLevelForCurrentClass();
			indent(invariantsToPrint);
			addLine(invariantsToPrint, " </creation-site>");

		}

		private void writeCallSiteHeader(StringBuilder invariantsToPrint, CreationSiteMapInfo cs) {

			indent(invariantsToPrint);
			invariantsToPrint.append(" <call-site ");
			invariantsToPrint.append("offset=" + "\"" + getOrder(cs) + "\"");
			invariantsToPrint.append(" srccode-offset=" + "\"" + extactLineNumberFromInsSite(cs.getInsSite()) + "\"");
			addLine(invariantsToPrint, ">");
			incrementIndentationLevelForCurrentClass();
		}

		private int getOrder(CreationSiteMapInfo cs) {
			String query = currentMethod + "@" + cs.getInsSite().substring(cs.getInsSite().indexOf("_") + 1);
			Integer offSet = SpecInvariantWriter.offsets.get(query);
			int order = offSet != null ? offSet.intValue() : cs.getOrder();
			return order;
		}

		private void writeCallSiteFooter(StringBuilder invariantsToPrint, CreationSiteMapInfo cs) {
			decrementIndentationLevelForCurrentClass();
			indent(invariantsToPrint);
			addLine(invariantsToPrint, " </call-site>");

		}

		private void writeCallSite(StringBuilder invariantsToPrint, CreationSiteMapInfo cs, CallSiteMapInfo ccInfo, String methodName, List oldRelevantParameters) {
			String l = cs.getInsSite();

			writeCallSiteHeader(invariantsToPrint, cs);
			
			
			
			

			// Lo calculo antes para tener los bindinds
			String inv = getInvariantForSite(l, cs.getVars());
			if (inv == null)
				inv = "";
			Set newInductives = new HashSet();
			String callInv = generateCallInvariant(ccInfo, inv, newInductives);

			InductiveVariablesInfo inductives = indr.getiInfo(cs.getInsSite());
			List inductivas = new Vector();
			if (inductives != null)
			{
				inductivas.addAll(inductives.inductiveInfo);
			}
			
			/*if (inductives != null) {
				inductivas = InductiveVariablesInfo.filterInductives(cs.getVars(), inductives, "");
			} else
				inductivas.addAll(cs.getVars());*/
			// inductivas.addAll(newInductives);
			
			
			
			List<String> vars = new ArrayList<String>();
			
			//List<AnnotationSiteInvariantForJson> toRemove = new ArrayList<AnnotationSiteInvariantForJson>();
			List<String> extraConstraints = new ArrayList<String>();
			for(AnnotationSiteInvariantForJson extraInvariant : anr.getAnnotations())
			{
				if(extraInvariant.getType() != null && extraInvariant.getType().equals("CC")
						&& extraInvariant.getMethodName().equals(methodName) && extraInvariant.getIndex() == getOrder(cs))
				{
					//toRemove.add(extraInvariant);
					extraConstraints.addAll(extraInvariant.getConstraints());
					vars.addAll(extraInvariant.getNewVariables());
					inductivas.addAll(extraInvariant.getNewInductives());
				}
			}

			//anr.getAnnotations().removeAll(toRemove);
			
			writeRevelantVariables(invariantsToPrint, vars, cs );
			
			writeInductiveVariables(invariantsToPrint, inductivas, cs);

			// writeInductiveVariables(inductives);

			writeCallee(invariantsToPrint, ccInfo);

			writeCallSiteInvariant(invariantsToPrint, callInv, ccInfo, cs.getMethod(), extraConstraints, cs, vars, inductivas, oldRelevantParameters);

			writeCallSiteFooter(invariantsToPrint, cs);
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

		private void writeCreationSite(StringBuilder invariantsToPrint, CreationSiteMapInfo cs, String methodName) {
			String l = cs.getInsSite();
			String inv = getInvariantForSite(l, cs.getVars());
			writeCreationSiteHeader(invariantsToPrint, cs);
			List vars = cs.getVars();
			Set paramsArr = new HashSet();
			int cont = 0;
			String invArray = "";
			for (Iterator iter = cs.getCsArrayParams().iterator(); iter.hasNext();) {
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

			

			List inductiveVars = new Vector();

			InductiveVariablesInfo inductives = indr.getiInfo(cs.getInsSite());
			
			List inductivas = new Vector();
			if (inductives != null)
			{
				inductiveVars.addAll(inductives.inductiveInfo);
			}
			
			
			//Me parece que esta bien esto de agregar solo las inductivas que vienen de indFake (las que vienen del analisis de vivas)
			
			/*if (inductives != null) {
				inductiveVars = InductiveVariablesInfo.filterInductives(cs.getVars(), inductives, "");
			} else
				inductiveVars.addAll(cs.getVars());*/
			
			//bah, y agregamos las que vienen de paramsArr? Estilo contadores? Estas no estan en inductivesFake?
			//TODO: hacer la prueba con un foreach
			inductiveVars.addAll(paramsArr);

			// writeInductiveVariables(inductives);
			if (!invArray.isEmpty()) {
				if (inv == null || inv.isEmpty())
					inv = invArray;
				else
					inv = inv + " && " + invArray;
			}


			//filterByQuotient(vars, inductiveVars, inv);


			
			
			//List<AnnotationSiteInvariantForJson> toRemove = new ArrayList<AnnotationSiteInvariantForJson>();
			List<String> extraConstraints = new ArrayList<String>();
			for(AnnotationSiteInvariantForJson extraInvariant : anr.getAnnotations())
			{
				if(extraInvariant.getType() != null && extraInvariant.getType().equals("CS") 
						&&  extraInvariant.getMethodName().equals(methodName) && extraInvariant.getIndex() == getOrder(cs))
				{
					//toRemove.add(extraInvariant);
					extraConstraints.addAll(extraInvariant.getConstraints());
					vars.addAll(extraInvariant.getNewVariables());
					inductivas.addAll(extraInvariant.getNewInductives());
				}
			}

			//anr.getAnnotations().removeAll(toRemove);
			
			
			writeRevelantVariables(invariantsToPrint, vars, cs);
			writeInductiveVariables(invariantsToPrint, inductiveVars, cs);
			
			writeCreationSiteInvariant(invariantsToPrint, inv, extraConstraints, cs, vars, inductivas);
			writeCreationSiteFooter(invariantsToPrint, cs);
		}

		private void writeCallee(StringBuilder invariantsToPrint, CallSiteMapInfo ccInfo) {
			if (ccInfo != null) {
				indent(invariantsToPrint);
				invariantsToPrint.append("<callee>");
				String callee = ccInfo.getMethodCallee();
				callee = callee.substring(1, callee.length() - 1);
				invariantsToPrint.append(XMLizar(callee));
				addLine(invariantsToPrint, "</callee>");
			}

		}

		private void writeRevelantVariables(StringBuilder invariantsToPrint, List vars, CreationSiteMapInfo cs) {
			indent(invariantsToPrint);
			invariantsToPrint.append("<variables>");

			// Sacar las que tienen $$$$.

			List varsWithoutObjects = new ArrayList();
			varsWithoutObjects.addAll(vars);
			varsWithoutObjects.removeAll(cs.getObjectVars());
			String s = extractStringVarList(varsWithoutObjects);
			s = filterBindingVariables(s);

			invariantsToPrint.append(s);
			addLine(invariantsToPrint, "</variables>");
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
				
				
				String sVars = vars.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("(size)\\((\\w+)\\)", "$2__f__$1").replaceAll("(?<!\\$t)[.]", "__f__");
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
		/*
		private void writeInductiveVariables(StringBuilder invariantsToPrint, List inds) {
			writeInductiveVariables(invariantsToPrint, inds);
		}*/

		
		//TODO que es newInd, ni se usa!
		private void writeInductiveVariables(StringBuilder invariantsToPrint, List inds, CreationSiteMapInfo cs) {
			indent(invariantsToPrint);
			invariantsToPrint.append("<inductives>");
			// String sInds=
			// inds.toString().replaceAll("\\[","").replaceAll("\\]","");

			List indsWithoutObjects = new ArrayList();
			indsWithoutObjects.addAll(inds);
			indsWithoutObjects.removeAll(cs.getObjectVars());
			
			invariantsToPrint.append(extractStringVarList(indsWithoutObjects));
			
			//no entiendo por que las variables del binding las contamos como inductivas!!
			/*if (!newInd.isEmpty()) {
				

				String s = extractStringParSet(newInd);
				
				
				s = filterBindingVariables(s);
				
				if (!inds.isEmpty() && s.length() > 0)
				{
					out.print(", ");
					out.print(s);
				}
			}*/
			addLine(invariantsToPrint, "</inductives>");
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

		private void writeCreationSiteInvariant(StringBuilder invariantsToPrint, String inv, List<String> extraConstraints, CreationSiteMapInfo cs, List vars, List inductivas) {			
			
			indent(invariantsToPrint);

			String[] constBind = adaptInvariant(inv, cs.getObjectVars());

			String constraints = constBind[0];		
			
			
			constraints = processObjects(constraints, null, cs.getObjectVars(), vars, inductivas);
			
			
			
			String extraConstraintsString = ConstraintUtils.adaptOneConstraint((StringUtils.join(extraConstraints, " && ")), cs.getObjectVars());
			extraConstraintsString.replaceAll("$", "__");
			
			if(constraints != null && constraints.length() > 0)
			{
				if (extraConstraintsString.length() > 0)
					constraints += " && " + extraConstraintsString;
			}
			else
			{
				constraints = extraConstraintsString;
			}
		
			
			
					
			if(constraints != null && !constraints.equals("null") && !constraints.equals(""))
			{	
				addLine(invariantsToPrint, "<constraints>");

				incrementIndentationLevelForCurrentClass();
				indent(invariantsToPrint);
				invariantsToPrint.append("<![CDATA[");
				invariantsToPrint.append(constraints);
				addLine(invariantsToPrint, "]]>");
				decrementIndentationLevelForCurrentClass();
				indent(invariantsToPrint);
				addLine(invariantsToPrint, "</constraints>");
			}
			else
			{
				incrementIndentationLevelForCurrentClass();
				addLine(invariantsToPrint, "<constraints></constraints>");
				decrementIndentationLevelForCurrentClass();
			}
			// out.println(newInv);
			

		}
		
		
		private String processObjects(String constraints, String binding, Set objectVars, List vars, List inductivas)
		{

			Set<DerivedVariable> derivedVariables = new HashSet<DerivedVariable>();
			
			if(binding != null && binding.length() > 0)
			{
				for (String singleBinding : binding.split(" and ")){
					Pattern pattern = Pattern.compile("\\$t.(\\w+) == (\\w+)");
					Matcher match = pattern.matcher(binding);
					while (match.find()) {
						//variables.add(match.group(1));
						vars.add(match.group(2));
					}
				}
			}
			
			//al pedo mergeamos el array y despues lo separamos
			//TODO no mergear el array antes de esta funcion...
			if(constraints != null)
				{
				String[] constraints_array = constraints.split("&&");
				List<String> new_constraints = new ArrayList<String>();			
				
				if(objectVars.size() > 0)
				{
					
					String regex = "^(";
					Iterator itt = objectVars.iterator();
					while(itt.hasNext())
					{
						String o = (String)itt.next();
						regex += " " + o + "|" + o + " ";
						if (itt.hasNext())
							regex += "|";
					}
					regex+=")";
					System.out.println(regex);
					Pattern patternObj = Pattern.compile(regex);
				
					for(int i = 0;  i < constraints_array.length; i++)
					{
						String constraint = constraints_array[i].trim();
						Matcher match = patternObj.matcher(constraint);
						if(!match.find())
						{
							new_constraints.add(constraint);
						}
					}
				}
				else
				{
					new_constraints.addAll(Arrays.asList(constraints_array));
				}
				
				List varsAndInductives = vars; varsAndInductives.addAll(inductivas);//esto no deberia ser necesario porque las variables deberian contener a las inductivas
				Iterator<String> it = varsAndInductives.iterator();
				while( it.hasNext())
				{
					String var = it.next();				
					DerivedVariable dv = null;
					//puede ser A.f1.f2
					Pattern pattern1 = Pattern.compile("(cont)_(\\w+)");
					Matcher match1 = pattern1.matcher(var);
					if (match1.find()) {
						//variables.add(match.group(1));
						String name = match1.group(2);
						dv = new DerivedVariable("size", name);
						new_constraints.add(dv.toString() + ">= 0");
					}
					else
					{
						Pattern pattern2 = Pattern.compile("(\\w+)__f__(\\w+)");
						Matcher match2 = pattern1.matcher(var);
						if (match2.find()) {
							//variables.add(match.group(1));
							String name = match1.group(1);
							String field = match1.group(2);
							dv = new DerivedVariable(field, name);
						}
					}
					
					if(dv!=null)
						derivedVariables.add(dv);
				}
				
				
				for(int i = 0 ; i < constraints_array.length; i++)
				{
					String constraint = constraints_array[i];
					String[] constraint_pair = constraint.split("==");
					if(constraint_pair.length == 2)
					{
						String c0 = constraint_pair[0].trim();
						String c1 = constraint_pair[1].trim();
						
						//hack, deberia hacer un mejor chequeo para que el contains() no se rompa mas adelante
						if(!StringUtils.isNumeric(c0) && !StringUtils.isNumeric(c1))
						{							
							for(DerivedVariable dv: derivedVariables)
							{
								String name = dv.getName();
								String field = dv.getField();
								if (name.equals(c0))
								{
									//String field = s.replace(constraint_pair[0], "");
									DerivedVariable dv2 = new DerivedVariable(field, c1);
									String constraint2 = dv.toString() + " == " + dv2.toString();							
									new_constraints.add(constraint2);
									
									//si una variable es igual a un size no me agrega el >= 0 porque se deriva de que es un size!!
									/*if(field.equals("size"))
									{
										String another_constraint = c1 + ">= 0";
										new_constraints.add(another_constraint);
									}*/
									
									
									break;
								}
								if (name.equals(c1))
								{
									DerivedVariable dv2 = new DerivedVariable(field, c0);
									String constraint2 = dv.toString() + " == " + dv2.toString();		
									new_constraints.add(constraint2);
									
									/*if(field.equals("size"))
									{
										String another_constraint = c0 + ">= 0";
										new_constraints.add(another_constraint);
									}*/
									
									break;
								}
							
							}
						}
					}
				}
				
				return  String.join(" && ", new_constraints);
			}
			return null;
			
		}
		
		
		
		private void writeCallSiteInvariant(StringBuilder invariantsToPrint, String inv, CallSiteMapInfo ccInfo, String methodName, List<String> extraConstraints,
				CreationSiteMapInfo cs, List vars, List inductivas, List oldRelevantParameters) {			
			
			indent(invariantsToPrint);
			

			String[] constBind = adaptInvariant(inv, cs.getObjectVars());

			String constraints = constBind[0];
			
			String binding = constBind[1];
			
		
			
			//Aca deberia adaptar las constraints y el binding para:
			
			//1) Propagar igualdades de objetos a igualdades de fields 
			//2) Eliminar igualdades de objetos
			
			constraints = processObjects(constraints, binding, cs.getObjectVars(), vars, inductivas);
			
			if (binding != null && binding.length() > 0)
				binding = binding.trim();

			
			BindingAnnotation newBindingAnnotation = new BindingAnnotation(methodName);
			AnnotationSiteInvariantForJson newAnnotation = new AnnotationSiteInvariantForJson(methodName);
			for(BindingAnnotation bindingAnnotation : anr.getBindingAnnotations())
			{
				//No se si coinciden en formato todavia, ojo
				if (bindingAnnotation.getCallee().equals(ccInfo.getMethodCallee()))
				{
					for (String param : bindingAnnotation.getNewRelevantParameters())
					{
						param = ConstraintUtils.adaptOneConstraint(param, null);
						if (binding.length() > 0)
							binding = binding += " and $t." + param + " == " + param;
						else
							binding = "$t." + param + " == " + param;
						
						//Para que no se repitan los relevant parameters durante la propagacion de los parametros.
						//Por ejemplo, en em3d pasa que hay una variable que no es detectada como relevant parameter, pero lo es.
						if(!oldRelevantParameters.contains(param))
							newBindingAnnotation.addNewRelevantParameter(param);
						
						newAnnotation.addNewRelevantParameter(param);
						
					}					 
				}
			}
			
			if (newBindingAnnotation.getNewRelevantParameters().size() > 0)
			{
				anr.getBindingAnnotations().add(newBindingAnnotation);
				anr.getAnnotations().add(newAnnotation);				
			}
			
			
			String extraConstraintsString = ConstraintUtils.adaptOneConstraint(StringUtils.join(extraConstraints, " && "), null);
			extraConstraintsString = extraConstraintsString.replaceAll("\\$", "__").replaceAll("\\.", "__f__");

					
			if(constraints != null && !constraints.equals("null") && !constraints.equals(""))
			{	
				if (extraConstraintsString.length() > 0)
					constraints += " && " + extraConstraintsString;

				addLine(invariantsToPrint, "<constraints>");
				incrementIndentationLevelForCurrentClass();

				indent(invariantsToPrint);
				invariantsToPrint.append("<![CDATA[");
				invariantsToPrint.append(constraints);
				addLine(invariantsToPrint, "]]>");
				
				// out.println(newInv);
				decrementIndentationLevelForCurrentClass();
				indent(invariantsToPrint);
				addLine(invariantsToPrint, "</constraints>");
			}
			else
			{
				if(extraConstraintsString.length() > 0)
				{
					constraints = extraConstraintsString;
					addLine(invariantsToPrint, "<constraints>");
					incrementIndentationLevelForCurrentClass();

					indent(invariantsToPrint);
					invariantsToPrint.append("<![CDATA[");
					invariantsToPrint.append(constraints);
					addLine(invariantsToPrint, "]]>");
					
					// out.println(newInv);
					decrementIndentationLevelForCurrentClass();
					indent(invariantsToPrint);
					addLine(invariantsToPrint, "</constraints>");
				}
				else
				{
					addLine(invariantsToPrint, "<constraints></constraints>");
				}
				

			}
			

			// Este es un hack feo porque los creation sites no tienen binding
			// asi que directamente aprovecho y pregunto
			// si el binding es vacio....pero no deberia "intentar calcularlo"
			// si estamos en un creation site
			if (binding != null && binding.length() > 0) {
				indent(invariantsToPrint);
				invariantsToPrint.append("<binding>");
				invariantsToPrint.append(binding);
				addLine(invariantsToPrint, "</binding>");
			}
		}

		
		
		String[] adaptInvariant(String inv, Set objectVars) {
			if (inv == null)
				return new String[2];

			
			String[] constBind = new String[2];

			// inv = inv.replaceAll("=", "==");
			String[] invList = inv.split(",");
			StringBuffer resCo = new StringBuffer();
			StringBuffer resBi = new StringBuffer();
			for (int i = 0; i < invList.length; i++) {
				
				String s = invList[i];
				s = ConstraintUtils.adaptOneConstraint(s, objectVars);
				
				
				if (s.length() == 0)
					continue;
				
				
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
