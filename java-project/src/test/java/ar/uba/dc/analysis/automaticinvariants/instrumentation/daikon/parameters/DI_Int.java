/*
 * Created on 05/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;



import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import soot.IntType;
import soot.Local;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Int extends DIParameter {

	DI_Int(String name)
	{
		super(name, IntType.v());
	}
	DI_Int(Local var)
	{
		super(var);
	}
}
