package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

import ar.uba.dc.analysis.automaticinvariants.inductives.InductivesFinder;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_JsonParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Object;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Value;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParametersNoRep;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.SimpleDIParameter;
import ar.uba.dc.analysis.automaticinvariants.regions.CreationSiteInfo;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonIRWriter.InvocationSerializer;
import ar.uba.dc.analysis.common.method.information.JsonBasedEscapeAnnotationsProvider;
import ar.uba.dc.analysis.common.method.information.JsonInstrumentationSiteInvariantsWriter;
import ar.uba.dc.analysis.escape.summary.EscapeAnnotation;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
import soot.MethodOrMethodContext;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.JastAddJ.ThisAccess;
import soot.jimple.internal.JimpleLocal;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
//import soot.jimple.toolkits.callgraph.EdgeFilter;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;

import java.util.Deque;
import java.util.ArrayDeque;

import org.apache.commons.lang.StringEscapeUtils;
import org.jboss.util.NotImplementedException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


/**
 * Tomado del ejemplo del GotoInstrumenter
 */

public class ProgramInstrumentatorForDaikonMain {
	private  static SootClass IntrumentedMethodClass=null;
	private  static ArrayList<SootClass> IntrumentedMethodSootClasses=null;
	private static PrintWriter pwIM;
	private static InductivesReader inductivesReader= new InductivesReader();
	private static String outputDir = "./out";
	private static List listMethods = new Vector();
	private static List listTypes = new Vector();
	
	private static Map newRelevantsMap = new HashMap();
	
	//el String es el packageName
	private static Map<String,InstrumentedMethodClass> instrumentedMethodClasses = new HashMap<String,InstrumentedMethodClass>();
	public static boolean inductivesAsRelevants = false;

	private static Set<EscapeAnnotation> annotatedMethods;

	
	public static Set<EscapeAnnotation> getAnnotatedMethods() {
		return annotatedMethods;
	}
	public static void setAnnotatedMethods(Set<EscapeAnnotation> annotatedMethods) {
		ProgramInstrumentatorForDaikonMain.annotatedMethods = annotatedMethods;
	}

	private static JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProvider;
	
	public static boolean isInCG(SootMethod m)
	{
		ProgramInstrumentatorForDaikonMain.computeReachableMethods();
		if(ProgramInstrumentatorForDaikonMain.cgMethods!=null)
		{			
			boolean found = ProgramInstrumentatorForDaikonMain.cgMethods.contains(m);		
			return found;
		}
		return true;
	}
	public static void computeReachableMethods() {
		if(ProgramInstrumentatorForDaikonMain.cgMethods==null)
		{
			CallGraph cg  = Scene.v().getCallGraph();
			if(cg!=null)
			{
				ProgramInstrumentatorForDaikonMain.cgMethods = new HashSet();
				for(Iterator it = cg.sourceMethods(); it.hasNext();)
				{
					MethodOrMethodContext mc = (MethodOrMethodContext)it.next();
					if(mc!=null && mc.method()!=null) {
						ProgramInstrumentatorForDaikonMain.cgMethods.add(mc.method());
						System.out.println("M:"+mc.method().getSignature());
					}
				}	
			}
		}
	}

	
	private static boolean isNotAnalyzable(String srcClass, String srcSig, String methodName) {
		
		//si tiene anotacion entonces no se deberia analizar
		System.out.println(methodName);
		EscapeAnnotation annotation = jsonBasedEscapeAnnotationsProvider.get(methodName); 
		if (annotation != null)
		{
			/* Si el no analizable es SRC entonces dale, lo filtro y agrego (despues de este filtro y antes del analisis) 
			 * la info de los relevant parameters a las estructuras correspondientes.
			 * Si es el target, me parece que no tengo que filtrarlo. Tal vez deberia agregarlo a una lista especial y usar esa info
			 * para hacer algo "distinto" en el call.
			 */
			
			
			/**
			 * Recordar que lo de los relevant parameters sirve para la propagacion para arriba en el callgraph, lo cual es neceario.
			 * Por otro lado, los memory summaries nos dicen el consumo. El default podria ser #objetos creados.
			 * Deberia cambiar de lugar lo que tengo ahora de memory summaries.			 *  
			 */
			
			
			
			
			//to Process later
			annotatedMethods.add(annotation);
			return true;
		}
		
		boolean srcNotAnalyzable =  srcClass.indexOf("java.")!=-1 
    		|| srcClass.indexOf("javax.")!=-1
    		|| srcClass.indexOf("sun.")!=-1;
		
		boolean sigNotAnalyzable = srcSig.indexOf("init>")!=-1;
		
		return srcNotAnalyzable ;
	}
	
	// public static CallGraph cg = null;
	public static Set cgMethods = null;
	
