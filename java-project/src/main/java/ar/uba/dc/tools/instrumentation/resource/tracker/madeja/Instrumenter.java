package ar.uba.dc.tools.instrumentation.resource.tracker.madeja;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Body;
import soot.BodyTransformer;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.VoidType;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.CaughtExceptionRef;
import soot.jimple.IdentityStmt;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.Jimple;
import soot.jimple.NewArrayExpr;
import soot.jimple.NewExpr;
import soot.jimple.NewMultiArrayExpr;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.jimple.internal.JimpleLocal;
import soot.jimple.internal.JimpleLocalBox;
import soot.util.Chain;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.StatementVisitor;
import ar.uba.dc.analysis.common.code.impl.DefaultCallStatement;
import ar.uba.dc.analysis.common.code.impl.DefaultNewStatement;
import ar.uba.dc.analysis.memory.code.impl.visitor.IdStatementVisitor;
import ar.uba.dc.memory.analysis.compositional.EscapeAnalysisResult;
import ar.uba.dc.memory.analysis.compositional.madeja.MadejaEscapeAnalysisResult;


public class Instrumenter  extends BodyTransformer { 
	
		private static boolean countAfterReturn = false;
		
		private static Log log = LogFactory.getLog(Instrumenter.class);
		
		public static final String LISTENER_PACKAGE = "ar.uba.dc.tools.instrumentation.resource.tracker.runtime";
		public  static final String LISTENER_CLASS =  LISTENER_PACKAGE + ".ListenerFacade";
		
		private static final String NEW_OBJECT_SIGNATURE = "void newObjectEvent(java.lang.Object,java.lang.String)";
		private static final String MAIN_METHOD_SIGNATURE = "void main(java.lang.String[])";
		private static final String CALL_SIGNATURE = "void callEvent(java.lang.String)";
		private static final String CAUGHT_EXCEPTION_SIGNATURE = "void caughtExceptionEvent(java.lang.String)";		
		private static final String END_CALL_SIGNATURE = "void endCallEvent(java.lang.String)";
		private static final String EXIT_PROGRAM_METHOD_SIGNATURE = "void exit(int)";
		
		private String currentNewStmt; 
		
		private String mainClass;

		private long newCounter = 0;
		
		private long invokeCounter = 0;
		
		private LifeTimeSummaryRepository repository;
		
		private StatementVisitor<String> visitor = new IdStatementVisitor();
		
		@SuppressWarnings("unchecked")
		private List endCallInstrumentation = null;
		
		public Instrumenter(String mainClass, LifeTimeSummaryRepository repository) {
			this.mainClass = mainClass;
			this.repository = repository;
		}
		
		
		private void reset() {
			this.newCounter = 0;
			this.invokeCounter = 0;
			this.currentNewStmt = null;
			if(this.endCallInstrumentation != null)
				throw new RuntimeException("Unable to instrument endCall event for: " + this.endCallInstrumentation.toString());
		}
	
		
		
		@SuppressWarnings("unchecked")
		protected void internalTransform(Body body, String phaseName, Map options) {
			
			SootMethod sootMethod = body.getMethod();			
			EscapeAnalysisResult escapeAnalysisResult = new MadejaEscapeAnalysisResult(sootMethod);

			if(!sootMethod.getDeclaringClass().getPackageName().startsWith(LISTENER_PACKAGE) && this.repository.contains(sootMethod)) {
				Chain units = body.getUnits();
				Iterator<Unit> stmtIt = units.snapshotIterator();
				
				//Iterator<Unit> nextStmtIt = units.snapshotIterator();
				
				boolean enterEventInserted = false;
				
				while (stmtIt.hasNext()) {
					Stmt next = (Stmt) stmtIt.next();
					
					// Para insertar la llamada al evento de entrada debemos esperar a pasar las primeras asignaciones de tipo "var = @this" y "param1 = @param1"
					if(!(next instanceof IdentityStmt) && !enterEventInserted) {
						if (sootMethod.getSubSignature().equals(MAIN_METHOD_SIGNATURE) && sootMethod.getDeclaringClass().getName().equals(mainClass)) {
							units.insertBefore(addMainEnterEvent(sootMethod.getSignature()), next);
						} else {
							units.insertBefore(addMethodEnterEvent(sootMethod.getSignature()), next);
						}
						enterEventInserted = true;
					}
					processStmt(body, units, next, escapeAnalysisResult);
				}
						
			
				this.reset();
			} else {
				log.info("Ignorando..." + body.getMethod().toString());
			}
		}

		private  List<Unit> addMainEnterEvent(String signature) {
			//FIXME
			return this.newStaticInvokeExpr("void enter(java.lang.String)", StringConstant.v(signature));
		}

