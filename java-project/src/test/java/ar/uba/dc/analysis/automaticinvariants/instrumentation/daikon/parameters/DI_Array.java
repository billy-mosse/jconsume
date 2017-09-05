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
import soot.jimple.Jimple;
import soot.jimple.NeExpr;
import soot.jimple.NullConstant;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Array extends DI_Sizeable {

	/**
	 * @param var
	 */
	public DI_Array(Local var) {
		super(var);
	}

	/**
	 * @param vn
	 * @param type
	 */
	public DI_Array(String vn, Type type) {
		super(vn, type);
	}
	

	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		
		List code = new Vector();
		//AssignStmt ass=Jimple.v().newAssignStmt(sVar.var,Jimple.v().newLengthExpr(var));
		NeExpr cond1 = Jimple.v().newNeExpr(var,NullConstant.v());
		//IfStmt toAdd1 = Jimple.v().newIfStmt(cond1,ass);
		//AssignStmt ass0 = Jimple.v().newAssignStmt(sVar.var, IntConstant.v(0));
		//GotoStmt jump = Jimple.v().newGotoStmt(ass0);
		
		//code.add(toAdd1);
		//code.add(ass0);
		//code.add(jump);
		//code.add(ass);
		
		//jump.addTag(new AdjustGotoTag(Integer.toString(1)));
		//toAdd1.addTag(new AdjustGotoTag(Integer.toString(2)));
		return code;
	}
}
