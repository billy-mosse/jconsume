package ar.uba.dc.invariant.spec.compiler.exceptions;

public class UnknownParameterException extends CompileException {

	private static final long serialVersionUID = 1L;

	private String parameterName;
	
	public UnknownParameterException(String className, String methodName, String constraint, String parameterName) {
		super("El metodo [" + methodName + "] de la clase [" + className + "] contiene entre sus requierimientos [" + constraint + "] un parametro relevante desconocido [" + parameterName + "]. Requierde especificar todos los parametros relevantes, incluso aquellos referenciados en los requerimientos");
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}
}
