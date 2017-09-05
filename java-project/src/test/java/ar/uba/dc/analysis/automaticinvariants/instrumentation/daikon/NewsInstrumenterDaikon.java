package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.inductives.InductivesFilter;
import ar.uba.dc.analysis.automaticinvariants.inductives.InductivesFinder;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameterFactory;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Iterator;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Object;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Value;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParametersNoRep;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListNoRep;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GLVMain;
import ar.uba.dc.analysis.automaticinvariants.pruebas.GlobalLive;
import soot.Body;
import soot.IntType;
import soot.Local;
import soot.Modifier;
import soot.ResolutionFailedException;
import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.VoidType;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.GotoStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.IfStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.NewArrayExpr;
import soot.jimple.NewExpr;
import soot.jimple.NewMultiArrayExpr;
import soot.jimple.NullConstant;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.jimple.toolkits.annotation.logic.LoopFinder;
import soot.toolkits.graph.CompleteUnitGraph;
import soot.toolkits.graph.DominatorsFinder;
import soot.toolkits.graph.SimpleDominatorsFinder;
import soot.toolkits.scalar.LiveLocals;
import soot.util.Chain;

/**
 * InstrumentClass example. Instruments the given class to print out the number
 * of Jimple "new" statements executed. To enable this class, enable the given
 * PackAdjuster by compiling it separately, into the soot package.
 */

class NewsInstrumenterDaikon extends LoopFinder {
	private static NewsInstrumenterDaikon instance = new NewsInstrumenterDaikon();
	private static int dummyLineNumber = 100000;
	private NewsInstrumenterDaikon() {super();}

	private DominatorsFinder dominator = null;
	
	public static NewsInstrumenterDaikon v() {
		return instance;
	}

	// newsMap : is -> < P(var), method > 
	private Map newsMap = new HashMap();
	// ccMethodsMap : cs -> method  
	private Map ccMethodsMap = new HashMap();
	private Map ccArgsMap = new HashMap();
	//   methodMap : method -> P(var)
	private Map methodMap = new HashMap();
	private SootClass intrumentedClass = NewsInvariantInstrumentator.getIntrumentedMethodClass();
	
	// Para los iteradores
	private Map itersMap = new HashMap();
	
	int ordenCreationSite = 0;
	int ordenCallSite = 0;

	
	/**
	 * @param body
	 * @return
	 */
	private ListDIParameters extractMethodParams(Body body)
	{
		ListDIParameters info2 = new ListDIParameters();
		GlobalLive analisisVivas = (GlobalLive) GLVMain.analysisCache.get(body.getMethod());
		List parameters = analisisVivas.getParameters();
		List lives = analisisVivas.getLiveInitialFlow();
		
		//hack feo....es realmente inductivesfilter el tipo?
		if(analisisVivas.getThisRef()!=null) // && body.getMethod().getName().indexOf("<init>")==-1
		{
			addParameter(body, info2, (InductivesFilter) analisisVivas, lives, analisisVivas.getThisRef(),analisisVivas.getThisRef());
		}
		for (Iterator iter = parameters.iterator(); iter.hasNext();) {
			Local parameter = (Local) iter.next();
			addParameter(body, info2, (InductivesFilter) analisisVivas, lives, parameter,parameter);
		}
		
		return info2;
	}
	

	
	/**
	 * @param body
	 * @param info2
	 * @param analisisVivas
	 * @param lives
	 * @param parameter
	 */
	
