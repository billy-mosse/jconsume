package ar.uba.dc.util;

/**
 * Excepcion que puede producirse al ejecutar un comando por consola
 * 
 * @author testis
 */
public class ConsoleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConsoleException() {
		super();
	}

	public ConsoleException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ConsoleException(String arg0) {
		super(arg0);
	}

	public ConsoleException(Throwable arg0) {
		super(arg0);
	}
}
