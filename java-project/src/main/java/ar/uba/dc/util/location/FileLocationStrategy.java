package ar.uba.dc.util.location;

import soot.SootClass;
import soot.SootMethod;

/**
 * Los invariantes estan en un unico archivo. Indicamos cual es el archivo
 * @author testis
 */
public class FileLocationStrategy implements ClassLocationStrategy {

	private String repositoryFile = null;
	
	public FileLocationStrategy() {
		super();
	}

	public String getLocation(SootClass clazz) {
		return repositoryFile;
	}
	
	public String getLocation(SootMethod method) {
		return repositoryFile;
	}

	public void setRepositoryFile(String repositoryFile) {
		this.repositoryFile = repositoryFile;
	}

	public String getRepositoryFile() {
		return repositoryFile;
	}
}