	//Esta es la papota
	private void addParameter(Body body, ListDIParameters info2, InductivesFilter analisisVivas, List lives, Local parameter, Local parameterFilter) {
		if(lives!=null)
		{
			List liveVars= analisisVivas.liveVars(lives);
			// OJO!!!!!!!!!
			if(liveVars.contains(parameterFilter)) // || Utils.isNum(parameterFilter))
			{
				DIParameter dip=null;
				List filter = analisisVivas.getDerivedVars(parameterFilter,lives);
				if(filter.size()>0)
				{
					dip = null;
				}
				dip = DIParameterFactory.createDIParameter(parameter,filter,body,true);
				if(dip!=null)
					info2.add(dip);
			}
		}
	}
	/**
	 * @param c
	 * @param mOrig
	 * @param name
	 * @param paramsInstru
	 * @param fields
	 * @return
	 */
	protected SootMethod crearMetodo(String insSite, SootClass c, SootMethod mOrig,
		String name, ListDIParameters allParams, InductiveVariablesInfo IVInfo) {
		
		
		Vector vTypes = new Vector();
		Vector vParams = new Vector();
		
		ListDIParametersNoRep params = new ListDIParametersNoRep();
		
		// params.addAll(paramsInstru);
		// params.addAll(paramsInit);
		
		
		
		ListNoRep paramsExp = new ListNoRep();
		
		if(NewsInvariantInstrumentator.inductivesAsRelevants )
		{
			
			if(insSite.indexOf("15c")!=-1)
			{
				System.out.println();
			}
			List paramsExpanded = allParams.toList(IVInfo);
			paramsExp.addAll(paramsExpanded);
		}
		else
		{
			paramsExp.addAll(allParams.toList());
		}
		
		
		
		for (Iterator itParams = paramsExp.iterator(); itParams.hasNext();) {
			Local var = (Local) itParams.next();
				if(!var.getType().equals(IntType.v()))
				{
					System.out.print("Problema");
					System.out.print(var.getType().toString());
					
					if(var.getType().toString().equals("ar.uba.dc.daikon.RichNumberPublic"))
					{
						System.out.println("Soy un richnumberpublic");
					}
				}
				vTypes.add(var.getType());
				vParams.add(var);
		}
		
		if(vParams.size()>0)
		{
			String methodName = c.getName().replace('.','_') + "_" + name;
			methodName = methodName.replace('$','_');
			
			//TODO esto tira error cuando un parametro no es entero!!!
			SootMethod method = new SootMethod(methodName, vTypes, VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);
			
				
			JimpleBody body = Jimple.v().newBody(method);
			method.setActiveBody(body);
			
			Chain units = body.getUnits();
			Local arg, tmpRef;
	
			// Add a name to each method parameter
			int i = 0;
			// System.out.println("Public:"+vParams);
			String lParams="";
			
			for (Iterator itChain = vParams.iterator(); itChain.hasNext();) {
				Local var = (Local) itChain.next();
				
				// Le cambio el nombre para evitar repetidos
				String varName = var.getName();
				String mOrigName = mOrig.getName();
				
				// Normalize variable names 
				mOrigName = Utils.normalizeMethodName(mOrigName,c.getName() );
				
				// lo cambio (PRUEBA) por el inssite
				mOrigName = insSite.replaceAll("\\.","_");
				
				String argName = mOrigName + "_" + varName;
				
				if (varName.startsWith("_f_"))
					argName= mOrigName + varName;
				
				arg = Utils.addLocalToBody(body,argName,(Type)vTypes.elementAt(i));
				// add "var := @parameter_i"
				units.add(Jimple.v().newIdentityStmt(arg,
						Jimple.v().newParameterRef((Type)vTypes.elementAt(i), i)));
				
				//lParams+="int "+argName;
					
				lParams+=var.getType()  + " " + argName;
				i++;
				
				if(itChain.hasNext())
				{
					lParams+=", ";
				}
			}
			
			// insert "return"
			// units.add(Jimple.v().newNopStmt());
			units.add(Jimple.v().newReturnVoidStmt());
			
			if(methodName.equals("mst_MST_CC_49c37"))
			{
				System.out.println("Hola!");
			}
			
			System.gc();
			// NewsInvariantInstrumentator.getIntrumentedMethodClass().addMethod(method);
			
			intrumentedClass.addMethod(method);
			String ss = "";
			ss = method.getName()+"("+lParams+")";
			// ss += "{ System.out.println(\"Entre a\"+ \" " + methodName+" \"); };";
			ss+="{}";
			ss += "\n";
			NewsInvariantInstrumentator.getListMethods().add(ss);
			
			
			
			// Printer.v().printTo(body,NewsInvariantInstrumentator.getPwIM());
			
			return method;
		}
		return null;
	}