		/**
		 * @param body
		 * @param units
		 * @param analisisVivas
		 * @param cParametersAum
		 * @param s
		 * @param nextStmtIt 
		 */
		@SuppressWarnings("unchecked")
		private void processStmt(Body body, Chain units, Stmt s, EscapeAnalysisResult result) {

			SootMethod sootMethod = body.getMethod();
			
			//boolean proccessEndCallEvent = true;
			
			//NewStmt
			if (s instanceof AssignStmt) {
		
				AssignStmt as = (AssignStmt) s;
				Value exp = as.getRightOp();

				// Si es un NEW se instrumenta como creation site
				if (exp instanceof AnyNewExpr) {
					
					JimpleLocal localVar = (JimpleLocal) as.getLeftOp();
					
					NewStatement newStmt = new DefaultNewStatement(body.getMethod(), s, this.newCounter);
					this.newCounter++;
					
					// ea.getPartition(newStatement)
				//	String partId = result.mayEscapeMethod(newStmt) ? result.getPartition(newStmt).id() : MemoryHeap.TEMPORAL_PARTITION_NAME;
					
					List instrumentation = null;
					//Stmt nextStmt = (Stmt) stmtIt.next();
					
					if (exp instanceof NewExpr) {
					//	instrumentation = this.addNewObjectEvent(localVar, newStmt.apply(this.visitor));
					//	units.insertAfter(instrumentation, succ);
						this.currentNewStmt = newStmt.apply(this.visitor);
						//Esperamos al invoke del constructor
						
					} else if (exp instanceof NewArrayExpr || exp instanceof NewMultiArrayExpr) {
						instrumentation = this.addNewArrayEvent(localVar, newStmt.apply(this.visitor),body, s);
						units.insertAfter(instrumentation, s);
					}

					
				} else if(this.endCallInstrumentation != null) {
					//Hay pendiente un endCall de un metodo con return
					units.insertAfter(this.endCallInstrumentation, s);
					this.endCallInstrumentation = null;
				}
				
			}
			
			if (s instanceof IdentityStmt) {
				IdentityStmt is = (IdentityStmt) s;
				Value exp = is.getRightOp();
				if (exp instanceof CaughtExceptionRef) {
					units.insertAfter(this.addCaughtExceptionEvent(sootMethod.toString()), s);
				}
				
			}
			
			// Para los calls instrumentamos el mapping de particiones
			if (s.containsInvokeExpr()) {
				
				InvokeExpr expr = s.getInvokeExpr();
				if(!expr.getMethod().getDeclaringClass().getName().contains(LISTENER_CLASS) ) {
					DefaultCallStatement callStatement = new DefaultCallStatement(sootMethod, s, this.invokeCounter);

					SootMethod method = expr.getMethod();
					List before = null;
					List after = null;
					boolean isReturn = !method.getReturnType().equals(VoidType.v());
					
					if (method.getDeclaringClass().getName().equals("java.lang.System") && EXIT_PROGRAM_METHOD_SIGNATURE.equals(method.getSubSignature())) {
						before = this.addMainMethodExitEvent(method.toString());
					} else {
						before = this.addCallEvent(method, body, s, callStatement.apply(this.visitor));
						
						after = this.addEndCallEvent(method, body, s, callStatement.apply(this.visitor));
						
					}
					
					if (before != null) 
						units.insertBefore(before, s);
					if (after != null) {
						if(isReturn && countAfterReturn) {
							this.endCallInstrumentation = after; //Tenemos que esperar al load del return
						} else
							units.insertAfter(after, s);

						if(this.currentNewStmt != null) {
							//Hay un new sin procesar
							//JInvokeStmt jInvokeStmt = (JInvokeStmt) s;
							JimpleLocalBox localBox = (JimpleLocalBox) expr.getUseBoxes().get(0);
							JimpleLocal local = (JimpleLocal) localBox.getValue();
							
							units.insertAfter(this.addNewObjectEvent(local, this.currentNewStmt),s);
							this.currentNewStmt = null;
						}
					}
					
				} else {
					log.info("Ignoring call to " + expr.getMethod().toString());
				}
				
				//Actualizamos el counter
				this.invokeCounter++;
			} else if(s instanceof ReturnStmt  || s instanceof ReturnVoidStmt ) {
				
				if(this.endCallInstrumentation != null) {
					//Hay pendiente un endCall de un metodo con return
					units.insertBefore(this.endCallInstrumentation, s);
					this.endCallInstrumentation = null;
				}
				
				//Main => export result
				if(sootMethod.equals(Scene.v().getMainClass().getMethod("void main(java.lang.String[])"))) {
					units.insertBefore(addMainMethodExitEvent(sootMethod.getSignature()),s);	
				} else {
					units.insertBefore(addMethodExitEvent(sootMethod.getSignature()),s);	
				}
			}
			
			
			
		}

		private List<Unit> addCallEvent(SootMethod method, Body body, Stmt s, String invokeStmt) {
			List<Unit> code = new Vector<Unit>();
			addNewStaticInvokeExpr(code, CALL_SIGNATURE, StringConstant.v(invokeStmt));
			return code;
		}
		
