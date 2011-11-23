package ar.uba.dc.invariant.spec.compiler.exceptions;

public class CompileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CompileException() {
		super("Ocurrió un error compilando la especificacion");
	}
	
	public CompileException(String message) {
		super(message);
	}
}