	@Override
	protected void internalTransform(Body body, String phaseName, Map options) {
		if(!NewsInvariantInstrumentator.isInCG(body.getMethod()))
			return;
	
		super.internalTransform(body, phaseName, options);
	
		//a ver que onda.
		/*try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		SootClass sClass = body.getMethod().getDeclaringClass();
		Chain units = body.getUnits();
		
		if(isMethodToSkip(sClass,body.getMethod()))
			return;
		if(body.getMethod().getDeclaringClass().toString().equals("VarTest"))
			return;
		
		/*
		if(body.getMethod().getDeclaringClass().getName().indexOf("Graph")!=-1 ||
			body.getMethod().getDeclaringClass().getName().indexOf("Hashtable")!=-1
				)
			return;
		*/		 
		
		ordenCreationSite = 0;
		ordenCallSite = 0;

		
		synchronized (this) {
			if (!Scene.v().getMainClass().declaresMethod(
					"void main(java.lang.String[])"))
				throw new RuntimeException("couldn't find main() in mainClass");
		}

		System.out.println("Analizando..." + body.getMethod().toString());
		
		DIParameter.setLocalsPool(new HashSet());

		// Perform Live Variables Analysis for each stmt of this method 
		CompleteUnitGraph unitGraph = new CompleteUnitGraph(body);
		
		// SimpleLiveLocals analisisVivas = new SimpleLiveLocals(unitGraph);
		GlobalLive analisisVivas = (GlobalLive) GLVMain.analysisCache.get(body.getMethod());
		
		
		//aca es de donde saca las inductivas
		InductivesFinder analisisInductivas = (InductivesFinder)InductivesFinder.analysisCache.get(body.getMethod());
		
		dominator = new SimpleDominatorsFinder(unitGraph);
		
		
		ListDIParameters lParameters = extractMethodParams(body);
		lParameters.addToBody(body);
		
		List code = new Vector();
		
		ListDIParameters lParametersInit = generateParametersInitsWithCode(body, lParameters, code);
		
		
		String methodNameDec = body.getMethod().getSignature();
		String nameClass = body.getMethod().getDeclaringClass().getName();
		System.out.println("\nAA:"+methodNameDec+";"+lParameters+";"+lParametersInit);
		if (methodMap.get(methodNameDec) == null) {
			methodMap.put(methodNameDec, new MethodMapInfo(lParameters,lParametersInit,nameClass ) );
		}
		
		Iterator stmtIt = units.snapshotIterator();
		boolean isInit = body.getMethod().getName().indexOf("<init>")!=-1;

		// Stmt s = skipNonInstrumentableStmts(units, code, stmtIt, isInit);
		Stmt s = skipNonInstrumentableStmts(units, code, stmtIt, false);
		//	Adjust possible Gotos generated when adding size adaptations
		adjustAddedCode(units, code);
		
		
		
		// Body instrumentation loop
		ListDIParameters extraParams = new ListDIParameters();
		// processStmt(body, units, analisisVivas, lParameters, lParametersInit, extraParams, s);
		processStmt(body, units, analisisVivas, analisisInductivas, extraParams, s);
		
		while (stmtIt.hasNext()) {
			s = (Stmt) stmtIt.next();
			// processStmt(body, units, analisisVivas, lParameters, lParametersInit, extraParams, s);
			processStmt(body, units, analisisVivas, analisisInductivas, extraParams, s);
		}
		// System.out.println(body.getMethod()+":"+units);
		List codeInitVars = new Vector();
		for (Iterator iter = body.getLocals().iterator(); iter.hasNext();) {
			Local var = (Local) iter.next();
			if(Utils.isObject(var) && 
					analisisVivas!=null && !analisisVivas.getParameters().contains(var) && analisisVivas.getThisRef()!=var)
			{
				codeInitVars.addAll(Utils.codeForAssig(NullConstant.v(),var));
			}
			if(var.getName().startsWith("cont_") && Utils.isNum(var))
			{
				codeInitVars.addAll(Utils.codeForAssig(IntConstant.v(0),var));
			}
	
		}
		
		Iterator stmtIt2 = units.snapshotIterator();
		Stmt s2 = skipNonInstrumentableStmts(units, codeInitVars, stmtIt2, isInit);
		
		
	}




	// Defines is method should not be instrumented
	private boolean isMethodToSkip(SootClass c, SootMethod method) {

		boolean ok = false;
		
		if(c.getName().indexOf("Main$1")!=-1)  
			ok=true;
		if(c.getName().indexOf("ImmortalEntry")!=-1)  
			ok=true;
		if(c.getName().indexOf("MemoryAllocator")!=-1)  
			ok=true;
		if(c.getJavaPackageName().indexOf("realtime")!=-1)  
			ok=true;
		/*
		if(c.getJavaPackageName().indexOf("immortal.persistentScope")==-1)  
			ok=true;
			*/
		return ok;
	}



	/**
	 * @param units
	 * @param code
	 * @param stmtIt
	 * @param isInit
	 * @return
	 */
	private Stmt skipNonInstrumentableStmts(Chain units, List code, Iterator stmtIt, boolean isInit) {
		Stmt s=null;
		boolean skip = true;
		if(!isInit)
		{
			while (stmtIt.hasNext() && skip) {
				s = (Stmt) stmtIt.next();
				skip = s  instanceof IdentityStmt;
				if(!skip && !code.isEmpty())
					units.insertBefore(code, s);
				else
					s.removeAllTags();
			}
		}
		else
		{
			boolean hasSpecialInvoke=false;
			while (stmtIt.hasNext() && skip) {
				s = (Stmt) stmtIt.next();
				skip = !hasSpecialInvoke ;
				if(s.containsInvokeExpr())
				{
					InvokeExpr ie = s.getInvokeExpr();
					if(ie instanceof SpecialInvokeExpr)
					{
						hasSpecialInvoke= true;
					}
				}
				if(!skip && !code.isEmpty())
					units.insertBefore(code, s);
				else
					s.removeAllTags();
			}
			
		}
		return s;
	}
	/**
	 * @param body
	 * @param lParameters
	 * @param code
	 * @return
	 */
	private ListDIParameters generateParametersInitsWithCode(Body body, ListDIParameters lParameters, List code) {
		ListDIParameters lParametersInit = new ListDIParameters();
	
		for(Iterator itParam = lParameters.iterator(); itParam.hasNext(); )
		{
			DIParameter param = (DIParameter)itParam.next();
			DIParameter  varInit=null;

			Collection filter = new Vector();
			if(param instanceof DI_Object)
			{
				DI_Object paramObject  =(DI_Object)param;
				filter = paramObject.getFields();
			}
			


			varInit = DIParameterFactory.createDIParameter(param.getName()+"_init",
					                                       param.getLocal().getType(), filter,
														   body, true);
			if(varInit!=null)
			{
				varInit.addToBody(body);
				// DIEGO Agregado 
				code.addAll(param.codeForVar());
				// 
				code.addAll(Utils.codeForAssig(param.getLocal(),varInit.getLocal()));
				lParametersInit.add(varInit);
			}
		}
		
		code.addAll(lParametersInit.codeForParameters());
		return lParametersInit;
	}
	
