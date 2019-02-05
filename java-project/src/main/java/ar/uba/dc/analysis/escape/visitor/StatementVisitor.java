package ar.uba.dc.analysis.escape.visitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Local;
import soot.RefLikeType;
import soot.SootMethod;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.BreakpointStmt;
import soot.jimple.CaughtExceptionRef;
import soot.jimple.Constant;
import soot.jimple.EnterMonitorStmt;
import soot.jimple.ExitMonitorStmt;
import soot.jimple.GotoStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.IfStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.LookupSwitchStmt;
import soot.jimple.NopStmt;
import soot.jimple.ParameterRef;
import soot.jimple.RetStmt;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.StmtSwitch;
import soot.jimple.TableSwitchStmt;
import soot.jimple.ThisRef;
import soot.jimple.ThrowStmt;
import ar.uba.dc.analysis.escape.CallAnalyzer;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.soot.Box;

public class StatementVisitor implements StmtSwitch {

	private static Log log = LogFactory.getLog(StatementVisitor.class);
	
	private AbstractAssignStatementVisitor assignVisitor = new DefaultAssignStatementVisitor();
	private SootMethod methodUnderAnalysis;
	private Box<EscapeSummary> in;
	private Box<EscapeSummary> out;
	private CallAnalyzer callAnalyzer;
	private int sensitivity;
		
	public void setMethodUnderAnalysis(SootMethod method) {
		this.methodUnderAnalysis = method;
	}
	
	public void setIn(Box<EscapeSummary> in) {
		this.in = in;
	}
	
	public void setOut(Box<EscapeSummary> out) {
		this.out = out;
	}
	
	public void setCallAnalyzer(CallAnalyzer analyzer) {
		this.callAnalyzer = analyzer;
	}
	
	/**
	 * En ambos casos se deberia considerar lo siguiente:
	 * si la asignacion se le esta haciendo a un nodo N que apunta via un eje "?" a otro nodo,
	 * podría darse la posibilidad de que se esté updateando ese otro nodo también.
	 */
	public void caseAssignStmt(AssignStmt stmt) {
		
		if (stmt.containsInvokeExpr()) {
			log.debug(" | | |- call: " + stmt.getInvokeExpr().getMethod().getDeclaringClass().getName() + " " + stmt.getInvokeExpr().getMethod().getSubSignature().toString());
			callAnalyzer.analyseCall(in, stmt, out);
		} else {
			assignVisitor.setIn(in);
			assignVisitor.setOut(out);
			assignVisitor.setMethodUnserAnalysis(methodUnderAnalysis);
			assignVisitor.setSensitivity(sensitivity);
			assignVisitor.visit(stmt);
		}
	}

	public void caseBreakpointStmt(BreakpointStmt stmt) {
		// do nothing...
		log.debug(" | | |- Ignoring Breakpoint");
	}

	public void caseEnterMonitorStmt(EnterMonitorStmt stmt) {
		// do nothing...
		log.debug(" | | |- Ignoring Enter monitor");
	}

	public void caseExitMonitorStmt(ExitMonitorStmt stmt) {
		// do nothing...
		log.debug(" | | |- Ignoring Exit monitor");
	}

	public void caseGotoStmt(GotoStmt stmt) {
		// do nothing...
		log.debug(" | | |- Ignoring Goto");
	}

	public void caseIdentityStmt(IdentityStmt stmt) {
		Local left    = (Local) stmt.getLeftOp();
	    Value rightOp = stmt.getRightOp();
	    
	    if (rightOp instanceof ThisRef) {
	    	out.getValue().assignThisToLocal(left);
	    } else if (rightOp instanceof ParameterRef) {
	    	ParameterRef p = (ParameterRef) rightOp;
	    	// ignore primitive types
	    	if (p.getType() instanceof RefLikeType) {
	    		out.getValue().assignParamToLocal(p.getIndex(), left);
	    	}
	    } else if (rightOp instanceof CaughtExceptionRef) {
	    	// local = exception
	    	out.getValue().localIsUnknown(left);
	    } else {
	    	throw new RuntimeException("IdentityStmt match failure (rightOp) " + stmt);
	    }
	}

	public void caseIfStmt(IfStmt stmt) { 
		// do nothing...
		log.debug(" | | |- Ignoring If");
	}

	public void caseInvokeStmt(InvokeStmt stmt) {
		log.debug(" | | |- call: " + stmt.getInvokeExpr().getMethod().getDeclaringClass().getName() + " " + stmt.getInvokeExpr().getMethod().getSubSignature().toString());
		callAnalyzer.analyseCall(in, stmt, out);
	}

	public void caseLookupSwitchStmt(LookupSwitchStmt stmt) {
		// do nothing...
		log.debug(" | | |- Ignoring LookupSwitch");
	}

	public void caseNopStmt(NopStmt stmt) {
		// do nothing...
		log.debug(" | | |- Ignoring NOP");
	}

	public void caseRetStmt(RetStmt stmt) {
		throw new RuntimeException("Stmt match faliure " + stmt);
	}

	public void caseReturnStmt(ReturnStmt stmt) {
		Value v = stmt.getOp();

	    if (v instanceof Local) {
	    	// ignore primitive types
	    	if (v.getType() instanceof RefLikeType) {
	    		out.getValue().returnLocal((Local) v);
	    	} else {
	    		log.debug(" | | |- Ignoring Returning primitive type local");
	    	}
	    }else if (v instanceof Constant) {
	    	// do nothing...
	    	log.debug(" | | |- Returning constant value");
	    } else {
	    	throw new RuntimeException("ReturnStmt match failure " + stmt);
		}
	}

	public void caseReturnVoidStmt(ReturnVoidStmt stmt) {
		// do nothing...
		log.debug(" | | |- Ignoring return void");
	}

	public void caseTableSwitchStmt(TableSwitchStmt stmt) {
		// do nothing...
		log.debug(" | | |- Ignoring table switch");
	}

	public void caseThrowStmt(ThrowStmt stmt) {
		Value op = stmt.getOp();

	    if (op instanceof Local) {
	    	Local v = (Local) op;
	    	out.getValue().localEscapes(v);
	    } else if (op instanceof Constant) {
	    	// do nothing...
	    	log.debug(" | | |- Ignoring Throw Constants");
	    } else {
	    	throw new RuntimeException("ThrowStmt match failure " + stmt);
	    }
	}

	public void defaultCase(Object obj) {
		throw new RuntimeException("Stmt match faliure " + obj);
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}
}
