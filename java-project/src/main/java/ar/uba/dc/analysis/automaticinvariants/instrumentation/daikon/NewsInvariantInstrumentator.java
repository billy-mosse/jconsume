package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;


import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameterFactory;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Iterator;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Object;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Value;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParametersNoRep;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListNoRep;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GLVMain;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLive;
import ar.uba.dc.analysis.automaticinvariants.pruebas.TestLV;
import ar.uba.dc.analysis.automaticinvariants.inductives.InductivesFilter;
import ar.uba.dc.analysis.automaticinvariants.inductives.InductivesFinder;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CallSiteMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CreationSiteMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductiveVariablesInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductivesReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.MethodMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.NewsInstrumenterDaikon;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.NewsInvariantInstrumentator;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import soot.Body;
import soot.IntType;
import soot.Local;
import soot.MethodOrMethodContext;
import soot.Modifier;
import soot.PackManager;
import soot.ResolutionFailedException;
import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Transform;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.VoidType;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.GotoStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.IfStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.NewArrayExpr;
import soot.jimple.NewExpr;
import soot.jimple.NewMultiArrayExpr;
import soot.jimple.NullConstant;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.jimple.toolkits.annotation.logic.LoopFinder;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
//import soot.jimple.toolkits.callgraph.EdgeFilter;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import soot.toolkits.graph.CompleteUnitGraph;
import soot.toolkits.graph.DominatorsFinder;
import soot.toolkits.graph.SimpleDominatorsFinder;
import soot.toolkits.scalar.LiveLocals;
//import sun.security.action.GetLongAction;
import soot.util.Chain;
/**
 * Tomado del ejemplo del GotoInstrumenter
 */

public class NewsInvariantInstrumentator {
	private  static SootClass IntrumentedMethodClass=null;
	private static PrintWriter pwIM;
	private static InductivesReader inductivesReader= new InductivesReader();
	private static String outputDir = "./out";
	private static List listMethods = new Vector();
	public static boolean inductivesAsRelevants = false;

	public static boolean isInCG(SootMethod m)
	{
		NewsInvariantInstrumentator.computeReachableMethods();
		if(NewsInvariantInstrumentator.cgMethods!=null)
		{			
			boolean found = NewsInvariantInstrumentator.cgMethods.contains(m);		
			return found;
		}
		return true;
	}
	public static void computeReachableMethods() {
		if(NewsInvariantInstrumentator.cgMethods==null)
		{
			CallGraph cg  = Scene.v().getCallGraph();
			if(cg!=null)
			{
				NewsInvariantInstrumentator.cgMethods = new HashSet();
				for(Iterator it = cg.sourceMethods(); it.hasNext();)
				{
					MethodOrMethodContext mc = (MethodOrMethodContext)it.next();
					if(mc!=null && mc.method()!=null) {
						NewsInvariantInstrumentator.cgMethods.add(mc.method());
						System.out.println("M:"+mc.method().getSignature());
					}
				}	
			}
		}
	}

