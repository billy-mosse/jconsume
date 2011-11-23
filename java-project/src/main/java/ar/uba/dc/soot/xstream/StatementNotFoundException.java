package ar.uba.dc.soot.xstream;

import com.thoughtworks.xstream.converters.ConversionException;

import soot.SootMethod;

public class StatementNotFoundException extends ConversionException {

	private static final long serialVersionUID = 1L;
	private String sootClass;
	private String sootMethod;
	private String statement;
	private Integer javaLineNumber;
	private Integer bytecodeOffset;

	public StatementNotFoundException(SootMethod method, String statement, Integer javaLineNumber, Integer bytecodeOffset) {
		super("El código ha cambiado desde que el summary fue serializado. No se pudo encontrar el statement [" + statement + "] del metodo [" + method + "] cuya linea de java es [" + javaLineNumber + "] y su bytecode-offset es [" + bytecodeOffset + "]");
		this.sootClass = method.getDeclaringClass().getName();
		this.sootMethod = method.getSubSignature();
		this.statement = statement;
		this.javaLineNumber = javaLineNumber;
		this.bytecodeOffset = bytecodeOffset;
	}

	public String getSootClass() {
		return sootClass;
	}

	public String getSootMethod() {
		return sootMethod;
	}

	public String getStatement() {
		return statement;
	}

	public Integer getJavaLineNumber() {
		return javaLineNumber;
	}

	public Integer getBytecodeOffset() {
		return bytecodeOffset;
	}
}
