package ar.uba.dc.analysis.automaticinvariants.inductives;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.uba.dc.analysis.automaticinvariants.pruebas.TestLV;
import soot.PackManager;
import soot.Scene;
import soot.Transform;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InductivesFinderMain {
	
	public static Map inductivesMap = new HashMap(); 
	public static Map inductivesSTMTMap = new HashMap();
	

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Syntax: NewsInvariantInstrumentator[soot options]");
			System.exit(0);
		}
		String outputDir="out";

		// Load java.lang.Object
		Scene.v().loadClassAndSupport("java.lang.Object");
		// Declare 'public class HelloWorld'

		String[] optsDefault = { "-app", "-f", "c",
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

		PackManager.v().getPack("jtp").add(
				new Transform("jtp.prueba", TestLV.v()));
		
		PackManager.v().getPack("jtp").add(
				new Transform("jtp.instrumenter", InductivesFinder.v()));

		try {
			
			// Generates sootOutput/InstrumentedMethod.jimple"
//			FileOutputStream streamIM = null;
//			if(optsDefault[2].equals("c"))
//				streamIM = new FileOutputStream(outputDir+"/InstrumentedMethod.jimple");
//			else
//				streamIM = new FileOutputStream(outputDir+"/DummyInstrumentedMethod.jimple");
//			PrintStream outIM = new PrintStream(streamIM);
//			pwIM = new PrintWriter(outIM,true);
//			
//			pwIM.write("public class InstrumentedMethod extends java.lang.Object \n{\n");
//			
//			try{
//				inductivesReader.analyze(args[0]+".ind");
//			}
//			catch (Exception e) {
//			}
			
			
			soot.Main.main(opts);
			
//			pwIM.flush();
//			pwIM.write("}\n");
//			pwIM.flush();
//			
//			
//			FileOutputStream streamCS = new FileOutputStream(args[0] + ".cs");
//			FileOutputStream streamCC = new FileOutputStream(args[0] + ".cc");
			FileOutputStream streamInductivesFakes = new FileOutputStream(args[0] + ".indFake2");
//
//			PrintStream outCS = new PrintStream(streamCS);
//			PrintStream outCC = new PrintStream(streamCC);
			PrintStream outInductivesFakes = new PrintStream(streamInductivesFakes);
//
//			showCreationSites(NewsInstrumenterDaikon.getNewsMap(), outCS);
//			showCallSites(NewsInstrumenterDaikon.getCcArgsMap(), NewsInstrumenterDaikon.getCcMethodsMap(),
//					NewsInstrumenterDaikon.getMethodMap(), outCC);
//			showFakeInductives(NewsInstrumenterDaikon.getNewsMap(), outInductivesFakes);
			
			
			outInductivesFakes.println("Inductives");
			for (Iterator iter = inductivesMap.keySet().iterator(); iter.hasNext();) {
				String is = (String) iter.next();
				String inductives = inductivesMap.get(is).toString();
				inductives=inductives.replace('{','[');
				inductives=inductives.replace('}',']');
				outInductivesFakes.println(is+"="+inductives+";[]");
			 }

			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			}
	}
		
	

}

