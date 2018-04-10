package ar.uba.dc.analysis.automaticinvariants.inductives;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.pruebas.FieldRefWrapper;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GLVMain;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLive;
import soot.Body;
import soot.Local;
import soot.SootField;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.DefinitionStmt;
import soot.jimple.IfStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.NewArrayExpr;
import soot.jimple.NewMultiArrayExpr;
import soot.jimple.Ref;
import soot.jimple.Stmt;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.BlockGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.BackwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;


public class InductivesAnalysis extends BackwardFlowAnalysis{
	
    FlowSet emptySet;
    Map unitToGenerateSet;
    Map unitToKillSet;
    Collection loops;
    GlobalLive liveVariablesAnalysis;
    BlockGraph blockGraph;
    

    InductivesAnalysis(UnitGraph g, Collection loops, GlobalLive liveVariablesAnalysis, BlockGraph blockGraph)
    {
		super(g);
		this.loops = loops;
		this.liveVariablesAnalysis = liveVariablesAnalysis;
		this.blockGraph = blockGraph;
		
		emptySet = new ArraySparseSet();
		
		genKillSet(g);
		genGenSet(g);

		doAnalysis();

    }

    //estamos haciendo un analisis de dependencias de valores sobre un analisis de dependencias de control
    
    // Solo estoy creando un genSet no vacio si estoy creando arrays [no se por que] o si estoy en un condicional
    // Es porque son los headers de "loops" (un array lo tomo como un loop)
    /**
	 * @param g
	 */
	private void genGenSet(UnitGraph g) {
		// Create generate sets
        {
        	System.out.println("Generando para "+g.getBody().getMethod());
            unitToGenerateSet = new HashMap(g.size() * 2 + 1, 0.7f);

            Iterator unitIt = g.iterator();

            while(unitIt.hasNext())
            {
                Unit s = (Unit) unitIt.next();
                FlowSet genSet = emptySet.clone();
                System.out.println(Utils.getLineNumber((Stmt)s)+":"+s);
/*
                if(Utils.getLineNumber((Stmt)s).indexOf("22c")!=-1)
                {
                	System.out.println();;
                }
*/                
                if(isConditional(s)  /* && isInLoop(s) */)
                {
                	Stmt loopHeader =getLoopHeader(s); 
                	if(loopHeader!=null)
                	{
                		// if(isWhileGuard((Stmt)s,loopHeader,g))
                		{	
                        	Iterator boxIt = s.getUseBoxes().iterator();
	                    	while(boxIt.hasNext())
	                        {
	                            ValueBox box = (ValueBox) boxIt.next();
	                            Value v = box.getValue();
	                            // AQUI HAY QUE VER QUE NO SEA LOOP INVARIANT. Diego
	                           if(v instanceof Local || v instanceof Ref  )
	                           {
	                            	genSet.add(v);
	                            	System.out.println("INDUCTIVA!:"+v);
	                            	
	                           }
	                        }
						
						}

                	}

                }
                
                //Las agrego al genSet porque despues una inductiva podria depender de estas cosas, creo
                if(s instanceof AssignStmt)
                {
                	AssignStmt as = (AssignStmt)s;
                	Value v = as.getRightOp();
                	if(v instanceof AnyNewExpr)
                    {
                    	if (v instanceof NewArrayExpr) {
							 NewArrayExpr naExp = (NewArrayExpr) v;
							 genSet.add(naExp.getSize());
							
						}
                    	if (v instanceof NewMultiArrayExpr) {
							 NewMultiArrayExpr naExp = (NewMultiArrayExpr) v;
							 for (Iterator iter = naExp.getSizes().iterator(); iter
									.hasNext();) {
								Value element = (Value) iter.next();
								genSet.add(element);
							}
						}
                    }
                }
            
                unitToGenerateSet.put(s, genSet);
                
            }
        }
	}
	
