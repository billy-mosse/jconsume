package ar.uba.dc.config;

/**
 * Interfaz que define un contenedor de objetos
 * @author testis
 *
 * @param <T> Tipo de los objetos contenidos
 */
public interface Container<T> {

	/**
	 * Registra un objeto en el contenedor. Un objeto puede estar varias veces en el contenedor
	 */
	public void register(T o);
	
	/**
	 * Elimina todas las apariciones del objeto objeto del contenedor
	 */
	public void unregister(T o);
}
