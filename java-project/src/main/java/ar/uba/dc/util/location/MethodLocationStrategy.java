package ar.uba.dc.util.location;

import soot.SootMethod;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;


/**
 * Estrategia de busqueda de un archivo por metodo. 
 * 
 * Esta intefaz permite determinar donde se encuentra en el file system un archivo 
 * asociado un {@link IntermediateRepresentationMethod}.
 * 
 * El contenido del archivo dependera de lo que se este buscando.
 * 
 * @author testis
 */
public interface MethodLocationStrategy {

	/** Extension de los archivos spec (especificacion de invariantes) */
	public String SPEC_EXTENSION = ".spec";
	
	/** Extension de los archivos XML. Estos puden ser los resumenes de un analisis serializados */
	public String XML_EXTENSION = ".xml";
	
	/** Extension de los archivos dot requeridos para generar los graficos de los resultados de los analisis */
	public String DOT_EXTENSION = ".dot";
	
	/**
	 * Dado un metodo retorna la ruta dentro del file system donde se encuentra el archivo asociado al mismo.
	 * El contenido del archivo dependera de lo que se este buscando.
	 */
	public String getLocation(SootMethod method);
	
	public String getLocation(IntermediateRepresentationMethod method);

	//public String getIRLocation(SootMethod method);
	public String getXMLLocation(IntermediateRepresentationMethod method, String mainClass);
	
	public String getBasePath();

	public String getHumanReadableLocation(IntermediateRepresentationMethod ir_method, String mainClass);

	public String getJsonIRLocation(IntermediateRepresentationMethod ir_method, String mainClass);
	
}
