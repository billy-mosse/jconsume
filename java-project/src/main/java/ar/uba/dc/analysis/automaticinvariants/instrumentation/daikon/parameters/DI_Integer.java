package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Sizeable;
import soot.Local;
import soot.Type;

public class DI_Integer extends DI_Sizeable{
	/**
	 * @param var
	 */
	public DI_Integer(Local var) {
		super(var);
	}

	/**
	 * @param vn
	 * @param type
	 */
	public DI_Integer(String vn, Type type) {
		super(vn, type);
	}

	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		List code = new Vector();
		//SootMethod toCall = Utils.varTestClass.getMethod("int valueInteger(java.lang.Integer)");
		//StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
		
		//AssignStmt ass = Jimple.v().newAssignStmt(sVar.var, invExpr);
		//code.add(ass);
		return code;
	}
}
