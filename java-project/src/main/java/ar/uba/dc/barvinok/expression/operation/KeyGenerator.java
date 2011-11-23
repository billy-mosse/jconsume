package ar.uba.dc.barvinok.expression.operation;

import java.util.Map;

/**
 * Generador de claves para el mapper. Usa variables en una secuencia
 * 
 * @author testis
 */
public interface KeyGenerator {
	
	/**
	 * Da la clave más alta usada para un mapping
	 */
	public String getInitialKey(Map<String, String> mapping);
	
	/**
	 * Devuelve la proxima clave en la secuencia de claves. 
	 */
	public String getNextKey(String actualKey);
}