	public static boolean IsFiltered(Edge e)
    {
    	//String sig  =  m.method().getSignature();
    	String tgtSig  =  e.getTgt().method().getSignature();
    	String tgtClass = tgtSig.substring(0,tgtSig.indexOf(":"));
    	String srcSig  =  e.getSrc().method().getSignature();
    	String srcClass = srcSig.substring(0,srcSig.indexOf(":"));

    	String srcName = e.getSrc().method().getDeclaringClass() + "." + e.getTgt().method().getName();
    	String tgtName = e.getSrc().method().getDeclaringClass() + "." + e.getTgt().method().getName(); 
    	
    	boolean srcNotAnalyzable = isNotAnalyzable(srcClass, srcSig, srcName);
    	
    	boolean tgtNotAnalyzable = isNotAnalyzable(tgtClass, tgtSig, tgtName);
    	if(tgtSig.indexOf("crypto")!=-1 || srcSig.indexOf("crypto")!=-1)
    		System.out.print("");
        return srcNotAnalyzable || tgtNotAnalyzable;	
    	//return false;
    }

	

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Syntax: NewsInvariantInstrumentator[soot options]");
			System.exit(0);
		}
		
		
		String mainClassAndName = args[0];
		
		jsonBasedEscapeAnnotationsProvider = new JsonBasedEscapeAnnotationsProvider("src/main/resources/annotations/escape/");
		jsonBasedEscapeAnnotationsProvider.fetchAnnotations(mainClassAndName);
		annotatedMethods = new HashSet<EscapeAnnotation>();
		
		if(mainClassAndName.contains("."))
		{
			//
			mainClass = mainClassAndName.substring(0, mainClassAndName.lastIndexOf("."));
			//pwIM.println("package " + mainClass + ";");
		}
		
		
		ReachableMethods.setEdgePredicate(new EdgePredicate() {
        	@Override
			public boolean want(Edge e) {
        		return !IsFiltered(e);
        	}
		});
		
		Scene.v().addBasicClass("ar.uba.dc.analysis.automaticinvariants.VarTest",SootClass.SIGNATURES);
		
		//Scene.v().addBasicClass("VarTest",SootClass.SIGNATURES);
		//Scene.v().addBasicClass("ar.uba.dc.daikon.RichNumberPublic",SootClass.SIGNATURES);
		
        //EdgeFilter ef = new EdgeFilter(pred);
        
		
