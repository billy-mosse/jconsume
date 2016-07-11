package ar.uba.dc.analysis.common.code;

import soot.SootMethod;


public interface CallStatement extends Statement {

	/**
	 * Es el metodo al cual se invoco. Puede ser un metodo de una interfaz
	 */
	public SootMethod getCalledMethod();

	/**
	 * Es la implementacion del metodo invocado. En caso de polimorfismo puede diferir de {@link CallStatement#getCalledMethod()}
	 */
	public SootMethod getCalledImplementation();

	public CallStatement virtualInvoke(SootMethod callee);

}
