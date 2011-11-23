package ar.uba.dc.analysis.escape.visitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Local;
import soot.SootMethod;
import soot.Value;
import soot.jimple.ArrayRef;
import soot.jimple.AssignStmt;
import soot.jimple.InstanceFieldRef;
import soot.jimple.StaticFieldRef;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.soot.Box;

public abstract class AbstractAssignStatementVisitor {

	private static Log log = LogFactory.getLog(AbstractAssignStatementVisitor.class);
	
	protected SootMethod methodUnderAnalysis;
	protected Box<EscapeSummary> in;
	protected Box<EscapeSummary> out;
	protected int sensitivity;
	
	public void setIn(Box<EscapeSummary> in) {
		this.in = in;
	}
	
	public void setOut(Box<EscapeSummary> out) {
		this.out = out;
	}
	
	public void setMethodUnserAnalysis(SootMethod method) {
		this.methodUnderAnalysis = method;
	}
	
	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	public void visit(AssignStmt stmt) {
		log.debug(" | | |- visit assignment " + stmt);
		Value leftOp = stmt.getLeftOp();
	    Value rightOp = stmt.getRightOp();
	    
	    if (leftOp instanceof Local) {
	    	caseAssignSomethingToLocal((Local) leftOp, rightOp, stmt);
	    } else if (leftOp instanceof ArrayRef) {
	    	caseAssignSomethingToArray((ArrayRef) leftOp, rightOp);	    	
	    } else if (leftOp instanceof InstanceFieldRef) {
	    	caseAssignSomethingToInstanceField((InstanceFieldRef) leftOp, rightOp);
	    } else if (leftOp instanceof StaticFieldRef) {
	    	caseAssignSomethingToStaticField((StaticFieldRef) leftOp, rightOp);
	    } else {
	    	throw new RuntimeException("AssignStmt match failure (leftOp) " + stmt);
	    }
	}

	protected abstract void caseAssignSomethingToLocal(Local leftOp, Value rightOp, Stmt stmt);
	protected abstract void caseAssignSomethingToArray(ArrayRef leftOp, Value rightOp);
	protected abstract void caseAssignSomethingToInstanceField(InstanceFieldRef leftOp, Value rightOp);
	protected abstract void caseAssignSomethingToStaticField(StaticFieldRef leftOp, Value rightOp);
}
