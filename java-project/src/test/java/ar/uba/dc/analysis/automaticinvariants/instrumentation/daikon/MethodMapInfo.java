/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MethodMapInfo {
	
	ListDIParameters params;
	ListDIParameters paramsInit;
	String nameClass;
	
	/**
	 * @param params
	 * @param paramsInit
	 */
	public MethodMapInfo(ListDIParameters params, ListDIParameters paramsInit, String nameClass) {
		super();
		this.params = params;
		this.paramsInit = paramsInit;
		this.nameClass = nameClass;
	}
	
	/**
	 * @return Returns the params.
	 */
	public ListDIParameters getParams() {
		return params;
	}
	/**
	 * @return Returns the paramsInit.
	 */
	public ListDIParameters getParamsInit() {
		return paramsInit;
	}
}
