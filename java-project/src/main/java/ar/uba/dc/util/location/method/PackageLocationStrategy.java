package ar.uba.dc.util.location.method;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
import soot.SootMethod;

/**
 * Estrategia que determina que el archivo asociado al metodo se encuentra dentro de un directorio
 * formado por una ruta base, el package de la clase a la que pertenece el metodo y el nombre de la clase.
 * Luego el archivo es la firma del metodo y la extension indicada como propiedad de la estrategia.
 * 
 * @author testis
 */
public class PackageLocationStrategy extends AbstractMethodLocationStrategy {

	public String getLocation(SootMethod method) {
		return processClass(method.getDeclaringClass()) + "/" + processMethodSignature(method.getSubSignature()) + getExtension();
	}
	
	@Override
	public String getLocation(IntermediateRepresentationMethod method) {
		throw new java.lang.UnsupportedOperationException();
	}
	
	
	@Override
	public String getXMLLocation(IntermediateRepresentationMethod method, String mainClass) {
		throw new java.lang.UnsupportedOperationException();

	}
	
	@Override
	public String getHumanReadableLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
		throw new java.lang.UnsupportedOperationException();
	}
	
	@Override
	public String getJsonIRLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getJsonIRLocation(
			AnnotationSiteInvariantForJson siteInvariant, String mainClass) {
		// TODO Auto-generated method stub
		return null;
	}
}
