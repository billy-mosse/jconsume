package ar.uba.dc.invariant.spec.compiler.exceptions;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CyclicDependenciesException extends CompileException {

	private static final long serialVersionUID = 1L;

	protected List<String> cycle;
	
	public CyclicDependenciesException(String method, List<String> cycle) {
		super("Los invariantes referenciados en algun call-site y/o creation-site del metodo [" + method + "] generan el ciclo [" + StringUtils.join(cycle, " -> ") + "]");
		this.cycle = cycle;
	}

	public List<String> getCycle() {
		return cycle;
	}
}
