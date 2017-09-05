package ar.uba.dc.analysis.automaticinvariants.pruebas;

import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.pruebas.TestLV;
import soot.Body;
import soot.BodyTransformer;
import soot.Main;
import soot.PackManager;
import soot.Scene;
import soot.Transform;
import soot.Value;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import soot.toolkits.graph.CompleteUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLVMain {

	public static Map analysisCache = new HashMap();
	public  static Stack stack = new Stack();
	public  static Set alreadyAnalyzedMethods = new HashSet();
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Syntax: NewsInvariantInstrumentator[soot options]");
			System.exit(0);
		}
		String outputDir="out";

		// Load java.lang.Object
		Scene.v().loadClassAndSupport("java.lang.Object");
		// Declare 'public class HelloWorld'

		String[] optsDefault = {/*"-w", */"-app",
				"-f","J",
				"-d",outputDir,
				"-src-prec","class",
				"-keep-line-number", 
				"-keep-bytecode-offset",
				"-x", "gnu", "-x", "spec.io",
				"-x", "java.lang.StringBuffer",
				"-p", "jb",	"use-original-names:true",
				"-p", "jb.a", "enabled:false",
				"-p", "jb.ne", "enabled:false",
				"-p", "jb.uce","enabled:false",
				"-p","jb.dae", "enabled:false",
				"-p","jb.ule", "enabled:false",
				"-p","jb.cp", "enabled:false",
				};
		
		String[] opts = new String[optsDefault.length + args.length];
		for (int i = 0; i < optsDefault.length; i++)
			opts[i] = optsDefault[i];
		for (int i = 0; i < args.length; i++)
			opts[i + optsDefault.length] = args[i];

//		PackManager.v().getPack("wjtp").add(
//				new Transform("wjtp.livevars", GlobalLiveVariablesAnalysis.v()));

		PackManager.v().getPack("jtp").add(
				new Transform("jtp.prueba", TestLV.v()));
		try {
			
			soot.Main.main(opts);
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			}
	}
}