	private boolean isWhileGuard(Stmt s, Stmt loopHeader, UnitGraph g)
	{
		boolean res = false;
		IfStmt ifStm = (IfStmt)s;
		Stmt target = ifStm.getTarget();
		if(getLoopHeader(target)!=loopHeader)
		{
			res = true;
		}
		else
		{
			List succs = g.getSuccsOf(s);
			for (Iterator iter = succs.iterator(); iter.hasNext() && !res;) {
				Stmt element = (Stmt) iter.next();
				if(getLoopHeader(element)!=loopHeader)
				{
					res=true;
				}
				
			}
		}
			
		return res;
	}

	/**
	 * @param g
	 */
	private Block getBlock(Stmt s)
	{
		Block res = null;
		for (Iterator iter = blockGraph.iterator() ; iter.hasNext() && res==null;) {
			Block element = (Block) iter.next();
			// element.g
			
		}
		return res;
	}
	
	//Aparentemente no hay ninguno que tenga que sacar en este analisis
	private void genKillSet(UnitGraph g) {
		// Create kill sets.
        {
            unitToKillSet = new HashMap(g.size() * 2 + 1, 0.7f);

            Iterator unitIt = g.iterator();

            while(unitIt.hasNext())
            {
                Unit s = (Unit) unitIt.next();
                // List vivas = liveVariablesAnalysis.getLiveLocalsBefore((Stmt)s);
                FlowSet killSet = emptySet.clone();
//                for (Iterator iter = vivas.iterator(); iter.hasNext();) {
//					Value element = (Value) iter.next();
//					
//				}
                unitToKillSet.put(s, killSet);
            }
        }
	}

	@Override
	protected Object newInitialFlow()
    {
        return emptySet.clone();
    }

    @Override
	protected Object entryInitialFlow()
    {
        return emptySet.clone();
    }
        
    @Override
	protected void flowThrough(Object inValue, Object unit, Object outValue)
    {
    	
        FlowSet in = (FlowSet) inValue, out = (FlowSet) outValue;

        doGen(in,unit,out);
        
        
       
       // Perform kill
       // in.difference((FlowSet) unitToKillSet.get(unit), out);

        // Perform generation
        // out.union(in, out);
       // out.union((FlowSet) unitToGenerateSet.get(unit), out);
    }

    
    
    //El OUT va a ser el IN del siguiente, creo
    
    //Se esta fijando de que variables vivas dependen las condiciones de los loops 
    
    /**
	 * @param in
	 * @param unit
	 * @param out
	 */
    