	private ListDIParameters extractParametersInits(Body body, ListDIParameters lParameters) {
		ListDIParameters lParametersInit = new ListDIParameters();
	
		for(Iterator itParam = lParameters.iterator(); itParam.hasNext(); )
		{
			DIParameter param = (DIParameter)itParam.next();
			DIParameter  varInit=null;
			Collection filter = new Vector();
			if(param instanceof DI_Object)
			{
				DI_Object paramObject  =(DI_Object)param;
				filter = paramObject.getFields();
			}
			
			varInit = DIParameterFactory.createDIParameter(param.getName()+"_init",
					                                       param.getLocal().getType(), filter,
														   body, true);
			if(varInit!=null)
			{
				lParametersInit.add(varInit);
			}
		}
		return lParametersInit;
	}

	
	


	/**
	 * @param units
	 * @param code
	 */
	private void adjustAddedCode(Chain units, List code) {
		
		for(Iterator codeIt=code.iterator();codeIt.hasNext();)
		{
			Unit u= (Unit)codeIt.next();
			if(u instanceof IfStmt && u.getTag("AdjustGotoTag")!=null)
			{
				IfStmt ifStmt = (IfStmt)u;
				Unit t = (Unit)units.getSuccOf(ifStmt);
				int toJump = Integer.parseInt( u.getTag("AdjustGotoTag").toString() );
				for(int i=0;i<toJump;i++)
					t = (Unit)units.getSuccOf(t);
				/*
				t = (Unit)units.getSuccOf(t);
				t = (Unit)units.getSuccOf(t);
				// t = (Unit)units.getSuccOf(t);
				*/
				ifStmt.setTarget(t);
			}
			else 
				if(u instanceof GotoStmt && u.getTag("AdjustGotoTag")!=null)
				{
					GotoStmt jump = (GotoStmt)u;
					// System.out.print(u+"="+u.getTags()+";"+u.getTag("AdjustGotoTag"));
					int toJump = Integer.parseInt( u.getTag("AdjustGotoTag").toString() );
					Unit t = (Unit)units.getSuccOf(jump);
					for(int i=0;i<toJump;i++)
						t = (Unit)units.getSuccOf(t);
					jump.setTarget(t);
				}
		}
		
	}

