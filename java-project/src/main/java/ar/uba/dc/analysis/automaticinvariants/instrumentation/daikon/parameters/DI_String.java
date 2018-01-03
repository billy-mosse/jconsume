/*
 * Created on 06/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Sizeable;
import soot.Local;
import soot.Type;


import soot.Local;
import soot.SootMethod;
import soot.Type;
import soot.jimple.AssignStmt;
import soot.jimple.Jimple;
import soot.jimple.StaticInvokeExpr;

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
		
		String varName = var.getName();
		if(!varName.startsWith("_f_"))
			varName =  varName + "__f__toString__f__length";
		else
			varName =  "size"+varName;
		sVar = new DI_Int(varName);
		this.derivedVarsForSpec.add(sVar);
	}

	/**
	 * @param vn
	 * @param type
	 */
	public DI_String(String vn, Type type) {
		super(vn, type);
		
		if(!vn.startsWith("_f_"))
			vn =  vn + "__f__toString__f__length";
		else
			vn =  "size"+vn;
		sVar = new DI_Int(vn);
		this.derivedVarsForSpec.add(sVar);
	}

	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		List code = new Vector();
		/*SootMethod toCall = Utils.varTestClass.getMethod("int sizeString(java.lang.String)");
		
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
		
		AssignStmt ass = Jimple.v().newAssignStmt(sVar.var, invExpr);
		code.add(ass);*/
		return code;
	}
}
