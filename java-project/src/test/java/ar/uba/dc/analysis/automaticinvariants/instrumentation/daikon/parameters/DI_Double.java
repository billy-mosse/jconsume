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
import soot.DoubleType;
import soot.Local;
import soot.SootMethod;
import soot.jimple.AssignStmt;
import soot.jimple.Jimple;
import soot.jimple.StaticInvokeExpr;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Double extends DIParameter {
	DI_Int dVar;
	DI_Double(Local var)
	{
		super(var);
		String vn=var.getName();
		
		if(!vn.startsWith("_f_"))
			vn = "d_"+vn;
		else 
			vn = "d"+vn;
		dVar = new DI_Int(vn);
		this.derivedVars.add(dVar);
		
	}
	DI_Double(String vn)
	{
		super(vn, DoubleType.v());
		if(!vn.startsWith("_f_"))
			vn = "d_"+vn;
		else 
			vn = "d"+vn;
		dVar = new DI_Int(vn);
		this.derivedVars.add(dVar);
	}
	
	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		List code = new Vector();
		SootMethod toCall = Utils.varTestClass.getMethod("int toInt(double)");
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
		AssignStmt ass = Jimple.v().newAssignStmt(dVar.var, invExpr);
		code.add(ass);
		return code;
	}
}
