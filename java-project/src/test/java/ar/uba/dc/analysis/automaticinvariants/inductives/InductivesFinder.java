package ar.uba.dc.analysis.automaticinvariants.inductives;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.NewsInvariantInstrumentator;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLive;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.SootClass;
import soot.Value;
import soot.jimple.IfStmt;
import soot.jimple.InstanceFieldRef;
import soot.jimple.Stmt;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.jimple.toolkits.annotation.logic.LoopFinder;
import soot.toolkits.graph.BlockGraph;
import soot.toolkits.graph.ClassicCompleteBlockGraph;
import soot.toolkits.graph.CompleteUnitGraph;
import soot.toolkits.graph.DominatorsFinder;
import soot.toolkits.scalar.FlowSet;
import soot.util.Chain;

public class InductivesFinder extends LoopFinder implements InductivesFilter{
	private static InductivesFinder instance = new InductivesFinder();
	private Map inductivesMap = new HashMap();
	public Map  inductivesBefore= new HashMap();
	public Map  inductivesAfter= new HashMap();
	
	public InductivesFinder() {}

	private DominatorsFinder dominator = null;
	
	public static InductivesFinder v() {
		return instance;
	}
	
	public static Map analysisCache = new HashMap(); 

	@Override
	protected void internalTransform(Body body, String arg1, Map arg2) {
		
		if(!NewsInvariantInstrumentator.isInCG(body.getMethod()))
			return;
		super.internalTransform(body, arg1, arg2);
		SootClass sClass = body.getMethod().getDeclaringClass();
		
		Chain units = body.getUnits();
		
		if(body.getMethod().getDeclaringClass().toString().equals("VarTest"))
			return;
		synchronized (this) {
			if (!Scene.v().getMainClass().declaresMethod(
					"void main(java.lang.String[])"))
				throw new RuntimeException("couldn't find main() in mainClass");
		}

		System.out.println("Analizando..." + body.getMethod().toString());
		
		// Perform Live Variables Analysis for each stmt of this method 
		CompleteUnitGraph unitGraph = new CompleteUnitGraph(body);
		BlockGraph blockGraph = new ClassicCompleteBlockGraph(body);
		
		if(body.getMethod().toString().indexOf("computeMST")!=-1)
		{
			System.out.println("Hola");
		}
		
		GlobalLive gl = new GlobalLive(unitGraph);
		
		InductivesAnalysis iAna  = new InductivesAnalysis(unitGraph,loops(),gl, blockGraph);
		
		System.out.println("Metodo:"+body.getMethod().getSignature());
		
		// System.out.println(blockGraph);
		
		for (Iterator iter = units.snapshotIterator(); iter.hasNext();) {
			Stmt s = (Stmt) iter.next();
			String is = Utils.getLineNumber(s);
			String prefijo = body.getMethod().getDeclaringClass().toString();
			
			FlowSet bs = (FlowSet)iAna.getFlowBefore(s);
			FlowSet as = (FlowSet)iAna.getFlowAfter(s);
			
			inductivesBefore.put(s,Collections.unmodifiableList(bs.toList()));
			inductivesAfter.put(s,Collections.unmodifiableList(as.toList()));

			
			System.out.println("--");
			System.out.println("Vivas:"+gl.getLiveLocalsBefore(s));
			System.out.println("--");
			System.out.println("b:"+getInductivesBefore(s));
			System.out.println(prefijo+" "+is+" "+s);
			System.out.println("a:"+getInductivesAfter(s));
			System.out.println("--");
			
			
			
			InductivesFinderMain.inductivesMap.put(prefijo+"_"+is,bs.toList());
			InductivesFinderMain.inductivesSTMTMap.put(s,bs.toList());
			
		}
		/*
		for (Iterator iter = units.snapshotIterator(); iter.hasNext();) {
			Stmt s = (Stmt) iter.next();
			System.out.println("-------------------");
			System.out.print(Utils.getLineNumber(s)+" STMT: "+s+" ");
			if(s instanceof IfStmt)
			{
				IfStmt ifs = (IfStmt)s;
				System.out.print(Utils.getLineNumber( ifs.getTarget()) +" ");
			}
			if(isLoopHeader(s))
				System.out.println(" ES HEADER!");
			else
				System.out.println();
		}
		*/
		analysisCache.put(body.getMethod(), this);
		
	}
	
	boolean isLoopHeader(Stmt s)
	    {
	    	return getLoopHeader(s)==s;
	    }
	    boolean isConditional(Stmt s)
	    {
	    	return s instanceof IfStmt;  
	    }
	        
	    Stmt getLoopHeader(Stmt s)
		{
			Stmt loopHeader = null;
			Collection loops = loops();
//			for (Iterator iter = loops.keySet().iterator(); iter.hasNext();) {
//				Stmt header = (Stmt) iter.next();
//				List loopStmts= (List)loops.get(header);
			for (Iterator iter = loops.iterator(); iter.hasNext();) {
				Loop loop = (Loop)iter.next();
				List loopStmts= loop.getLoopStatements();
			// System.out.println("Eli2:"+element+","+element.getClass());
				if(loopStmts.contains(s))
				{
					//loopHeader =header; 
					loopHeader = loop.getHead();
				}
			}
			return loopHeader;
		}
	
	
	/**
	 * @return Returns the inductivesMap.
	 */
	public Map getInductivesMap() {
		return inductivesMap;
	}

	/**
	 * @return Returns the inductivesAfter.
	 */
	@Override
	public List getInductivesAfter(Stmt s) {
		return (List)inductivesAfter.get(s);
	}

	/**
	 * @return Returns the inductivesBefore.
	 */
	@Override
	public List getInductivesBefore(Stmt s) {
		return (List)inductivesBefore.get(s);
	}

	@Override
	public List liveVars(List lives) {
		List res = new Vector();
		for (Iterator iterLive = lives.iterator(); iterLive.hasNext();) {
			Value v = (Value) iterLive.next();
			if(v instanceof Local)
			{
				if(!res.contains(v))
					res.add(v);
			}
			else if(v instanceof InstanceFieldRef)
			{
				InstanceFieldRef ifr = (InstanceFieldRef)v;
				Value base = ifr.getBase();
				if(!res.contains(base))
					res.add(base);
			}
			
		}
		return res;
	}

	@Override
	public List getDerivedVars(Value l, List lives) {
		List res = new Vector();
		for (Iterator iterLive = lives.iterator(); iterLive.hasNext();) {
			Value v = (Value) iterLive.next();
			/*
			if(v instanceof Local && v.equals(l))
			{
				if(!res.contains(v))
					res.add(v);
			}
			
			else */if(v instanceof InstanceFieldRef)
			{
				InstanceFieldRef ifr = (InstanceFieldRef)v;
				Value base = ifr.getBase();
				if(base.equals(l) && !res.contains(v))
					res.add(ifr.getField());
			}
		}
		return res;
	}
}
