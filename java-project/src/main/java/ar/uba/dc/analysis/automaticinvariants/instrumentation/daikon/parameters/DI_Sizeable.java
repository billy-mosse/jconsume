/*
 * Created on 05/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import soot.Local;
import soot.Type;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Sizeable extends DIParameter {

	/**
	 * @param var
	 */
	DI_Int sVar;
	public DI_Sizeable(Local var) {
		super(var);
		String vn=var.getName();
		
		/*if(!vn.startsWith("_f_"))
			vn =  "size_"+vn;
		else
			vn =  "size"+vn;
		sVar = new DI_Int(vn);
		this.derivedVarsForSpec.add(sVar);*/
		
		//if(!vn.startsWith("_f_"))
		//	vn =  "size_"+vn;
		//else
		//	vn =  "size"+vn;
		//sVar = new DI_Int(vn);
		//this.derivedVars.add(sVar);
		
	}
	
	public DI_Sizeable(Local var, boolean _) {
		super(var);
	}

	/**
	 * @param varName
	 * @param type
	 */
	public DI_Sizeable(String vn, Type type) {
		super(vn, type);
		//if(!vn.startsWith("_f_"))
		//	vn =  "size_"+vn;
		//else
		//	vn =  "size"+vn;
		//sVar = new DI_Int(vn);
		//this.derivedVars.add(sVar);
		
		/*if(!vn.startsWith("_f_"))
			vn =  "size_"+vn;
		else
			vn =  "size"+vn;
		sVar = new DI_Int(vn);
		this.derivedVarsForSpec.add(sVar);*/
	}

}
