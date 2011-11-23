package ar.uba.dc.analysis.escape.visitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Local;
import soot.RefLikeType;
import soot.Value;
import soot.jimple.InstanceOfExpr;
import soot.jimple.AnyNewExpr;
import soot.jimple.BinopExpr;
import soot.jimple.CastExpr;
import soot.jimple.ArrayRef;
import soot.jimple.Constant;
import soot.jimple.InstanceFieldRef;
import soot.jimple.NullConstant;
import soot.jimple.StaticFieldRef;
import soot.jimple.Stmt;
import soot.jimple.UnopExpr;

public class DefaultAssignStatementVisitor extends AbstractAssignStatementVisitor {

	private static Log log = LogFactory.getLog(DefaultAssignStatementVisitor.class);
	
	@Override
	protected void caseAssignSomethingToArray(ArrayRef leftOp, Value rightOp) {
		Local left = (Local) leftOp.getBase();

		// v[i] = v
		if (rightOp instanceof Local) {
			Local right = (Local)rightOp;
			if (right.getType() instanceof RefLikeType) {
				out.getValue().assignLocalToField(right, left, "[]");
			} else {
				out.getValue().mutateField(left, "[]");
			}
		} else if (rightOp instanceof Constant) {
			// v[i] = cst
			out.getValue().mutateField(left, "[]");
		} else {
			throw new RuntimeException("AssignStmt match failure (rightOp)" + rightOp);
		}
	}

	@Override
	protected void caseAssignSomethingToInstanceField(InstanceFieldRef leftOp, Value rightOp) {
		Local  left  = (Local) leftOp.getBase();
		String field = leftOp.getField().getName();

		if (rightOp instanceof Local) {
			// v.f = v
			Local right = (Local)rightOp;
			// ignore primitive types
			if (right.getType() instanceof RefLikeType) {
				out.getValue().assignLocalToField(right, left, field);
			} else {
				out.getValue().mutateField(left, field);
			}
		} else if (rightOp instanceof Constant) {
			// v.f = cst
			out.getValue().mutateField(left, field);
		} else { 
			throw new RuntimeException("AssignStmt match failure (rightOp) " + rightOp);
		}
	}

	@Override
	protected void caseAssignSomethingToLocal(Local leftOp, Value rightOp, Stmt stmt) {
		// remove optional cast
		if (rightOp instanceof CastExpr) 
		    rightOp = ((CastExpr) rightOp).getOp();

		// ignore primitive types
		if (!(leftOp.getType() instanceof RefLikeType)) {
			log.debug(" | | |- Ingoring assignment to primitive types");
		} else if (rightOp instanceof Local) {
			// v = v
		    Local right = (Local) rightOp;
		    out.getValue().assignLocalToLocal(right, leftOp);
		} else if (rightOp instanceof ArrayRef) {
			// v = v[i]
		    Local right = (Local) ((ArrayRef) rightOp).getBase();
		    out.getValue().assignFieldToLocal(methodUnderAnalysis, stmt, right, "[]", leftOp, sensitivity);
		} else if (rightOp instanceof InstanceFieldRef) {
			// v = v.f
		    Local  right  = (Local) ((InstanceFieldRef) rightOp).getBase();
		    String field =  ((InstanceFieldRef) rightOp).getField().getName();
		    out.getValue().assignFieldToLocal(methodUnderAnalysis, stmt, right, field, leftOp, sensitivity);
		} else if (rightOp instanceof StaticFieldRef) {
			// v = C.f
			out.getValue().localIsUnknown(leftOp);
		} else if (rightOp instanceof Constant) {
			if (rightOp instanceof NullConstant) {
				// v = null
				out.getValue().assignNullToLocal(leftOp);
			}
			// v = cst
		    // do nothing...
		} else if (rightOp instanceof AnyNewExpr)  {
			// v = new / newarray / newmultiarray
			out.getValue().assignNewToLocal(methodUnderAnalysis, stmt, leftOp, sensitivity);
		} else if (rightOp instanceof BinopExpr || rightOp instanceof UnopExpr || rightOp instanceof InstanceOfExpr) {
			// v = binary or unary operator
		    // do nothing...
			log.debug(" | | |- Ignoring assign binary operation");
		} else {
			throw new RuntimeException("AssignStmt match failure (rightOp) " + rightOp);
		}
	}

	@Override
	protected void caseAssignSomethingToStaticField(StaticFieldRef leftOp, Value rightOp) {
		String field = leftOp.getField().getName();

		if (rightOp instanceof Local) {
			// C.f = v
			Local right = (Local) rightOp;
			if (right.getType() instanceof RefLikeType) {
				out.getValue().assignLocalToStaticField(right, field);
			} else {
				out.getValue().mutateStaticField(field);
			}
		} else if (rightOp instanceof Constant) {
			// C.f = v
			out.getValue().mutateStaticField(field);
		} else {
			throw new Error("AssignStmt match failure (rightOp) " + rightOp);
		}
	}
}
