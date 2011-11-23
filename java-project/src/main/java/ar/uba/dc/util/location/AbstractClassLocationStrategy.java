package ar.uba.dc.util.location;

import org.apache.commons.lang.StringUtils;

/**
 * Clase abstracta que posee algunos metodos utiles para retornar la ruta asociada a una clase
 * 
 * @author testis
 */
public abstract class AbstractClassLocationStrategy implements ClassLocationStrategy {

	protected String basePath;
	
	protected String extension;
	
	public AbstractClassLocationStrategy() {
		super();
	}
	
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
