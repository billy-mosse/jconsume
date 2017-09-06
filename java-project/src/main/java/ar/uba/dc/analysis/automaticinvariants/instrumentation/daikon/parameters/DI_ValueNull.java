/*
 * Created on Jan 11, 2005
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
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Value;
import soot.IntType;
import soot.jimple.IntConstant;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_ValueNull extends DI_Value {

	public static DIParameter nullLocal = DIParameterFactory.createDIParameter("nullLocal",IntType.v());
	@Override
	public List codeForVar() {
		List code = new Vector();
		code.addAll(Utils.codeForAssig(IntConstant.v(0),nullLocal.getLocal()));
		return new Vector();
	}
	
}
