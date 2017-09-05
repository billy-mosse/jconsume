/*
 * Created on 25/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.pruebas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLiveVariablesAnalysis;
import soot.Body;
import soot.G;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GlobalLiveVariablesAnalysis extends SceneTransformer {

	/**
	 * 
	 */
	private static GlobalLiveVariablesAnalysis instance=new GlobalLiveVariablesAnalysis();
//	 ---------------------------------------------------------
	// worklist (which is basically a queue) that stores methods whose
	// bodies have to be processed.
	private ArrayList worklist = new ArrayList();

	// ----------------------------------------------------------
	// The set of all methods already seen .
	private HashSet reachable_methods = new HashSet();
	private HashSet analyzed_methods = new HashSet();


	
	public static GlobalLiveVariablesAnalysis v()
	{
		return  instance;
	}

	/* (non-Javadoc)
	 * @see soot.SceneTransformer#internalTransform(java.lang.String, java.util.Map)
	 */
	@Override
	protected void internalTransform(String phaseName, Map options) {
		CallGraph cg = Scene.v().getCallGraph();
		
		if (!Scene.v().getMainClass().declaresMethod(
		"void main(java.lang.String[])"))
			throw new RuntimeException("couldn't find main() in main Class ("
					+ Scene.v().getMainClass() + ")");
		
		SootMethod mainMethod = Scene.v().getMainClass()
				.getMethodByName("main");
		// addMethod(mainMethod);
		
		for (Iterator iter = cg.sourceMethods(); iter.hasNext();) {
			SootMethod m = (SootMethod) iter.next();
			addToWorklist(m);
		}
		// addToWorklist(mainMethod);
		
		while (worklist.size() > 0) {
			SootMethod theMethod = (SootMethod) worklist.remove(0);
			G.v().out.println("========== " + theMethod);
		
			if (addMethod(theMethod))
				addCallersToWorklist(theMethod);
		
			if (!theMethod.hasActiveBody()) {
				G.v().out.println("WARNING: " + theMethod
						+ " had no active body\n");
				continue;
			}
		
			Body body = theMethod.getActiveBody();
		}

		
	}
	private boolean addMethod(SootMethod m) {
		boolean isNew = false;
		isNew=analyzed_methods.add(m);
		return isNew;
	}
	// --------------------------------------------------------------
	// a helper method for adding a newly-discovered reachable method
	// to the end of the worklist. this schedules the method for
	// processing in the future.

	private void addToWorklist(SootMethod m) {
		// debugging print
		//G.v().out.println("addToWorklist("+m+")");

		if (reachable_methods.contains(m))
		    return; // we only add methods that haven't been discovered yet

		if (!worklist.contains(m)) {
			worklist.add(m); // add at the end of the worklist
			//if (Verbose.worklist())
				// G.v().out.println("added to worklist: " + m);
		}

		// add to the set of reachable methods
		reachable_methods.add(m);
	}

	private void addCallersToWorklist(SootMethod m) {
		//G.v().out.println("addCallersToWorklist("+m+")");

		// on n'est jamais trop prudent
		addToWorklist(m);

		CallGraph cg = Scene.v().getCallGraph();

		Iterator mIt = cg.edgesInto(m);

		while (mIt.hasNext()) {
			SootMethod caller = ((Edge) mIt.next()).getSrc().method();
			// XXX peut-?tre qu'ici, ?a m?rite un appel ? addMethod

			if (isToBeProcessed(caller)) {
				// GS-12/11/2004-16:50
				// this 'if' is a hack. with this, i don't include
				// uncalled methods of Class I process. (for example,
				// compareToIgnoreCase, or lastIndexOf, that *do* call
				// <clinit>, but I don't want to process them if
				// they're not called during a program run)
				if (reachable_methods.contains(caller)) {
					//if (Verbose.worklist())
					//	G.v().out.println("    called by : " + caller);
					addToWorklist(caller);
				}
			}
		}
	}
	private boolean isToBeProcessed(SootMethod m) {

		// GS-19/11/2004-09:08
		// do not try to analyze unavailable methods...
		if (!m.hasActiveBody())
			return false;

		if (m.getDeclaringClass().isApplicationClass())
			return true;

		//G.v().out.println(m+" is to be skipped");
		return false;
	}
	
	
}
