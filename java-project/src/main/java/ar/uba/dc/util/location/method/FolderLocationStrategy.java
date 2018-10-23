package ar.uba.dc.util.location.method;

import java.awt.RenderingHints.Key;

import ar.uba.dc.analysis.common.intermediate_representation.IRUtils;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
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
	
	public String getLocation(IntermediateRepresentationMethod ir_method) {		

		return getBasePath() + IRUtils.key(ir_method) + getExtension();
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
		throw new java.lang.UnsupportedOperationException();
	}
}
