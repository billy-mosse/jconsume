package ar.uba.dc.analysis.automaticinvariants.inductives;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.ProgramInstrumentatorForDaikonMain;


import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;



import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLive;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.SootClass;
import soot.Unit;
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
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.MHGDominatorsFinder;
import soot.toolkits.graph.UnitGraph;
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

   /* @Override
    protected void internalTransform(String phaseName, Map options) {
        SootClass sootClass = Scene.v().getSootClass("java.net.Socket");
        System.out.println("Modificando a public");
 
        for (SootField sootField : sootClass.getFields()){
            sootField.setModifiers(Modifier.PUBLIC);
        }
        for (SootMethod method : sootClass.getMethods()){
            System.out.println("Modificando: " + method.getName());
            method.setModifiers(Modifier.PUBLIC);
        }
    }*/
    
	public static InductivesFinder getInstance() {
		return instance;
	}
	
	/**
	 * Creo que este metodo consigue las relevantes para cada statement
	 * Lo que yo deberia hacer es...guardar los parametros relevantes aparte? como hago eso?
	 * puedo ir guardando aparte cada vez que aparece un field de parametro que es "inductivo"
	 */
	
	
	@Override
	protected void internalTransform(Body body, String arg1, Map arg2) {
		
		//agrego esto para ver si el error que tira es porque NewsInstrumenterDaikon necesita algo de esto
		//y como los jtp se corren en paralelo se termina rompiendo porque algo devuelve null
		synchronized (getInstance()) {			

			if(!ProgramInstrumentatorForDaikonMain.isInCG(body.getMethod()))
				return;
			
		    //Map<Stmt, List<Stmt>> loops = new HashMap<Stmt, List<Stmt>>();
		    
			/*if(super.getLoops(body) != null && super.getLoops(body).size() == 0)
			{	
				throw new NotImplementedException();
				//aca esta pasando algo raro. No busca los loops de mainParameters :(
				//dice que es []
			}*/
			super.internalTransform(body, arg1, arg2);
			SootClass sClass = body.getMethod().getDeclaringClass();
			
			Chain units = body.getUnits();
			
			if(body.getMethod().getDeclaringClass().toString().equals("ar.uba.dc.analysis.automaticinvariants.VarTest"))
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
			//Collection loops = super.loops();
			

			NewInductivesAnalysis iAna  = new NewInductivesAnalysis(unitGraph,
					//this.getLoops(body),
					loops(),					
					gl, blockGraph);
			
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
	}
	
	boolean isLoopHeader(Stmt s, Body body)
	    {
	    	return getLoopHeader(s, body)==s;
	    }
	    boolean isConditional(Stmt s)
	    {
	    	return s instanceof IfStmt;  
	    }
	        
	    Stmt getLoopHeader(Stmt s, Body body)
		{
			Stmt loopHeader = null;
			
			
			Collection loops =  loops();//this.getLoops(body);
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

	/*@Override
	public List filtradas(List lives) {
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
				String base = ifr.getBase().toString();
				String field = ifr.getField().toString();
				String s = base + "." + field;
			}
			
		}
		return res;
	}*/
	
	
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