	/**
	 * @param body
	 * @param units
	 * @param analisisVivas
	 * @param cParametersAum
	 * @param s
	 */
	// private void processStmt(Body body, Chain units, SimpleLiveLocals analisisVivas, ListDIParameters lParameters, ListDIParameters lParametersInit, ListDIParameters extraParams, Stmt s) {
	private void processStmt(Body body, Chain units, 
						GlobalLive analisisVivas, 
						InductivesFilter analisisInductivas, 
						ListDIParameters extraParams, Stmt s) {
		
		
		boolean isIterator = false;
		
		Stmt loopHeader = getLoopHeader(s);
		
		System.out.println("Procesando:"+Utils.getLineNumber(s));
		// Limpia los contadores de iteradores viejos
		// if((loopHeader==null || itersMap.get(loopHeader)==null) &&  !itersMap.keySet().isEmpty())
		/*
		if( !itersMap.keySet().isEmpty())
		{
			for (Iterator iter = itersMap.keySet().iterator(); iter.hasNext();) {
				Stmt lh = (Stmt) iter.next();
				if(!lh.equals(loopHeader))
				{
					Set its = (Set)itersMap.get(lh); 
					// DI_Iterator itToDelete = (DI_Iterator) itersMap.get(lh);
					extraParams.removeAll(its);
				}
			}
			// itersMap.clear();
		}
		*/
		if(Utils.getLineNumber(s).indexOf("00038c")!=-1)
		{
			System.out.println();
		}
		if (s instanceof AssignStmt) {
			AssignStmt as = (AssignStmt) s;
			Value exp = as.getRightOp();

			// Si es un NEW se instrumenta como creation site
			if (exp instanceof AnyNewExpr)  {
				// Me genero una nueva copia por si los filtro
				ListDIParameters lParameters = extractMethodParams(body);
				ListDIParameters lParametersInit = extractParametersInits(body, lParameters);
				
//				List invStmts = instrumentarCSoCallSite("CS", s,
//						analisisVivas, lParameters, lParametersInit, extraParams, body);
				List invStmts = instrumentarCSoCallSite("CS", ordenCreationSite, s,
						analisisVivas, analisisInductivas, lParameters, lParametersInit, extraParams, body);
				ordenCreationSite++;
				if (invStmts != null)
				{
					units.insertBefore(invStmts, s);
					adjustAddedCode(units, invStmts);
				}
			}
			
			Value lv =as.getLeftOp(); 
			Value rv = as.getRightOp();
			// Si la parte izq es de tipo iterator...
			if(Utils.isIterator(lv) )
			{
				DI_Iterator it_cont  = new DI_Iterator((Local)lv);
				//DI_Iterator it_cont  = new DI_Iterator("_it",IntType.v());
				if(!extraParams.contains(it_cont))
				{
					// extraParams.add(it_cont);
				}
				if(as.containsInvokeExpr() || Utils.isIterator(rv))
				{
					// Esto iba en el if anterior
					//extraParams.add(it_cont);
					
					it_cont.addToBody(body);
					List code = it_cont.codeForVar();
					
					if(!as.containsInvokeExpr())
					{
						Local contIt_anterior = Utils.getLocalForName(body.getLocals(),"cont_"+rv); 
						code=it_cont.codeForAssign(contIt_anterior);
					}
					if(loopHeader!=null)
					{
						Set its = (Set)itersMap.get(loopHeader);
						if(its==null)
							its = new HashSet();
						its.add(it_cont);
						itersMap.put(loopHeader,its);
					}
					// hasta aqui
					//List code = it_cont.codeForVar();
					
//					if(loopHeader!=null)
//					{
//						units.insertBefore(code, loopHeader);
//					}
//					else
						units.insertBefore(code, s);
					isIterator = true;
				}
			}
		}
		// Si hay una invovacion se instrumenta el call

		if (s.containsInvokeExpr()) 
		{
			

			InvokeExpr ie = s.getInvokeExpr();
			if(ie instanceof InstanceInvokeExpr)
			{
				InstanceInvokeExpr iie= (InstanceInvokeExpr)ie;
				Value it=iie.getBase();
				// String methodCalled = iie.getMethod().toString();
				if(Utils.isMethodNext(ie.getMethod()))
				{
					// Stmt loopHeader = getLoopHeader(s);
					// if(loopHeader!=null)
					{
						DI_Iterator itCont  = new DI_Iterator((Local)it);
						// DI_Iterator itCont = (DI_Iterator) extraParams.getParameterFromLocal((Local)it);
						
						// DI_Iterator itCont = (DI_Iterator) extraParams.getParameterFromName("_it");
						if(itCont!=null)
						{
							if(body.getLocals().contains(itCont.getContIt().getLocal()))
							{
								if(!extraParams.contains(itCont))
								{
									extraParams.add(itCont);
								}
								List code = itCont.codeForIncrement();
								units.insertBefore(code, s);
								//units.insertAfter(code, s);
								isIterator = true;
								if(loopHeader!=null)
								{
									Set its = (Set)itersMap.get(loopHeader);
									if(its==null)
										its = new HashSet();
									its.add(itCont);
									itersMap.put(loopHeader,its);
								}
							}
							else
							{
								System.out.println("Problema");
							}
						}
					}
				}

			}
			if(isInstrumentable(ie) /*&& !isIterator*/)
			{
//				 Me genero una nueva copia por si los filtro
				ListDIParameters lParameters = extractMethodParams(body);
				ListDIParameters lParametersInit = extractParametersInits(body, lParameters);
//				List invStmts = instrumentarCSoCallSite("CC", s, analisisVivas,
//						lParameters, lParametersInit, extraParams, body);
				List invStmts = instrumentarCSoCallSite("CC", ordenCallSite, s, analisisVivas,analisisInductivas,
						lParameters, lParametersInit, extraParams, body);
				if (invStmts != null) {
					if(!isInternal(ie) )
					{
						units.insertBefore(invStmts, s);
					}
					else
					{
						// units.insertBefore(invStmts, s);
						units.insertAfter(invStmts, s);
					}
					adjustAddedCode(units, invStmts);
				}

			}
			ordenCallSite++;

		}
		// Elimino los numero de linea una vez usados

		s.removeAllTags();
	}

