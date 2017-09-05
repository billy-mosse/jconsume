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
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Int;
import soot.Local;
import soot.Type;
import soot.Value;
import soot.jimple.IntConstant;
import soot.jimple.internal.JAddExpr;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Iterator extends DIParameter {

	DI_Int cont;
	/**
	 * 
	 */
	public DI_Iterator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param var
	 */
	public DI_Iterator(Local var) {
		super(var);
		String vn=var.getName();
		
		if(!vn.startsWith("_f_"))
			vn = "cont_"+vn;
		else 
			vn = "cont"+vn;
		cont  = new DI_Int(vn);
		this.derivedVars.add(cont);
	}

	/**
	 * @param vn
	 * @param type
	 */
	public DI_Iterator(String vn, Type type) {
		super(vn,type);
		if(!vn.startsWith("_f_"))
			vn = "cont_"+vn;
		else 
			vn = "cont"+vn;
		cont  = new DI_Int(vn);
		this.derivedVars.add(cont);
	}
	

	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		List code = new Vector();
		code.addAll(Utils.codeForAssig(IntConstant.v(0),cont.getLocal()));
		return code;
	}
	
	public List codeForIncrement()
	{
		Vector code = new Vector();
		code.addAll(Utils.codeForAssig(new JAddExpr(cont.getLocal(),IntConstant.v(1)),cont.getLocal()));
		return code;
	}
	public List codeForAssign(Value v) {
		List code = new Vector();
		code.addAll(Utils.codeForAssig(v,cont.getLocal()));
		return code;
	}
	public DI_Int getContIt()
	{
		return cont;
	}
}