	private static boolean isNotAnalyzable(String srcClass, String srcSig) {
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
    	boolean srcNotAnalyzable = isNotAnalyzable(srcClass, srcSig);
    	
    	boolean tgtNotAnalyzable = isNotAnalyzable(tgtClass, tgtSig);
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
		Scene.v().addBasicClass("VarTest",SootClass.SIGNATURES);
		//Scene.v().addBasicClass("ar.uba.dc.daikon.RichNumberPublic",SootClass.SIGNATURES);
		
        //EdgeFilter ef = new EdgeFilter(pred);
        ReachableMethods.setEdgePredicate(new EdgePredicate() {
        	@Override
			public boolean want(Edge e) {
        		return !IsFiltered(e);
        	}
		});
		
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
		IntrumentedMethodClass = new SootClass("InstrumentedMethod",
				Modifier.PUBLIC);
		// 'extends Object'
		IntrumentedMethodClass.setSuperclass(Scene.v().getSootClass(
				"java.lang.Object"));
		IntrumentedMethodClass.setModifiers(Modifier.PUBLIC);
		//Scene.v().addClass(IntrumentedMethodClass);
		
		
		String[] optsDefault = { 
				"-app", 
				"-f", "c", //J es jimple, j es jimp (jimple simplificado)
				"-d",outputDir,
				"-W", //Cambie w por W. Sera lo mismo? HACK TODO: revisar
				
				"-java-version", "1.8",

				//"-version",
				"-p", "cg", "enabled:true",
				 
				 
				 //"-asm-backend",
				 "-p", "cg", "jdkver:8", //lo saque a ver que pasa.
				 
				 
				//"-version",
				 
//				 "-p", "cg.cha","enabled:true",
				 "-p", "cg.spark","enabled:true",
//				 "-p", "cg.spark", "on-fly-cg:false",
				"-allow-phantom-refs", //Internet me dice que el error viene de ahi
				"-src-prec","class",
				"-keep-line-number", 
				"-keep-bytecode-offset",
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
				//"-version",
				"-p", "jj",	"use-original-names:true", 
				"-p","jj.dae", "enabled:false",
				 "-p","jj.ule", "enabled:false",
				
				 "-p","jap.dmt","enabled:true",
				 "-p", "jb.ulp", "unsplit-original-locals:false",
				 "-p", "jj.ulp", "unsplit-original-locals:false"
				
				//TODO: mañana cambiar esto por args[0] y chequear que anda
				//"-main-class", args[0]/*, args[0]*/
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
		PackManager.v().getPack("jtp").add(
				new Transform("jtp.inductives", InductivesFinder.v()));
		
		PackManager.v().getPack("jtp").add(
				new Transform("jtp.instrumenter", NewsInstrumenterDaikon.v()));
		
		
		
		try {
			
			// Generates sootOutput/InstrumentedMethod.jimple"
			FileOutputStream streamIM = null;
			if(optsDefault[2].equals("c") || optsDefault[2].equals("J"))
				// streamIM = new  FileOutputStream(outputDir+"/InstrumentedMethod.jimple");
				streamIM = new FileOutputStream(outputDir+"/InstrumentedMethod.java");
			else
				streamIM = new FileOutputStream(outputDir+"/DummyInstrumentedMethod.jimple");
			PrintStream outIM = new PrintStream(streamIM);
			pwIM = new PrintWriter(outIM,true);
			
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
	        sootOpt.set_java_version(soot.options.Options.java_version_1_8);
	        sootOpt.set_asm_backend(true);      
	        sootOpt.set_keep_offset(true);
	        sootOpt.set_main_class(args[0]);
	        sootOpt.set_keep_line_number(true);
	        
			
				        
	        Scene.v().setSootClassPath(args[2]);
	        

			
			
	        
			soot.Main.main(opts);
			
			
			Scene.v().addClass(IntrumentedMethodClass);
			
			
			String mainClass = args[0];
			
			if(mainClass.contains("."))
			{
				mainClass = mainClass.substring(0, mainClass.lastIndexOf("."));
				pwIM.println("package " + mainClass + ";");
			}
			
			
			
			pwIM.println("public class InstrumentedMethod {");
			
			for (Iterator iter = listMethods.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				element=element.replaceAll("\\$","__");
				pwIM.println("  public static void "+ element);
				pwIM.println();
				pwIM.println();
			}
			pwIM.println("}");
			
			
			pwIM.flush();
//			pwIM.write("}\n");
//			pwIM.flush();
			
			
			FileOutputStream streamCS = new FileOutputStream("out/" + args[0] + ".cs");
			FileOutputStream streamCC = new FileOutputStream("out/" + args[0] + ".cc");
			FileOutputStream streamInductivesFakes = new FileOutputStream("out/" + args[0] + ".indFake");

			PrintStream outCS = new PrintStream(streamCS);
			PrintStream outCC = new PrintStream(streamCC);
			PrintStream outInductivesFakes = new PrintStream(streamInductivesFakes);

			showCreationSites(NewsInstrumenterDaikon.getNewsMap(), outCS);
			showCallSites(NewsInstrumenterDaikon.getCcArgsMap(), NewsInstrumenterDaikon.getCcMethodsMap(),
					NewsInstrumenterDaikon.getMethodMap(), outCC);
			showFakeInductives(NewsInstrumenterDaikon.getNewsMap(), outInductivesFakes);

			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			}
	}

	private static void showCreationSites(Map csMap, PrintStream out) {

		out.println("Instumentacion Sites");
		for (Iterator it = csMap.keySet().iterator(); it.hasNext();) {
			String cs = (String)it.next();
			CreationSiteMapInfo csInfo = (CreationSiteMapInfo) csMap.get(cs);
			out.println(cs + "=" + csInfo);
		}
	}
	
	private static void showFakeInductives(Map csMap, PrintStream out) {

		out.println("Inductives");
		for (Iterator it = csMap.keySet().iterator(); it.hasNext();) {
			String cs = (String)it.next();
			CreationSiteMapInfo csInfo = (CreationSiteMapInfo) csMap.get(cs);
			String list = csInfo.vars.toString();
			list = list.replaceAll("\\$","__");
			// list = list.replaceAll("\\$","_");
			out.println(cs + "=" + list +";[]");
		}
	}

	public static void showCallSites(Map ccArgsMap, Map ccMethodsMap, Map methodMap, PrintStream out) {
		out.println("Call Sites");
		for (Iterator it = ccArgsMap.keySet().iterator(); it.hasNext();) {
			String callSite = (String) it.next();
			CallSiteMapInfo ccInfo = getCallSiteInfo(callSite,ccArgsMap,ccMethodsMap, methodMap);

			out.println(callSite + "=" + ccInfo);
		}
	}
	private static CallSiteMapInfo getCallSiteInfo(String callSite, Map ccArgsMap, Map ccMethodsMap, Map methodMap)
	{
		 ListDIParameters args = (ListDIParameters) ccArgsMap.get(callSite);;
		
		String m = (String) ((Object[])ccMethodsMap.get(callSite))[0];
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
		
		List paramInitFil = new Vector(), 
		                 paramFil = new Vector(), 
						 argFil=new Vector();
		
				
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
		
		for(; itA.hasNext();)
		{
			DIParameter a = (DIParameter) itA.next();
			if(a.toString().indexOf("size")!=-1)
			{
				System.out.println();
			}
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
				if(!NewsInvariantInstrumentator.inductivesAsRelevants
						|| ( (IVInfo!=null && !IVInfo.isInExcludeInfo(a.getName())) 
								|| (a instanceof DI_Value && !((DI_Value)a).isNull() ) )
					)
				{
					// DIEGO OJO! Tiene que decir compatible!
					if(a.getLocal().getType().equals(p.getLocal().getType()) 
							|| a.getDerivedVariables().size()>=p.getDerivedVariables().size()  )
					{	
						
						
						//OK, creo que los parametros son los bla_derived
						//tengo que cambiarlo para que admite parametros...posta
						//HACK le puse false porque *supuestamente* ya no estamos usando mas esto
						if(false/*a.hasDerivedVariables()*/)
						{
							//ListDIParameters la = a.getDerivedVariables();
							//ListDIParameters lp = p.getDerivedVariables();
							//ListDIParameters lpInit = pInit.getDerivedVariables();
							ListDIParameters la = a.toListDIP();
							ListDIParameters lp = p.toListDIP();
							ListDIParameters lpInit = pInit.toListDIP();
//							
							System.out.println("a p pinit");
//							System.out.println(la);
//							System.out.println(lp);
//							System.out.println(lpInit);
							if(!lp.isEmpty() && lpInit.isEmpty())
							{
								System.out.println("");
							}
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
										paramInitFil.addAll(e_pInit.toList());
										paramFil.addAll(e_p.toList());
										argFil.addAll(e_a.toList());
									}
								}
								
							}
						}
						else
						{
							paramInitFil.add(pInit.getName());
							paramFil.add(p.getName());
							argFil.add(a.getName());
						}
					}
				}
			}
		}
	
		return new CallSiteMapInfo(argFil,
				                   paramFil,
								   paramInitFil, 
								   m, nameClass);

	}
	

	/**
	 * @return Returns the intrumentedMethodClass.
	 */
	public static SootClass getIntrumentedMethodClass() {
		return IntrumentedMethodClass;
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

}




/*
Local varInit = Jimple.v().newLocal(var.getName()+ "_init",
var.getType());

Collection code = codeForAssig(var, varInit, body);
*/