    private boolean contains(List list, Value value)
    {
    	for(Object o : list)
    	{

    		//no se si preguntar por el hashcode esta bien.
    		//Alguna vez CREO que paso que una variable estaba pero list.contains(value) daba false
    		//¿sera porque usa el equals y el equals no usa el hashcode?
    		//if (s.equals(s2) && o.getClass().equals(value.getClass()))    		
			if(o.hashCode() == value.hashCode())
			{
				return true;
			}
    	}
    	return false;
    }
	private void doGen(FlowSet in, Object unit, FlowSet out) {
		
		List vivas = liveVariablesAnalysis.getLiveLocalsBefore((Stmt)unit);
   	    Unit u = (Unit) unit;
   	    
   	    //TODO: podria volar el killset si igual siempre es vacio. O tal vez esta conceptualmente bueno dejarlo
 		FlowSet killSet = ((FlowSet) unitToKillSet.get(unit)).clone();
 		
 		FlowSet genSet = ((FlowSet) unitToGenerateSet.get(unit)).clone();
		
 		FlowSet addSet = emptySet.clone();

 		// FlowSet addSet = (FlowSet)genSet.clone();
		
		FlowSet inFiltered = emptySet.clone();
        		
        if(u instanceof Stmt)
        {
        	Stmt st = (Stmt)u;
            if(st instanceof DefinitionStmt )
            {
            	//if(!(st instanceof IdentityStmt)
            	//		&& !st.containsInvokeExpr() && !st.containsFieldRef() && !st.containsArrayRef())
            	{
            		DefinitionStmt ds = (DefinitionStmt)st;
            		Value left = ds.getLeftOp();
            		Value rigth = ds.getRightOp();
            		boolean mustAdd = false;
            		
            		
            		//el flow de una variable a otra
            		if(in.contains(left))
            		{
	            		Iterator boxIt = ds.getUseBoxes().iterator();
	                	while(boxIt.hasNext())
	                    {
	                        ValueBox box = (ValueBox) boxIt.next();
	                        Value v = box.getValue();
	                        //De ultima estoy sobreaproximando
	                        
	                        // AQUI HAY QUE VER QUE NO SEA LOOP INVARIANT
	                        // if(v instanceof Local || v instanceof Ref  )
	                        String s = v.toString();
	                        s = s;
	                        if(contains(vivas,v))
	                        {
	                        		addSet.add(v);
	                        }
	                        
	                    }
            		}
            		/*
           			for (Iterator iter = in.iterator(); iter.hasNext();) {
							Value v = (Value) iter.next();
							if(in.contains(v))
							{
								mustAdd = true;
							}
							
						}
           			if(mustAdd && vivas.contains(left))
           			{
           				addSet.add(left);
           			}
           			*/
            	}
            		
            }
            
            //Los conditionals son GOTOs
            if(isConditional(st))
            {
            	boolean hasInductive=false;
            	Iterator boxIt = st.getUseBoxes().iterator();
            	while(boxIt.hasNext())
                {
                    ValueBox box = (ValueBox) boxIt.next();
                    Value v = box.getValue();
                    if(in.contains(v) )
            			hasInductive=true;
                }
            	if(hasInductive)
            	{
	            	boxIt = st.getUseBoxes().iterator();
	            	while(boxIt.hasNext())
	                {
	                    ValueBox box = (ValueBox) boxIt.next();
	                    Value v = box.getValue();
	                    // AQUI HAY QUE VER QUE NO SEA LOOP INVARIANT
	                    if(contains(vivas,v) )
	                    {
	                    	//no tiene mucho sentido esto, porque ya venia con el genSet
	                    	addSet.add(v);
	                    }
	            			
	                }
            	}
            }
            
            if(st.containsInvokeExpr() && false )
            {
            	InvokeExpr iExpr = st.getInvokeExpr();
            	Iterator boxIt = iExpr.getUseBoxes().iterator();
            	
                // Habria que mirar en el cache...
            	/*
            	while(boxIt.hasNext())
                {
                    ValueBox box = (ValueBox) boxIt.next();
                    Value v = box.getValue();
                    // AQUI HAY QUE VER QUE NO SEA LOOP INVARIANT
                    // if(v instanceof Local || v instanceof Ref  )
                    	if(vivas.contains(v))
                    			addSet.add(v);
                    	
                }
            	*/
            	SootMethod callee = iExpr.getMethod();
            	GlobalLive gl = null;
            	List livesCallee = null;
            	if(callee.hasActiveBody() )
            	{
            		Body calleeBody = callee.getActiveBody();
            		gl = (GlobalLive) GLVMain.analysisCache.get(calleeBody.getMethod());
            		// gl = new GlobalLive(new CompleteUnitGraph(calleeBody));
            		livesCallee = gl.getLiveInitialFlow();

            		List parameters = gl.getParameters();
            		if(gl!=null)
            		{
            			livesCallee = gl.getLiveInitialFlow();
            			
            			int cArgs = iExpr.getArgCount();
                		for(int i=0;i<cArgs;i++)
                		{
                			Local  param = (Local)parameters.get(i);
	                		if(livesCallee.contains(param))
	    	                {
	    	                	List derived = gl.getDerivedVars(param,livesCallee);
	    	                	System.out.println("Derived:"+derived);
	    	                	if(!derived.isEmpty())
	    	                	{
		    	                	for (Iterator iter = derived.iterator(); iter.hasNext();) 
		    	                	{
		    	    					SootField sf = (SootField) iter.next();
		    	    					FieldRefWrapper frw = new FieldRefWrapper(iExpr.getArgBox(i),sf.makeRef());
		    	    					addSet.add(frw);
		    	    				}
	    	                	}
	    	                }
                		}
                		if(iExpr instanceof InstanceInvokeExpr)
                		{
                			InstanceInvokeExpr iiExpr=(InstanceInvokeExpr)iExpr;
                			ValueBox thisRefBox = iiExpr.getBaseBox();
                			List derived = gl.getDerivedVars(gl.getThisRef(),livesCallee);
                			System.out.println("Derived:"+derived);
    	                	if(!derived.isEmpty())
    	                	{
	    	                	for (Iterator iter = derived.iterator(); iter.hasNext();) 
	    	                	{
	    	    					SootField sf = (SootField) iter.next();
	    	    					FieldRefWrapper frw = new FieldRefWrapper(thisRefBox,sf.makeRef());
	    	    					addSet.add(frw);
	    	    				}
	    	                	}
                		}
                		
            			
//            			for (Iterator iter = livesCallee.iterator(); iter.hasNext();) 
//	                	{
//	    					Value element = (Value) iter.next();
//	    					addSet.add(element);
//	    					System.out.print(element+" ");
//	    				}
//                		
    	                
            		}
            		else
            		{
            			System.out.println("El callee "+callee +"no esta en el cache...");
            		}
            	}
        	
            
            	
            }
            
            System.out.println();
            // Para matar a una variables y sus campos cuando se llama al init. 
            // No funciona por culpa del aliasing... :(
        }
        
        
        
        
        for (Iterator iter = genSet.iterator(); iter.hasNext();) {
			Value v = (Value) iter.next();
			    if(contains(vivas,v))
				  inFiltered.add(v);
		}
        
        for (Iterator iter = in.iterator(); iter.hasNext();) {
			Value v = (Value) iter.next();
			    if(contains(vivas,v))
				  inFiltered.add(v);
		    
			
		}
        
        
        
        
        
        //preguntar si estan vivas esta totalmente de mas aca
        for (Iterator iter = addSet.iterator(); iter.hasNext();) {
			Value v = (Value) iter.next();
			    if(contains(vivas,v))
				  inFiltered.add(v);
		}
        
        
        
        
        // inFiltered.union(genSet);
        
        inFiltered.difference(killSet,inFiltered);
        
        
        //son las que pasan para adelante
        inFiltered.copy(out);
        
        
        // out.union(addSet,out);
	}