//		Scene.v().addBasicClass("sun.reflect.generics.repository.ClassRepository",SootClass.SIGNATURES);
//		Scene.v().addBasicClass("java.lang.Class$EnclosingMethodInfo",SootClass.SIGNATURES);
//		Scene.v().addBasicClass("java.lang.AbstractStringBuilder",SootClass.BODIES);
		// Load java.lang.Object
		
        
        //Scene.v().loadClassAndSupport("java.lang.Object");
		
		//Scene.v().addBasicClass("java.lang.Object");
		

		//Scene.v().tryLoadClass("java.lang.Object", 0);
        
        /*File someClassFile = new File("./src/examples/").getAbsoluteFile();
        String s = Scene.v().getSootClassPath() + File.pathSeparator + someClassFile;
        Scene.v().setSootClassPath(s);
        
        SootClass c = Scene.v().loadClassAndSupport("examples.OtroEjemplo");
        
        Scene.v().loadNecessaryClasses();
        
        Scene.v().loadClassAndSupport("java.lang.Object");
        
        c.setApplicationClass();*/

        Scene.v().tryLoadClass("java.lang.Object", 0);
        
        
        
        //Scene.v().defaultClassPath()
        
		
		//a ver...
		//Scene.v().loadClassAndSupport("java.lang.Integer");

		
		// Declare 'public class HelloWorld'
        //Una prueba para chequear que lo que voy a tener que hacer es tener muchos InstrumentedMethodClass
        IntrumentedMethodSootClasses = new ArrayList<SootClass>();
        
        /*String packageName = args[0].substring(0,args[0].lastIndexOf("."));
		IntrumentedMethodClass = new SootClass(packageName + ".InstrumentedMethod",
				Modifier.PUBLIC);
		
		// 'extends Object'
		IntrumentedMethodClass.setSuperclass(Scene.v().getSootClass(
				"java.lang.Object"));
		IntrumentedMethodClass.setModifiers(Modifier.PUBLIC);
		//Scene.v().addClass(IntrumentedMethodClass);*/
		
		fullOutputDir = outputDir + "/" + args[0];
		//System.out.println(fullOutputDir);
		String[] optsDefault = { 
				"-app", 
				"-f", "c", //J es jimple, j es jimp (jimple simplificado)
				"-d", fullOutputDir,

				"-p", "cg", "enabled:true",
				
				
				//vamos a sacarlo y poner full-resolver
				"-w", //w es whole program  mode. W es optimizations
				//"-ignore-resolution-errors",
				
				
				"-write-local-annotations",
				//"full-resolver",
				//"-validate", //a ver que onda
				 
				
				 //lo deje a ver que pasa
				 "-asm-backend",	
				 //"-version",
				 
				 //This option sets the JDK version of the standard library being analyzed so that Soot can simulate the native methods in the specific version of the library.
				 "-p", "cg", "jdkver:8", //lo saque a ver que pasa.
				 
				 //verion java del output
				"-java-version", "1.8",

				 
				//"-version",
				 
//				 "-p", "cg.cha","enabled:true",
				 "-p", "cg.spark","enabled:true",
//				 "-p", "cg.spark", "on-fly-cg:false",
				"-allow-phantom-refs", //Internet me dice que el error viene de ahi
				"-src-prec","class",
				"-keep-line-number", 
				"-keep-bytecode-offset",
				"-keep-offset",
				//"-java-version","default",
				// "-full-resolver",
				// "-x", "immortal",
				// "-i","immortal.persintentScope",
				// "-x", "realtime", 
				"-x","javazoom.jl.decoder.huffcodetab",
				//"-x","simulator",
				 "-x","memory",  
				// "-version",
				"-x", "gnu", "-x", "spec.io",
				"-x", "java.lang.StringBuffer",
				// "-x", "VarTest",
				// "-x", "InstrumentedMethod",
				"-p", "jb",	"use-original-names:true",
				"-p", "jb.a", "enabled:false",
				"-p", "jb.ne", "enabled:false",
				"-p", "jb.uce","enabled:false",
				"-p","jb.dae", "enabled:false",
				"-p","jb.ule", "enabled:false",
				"-p","jb.cp", "enabled:false",
				

				//"-p", "jop", "enabled:false",
				//"-p", "jop.bcm", "enabled:false",
				//"-p", "jop.lcm", "enabled:false",
				
				// Only Stack Locals (only-stack-locals)
				//(default value: false)
				//Only eliminate dead assignments to locals that represent stack locations in the original bytecode.

				
				 //The Dead Assignment Eliminator eliminates assignment statements to locals whose values are not subsequently used,
				 //unless evaluating the right-hand side of the assignment may cause side-effects. 
				"-p","jop.dae","enabled:false",
				
				
				//"-version",
				//"-p", "jj",	"use-original-names:true", 
				
				// The Dead Assignment Eliminator eliminates assignment statements to locals
				//whose values are not subsequently used,
				//unless evaluating the right-hand side of the assignment may cause side-effects. 
				"-p","jj.dae", "enabled:false",
				
				
				/*************************************/

				"-p", "jop.uce1", "enabled:false",
				"-p", "jop.cpf", "enabled:false",
				"-p", "jop.cp", "enabled:false",
				"-p", "jop.uce2", "enabled:false",
				
				
				/*************************************/
				
				// This phase performs cascaded copy propagation.
				//If the propagator encounters situations of the form: A: a = ...; ... B: x = a; ... C: ... = ... x;
				//where a and x are each defined only once (at A and B, respectively),
				//then it can propagate immediately without checking between B and C for redefinitions of a.
				//In this case the propagator is global. Otherwise, if a has multiple definitions
				//then the propagator checks for redefinitions and propagates copies only within extended basic blocks. 
				"-p", "jj.dae", "enabled:false",
				
				//"-version",
				// The Unused Local Eliminator removes any unused locals from the method. 
				 "-p","jj.ule", "enabled:false",
				
				 //Provides link tags at a statement to all of the satements dominators.
				 //no se que son los dominators
				 "-p","jap.dmt","enabled:true",
				 
				 //Los cambie a true
				 
				 // The Unsplit-originals Local Packer executes only when the `use-original-names' option is chosen for the `jb' phase.
				 //The Local Packer attempts to minimize the number of local variables required in a method
				 //by reusing the same variable for disjoint DU-UD webs
				 //Conceptually, it is the inverse of the Local Splitter.
				 // Use the variable names in the original source as a guide when determining how to share local variables among non-interfering variable usages.
				 //This recombines named locals which were split by the Local Splitter. 
				 "-p", "jb.ulp", "unsplit-original-locals:true",
				 
				 
				 // The Unsplit-originals Local Packer executes only when the `use-original-names' option is chosen for the `jb' phase.
				 //The Local Packer attempts to minimize the number of local variables required in a method
				 //by reusing the same variable for disjoint DU-UD webs.
				 //Conceptually, it is the inverse of the Local Splitter.
				 // Use the variable names in the original source as a guide when determining how to share local variables among non-interfering variable usages.
				 // This recombines named locals which were split by the Local Splitter. 
				 //no entiendo la diferencia con la option de jb.ulp
				 "-p", "jj.ulp", "unsplit-original-locals:true",
				
				//TODO: mañana cambiar esto por args[0] y chequear que anda
				//"-main-class", args[0]/*, args[0]*/
				 
				 
				 // Estos son los que usa MainRunner
				   "simplify-sccs:false",
				"set-mass:false",
				"double-set-old:hybrid",
				"dump-solution:false",
				"simple-edges-bidirectional:false",
				"on-fly-cg:true",
				"ignore-types:false",
				"rta:false",
				"string-constants:false",
				"dump-pag:false",
				"force-gc:false",
				"double-set-new:hybrid",
				"types-for-sites:false",
				"merge-stringbuffer:true",
				"simulate-natives:true",
				"simplify-offline:false",
				"propagator:worklist",
				"pre-jimplify:false",
				"field-based:false",
				"topo-sort:false",
				"dump-answer:false",
				"add-tags:false",
			   "dump-html:false",
			   "dump-types:true",
			   "vta:false",
			   "class-method-var:true", 
			   "-allow-phantom-refs"
			 
				  
				};
				// "-p", "jb.lp", "enabled:true", "-p", "jb.ulp", "enabled:false",
				// "-p", "jb.ulp", "unsplit-original-locals:false" };
		// String[] optsDefault = {"-app", "-f", "J"};
		String[] opts = new String[optsDefault.length + args.length];
		for (int i = 0; i < optsDefault.length; i++)
			opts[i] = optsDefault[i];
		for (int i = 0; i < args.length; i++)
			opts[i + optsDefault.length] = args[i];

		//PackManager.v().getPack("jtp").add(
			//	new Transform("jtp.prueba", TestLV.v()));
		
		// Elimino el analisis de inductivas
		
		
		//Esto es multithreading?
		//Probar wjtp
		//Esta andando devolver dava en java 7!
		PackManager.v().getPack("jtp").add(
				new Transform("jtp.inductives", InductivesFinder.v()));
		
		PackManager.v().getPack("jtp").add(
				new Transform("jtp.instrumenter", MethodInstrumenterForDaikon.v()));
		
		
		
		try {
			
			// Generates sootOutput/InstrumentedMethod.jimple"
			/*FileOutputStream streamIM = null;
			if(optsDefault[2].equals("c") || optsDefault[2].equals("J"))
				// streamIM = new  FileOutputStream(outputDir+"/InstrumentedMethod.jimple");
				streamIM = new FileOutputStream(fullOutputDir+"/InstrumentedMethod.java");
			else
				streamIM = new FileOutputStream(fullOutputDir+"/DummyInstrumentedMethod.jimple");*/
			//PrintStream outIM = new PrintStream(streamIM);
			//pwIM = new PrintWriter(outIM,true);
			
			// pwIM.write("public class InstrumentedMethod extends java.lang.Object \n{\n");
			
			if(inductivesAsRelevants)
			{
				try{
					inductivesReader.analyze(args[0]+".ind");
				}
				catch (Exception e) {
				}
			}

			soot.options.Options sootOpt =	soot.options.Options.v();
		
	        sootOpt.set_src_prec(soot.options.Options.src_prec_class);
	      //  sootOpt.set_java_version(soot.options.Options.java_version_1_7);
	        
	        
	        //sootOpt.set_asm_backend(true);
	        //esto lo saque porque sino no anda pero estoy usando la version incorrecta de soot
	        //sootOpt.set_asm_backend(true);      
	        //sootOpt.set_keep_offset(true);
	        sootOpt.set_main_class(args[0]);
	        //sootOpt.set_keep_line_number(false);
	        sootOpt.set_write_local_annotations(true);
	        
	        sootOpt.set_xml_attributes(true);
	        sootOpt.print_tags_in_output();
	        
	        Scene.v().setSootClassPath(args[2]);
	        
	        System.out.println(args[2]);
	        System.out.println("____________________________________");
	        

			
	        
			soot.Main.main(opts);
			
			
			
			//MethodInstrumenterForDaikon.getInstance().getRelevantsMap().put(srcSig, annotation.get)
			for(EscapeAnnotation annotation : annotatedMethods)
			{
				//para hacer el binding en el .cs
				MethodInstrumenterForDaikon.getInstance().getRelevantsMap().put(annotation.getSignature(), annotation.getRelevantParameters());
				
				ListDIParameters parametersInit = new ListDIParameters();
				Iterator it = annotation.getParameters().iterator();
				while(it.hasNext())
				{
					DI_JsonParameter jsonParameter = ((DI_JsonParameter) it.next()).clone();
					jsonParameter.name = jsonParameter.name + "_init";
					parametersInit.add(jsonParameter);
				}
				MethodMapInfo m = new MethodMapInfo(annotation.getParameters(), parametersInit, annotation.getClassName());
				
				
				MethodInstrumenterForDaikon.getInstance().getMethodMap().put(annotation.getSignature(), m);
				
				//add relevant parameters and parameters to corresponding lists

			}
			
			
			
			//Scene.v().addClass(IntrumentedMethodClass);
			
			
			
			
			
			
			/*pwIM.println("public class InstrumentedMethod {");
			Iterator iter_types = getListMethodsTypes().iterator();
			for (Iterator iter = listMethods.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				List types = (List) iter_types.next();
				element=element.replaceAll("\\$","__");
				pwIM.println("  public static void "+ element);
				pwIM.println();
				pwIM.println();
			}
			pwIM.println("}");
			
			
			pwIM.flush();
//			pwIM.write("}\n");
//			pwIM.flush();*/
			

			

            BufferedWriter bwrCS = new BufferedWriter
            	    (new OutputStreamWriter(new FileOutputStream(fullOutputDir + "/" + args[0] + ".cs"),"UTF-8"));
			
			FileOutputStream streamCS = new FileOutputStream(fullOutputDir + "/" + args[0] + ".cs");
			FileOutputStream streamCC = new FileOutputStream(fullOutputDir + "/" + args[0] + ".cc");
			FileOutputStream streamInductivesFakes = new FileOutputStream(fullOutputDir + "/" + args[0] + ".indFake");

			PrintStream outCS = new PrintStream(streamCS);
			PrintStream outCC = new PrintStream(streamCC);
			PrintStream outInductivesFakes = new PrintStream(streamInductivesFakes);

			showInstrumentationSites(MethodInstrumenterForDaikon.getNewsMap(), bwrCS);
			showCallSites(MethodInstrumenterForDaikon.getCcArgsMap(), MethodInstrumenterForDaikon.getCcMethodsMap(),
					MethodInstrumenterForDaikon.getMethodMap(), MethodInstrumenterForDaikon.getNewsMap(), MethodInstrumenterForDaikon.getArgsCallsList(), MethodInstrumenterForDaikon.getRelevantsMap(), outCC);
			showFakeInductives(MethodInstrumenterForDaikon.getNewsMap(), outInductivesFakes);
			writeInstrumentedMethods(optsDefault[2], fullOutputDir);
		
			writeAnnotations(fullOutputDir + "/" + args[0] + ".ann");
			
			

			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			}
	}
	
	static String mainClass;
	static String fullOutputDir;
	private static void writeAnnotations(String location) {
		
		List<AnnotationSiteInvariantForJson> siteInvariants = MethodInstrumenterForDaikon.getInstrumentationSiteInvariants(); 

		if(siteInvariants.size() > 0)
		{
			File srcFile = new File(location);
			
			if (!srcFile.getParentFile().exists()) {
				srcFile.getParentFile().mkdirs();
			}
					
			try {
				//xstream.toXML(ir_method, new FileWriter(srcFile, false));
				//PrintWriter writer = new PrintWriter(srcFile, "UTF-8");
				//writer.println(ir_method.toHumanReadableString());
				//writer.close();
				
	            //BufferedWriter bwr = new BufferedWriter(new FileWriter(srcFile));
	            
	            BufferedWriter bwr = new BufferedWriter
	            	    (new OutputStreamWriter(new FileOutputStream(srcFile),"UTF-8"));
	            
	            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
				Gson gson = builder.create();
	            
				
				Type listType = new TypeToken<List<AnnotationSiteInvariantForJson>>() {}.getType();
				 String json = gson.toJson(siteInvariants, listType);
				
	            JsonInstrumentationSiteInvariantsWriter jsonInstrumentationSiteInvariantsWriter = new JsonInstrumentationSiteInvariantsWriter(); 
	            
	            String s =  StringEscapeUtils.unescapeJava(json); 
	            bwr.write(s);
	            bwr.flush();
	    		/*for (AnnotationSiteInvariantForJson siteInvariant : siteInvariants) {
	    			//TODO: agregar los parametros o un mejor nombre para debug
	    			jsonInstrumentationSiteInvariantsWriter.write(siteInvariant, bwr, gson);			    			
	    		}*/
	            bwr.close();
	            
			} catch (IOException e) {
				//log.error("Error al imprimir el summary para el site invariant [" + siteInvariant.toString() + "] a xml: " + e.getMessage(), e);
			} catch(Exception e) {
				//log.error("Que carajo esta pasando??");
			}
		}

		
	}
	/**
	 * Writes the dummy methods for Instrumentation in the corresponding file
	 * @param opt
	 * @param fullOutputDir
	 */
	public static void writeInstrumentedMethods(String opt, String fullOutputDir)
	{
	    Iterator it = instrumentedMethodClasses.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			
			String packageName = (String) pair.getKey();
			InstrumentedMethodClass ins = (InstrumentedMethodClass) pair.getValue();
			try
			{
				String subFolder = ins.packageName.replace(".", "/");
				FileOutputStream streamIM = null;
				File dirWithPackage=  new File(fullOutputDir + "/" + subFolder);
				if(dirWithPackage.exists() && dirWithPackage.isDirectory())
				{
					if(opt.equals("c") || opt.equals("J"))
						// streamIM = new  FileOutputStream(outputDir+"/InstrumentedMethod.jimple");
						streamIM = new FileOutputStream(dirWithPackage.toString() + "/InstrumentedMethod.java");
					else
						streamIM = new FileOutputStream(dirWithPackage.toString()+ "/DummyInstrumentedMethod.jimple");
					PrintStream outIM = new PrintStream(streamIM);
					pwIM = new PrintWriter(outIM,true);
					pwIM.println("package " + ins.packageName + ";");
					
					for(String sClass : ins.usedClasses)
					{
						pwIM.println("import " + sClass + ";");
					}
					
					pwIM.println("public class InstrumentedMethod {");
					for (Iterator iter_methods = ins.methods.iterator(); iter_methods.hasNext();) {
						String element = (String) iter_methods.next();
						element=element.replaceAll("\\$","__");
						pwIM.println("  public static void "+ element);
						pwIM.println();
						pwIM.println();
					}
					pwIM.println("}");
					
					
					pwIM.flush();
				}
				else
				{
					throw new Exception("Package doesn't exist");
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				}
		}
	}
	
	
	
	/**
	 * FORMAT: InsSite=#id;[liveVars];method-belongs-to;CC/CS;csArrayParams
	 * @param csMap
	 * @param out
	 */
	private static void showInstrumentationSites(Map csMap, BufferedWriter bwrCS) {
		
		JsonInstrumentationSiteWriter writer = new JsonInstrumentationSiteWriter();
		writer.registerTypeAdapters();
		for (Iterator it = csMap.keySet().iterator(); it.hasNext();) {
			String cs = (String)it.next();
			
			//por que los toma como CreationSite? Son Instrumentation sites. Me parece que los usa "de comodin"
			CreationSiteMapInfo csInfo = (CreationSiteMapInfo) csMap.get(cs);
	
			
			//csInfo.setKey(cs);
			
			writer.write(csInfo, bwrCS);
			

		}
		
		
		
	}
	
	
	/*private static void showInstrumentationSites(Map csMap, PrintStream out) {

		out.println("Inductives");
		for (Iterator it = csMap.keySet().iterator(); it.hasNext();) {
			String cs = (String)it.next();
			CreationSiteMapInfo csInfo = (CreationSiteMapInfo) csMap.get(cs);	
			
			out.println(cs + "=" + csInfo.toString());
			
			
		}
	}*/

	
	private static void showFakeInductives(Map csMap, PrintStream out) {

		out.println("Inductives");
		for (Iterator it = csMap.keySet().iterator(); it.hasNext();) {
			String cs = (String)it.next();
			CreationSiteMapInfo csInfo = (CreationSiteMapInfo) csMap.get(cs);
				
			String list = csInfo.inductivesFake.toString();
			//list = list.replaceAll("\\$","__");
			
			//algunas variables tienen numeral. Deberia cambiarlo por un __n__ o algo que no joda a $
			list = list.replaceAll("#","_");
			//list = list.replaceAll("$","__");
			// list = list.replaceAll("\\$","_");
			out.println(cs + "=" + list +";[]");
			
			
		}
	}

	
	//de aca van a salir los relevant parameters
	public static void showCallSites(Map ccArgsMap, Map ccMethodsMap, Map methodMap, Map csMap, Deque argsMethodList, Map relevantsMap, PrintStream out) {
		out.println("Call Sites");
		
		Iterator<CallInfo> iter = argsMethodList.iterator();
		
		//Itero un LIFO porque soot me devuelve los metodos en el orden topologico inverso
		//No uso STACK porque tiene un bug que me hace iterarlo como FIFO y yo quiero LIFO
		while(iter.hasNext())
		{
			//cree una clase nueva CallInfo. Es parecida a CallSiteMapInfo pero quiero hacerlo en paralelo en vez de tocar esa clase asi no rompo codigo
			//despues volare CallSiteMapInfo si no la necesito mas
			CallInfo callInfo = iter.next();
			
			System.out.println(callInfo.toString());
			
			String callSite = callInfo.getInsSite();
			ListDIParameters args = callInfo.getArgs();
			CallSiteMapInfo ccInfo = getCallSiteInfo(callSite, args, ccArgsMap,ccMethodsMap, csMap, methodMap, callInfo, relevantsMap);

			//TODO los params no tienen bien las derived variables como los params init
			out.println(callSite + "=" + ccInfo);
		}
	}
	/**
	 * 
	 * @param callSite
	 * @param args
	 * @param ccArgsMap
	 * @param ccMethodsMap
	 * @param csMap Esto tiene, entre otras cosas, informacion de las variables (tipo a.f) relevantes de cada instrumentation site
	 * @param methodMap
	 * @param callInfo 
	 * @return
	 */
	private static CallSiteMapInfo getCallSiteInfo(String callSite, ListDIParameters args, Map ccArgsMap, Map ccMethodsMap, Map csMap, Map methodMap, CallInfo callInfo, Map relevantsMap)
	{
		// ListDIParameters args = (ListDIParameters) ccArgsMap.get(callSite);;
		
			
		//	ListDIParametersNoRep relevants = csInfo.getObjectVars();
			
		
		
		String m = (String) ((Object[])ccMethodsMap.get(callSite))[0];		
		
		
		
		
		//por que los toma como CreationSite? Son Instrumentation sites. Me parece que los usa "de comodin"
		
		
		//TODO tengo que recorrerlos AL REVES porque con soot los fui metiendo en el orden inverso
		
		/*for (Iterator it = csMap.keySet().iterator(); it.hasNext();) {
			String cs = (String)it.next();
			CreationSiteMapInfo csInfo = (CreationSiteMapInfo) csMap.get(cs);

			
			System.out.println("Comparando con..." + csInfo.methodCaller);
			if (csInfo.methodCaller.equals(callInfo.getCallee()))
			{
				System.out.println("Hola hola hola!");
			}
			
		}		
		*/
		
		
		//args,callee,caller,args
		//callInfo
		
		//Consigo los insSites pertenecientes al callee
		
		
		
		
		//1) Consigo el binding entre objetos <--- Listo
		//a) Mientras itero sobre los bindings, ya voy agregando los derived variables de tipo size de lista, length de string
		//b) Mientras itero sobre los bindings, chequeo si alguna variable relevante del callee contiene a ese objeto.
		//Tambien miro L(callee) para ello (voy a necesitar una estructura de datos mas)
		//El "contiene" va a ser medio un hack de strings feo por ahora, creo
		//Si pasa eso, "agrego el field" al binding de objetos, creando uno nuevo
		// Agrego a L(caller) las variables que agregue al binding en el paso anterior
		//
		
		
		
		CreationSiteMapInfo csInfo = (CreationSiteMapInfo) csMap.get(callSite);
		
				
		//String caleeInductives = csInfo.inductivesFake.toString();
		//list = list.replaceAll("\\$","__");
		
		//algunas variables tienen numeral. Deberia cambiarlo por un __n__ o algo que no joda a $
		//inductives = inductives.replaceAll("#","_");
		
		MethodMapInfo mInfo = (MethodMapInfo) methodMap.get(m);
		ListDIParameters params=null,paramsInit=null;
		String nameClass = (String) ((Object[])ccMethodsMap.get(callSite))[1];
		if(mInfo!=null)
		{
			params = mInfo.getParams();
			paramsInit = mInfo.getParamsInit();
			// nameClass = mInfo.nameClass;
		}
		else
		{
			// System.out.println("Diego: NULL "+callSite+":"+m);
			params=new ListDIParameters();
			paramsInit=new ListDIParameters();
		}
		
		InductiveVariablesInfo IVInfo = getInductivesReader().getiInfo(callSite);
		
		List paramInitFil = new ArrayList(), 
		                 paramFil = new ArrayList(), 
						 argFil=new ArrayList();
		
				
		Iterator itP = params.iterator();
		Iterator itA = args.iterator();
		Iterator itPinit = paramsInit.iterator();
		
		// System.out.println("Magui:"+callSite+" A:"+args+" P:"+params+" PI:"+paramsInit);
		/*
		if(callSite.indexOf("44c")!=-1)
		{
			System.out.println();
		}
		*/
		ListDIParameters relevant_variables_callee = (ListDIParameters) relevantsMap.get(callInfo.getCallee());
		
		for(; itA.hasNext();)
		{
			DIParameter a = (DIParameter) itA.next();
			if(itP.hasNext())
			{
				DIParameter pInit = (DIParameter)itPinit.next();
				DIParameter p = (DIParameter) itP.next();
				/* Diego 1-7-09 
				if(  ( InductiveVariablesInfo.isAcceptedInductive(IVInfo, a)
					   || (a instanceof DI_Value && !((DI_Value)a).isNull() ) )
					&& InductiveVariablesInfo.isAcceptedInductive(IVInfo, p)
					&& InductiveVariablesInfo.isAcceptedInductive(IVInfo, pInit)
					 )
				{
				*/
				if(!ProgramInstrumentatorForDaikonMain.inductivesAsRelevants
						|| ( (IVInfo!=null && !IVInfo.isInExcludeInfo(a.getName())) 
								|| (a instanceof DI_Value && !((DI_Value)a).isNull() ) )
					)
				{
					// DIEGO OJO! Tiene que decir compatible!
					//mhm, aca se fija que los argumentos y los parametros bindeen bien
					//pero no se como machearlos....
					//tampoco entiendo por que importan los argumentos
					//ah, creo que es para hacer el binding despues
					
					
					
					
					//if(true || a.getLocal().getType().equals(p.getLocal().getType()) 
					//		|| a.getDerivedVariables2_size()>=p.getDerivedVariables2_size())
					//{	
						
						
					//OK, creo que los parametros son los bla_derived
					//tengo que cambiarlo para que admite parametros...posta
					//HACK le puse false porque *supuestamente* ya no estamos usando mas esto
					//le saque el false 17-8-17
					//lo puse como true 19-10-17 porque "confio" en que me conviene hacer esto siempre
					//if(true || a.hasDerivedVariables2())
					
						
						
						
						
						
					//ListDIParameters la = a.getDerivedVariables();
					//ListDIParameters lp = p.getDerivedVariables();
					//ListDIParameters lpInit = pInit.getDerivedVariables();
					ListDIParameters la = a.toListDIP2();
					ListDIParameters lp = p.toListDIP2();
					ListDIParameters lpInit = pInit.toListDIP2();

					Iterator iter_lp = lp.iterator();
					Iterator iter_lpInit = lpInit.iterator();
					for (Iterator iter_la = la.iterator(); iter_la.hasNext();) {
						DIParameter e_a = (DIParameter) iter_la.next();
						if(iter_lp.hasNext())
						{
							DIParameter e_p  = (DIParameter) iter_lp.next();
							DIParameter e_pInit  = (DIParameter) iter_lpInit.next();
							/* Diego 1-7-09 
							if(InductiveVariablesInfo.isAcceptedInductive(IVInfo, e_a)
							&& InductiveVariablesInfo.isAcceptedInductive(IVInfo, e_p)
							&& InductiveVariablesInfo.isAcceptedInductive(IVInfo, e_pInit)
							)
							*/
							if(InductiveVariablesInfo.isAcceptedInductive(IVInfo, e_a)
								//	|| InductiveVariablesInfo.isAcceptedInductive(IVInfo, e_p)
								//	|| InductiveVariablesInfo.isAcceptedInductive(IVInfo, e_pInit)
									)
							{ 
								List l_pInit = e_pInit.toListOnlyEnterizedVariables();
								List l_p = e_p.toListOnlyEnterizedVariables();
								List l_e = e_a.toListOnlyEnterizedVariables();
								
								
								//puede pasar que un arg no sea relevante (por ser object) pero un parametro si (por ser entero)
								//una solucion posible para este problema es controlar que las cantidades sean las mismas
								//una mejor solucion seria que la transformacion a List de e_pInit, e_p y e_a se hagan en paralelo
								//y se tengan en cuenta solo las derivaciones (recursivas) que hay para los 3 objetos a la vez
								//pero no vale la pena
								if(l_pInit.size() == l_p.size() && l_p.size() == l_e.size())
								{
									paramInitFil.addAll(l_pInit);
									paramFil.addAll(l_p);
									argFil.addAll(l_e);
								}
							}
						}
						
					}
					
					

					String pInitName = pInit.getName();
					String pName = p.getName();
					String argName = a.getName();
					
					/*paramInitFil.add(pInitName);
					paramFil.add(pName);
					argFil.add(argName);*/
					
					
					List new_relevants = new Vector();
					
					
					
					//aca hago el binding entre las variables relevantes enteras del callee
					//y las del caller
					if (relevant_variables_callee != null)
					{
						for(Iterator iter = relevant_variables_callee.iterator(); iter.hasNext();)
						{
							DIParameter element = (DIParameter) iter.next();
							String relevantVar = element.toString();
							if(element.getName().equals(pName))
							{
								//int index = relevantVar.indexOf(pName);
								
								//String notBase = relevantVar.substring(index + pName.length());
								
								
								SimpleDIParameter new_relevant = new SimpleDIParameter(argName);
								
								
								Stack derived_parameters = new Stack();
																		
								derived_parameters.addAll(element.getDerivedVariables2());
								//Por cada field relevante agrego a la lista el binding entre fields
								
								for(Object o : element.toListOnlyFieldEnterizedVariables())
								{
									String fullName;
									if(o.getClass().equals(String.class))
									{
										fullName = (String) o;
									}
									else
									{
										JimpleLocal jlocal = (JimpleLocal) o;
										fullName = jlocal.toString();
									}
									String notBase = fullName.substring(fullName.indexOf(element.getName()) + element.getName().length());
									boolean contains = false;
									for(Object item : paramInitFil)
									{
										if (item.toString().equals(pInitName+notBase))
											contains =true;												
									}
									
									//creo que esto esta de mas
									if(!contains)
									{
										paramInitFil.add(pInitName + notBase);
										argFil.add(argName + notBase);
										paramFil.add(pName + notBase);
									}
									new_relevant.derivedFields.add(notBase);									

								}
								
								if(new_relevant.derivedFields.size > 0)
								{
									new_relevants.add(new_relevant);
								}
								
								/*while(!derived_parameters.empty())
								{
									
									DIParameter dip = (DIParameter) derived_parameters.pop();
									derived_parameters.addAll(dip.getDerivedVariables2());
									String fullName = dip.getName();
									String notBase = fullName.substring(fullName.indexOf(element.getName()) + element.getName().length());
									
									
									boolean contains = false;
									for(Object item : paramInitFil)
									{
										if (item.toString().equals(pInitName+notBase))
											contains =true;												
									}
									if(!contains)
									{
										paramInitFil.add(pInitName + notBase);
										argFil.add(argName + notBase);
										paramFil.add(pName + notBase);
									}

									
									new_relevant.derivedFields.add(notBase);
									
									
									System.out.println(notBase);
									
								}
								
								if(new_relevant.derivedFields.size > 0)
								{
									new_relevants.add(new_relevant);
								}*/
								
							}
							
						}
					}
					
					
					//ver binding.pdf para una explicacion del algoritmo
					
					//tal vez se generaron nuevas a partir de un callee de un callee
					List new_relevantsCallee = (List) newRelevantsMap.get(callInfo.getCallee());
					if(new_relevantsCallee != null)
					{
						for(Iterator iter = new_relevantsCallee.iterator(); iter.hasNext();)
						{
							SimpleDIParameter element = (SimpleDIParameter) iter.next();
							if(element.name.equals(pName))
							{
								//int index = relevantVar.indexOf(pName);
								
								//String notBase = relevantVar.substring(index + pName.length());
								
								
								SimpleDIParameter new_relevant = new SimpleDIParameter(argName);

								//Por cada field relevante agrego a la lista el binding entre fields
								for (Iterator dip_it= element.derivedFields.iterator(); dip_it.hasNext();)
								{
									String notBase = (String) dip_it.next();
									
									
									boolean contains = false;
									for(Object item : paramInitFil)
									{
										if (item.toString().equals(pInitName+notBase))
											contains =true;												
									}
									if(!contains)
									{
										paramInitFil.add(pInitName + notBase);
										argFil.add(argName + notBase);
										paramFil.add(pName + notBase);
									}
									
									new_relevant.derivedFields.add(notBase);									
									
								}
								
								if(new_relevant.derivedFields.size > 0)
								{
									new_relevants.add(new_relevant);
								}
							}
							
						}
					}
					
					
					/*if(pInit.getClass() != DI_Object.class)
					{
						SimpleDIParameter new_relevant = new SimpleDIParameter(pInit.getName());
						String base = new_relevant.name;

						for(Iterator itD = pInit.getDerivedVariables2().iterator(); itD.hasNext();)
						{
							
							DIParameter dip = (DIParameter) itD.next();
							
							String notBase = dip.getName().substring(dip.getName().indexOf(base) + base.length());
							
							paramInitFil.add(pInitName + notBase);
							argFil.add(argName + notBase);
							paramFil.add(pName + notBase);

							
							new_relevant.derivedFields.add(notBase);
							
							
						}
						
						if(new_relevant.derivedFields.size > 0)
						{
							new_relevants.add(new_relevant);
						}
					}*/
					
					//agrego las que ahora son relevantes para el caller
					newRelevantsMap.put(callInfo.getCaller(), new_relevants);
							

					
				}
			}
		}
		
		
		
		/*
	 	List<String> newParamInits = new ArrayList<String>();
		String[] inductivesArray = inductives.substring(1,inductives.length()-1).split(","); 
		for(int i = 0; i < inductivesArray.length; i++)
		{
			String ind = inductivesArray[i].trim();
			for(Iterator paramIt = paramInitFil.iterator(); paramIt.hasNext();)
			{
				String param = paramIt.next().toString().trim();
				if (ind.contains("."))
				{
					String base = ind.split(".")[0];
					if(base.contains(param))
					{
						newParamInits.add(ind);
					}
				}
			}
		}
		newParamInits.addAll(paramInitFil);
		*/
		
		
		
	
		

		List argList = new ArrayList();
		argList.addAll(argFil);
		
		List paramList = new ArrayList();
		paramList.addAll(paramFil);
		
		List paramInitList = new ArrayList();
		paramInitList.addAll(paramInitFil);
		
		return new CallSiteMapInfo(argList,
				                   paramList,
								   paramInitList, 
								   m, nameClass);

	}
	

	/**
	 * @return Returns the intrumentedMethodClass.
	 */
	public static SootClass getIntrumentedMethodClass() {
		return IntrumentedMethodClass;
	}
	public static ArrayList<SootClass> getIntrumentedMethodSootClasses() {
		return IntrumentedMethodSootClasses;
	}
	/**
	 * @return Returns the pwIM.
	 */
	public static PrintWriter getPwIM() {
		return pwIM;
	}
	/**
	 * @return Returns the inductivesReader.
	 */
	public static InductivesReader getInductivesReader() {
		return inductivesReader;
	}
	/**
	 * @return Returns the listMethods.
	 */
	public static List getListMethods() {
		return listMethods;
	}
	
	public static List getListMethodsTypes() {
		return listTypes;
	}
	
	public static Map getInstrumentedMethodClasses()
	{
		return instrumentedMethodClasses;
	}

}




/*
Local varInit = Jimple.v().newLocal(var.getName()+ "_init",
var.getType());

Collection code = codeForAssig(var, varInit, body);
*/
