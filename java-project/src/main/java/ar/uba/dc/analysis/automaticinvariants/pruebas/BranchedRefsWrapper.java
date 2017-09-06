/*
 * Created on May 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.pruebas;

import java.util.Iterator;
import java.util.Map;

import ar.uba.dc.analysis.automaticinvariants.pruebas.BranchedRefsWrapper;
import soot.Body;
import soot.BodyTransformer;
import soot.jimple.Stmt;
import soot.jimple.toolkits.annotation.nullcheck.BranchedRefVarsAnalysis;
import soot.jimple.toolkits.callgraph.Units;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BranchedRefsWrapper extends BodyTransformer {
	

	/* (non-Javadoc)
	 * @see soot.BodyTransformer#internalTransform(soot.Body, java.lang.String, java.util.Map)
	 */
	private static BranchedRefsWrapper instance = new BranchedRefsWrapper();
	
	public static BranchedRefsWrapper v()
	{
		return instance;
	}
	protected void internalTransform(Body b, String phaseName, Map options) {
		// TODO Auto-generated method stub
		UnitGraph cfg =new ExceptionalUnitGraph(b);
		BranchedRefVarsAnalysis bra = new BranchedRefVarsAnalysis(cfg);
		System.out.println("--- "+b.getMethod().getName()+" ---");
		
		Chain  units = b.getUnits();
		Iterator stmtIt = units.snapshotIterator();
		
		boolean skip = true;
		Stmt s=null;
		while (stmtIt.hasNext() && skip) {
			s = (Stmt) stmtIt.next();
			System.out.println("STMT:"+s);
			System.out.println(bra.getFlowBefore(s));
			System.out.println(bra.getFallFlowAfter(s));
		}
		

	}
}
