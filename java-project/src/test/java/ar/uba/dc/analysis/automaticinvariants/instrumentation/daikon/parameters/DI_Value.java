/*
 * Created on Jan 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameterFactory;
import soot.Local;
import soot.Type;
import soot.Value;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Value extends DIParameter {

	/**
	 * 
	 */
	private static int auxVarCount = 0;
	
	Value v;
	DIParameter varAux;
	boolean isValue = true;
	boolean isNull = false;
	public DI_Value() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param var
	 */
	public DI_Value(Local var) {
		super(var);
		v = var;
		varAux= this;
		this.derivedVars = varAux.derivedVars;
		isValue = false;
	}
	public DI_Value(Value v) {
		this.v = v;
		System.out.println("Lis:"+v+" "+v.getType());
		varAux = DIParameterFactory.createDIParameter(genVarAuxName(),v.getType());
		if(varAux!=null)
		{
			var = varAux.getLocal();
			this.derivedVars = varAux.derivedVars;
		}
		else
		{/*
			varAux = DIParameterFactory.createDIParameter(genVarAuxName()+"_null",IntType.v());
			var = varAux.getLocal();
			this.derivedVars = varAux.derivedVars;
			isNull = true;
		  */
		}
	}

	/**
	 * @param vn
	 * @param type
	 */
	public DI_Value(String vn, Type type) {
		varAux = DIParameterFactory.createDIParameter(genVarAuxName()+v,v.getType());
		// varAux = this;
		v = varAux.getLocal();

		this.derivedVars = varAux.derivedVars;
	}
	
	/*
	public String getName()
	{
		return nameVar;
	}
	*/

	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#getLocal()
	 */
	@Override
	public Local getLocal() {
		return super.getLocal();
	}
	
	private String genVarAuxName()
	{
		String sn = Integer.toString(auxVarCount);
		auxVarCount++;
		return "aux_"+sn;
	}
	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		List code = new Vector();
		// if(!isNull())
		{
			if(isValue)
				code.addAll(Utils.codeForAssig(v,varAux.getLocal()));
			code.addAll(varAux.codeForVar());
		}
		return code;
	}
	/**
	 * @return Returns the isNull.
	 */
	public boolean isNull() {
		return isNull;
	}
}
