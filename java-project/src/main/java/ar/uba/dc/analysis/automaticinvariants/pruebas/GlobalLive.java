/*
 * Created on May 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.pruebas;
import soot.options.*;

import soot.*;
import ar.uba.dc.analysis.automaticinvariants.inductives.InductivesFilter;
import ar.uba.dc.analysis.automaticinvariants.pruebas.FieldRefWrapper;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GLVMain;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLiveAnalysis;

import java.util.*;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import soot.baf.BafBody;
import soot.baf.internal.BIdentityInst;
import soot.jimple.*;
import soot.toolkits.graph.*;
import soot.toolkits.scalar.FlowSet;
import soot.toolkits.scalar.LiveLocals;



/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GlobalLive implements LiveLocals,InductivesFilter {

	    Map unitToLocalsAfter;
	    Map unitToLocalsBefore;
	    List initialFlow = null;
	    List parameters = new Vector();
	    Local thisRef = null;
	    

	    public GlobalLive(UnitGraph graph)
	    {
	        
	        if(Options.v().verbose())
	            G.v().out.println("[" + graph.getBody().getMethod().getName() +
	                "]     Constructing SimpleLiveLocals...");

	                        
	        GlobalLiveAnalysis analysis = new GlobalLiveAnalysis(graph);
	        
	        
	        // Build unitToLocals map
	        {
	            unitToLocalsAfter = new HashMap(graph.size() * 2 + 1, 0.7f);
	            unitToLocalsBefore = new HashMap(graph.size() * 2 + 1, 0.7f);

	            Iterator unitIt = graph.iterator();
	            

	            while(unitIt.hasNext())
	            {
	                Unit s = (Unit) unitIt.next();
	                
	 
	                FlowSet set = (FlowSet) analysis.getFlowBefore(s);
	                unitToLocalsBefore.put(s, Collections.unmodifiableList(filterLive(set.toList())));
	                
	                if(graph.getHeads().contains(s))
	                {
	                	List initFlowAux = filterLive(set.toList());
	                	List initFlowFinal = filterLive(set.toList());
	                	if(graph.getBody().getMethod().toString().indexOf("<init>")!=-1)
	                	{
	                		Local l = graph.getBody().getThisLocal();
	                		initFlowFinal.remove(l);
	                		for (Iterator iter = initFlowAux.iterator(); iter.hasNext();) {
	                				Value v = (Value) iter.next();
	                				if (v instanceof FieldRefWrapper /*InstanceFieldRef*/) {
	                						InstanceFieldRef ifr = (InstanceFieldRef) v;
	                						if(ifr.getBase().equals(l))
	                							initFlowFinal.remove(ifr);
	                						
	                				}
	                			}
	                		
	                	}
	                	initialFlow = Collections.unmodifiableList(initFlowFinal);
	                }
	                
	                set = (FlowSet) analysis.getFlowAfter(s);
	                unitToLocalsAfter.put(s, Collections.unmodifiableList(filterLive(set.toList())));
	                
	            }            
	        }
	        Body b = graph.getBody();
	        
	        GLVMain.analysisCache.put(b.getMethod(),this);
	        
	        //me parece que esta obteniendo los parametros posta del metodo que estan involucrados en la linea
	        for(int i=0;i<b.getMethod().getParameterCount();i++)
	        {
	        	Local p = b.getParameterLocal(i);
	        	parameters.add(i,p);
	        }
	        if(!b.getMethod().isStatic())
	        {
	        	if(b instanceof BafBody)
	        	{
	        	        Iterator<Unit> unitsIt = b.getUnits().iterator();
	        	        while (unitsIt.hasNext() && thisRef==null)
	        	        {
	        	            Unit s = unitsIt.next();
	        	            if (s instanceof IdentityStmt &&
	        	                ((IdentityStmt)s).getRightOp() instanceof ThisRef)
	        	            {
	        	            	thisRef = (Local)(((IdentityStmt)s).getLeftOp());
	        	             }
	        	            else if (s instanceof BIdentityInst &&
		        	                ((BIdentityInst)s).getRightOp() instanceof ThisRef)
		        	                thisRef= (Local)(((BIdentityInst)s).getLeftOp());

	        	        }

	        	}
	        	else thisRef = b.getThisLocal();
	        }
	    }
	    
	    List filterLive(List liveValue)
		{
			List liveFilter = new Vector();
			for (Iterator iterLive = liveValue.iterator(); iterLive.hasNext();) {
				Value v = (Value) iterLive.next();
				if((Utils.isPossibleArgument(v) || Utils.isObject(v)) && !(v instanceof IdentityRef) )
					liveFilter.add(v);
				
			}
			return liveFilter;
		}

	    @Override
		public List getLiveLocalsAfter(Unit s)
	    {
	        return (List) unitToLocalsAfter.get(s);
	    }
	    
	    @Override
		public List getLiveLocalsBefore(Unit s)
	    {
	        return (List) unitToLocalsBefore.get(s);
	    }
		/**
		 * @return Returns the parameters.
		 */
		public List getParameters() {
			return parameters;
		}
		/**
		 * @return Returns the thisRef.
		 */
		public Local getThisRef() {
			return thisRef;
		}
		public List getLiveInitialFlow()
		{
			return initialFlow;
			
		}
		@Override
		public List liveVars(List lives)
		{
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
		public List getDerivedVars(Value l,List lives)
		{
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

		@Override
		public List getInductivesBefore(Stmt s) {
			
			//  OJO! 5/8/2009  return this.getInductivesBefore(s);
			return this.getLiveLocalsBefore(s);
		}

		@Override
		public List getInductivesAfter(Stmt s) {
			return this.getInductivesAfter(s);
		}
	}



