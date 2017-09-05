/*
 * Created on 06/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Sizeable;
import soot.Local;
import soot.Type;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_String extends DI_Sizeable {

	/**
	 * @param var
	 */
	public DI_String(Local var) {
		super(var);
	}

	/**
	 * @param vn
	 * @param type
	 */
	public DI_String(String vn, Type type) {
		super(vn, type);
	}

	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		List code = new Vector();
		//SootMethod toCall = Utils.varTestClass.getMethod("int sizeString(java.lang.String)");
		
		//StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
		
		//AssignStmt ass = Jimple.v().newAssignStmt(sVar.var, invExpr);
		//code.add(ass);
		return code;
	}
}
