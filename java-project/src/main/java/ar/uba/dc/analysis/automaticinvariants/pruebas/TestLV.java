package ar.uba.dc.analysis.automaticinvariants.pruebas;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.NewsInvariantInstrumentator;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLive;
import ar.uba.dc.analysis.automaticinvariants.pruebas.TestLV;
import soot.Body;
import soot.BodyTransformer;
import soot.Unit;
import soot.Value;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import soot.toolkits.graph.CompleteUnitGraph;
import soot.util.Chain;

public class TestLV extends BodyTransformer
{
	
	private static TestLV instance=new TestLV();
	public static TestLV v()
	{
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see soot.BodyTransformer#internalTransform(soot.Body, java.lang.String, java.util.Map)
	 */
	protected void internalTransform(Body b, String phaseName, Map options) {
		if(!NewsInvariantInstrumentator.isInCG(b.getMethod()))
			return;
		Chain units= b.getUnits();
		CompleteUnitGraph unitGraph = new CompleteUnitGraph(b);
		GlobalLive gl = new GlobalLive(unitGraph);
		System.out.println("Metodo:"+b.getMethod().getSignature());
		
		// if(true) return;
		
		if(!units.isEmpty())
		{
			Unit first = (Unit) units.getFirst();
			// System.out.println("First="+gl.getLiveInitialFlow()+";[]");
		}
		for (Iterator iter = units.snapshotIterator(); iter.hasNext();) {
			Stmt s = (Stmt) iter.next();
			String is = Utils.getLineNumber(s);
			String prefijo = b.getMethod().getDeclaringClass().toString();
			// System.out.println(prefijo+"_"+is+" ("+s+") "+"="+gl.getLiveLocalsBefore(s)+";[]");
			/*
			if(s.containsInvokeExpr())
			{
				System.out.println(prefijo+"_"+is+"="+gl.getLiveLocalsBefore(s)+";[]");
			}
			if(s instanceof AssignStmt)
			{
				AssignStmt as = (AssignStmt)s;
				Value v = as.getRightOp();
				if(v instanceof AnyNewExpr)
				{
					System.out.println(prefijo+"_"+is+"="+gl.getLiveLocalsBefore(s)+";[]");
				}
				
			}
			*/
			
			
//			System.out.println("--");
//			System.out.println("b:"+gl.getLiveLocalsBefore(s));
//			System.out.println(s);
//			System.out.println("a:"+gl.getLiveLocalsAfter(s));
//			System.out.println("--");			
		}
	

	}
	
}