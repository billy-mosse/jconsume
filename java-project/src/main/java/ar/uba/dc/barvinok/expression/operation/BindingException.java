package ar.uba.dc.barvinok.expression.operation;

import java.util.Set;
import java.util.TreeSet;

public class BindingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Set<String> parameters = new TreeSet<String>();

	public BindingException() {
	}

	public BindingException(String arg0) {
		super(arg0);
	}

	public BindingException(Throwable arg0) {
		super(arg0);
	}

	public BindingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public Set<String> getParameters() {
		return parameters;
	}

	public void setParameters(Set<String> parameters) {
		this.parameters = parameters;
	}
}
