package ar.uba.dc.util.location.method;

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
}
