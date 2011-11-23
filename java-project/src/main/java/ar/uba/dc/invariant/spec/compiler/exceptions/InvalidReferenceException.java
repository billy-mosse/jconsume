package ar.uba.dc.invariant.spec.compiler.exceptions;

import ar.uba.dc.invariant.spec.bean.InvariantSpecification;

public class InvalidReferenceException extends CompileException {

	private static final long serialVersionUID = 1L;

	protected InvariantSpecification invariant;
	
	public InvalidReferenceException(String message, InvariantSpecification inv) {
		super(message);
		this.invariant = inv;
	}

	public InvariantSpecification getInvariant() {
		return invariant;
	}
}