	/**
	 * @param ie
	 * @return
	 */
	private boolean isInstrumentable(InvokeExpr ie) {
		return true;
		/*
		return !ie.getMethod().toString().startsWith("<java.") 
		&& !ie.getMethod().getDeclaringClass().getName().startsWith("System.");
		*/
		// return ie.getMethod().getName().indexOf("<init>")==-1 && !ie.getMethod().toString().startsWith("<java.lang.");
	}
	
	private boolean isInternal(InvokeExpr ie) {
		return ie.getMethod().getName().indexOf("<init>")!=-1 
		|| ie.getMethod().getName().indexOf("<clinit>")!=-1 
		/*
		|| ie.getMethod().toString().startsWith("<java.lang.")*/;
	}

	
	private ListDIParameters getCallArgs(Stmt s, Body body, InductiveVariablesInfo IVInfo) {
		
		ListDIParameters info2 = new ListDIParameters();
		Vector res = new Vector();
		InvokeExpr ie = s.getInvokeExpr();
		try {
		SootMethod m = ie.getMethod();
		
		int i=0;
		
		List args = ie.getArgs();
		
		GlobalLive analisisVivasCaller = (GlobalLive) GLVMain.analysisCache.get(body.getMethod());
		List livesCaller = analisisVivasCaller.getLiveLocalsBefore(s);
		
		GlobalLive analisisVivasCallee = (GlobalLive) GLVMain.analysisCache.get(m);
		
		if(analisisVivasCallee==null)
			return info2;
		
		List livesCallee = analisisVivasCallee.getLiveInitialFlow();
		
		Local calleeThisRef = analisisVivasCallee.getThisRef();
		List parameters = analisisVivasCallee.getParameters();
		List argsParamMap = new Vector();
		
		
		
		if(ie instanceof InstanceInvokeExpr)
		{
			InstanceInvokeExpr iie = (InstanceInvokeExpr)ie;
			Value argThis=iie.getBase();
			addParameter(body, info2, (InductivesFilter) analisisVivasCallee, livesCallee, (Local)argThis,analisisVivasCallee.getThisRef());
		}
		i=0;
		for (Iterator iter = args.iterator(); iter.hasNext();) {
			Value arg = (Value) iter.next();
			if(arg instanceof Local)
			{
				Local lArg = (Local)arg;
				addParameter(body, info2, (InductivesFilter) analisisVivasCallee, livesCallee, lArg,(Local)parameters.get(i));
			}
			else
			{
				info2.add(new DI_Value(arg));
			}
			i++;
		}
		}
		catch(ResolutionFailedException crfe)
		{
			return info2;
		}
		return info2;
	}

	
	
