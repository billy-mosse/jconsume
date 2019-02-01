package ar.uba.dc.util.location.method;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
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
		
		return getBasePath() + method.getDeclaringClass() + "/" + processMethodSignature(method.getDeclaration()) + getExtension();
	}
	
	@Override
	public String getLocation(IntermediateRepresentationMethod ir_method) {
		return getBasePath() + ir_method.getDeclaringClass() + "/" + processMethodSignature(ir_method.getDeclaration()) + getExtension();
	}
	
	public String getLocation(String type, IntermediateRepresentationMethod ir_method, String mainClass)
	{		
		return getBasePath() + type + "/" + mainClass + "/" + processMethodSignature(ir_method.getDeclaringClass() + "/" + ir_method.getMethodSignature() + getExtension());
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
	public String getJsonIRLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
		return getLocation("json", ir_method, mainClass);
	}

	@Override
	public String getJsonIRLocation(
			AnnotationSiteInvariantForJson siteInvariant, String mainClass) {
		//TODO method name es un nombre incorrecto
		
		//Aca habia un typo, decia siteInvatiants.
		return getBasePath() +  "/siteInvariants/" + siteInvariant.getMethodName();
	}

	@Override
	public String getEscapeAnnotationsLocation(String mainClass) {
		// TODO cual es el base path?
		return getBasePath() +  mainClass + ".json";
	}

}
