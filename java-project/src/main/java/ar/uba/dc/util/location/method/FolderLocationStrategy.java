package ar.uba.dc.util.location.method;

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
}
