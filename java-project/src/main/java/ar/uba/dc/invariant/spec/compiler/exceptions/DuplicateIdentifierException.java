package ar.uba.dc.invariant.spec.compiler.exceptions;

public class DuplicateIdentifierException extends CompileException {

	private static final long serialVersionUID = 1L;

	private String duplicatedId;
	
	public DuplicateIdentifierException(String message, String id) {
		super(message);
		this.duplicatedId = id;
	}

	public String getDuplicatedId() {
		return duplicatedId;
	}
}
