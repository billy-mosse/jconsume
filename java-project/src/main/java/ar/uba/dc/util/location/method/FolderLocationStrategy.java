package ar.uba.dc.util.location.method;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import soot.SootMethod;

/**
 * Estrategia que dicta que el archivo asociado al metodo se encuentra en una ruta base.
 * Luego el nombre del archivo es la firma del metodo y la extension especificada como propiedad de la estrategia
 *
 * @author testis
 */
public class FolderLocationStrategy extends AbstractMethodLocationStrategy {

	public String getLocation(SootMethod method) {
		return getBasePath() + processMethodSignature(method.getSignature()) + getExtension();
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
	public String getJsonLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
		throw new java.lang.UnsupportedOperationException();
	}
}
