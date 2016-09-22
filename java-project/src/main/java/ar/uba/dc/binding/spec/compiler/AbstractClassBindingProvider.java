package ar.uba.dc.binding.spec.compiler;


public abstract class AbstractClassBindingProvider implements CompiledClassBindingProvider{

	protected String clazz; 
	
	public AbstractClassBindingProvider(String forClass) {
		this.clazz = forClass;
	}
	
	public boolean forClass(String className) {
		return clazz.equals(className);
	}

}