	@Override
	protected void merge(Object in1, Object in2, Object out)
    {
        FlowSet inSet1 = (FlowSet) in1,
            inSet2 = (FlowSet) in2;

        FlowSet outSet = (FlowSet) out;

        inSet1.union(inSet2, outSet);
    }
    
    @Override
	protected void copy(Object source, Object dest)
    {
        FlowSet sourceSet = (FlowSet) source,
            destSet = (FlowSet) dest;
            
        sourceSet.copy(destSet);
    }
    
    boolean isLoopHeader(Stmt s)
    {
    	return getLoopHeader(s)==s;
    }
    boolean isInLoop(Unit s)
    {
    	return getLoopHeader(s)!=null;
    }
    boolean isConditional(Unit s)
    {
    	return s instanceof IfStmt;  
    }
        
    Stmt getLoopHeader(Unit s)
	{
		Stmt loopHeader = null;
		if(loops()!=null)
		{
//			for (Iterator iter = loops().keySet().iterator(); iter.hasNext();) {
			for (Iterator iter = loops().iterator(); iter.hasNext();) {
				//Stmt header = (Stmt) iter.next();
				//List loopStmts= (List)loops().get(header);
				Loop loop = (Loop)iter.next();
				List loopStmts=loop.getLoopStatements();
				// System.out.println("Eli2:"+element+","+element.getClass());
				if(loopStmts.contains(s))
				{
					//loopHeader =header;
					loopHeader = loop.getHead();
				}
			}
		}
		return loopHeader;
	}
    
    
    Collection loops()
    {
    	return loops;
    }
    
	

}