	private List instrumentarCSoCallSite(String tipo, int orden, Stmt s,
			GlobalLive analisisVivas,
			InductivesFilter analisisInductivas, ListDIParameters origLParameters,ListDIParameters origLParametersInit, 
			ListDIParameters extraParams, Body body) {
		
		SootClass sc = body.getMethod().getDeclaringClass();
		String  sLn = Utils.getLineNumber(s);
		if(sLn.length() == 0)
			sLn = Integer.toString(dummyLineNumber++)+"c0";
		
		String insSite;

		insSite = sc.toString() + "_" + sLn;

		// Obatin variables to add to the instrumented methdo
		
		InductiveVariablesInfo inductivesInfo = NewsInvariantInstrumentator.getInductivesReader().getiInfo(insSite);
		
			// // Diego OJO 6/8/2009  GlobalLive analisisVivas2 = (GlobalLive) GLVMain.analysisCache.get(body.getMethod());
		 List vivas = analisisVivas.getLiveLocalsBefore(s);
		 // OJO List vivas = analisisInducativas.getInductivesBefore(s);

	
		boolean estaStmt=false;
		
		
	
		ListDIParametersNoRep paramsIntru = new ListDIParametersNoRep();
		ListDIParameters lParameters = new ListDIParameters();
		ListDIParameters lParametersInit = new ListDIParameters();
		
		
		
		if(inductivesInfo ==null || inductivesInfo.takeAllInductives())
		{
			List localVivas = analisisInductivas.liveVars(vivas);
			// List localVivas = (List)InductivesFinderMain.inductivesSTMTMap.get(s);
			
			for (Iterator iter = localVivas.iterator(); iter.hasNext();) {
				Local viva = (Local) iter.next();
				// OJO addParameter(body, paramsIntru, analisisInductivas, vivas, viva,viva);
				addParameter(body, paramsIntru, (InductivesFilter) analisisVivas, vivas, viva,viva);
			}
		}
		else
		{
			System.out.println("Inductivas:"+insSite+":"+inductivesInfo);
			for (Iterator iter = inductivesInfo.getInductiveInfo().iterator(); iter.hasNext();) {
				String sVar = (String) iter.next();
				Local var =Utils.getLocalForName(body.getLocals(),sVar);
				if(var!=null)
				{
					DIParameter dip = DIParameterFactory.createDIParameter(var,body,true);
					if(dip!=null) paramsIntru.add(dip);
					/* Antes filtraba por aqui los parameters ahora lo hace en filter
					dip = origLParameters.getParameterFromLocal(var);
					if(dip!=null)
						lParameters.add(dip);
					dip = origLParametersInit.getParameterFromLocal(var);
					if(dip!=null)
						lParametersInit.add(dip);
					*/
				}
			}
		}
		
		// Me hago una copia de los parametros para no arruinarlos en el filtro 
		// de las variables derivadas
		// lParameters = (ListDIParameters)origLParameters.clone();
		// lParametersInit = (ListDIParameters)origLParametersInit.clone();
		
		lParameters =  origLParameters;
		lParametersInit = origLParametersInit;
		
		// Esto no servia 
		// lParameters.addAll(origLParameters);
		//lParametersInit.addAll(origLParametersInit);
		
		// Agrega los parametros extras (por ejemplo, iteradores)
		// paramsIntru.addAll(extraParams);
		
		// Agrega los parametros
		// Diego: AGREGAR
		// paramsIntru.addAll(lParameters);
		
		List newArrayParams = new Vector();
		String creationSiteType = "";
		
		if (tipo.equals("CS")) {
			System.out.println("NEW in:" + insSite + " " + s.toString());
			AssignStmt as = (AssignStmt) s;
			Value exp = as.getRightOp();
			if(exp instanceof AnyNewExpr)
			{
				if(exp instanceof NewExpr)
				{
					NewExpr newExp = (NewExpr)exp;
					creationSiteType = exp.getType().toString();
				}
				else if(exp instanceof NewArrayExpr)
				{
					NewArrayExpr newArrExp = (NewArrayExpr)exp;
					newArrayParams.add(newArrExp.getSize());
					creationSiteType = newArrExp.getBaseType().toString();
				}
				else if(exp instanceof NewMultiArrayExpr)
				{
					NewMultiArrayExpr newMArrExp = (NewMultiArrayExpr)exp;
					newArrayParams.addAll(newMArrExp.getSizes());
					creationSiteType = newMArrExp.getBaseType().toString();
				}
				System.out.println("Magda: "+insSite+"="+creationSiteType+" "+newArrayParams);
			}
		} 
		else
		{
			System.out.println("CALL in:" + insSite + " " + s.toString());
	
			// Obtiene los argumentos del metodo llamado

			
			// ListDIParameters args = getCallArgs(s,body,null);
			ListDIParameters args = getCallArgs(s,body,null);
			
			
			ccArgsMap.put(insSite, args.clone());
			
			// ccArgsMap.put(insSite, argsMapList);
			
			
			paramsIntru.addAll(args);
//			for (Iterator iter = argsMapList.iterator(); iter.hasNext();) {
//				Object[] element = (Object[]) iter.next();
//				paramsIntru.add(element[0]);
//				
//			}
			
			String nameMethodDec = s.getInvokeExpr().getMethod().getSignature();
			//String nameMethodDec = s.getInvokeExpr().getMethod().getSubSignature();
			String className = s.getInvokeExpr().getMethod().getDeclaringClass().getName();
			
			ccMethodsMap.put(insSite, new Object[] {nameMethodDec,className} );
			
		}
		

		if (!newsMap.containsKey(insSite)) {
			
			String nombreMetodo = tipo + "_" + sLn;
			
			//Filtra segun la configuracion de inductivas
			// OJO paramsIntru.filter(inductivesInfo);
			// Agrego parametros de creacion de arrays
			ListDIParameters newArrayParamsList = new ListDIParameters();
			for (Iterator itParamArr = newArrayParams.iterator(); itParamArr.hasNext();) {
				Value size = (Value) itParamArr.next();
				if (size instanceof Local) {
					DIParameter arrPar = DIParameterFactory.createDIParameter((Local)size); 
					paramsIntru.add(arrPar);
					newArrayParamsList.add(arrPar);
				}
			}
			
			// Agrega los parametros al body del metodo 
			paramsIntru.addToBody(body);
			
			ListDIParametersNoRep allParams = new ListDIParametersNoRep();
			
			//	Agrego los params_init al metodo a instrumentar
			allParams.addAll(paramsIntru);
			allParams.addAll(lParametersInit);
			
			
			// Agrega los parametros extras (por ejemplo, iteradores)
			allParams.addAll(extraParams);
			// OJO allParams.filter(inductivesInfo);
			
			// Aplico opcionalmente las inductivas aqui
			

			// Create a dummy method for the insSite with live variables and field
			SootMethod sm = crearMetodo(insSite, sc, body.getMethod(), nombreMetodo,
					allParams, inductivesInfo);
			
			
			
			//	Instrument an invocation  to the created dummy method 
			List invStmts = null;
			
			if(sm!=null)
			{
				invStmts = crearInvocacionMetodo(sm, body, paramsIntru, allParams, inductivesInfo);
				InvokeStmt is = (InvokeStmt)invStmts.get(invStmts.size()-1);
				if(sm.getParameterCount()!=is.getInvokeExpr().getArgCount())
				{
					throw new RuntimeException("Problema con los metodos!");
					
				}
			}
			
			String nameMethodDec = body.getMethod().getSignature();
			
			List allParamsString = new Vector();
			
			if(NewsInvariantInstrumentator.inductivesAsRelevants )
			{
				//if(insSite.indexOf("00570c00005")!=-1) 
				if(insSite.indexOf("00442c00246")!=-1)
				{
					System.out.println();
				}
				
				
				//Aca es donde se rompe!!!!!! cuando convierte parametros
				allParamsString = allParams.toStringList(inductivesInfo);
				
			}
			else
			{
				allParamsString =allParams.toStringList();
			}
			newsMap.put(insSite,  new CreationSiteMapInfo(insSite, orden, allParamsString, nameMethodDec,tipo,creationSiteType,newArrayParamsList.toList()));
				
			return invStmts;
			
		} else {
			// System.out.println("Dupplicate :"+insSite);
		}
		return null;
	}
	
		
	void inductiveAnalysis(Stmt s, LiveLocals analisisVivas)
	{
		List vivas;
		Collection loops = loops();

		System.out.println("Eli:"+loops);
		//for (Iterator iter = loops.keySet().iterator(); iter.hasNext();) {
		//		Stmt element = (Stmt) iter.next();
		//		List loopStmt= (List)loops.get(element);
		for (Iterator iter = loops.iterator(); iter.hasNext();) {
			Loop loop = (Loop)iter.next();
			List loopStmt= loop.getLoopStatements();
			// System.out.println("Eli2:"+element+","+element.getClass());
			if(loopStmt.contains(s))
			{
				// estaStmt=true;
				//vivas = analisisVivas.getLiveLocalsBefore(element);
				vivas = analisisVivas.getLiveLocalsBefore(loop.getHead());				
				// System.out.println("Eli2:"+insSite+" "+s+"esta en loop de:"+element+","+element.getClass());
			}
		}

		System.out.println("Eli3:"+dominator.getDominators(s));
	
	}
	Stmt getLoopHeader(Stmt s)
	{
		Stmt loopHeader = null;
		Collection loops = this.loops();

		if(loops!=null)
		{
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
		}
		return loopHeader;
	}


