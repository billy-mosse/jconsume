package ar.uba.dc.invariant.spec.compiler.exceptions;


public class UnknownInvariantException extends CompileException {

	private static final long serialVersionUID = 1L;

	public UnknownInvariantException(String methodName, Integer index, String id) {
		super("El site numero [" + index + "] del metodo [" + methodName + "] hace referencia a un invariante desconocido [" + id + "].");
	}
	
	public UnknownInvariantException(String refInvariant, String id) {
		super("El invariante [" + refInvariant + "] hace referencia a un invariante desconocido [" + id + "]. Recuerde que los invariantes de clase sólo pueden referenciar a otros invariantes de clase");
	}
}
