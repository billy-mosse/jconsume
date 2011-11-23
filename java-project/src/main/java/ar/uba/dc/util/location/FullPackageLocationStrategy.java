package ar.uba.dc.util.location;

import soot.SootClass;

/**
 * Estrategia que determina que la ruta asociada a una clase esta compuesta por una ruta base, luego
 * una subruta compuesta del package de la clase y finalmente el archivo es el nombre de la clase y una extension.
 * 
 * @author testis
 */
public class FullPackageLocationStrategy extends AbstractClassLocationStrategy {

	public FullPackageLocationStrategy() {
		super();
	}

	public String getLocation(SootClass clazz) {
		return getBasePath() + clazz.getName().replaceAll("\\.", "/") + getExtension();
	}	
}
