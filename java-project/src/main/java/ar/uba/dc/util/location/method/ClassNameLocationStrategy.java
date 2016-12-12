package ar.uba.dc.util.location.method;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import soot.SootMethod;

/**
 * Estrategia que asume que el archivo asociado a un metodo se encuentra en una ruta base, seguido de un dictorio llamado como 
 * el nombre completo de la clase a al que pertence (package + className) y luego el archivo se llama "firma" + extension. Donde la extension
 * es una propiedad de la estrategia.
 * 
 * @author testis
 */
public class ClassNameLocationStrategy extends AbstractMethodLocationStrategy {

	public String getLocation(SootMethod method) {
		
		return getBasePath() + method.getDeclaringClass() + "/" + processMethodSignature(method.getSubSignature()) + getExtension();
	}
	
	@Override
	public String getLocation(IntermediateRepresentationMethod ir_method) {
		return getBasePath() + ir_method.getDeclaringClass() + "/" + processMethodSignature(ir_method.getSubSignature()) + getExtension();
	}
	
	public String getLocation(String type, IntermediateRepresentationMethod ir_method, String mainClass)
	{		
		return getBasePath() + type + "/" + mainClass + "/" + ir_method.getDeclaringClass() + "/" + ir_method.processMethodSignature() + getExtension();
	}
	
	//TODO: sacar el processMethodSignature de las otras clases
	public String getXMLLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
		return getLocation("xml", ir_method, mainClass);
	}
	
	
	@Override
	public String getHumanReadableLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
		return getLocation("humanReadable", ir_method, mainClass);
	}
	
	@Override
	public String getJsonLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
		return getLocation("json", ir_method, mainClass);
	}

}