		private List<Unit> addEndCallEvent(SootMethod method, Body body, Stmt s, String invokeStmt) {
			List<Unit> code = new Vector<Unit>();
			addNewStaticInvokeExpr(code, END_CALL_SIGNATURE, StringConstant.v(invokeStmt));
			return code;
		}

		/**
		 * @param localVar 
		 * @param insSite
		 * @param partition
		 * @return
		 */
		private List<Unit> addNewObjectEvent(JimpleLocal localVar, String insSite) {
			List<Unit> code = new Vector<Unit>();
	//		addNewStaticInvokeExpr(code, NEW_OBJECT_REGISTRATION_SIGNATURE, StringConstant.v(insSite), StringConstant.v(partition));
			addNewStaticInvokeExpr(code, NEW_OBJECT_SIGNATURE, localVar, StringConstant.v(insSite));
			return code;
		}
		
		
		@SuppressWarnings("unchecked")
		private List addNewArrayEvent(JimpleLocal localVar, String insSite, Body body, Stmt s) {

			AssignStmt as = (AssignStmt) s;
			Value exp = as.getRightOp();
			List newArrayParam = new Vector();
			
			if (exp instanceof AnyNewExpr) {
				//1 object
				if (exp instanceof NewExpr) {
					newArrayParam.add(IntConstant.v(0));
				} else if (exp instanceof NewArrayExpr) { //Array
					NewArrayExpr newArrExp = (NewArrayExpr) exp;
					newArrayParam.add(newArrExp.getSize());
				} else if (exp instanceof NewMultiArrayExpr) { //MultiArray
					NewMultiArrayExpr newMArrayExp = (NewMultiArrayExpr) exp;
					for (Iterator iter = newMArrayExp.getSizes().iterator(); iter.hasNext();) {
						Value element = (Value) iter.next();
						newArrayParam.add(element);
					}
				}

			}
			
			//Generamos el codigo
			List<Unit> code = new Vector<Unit>();
			//addNewStaticInvokeExpr(code, NEW_OBJECT_REGISTRATION_SIGNATURE, StringConstant.v(insSite), StringConstant.v(partId));
			
			String sMethodtoCall = "void newArrayEvent(java.lang.Object,java.lang.String";
			for (int i = 0; i < newArrayParam.size(); i++)
				sMethodtoCall += ",int";
			sMethodtoCall += ")";
			
			SootMethod toCall = this.getTargetMethod(sMethodtoCall);
			
			List args = new Vector();
			args.add(localVar);
			args.add(StringConstant.v(insSite));
			//args.add(StringConstant.v(partId));
			args.addAll(newArrayParam);
			StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), args);
			Stmt invStmt = Jimple.v().newInvokeStmt(invExpr);
			code.add(invStmt);
			return code;
		}
		
		
	private List<Unit> newStaticInvokeExpr(String methodDescription, Value... values) {
		List<Unit> code = new Vector<Unit>();
		SootMethod toCall = this.getTargetMethod(methodDescription);
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), Arrays.asList(values));
		Stmt invStmt = Jimple.v().newInvokeStmt(invExpr);
		code.add(invStmt);
		return code;
	}
	
	private void addNewStaticInvokeExpr(List<Unit> code , String methodDescription, Value... values) {
		SootMethod toCall = this.getTargetMethod(methodDescription);
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), Arrays.asList(values));
		Stmt invStmt = Jimple.v().newInvokeStmt(invExpr);
		code.add(invStmt);
	}
	
	private List<Unit> addCaughtExceptionEvent(String caughtInMethod) {
		return this.newStaticInvokeExpr(CAUGHT_EXCEPTION_SIGNATURE, StringConstant.v(caughtInMethod));
	}
		
	private SootMethod getTargetMethod(String methodDescription) {
	//	Scene.v().loadClassAndSupport(LISTENER_CLASS);
		return Scene.v().getSootClass(LISTENER_CLASS).getMethod(methodDescription);
	}
	
	@SuppressWarnings("unchecked")
	private List addMethodEnterEvent(String method) {
		return this.newStaticInvokeExpr("void enter(java.lang.String)", StringConstant.v(method));
	}
	
	@SuppressWarnings("unchecked")
	private List addMethodExitEvent(String method) {
		return this.newStaticInvokeExpr("void exit(java.lang.String)", StringConstant.v(method));
	}
	

	@SuppressWarnings("unchecked")
	private List addMainMethodExitEvent(String method) {
		List code = new Vector();
		SootMethod toCall = this.getTargetMethod("void exit(java.lang.String)");
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(),  StringConstant.v(method));
		Stmt invStmt = Jimple.v().newInvokeStmt(invExpr);
		code.add(invStmt);
		toCall = this.getTargetMethod("void exit()");
		invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef());
		invStmt = Jimple.v().newInvokeStmt(invExpr);
		code.add(invStmt);
		return code;
		
	}

	
}

