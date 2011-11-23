package ar.uba.dc.util.location;

import soot.SootClass;

public class FullPackageJarLocationStrategy extends AbstractClassLocationStrategy {

	protected String specJarPah;
	
	
	
	public String getSpecJarPah() {
		return specJarPah;
	}



	public void setSpecJarPah(String specJarPah) {
		this.specJarPah = specJarPah;
	}



	@Override
	public String getLocation(SootClass clazz) {
		return "jar:" +  this.specJarPah + ":" +  this.basePath + clazz.getName().replaceAll("\\.", "/") + getExtension();
	}	

}
