package ar.uba.dc.invariant.spec.compiler.compilation;

import ar.uba.dc.invariant.spec.compiler.CompiledClassInvariantProvider;

public abstract class AbstractClassInvariantProvider implements CompiledClassInvariantProvider {

	protected String clazz; 
	
	public AbstractClassInvariantProvider(String forClass) {
		this.clazz = forClass;
	}
	
	public boolean forClass(String className) {
		return clazz.equals(className);
	}
}