	/**
	 * @param m
	 * @param body
	 * @param paramsInstru
	 * @return
	 */
	private List crearInvocacionMetodo(SootMethod m, Body body, ListDIParameters paramsInstru, 
									ListDIParameters allParams, InductiveVariablesInfo IVInfo) 
	{
		
		List code = paramsInstru.codeForParameters();
		
				
		List paramsExpanded = NewsInvariantInstrumentator.inductivesAsRelevants? allParams.toList(IVInfo):allParams.toList();
		
		ListNoRep paramsExp = new ListNoRep();
		paramsExp.addAll(paramsExpanded);
		
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(m.makeRef(), paramsExp);
		Stmt invStmt = Jimple.v().newInvokeStmt(invExpr);
		code.add(invStmt);
		
		// System.out.println("Tincho: " + allParams +  "params codigo "+paramsInstru);
		// System.out.println("Diego2: M="+m+" s="+invStmt);
		
		// System.out.println("Locals " + body.getLocals());
		// System.out.println("code= " + code.toString());

		return code;
	}

public static boolean isPossibleField(SootClass fieldClass,SootField f, SootClass methodClass )
	{
		boolean ok = false;
		if(f.isPublic())
			ok=true;
		else 
		{
			if(/*fieldClass.isProtected() || fieldClass.isPublic() || */fieldClass.equals(methodClass) )
				ok = true;
			else
				if(fieldClass.getPackageName().equals(methodClass.getPackageName()))
					ok=true;
				// methodClass.getPackageName().equals(fieldClass.getPackageName()
			
		}
		
		return ok; //&& !f.isFinal();
	}
public static Map getNewsMap() {
		return getInstance().newsMap;
	}

	public static Map getMethodMap() {
		return getInstance().methodMap;
	}
	public static Map getCcArgsMap() {
		return getInstance().ccArgsMap;
	}
	public static NewsInstrumenterDaikon getInstance() {
		return instance;
	}
	public static Map getCcMethodsMap() {
		return getInstance().ccMethodsMap;
	}
}
