package ar.uba.dc.util.location;

import soot.SootClass;

/**
 * Interfaz que define un contrato para obtener la ruta del archivo asociado a una class.
 * 
 * @author testis
 */
public interface ClassLocationStrategy {

	/** Extension de los archivos spec (especificacion de invariantes) */
	public String SPEC_EXTENSION = ".spec";
	
	/** Extension de los archivos XML. Estos puden ser los resumenes de un analisis serializados */
	public String XML_EXTENSION = ".xml";
	
	/** Extension de los archivos dot requeridos para generar los graficos de los resultados de los analisis */
	public String DOT_EXTENSION = ".dot";
	
	/**
	 * Dado una clase retorna la ruta dentro del file system donde se encuentra el archivo asociado al mismo.
	 * El contenido del archivo dependera de lo que se este buscando.
	 */
	public String getLocation(SootClass clazz);	
}
