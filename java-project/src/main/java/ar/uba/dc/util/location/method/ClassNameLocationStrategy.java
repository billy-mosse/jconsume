package ar.uba.dc.util.location.method;

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
}
