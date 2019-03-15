/*
 * Created on May 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.pruebas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.ProgramInstrumentatorForDaikonMain;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_JsonParameter;
import ar.uba.dc.analysis.automaticinvariants.pruebas.FieldRefWrapper;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GLVMain;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLive;
import ar.uba.dc.analysis.escape.summary.EscapeAnnotation;
import soot.Body;
import soot.Local;
import soot.RefLikeType;
import soot.ResolutionFailedException;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.baf.BafBody;
import soot.jimple.DefinitionStmt;
import soot.jimple.FieldRef;
import soot.jimple.IdentityRef;
import soot.jimple.IdentityStmt;
import soot.jimple.InstanceFieldRef;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.internal.JimpleLocalBox;
import soot.toolkits.graph.CompleteUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.BackwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;
import soot.util.Chain;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GlobalLiveAnalysis  extends BackwardFlowAnalysis 	{
	    FlowSet emptySet;
	    Map unitToGenerateSet;
	    Map unitToKillSet;
	    

	    GlobalLiveAnalysis(UnitGraph g)
	    {
			super(g);
			
		
			emptySet = new ArraySparseSet();
			
			genKillSet(g);
			genGenSet(g);
			
			doAnalysis();
			

	    }

	    /**
		 * @param g
		 */
		private void genGenSet(UnitGraph g) {
			// Create generate sets
	        {
	            unitToGenerateSet = new HashMap(g.size() * 2 + 1, 0.7f);

	            Iterator unitIt = g.iterator();

	            while(unitIt.hasNext())
	            {
	                Unit s = (Unit) unitIt.next();
	                FlowSet genSet = emptySet.clone();

	                Iterator boxIt = s.getUseBoxes().iterator();

	                while(boxIt.hasNext())
	                {
	                    ValueBox box = (ValueBox) boxIt.next();

	                    Value v = box.getValue(); 
	                    if( v instanceof Local || 
	                    	v instanceof FieldRef ||
							(v instanceof IdentityRef) )
	                    {
	                      if(v instanceof InstanceFieldRef)
	                      {
	                      	InstanceFieldRef ifr = (InstanceFieldRef)v;
	                      	if(!genSet.contains(v))
	                      		genSet.add(new FieldRefWrapper(ifr.getBaseBox(),ifr.getFieldRef()));
	                      }
	                      else
	                      {
	                      	if(!genSet.contains(v))
	                      		genSet.add(v, genSet);
	                      }
	                    }
	                }
	                if(s instanceof Stmt)
	                {
	                	Stmt st = (Stmt)s;
	                	if(st.containsInvokeExpr())
	                	{
	                		InvokeExpr ie = st.getInvokeExpr();
	                		
	                		if (ie instanceof InstanceInvokeExpr)
							  {
		                      	InstanceInvokeExpr iie = (InstanceInvokeExpr)ie;
		                      	Local l = (Local)iie.getBase();
		                      	if(!genSet.contains(l))
		                      		genSet.add(l, genSet);
							  }
	                		Local thisLocal = null;
//	                		if(!g.getBody().getMethod().isStatic())
//	                		{
//	                			thisLocal = g.getBody().getThisLocal();
//	                		}
	                		
	                		genSet.union(resolveInvokeExpr(st,genSet),genSet);
	                		
	                	}
	                }

	                unitToGenerateSet.put(s, genSet);
	            }
	        }
		}

		/**
		 * @param st
		 * @param genSet
		 * @return
		 */
		private FlowSet resolveInvokeExpr(Stmt st, FlowSet genSet) {
			Local thisRef = null;
			
			FlowSet retSet = emptySet.clone();
			InvokeExpr ie = st.getInvokeExpr();
			
			if(ie instanceof InstanceInvokeExpr)
			{
				InstanceInvokeExpr iie = (InstanceInvokeExpr)ie;
				thisRef = (Local)iie.getBase();
			}
			try {
			SootMethod callee = ie.getMethod();
//			SootClass calleeClass = callee2.getDeclaringClass();
//			SootMethod callee = calleeClass.getMethod(callee2.getSubSignature());
			
			GlobalLive glvCallee = 		
				(GlobalLive)GLVMain.analysisCache.get(callee);
			if(!ProgramInstrumentatorForDaikonMain.isAnnotated(callee.getDeclaringClass().toString() + "." + callee.getName()))
			{
				if(callee.hasActiveBody())
				{
					Body bCallee = callee.getActiveBody();
					if(bCallee instanceof BafBody)
					{
						System.out.print("");
					}
					
					Chain unitsCallee = bCallee.getUnits();
					
					
					//...esto va a hacer que se rompa todo.
					if(!ProgramInstrumentatorForDaikonMain.isAnnotated(callee.getDeclaringClass().toString() + "." + callee.getName()))
					{
						if(glvCallee==null)
						{
							if(!GLVMain.alreadyAnalyzedMethods.contains(callee))
							{
								GLVMain.alreadyAnalyzedMethods.add(callee);
								if(!GLVMain.stack.contains(callee))
								{
									GLVMain.stack.push(callee);
									glvCallee = new GlobalLive(new CompleteUnitGraph(bCallee));
									GLVMain.stack.pop();
								}
								else return retSet; 
							}
							else 
								return retSet;
						}
					
						if(unitsCallee!=null)
						{
							// System.out.println(unitsCallee);
							Unit firstStmt = (Unit)unitsCallee.getFirst();
							List liveCallee = null;
							
							
							liveCallee= glvCallee.getLiveLocalsBefore(firstStmt);
							
							
							
							if(liveCallee!=null)
							{
								// System.out.println(ie+":"+liveCallee);
								if(thisRef!=null)
									analyzeParameter(retSet, glvCallee, liveCallee, glvCallee.getThisRef(), thisRef);
								for(int i=0;i<ie.getArgCount();i++)
								{
									Value arg = ie.getArg(i);
									Value parameter = (Value)glvCallee.getParameters().get(i);
									analyzeParameter(retSet, glvCallee, liveCallee, parameter, arg);
									
								}
							}
						}
					}
				}
			}
			else
			{
				List parameters = new ArrayList();
				for(int i=0;i<callee.getParameterCount();i++)
		        {					
		        	Local p = callee.getActiveBody().getParameterLocal(i);
		        	parameters.add(i,p);
		        }
				
				EscapeAnnotation annotation = ProgramInstrumentatorForDaikonMain.jsonBasedEscapeAnnotationsProvider.get(callee.getDeclaringClass().toString() + "." + callee.getName());

				Iterator it = annotation.getRelevantParameters().iterator();
				while(it.hasNext())
				{
					DI_JsonParameter jsonParameter = (DI_JsonParameter) it.next();
					String name = jsonParameter.name;
					
					for(int i=0;i<ie.getArgCount();i++)
					{
						Value arg = ie.getArg(i);
						
						Value parameter = (Value)parameters.get(i);
						if(name.equals(parameter.toString()))
						{
							Iterator it_derived = jsonParameter.getDerivedVariables2().iterator();
							while(it_derived.hasNext())
							{
								DI_JsonParameter jsonField = (DI_JsonParameter) it_derived.next();
								
								int posDot = jsonField.name.indexOf('.');
								String field = jsonField.name.substring(posDot+1);
								pushAnnotatedRelevantParameterFieldsToArg(retSet, field, arg);
							}							
						}
						//Falta procesar el parametro THIS
						//deberia agregar a retSet la base tambien?
					}
				}
				
			}			
		}
		catch(ResolutionFailedException crfe)
		{
			System.out.println("Fallo en getMethod!");
		}
		// TODO Auto-generated method stub
		return retSet;
	}

		/**
		 * @param retSet
		 * @param glvCallee
		 * @param liveCallee
		 * @param i
		 * @param arg
		 */
		private void analyzeParameter(FlowSet retSet, GlobalLive glvCallee, List liveCallee, Value parameter, Value arg) {
			if(arg.getType() instanceof RefLikeType)
			{
				
				for (Iterator iterLive = liveCallee.iterator(); iterLive
						.hasNext();) {
					Value live = (Value) iterLive.next();
					if(live instanceof InstanceFieldRef)
					{
						InstanceFieldRef ifr = (InstanceFieldRef)live;
						if(ifr.getBase().equals(parameter))
						{
							//le paso los fields vivos al argumento - creo.
							InstanceFieldRef ifrClone = (InstanceFieldRef)ifr.clone();
							ifrClone.setBase(arg);
							
							if(!retSet.contains(ifrClone))
								retSet.add(ifrClone);
						}
					}
				}
				// for(int j=0;j<glvCallee.getParameters().size())
				
			}
		}
		
		/**
		 * The function should add b.f to retSet
		 * @param retSet
		 * @param relevantParameter. Example: a.f
		 * @param arg. Example: b
		 */
		private void pushAnnotatedRelevantParameterFieldsToArg(FlowSet retSet, String relevantField, Value arg)
		{
			//InstanceFieldRef ifr = arg;
			
			AbstractSootFieldRef ref = new AbstractSootFieldRef(Scene.v().getSootClass("java.lang.Integer"), relevantField, Scene.v().getType("int"), false);
			JimpleLocalBox v = new JimpleLocalBox(arg);
			InstanceFieldRef ifr = new FieldRefWrapper(v, ref); 
			ifr.setBase(arg);
			
			//AbstractSootFieldRef ref = null;
			
			//Por ahora los parametros relevantes pueden ser solo enteros
			
			//ifr.setFieldRef(relevantParameterRef);
			retSet.add(ifr);
		}

		/**
		 * @param g
		 */
		private void genKillSet(UnitGraph g) {
			// Create kill sets.
	        {
	            unitToKillSet = new HashMap(g.size() * 2 + 1, 0.7f);

	            Iterator unitIt = g.iterator();

	            while(unitIt.hasNext())
	            {
	                Unit s = (Unit) unitIt.next();

	                FlowSet killSet = emptySet.clone();

	                if(!(s instanceof IdentityStmt))
	                {
		                Iterator boxIt = s.getDefBoxes().iterator();
	
		                while(boxIt.hasNext())
		                {
		                    ValueBox box = (ValueBox) boxIt.next();
	
		                    Value v = box.getValue(); 
		                    if( v instanceof Local || v instanceof FieldRef)
		                    	if(v instanceof InstanceFieldRef)
			                      {
			                      	InstanceFieldRef ifr = (InstanceFieldRef)v;
			                      	killSet.add(new FieldRefWrapper(ifr.getBaseBox(),ifr.getFieldRef()));
			                      }
			                      else
			                      	killSet.add(v, killSet);
		                        
		                }
	                }

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

	       doKill(in,unit,out);
	       // Perform kill
	       // in.difference((FlowSet) unitToKillSet.get(unit), out);

	        // Perform generation
	        out.union((FlowSet) unitToGenerateSet.get(unit), out);
	    }

	    /**
		 * @param in
		 * @param unit
		 * @param out
		 */
		private void doKill(FlowSet in, Object unit, FlowSet out) {
			
			Unit u = (Unit) unit;
			FlowSet killSet = ((FlowSet) unitToKillSet.get(unit)).clone();
			FlowSet addKillSet = emptySet.clone();
			FlowSet addSet = emptySet.clone();
		   if(u.toString().indexOf("$r3 = new ArrayDim")!=-1)
            {
            	System.out.println("HOLA");
            }
			
            if(u instanceof Stmt)
            {
            	Stmt st = (Stmt)u;
                if(st instanceof DefinitionStmt )
                {
                	if(!(st instanceof IdentityStmt)
                			&& !st.containsInvokeExpr() && !st.containsFieldRef() && !st.containsArrayRef())
	            	{
	            		DefinitionStmt ds = (DefinitionStmt)st;
	            		Value left = ds.getLeftOp();
	            		Value rigth = ds.getRightOp();
	            		if(left instanceof Local)
	            		{
	            			for (Iterator iter = in.iterator(); iter
									.hasNext();) {
								Value v = (Value) iter.next();
								if (v instanceof FieldRefWrapper /*InstanceFieldRef*/) {
									InstanceFieldRef ifr = (InstanceFieldRef) v;
									
									
										if(ifr.getBase().equals(left))
										{
											if(rigth instanceof Local)
											{
												InstanceFieldRef ifrClone = (InstanceFieldRef)ifr.clone();
												ifrClone.setBase(rigth);
												addSet.add(ifrClone);
											}
											addKillSet.add(ifr);
										}
									
								}
								
							}
	            		}
	            	}
                }
                // Para matar a una variables y sus campos cuando se llama al init. 
                // No funciona por culpa del aliasing... :(
                if(st.containsInvokeExpr())
                {
                	InvokeExpr ie = st.getInvokeExpr();
                	if(ie instanceof SpecialInvokeExpr && ie.getMethod().toString().indexOf("<init>")!=-1)
                	{
                		SpecialInvokeExpr sie = (SpecialInvokeExpr)ie;
                		Local l = (Local)sie.getBase();
                		addKillSet.add(l);
                		for (Iterator iter = in.iterator(); iter.hasNext();) {
                				Value v = (Value) iter.next();
                				if (v instanceof FieldRefWrapper /*InstanceFieldRef*/) {
                						InstanceFieldRef ifr = (InstanceFieldRef) v;
                						if(ifr.getBase().equals(l))
                							addKillSet.add(ifr);
                						
                				}
                			}
                		
                	}
                }
                	
            }
         
            addKillSet.union(killSet);
            in.difference(addKillSet,out);
            addSet.union(out,out);
			// TODO Auto-generated method stub
			
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

}
