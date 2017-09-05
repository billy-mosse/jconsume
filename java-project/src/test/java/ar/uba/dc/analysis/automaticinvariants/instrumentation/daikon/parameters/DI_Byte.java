/*
 * Created on 05/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Int;
import soot.ByteType;
import soot.Local;
import soot.SootMethod;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Byte extends DIParameter {

	/**
	 * @param var
	 */
	DI_Int lVar;
	public DI_Byte(Local var) {
		super(var);
		String vn=var.getName();
		if(!vn.startsWith("_f_"))
			vn = "b_"+vn;
		else 
			vn = "b"+vn;
		lVar = new DI_Int(vn);
		this.derivedVars.add(lVar);
	}

	/**
	 * @param varName
	 * @param type
	 */
	public DI_Byte(String vn) {
		super(vn, ByteType.v());
		if(!vn.startsWith("_f_"))
			vn = "b_"+vn;
		else 
			vn = "b"+vn;
		lVar = new DI_Int(vn);
		this.derivedVars.add(lVar);
	}
	@Override
	public List codeForVar() {
		List code = new Vector();
		SootMethod toCall = Utils.varTestClass.getMethod("int toInt(byte)");
		//StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
		//AssignStmt ass = Jimple.v().newAssignStmt(lVar.var, invExpr);
		//code.add(ass);
		return code;
	}

}
