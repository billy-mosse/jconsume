package ar.uba.dc.util.location.method;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.util.location.MethodLocationStrategy;

import soot.SootClass;

/**
 * Clase abstracta que posee algunos metodos utiles para retornar la ruta asociada a una clase
 * 
 * @author testis
 */
public abstract class AbstractMethodLocationStrategy implements MethodLocationStrategy {

	protected String basePath;
	
	protected String extension;
	
	protected String getBasePath() {
		return basePath;
	}
	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
		
		if (this.basePath == null) {
			this.basePath = StringUtils.EMPTY;
		} else {
			this.basePath = this.basePath.trim();
		}
		
		if (!StringUtils.isBlank(this.basePath) && !this.basePath.endsWith("/")) {
			this.basePath = this.basePath + "/";
		}
	}
	
	protected String processClass(SootClass clazz) {
		return getBasePath() + clazz.getName().replaceAll("\\.", "/");
	}
	
	protected String processMethodSignature(String signature) {
		return signature.replaceAll("<", "").replaceAll(">", "").replaceAll(":", "").replaceAll(" ", "_");
	}

	public void setExtension(String extension) {
		this.extension = extension;
		
		if (extension == null) {
			this.extension = StringUtils.EMPTY;
		}
	}

	protected String getExtension() {
		return extension;
	}
}
