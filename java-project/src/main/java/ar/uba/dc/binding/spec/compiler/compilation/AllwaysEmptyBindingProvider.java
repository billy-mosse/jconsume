package ar.uba.dc.binding.spec.compiler.compilation;

import ar.uba.dc.binding.spec.compiler.AbstractClassBindingProvider;
import ar.uba.dc.binding.spec.compiler.CompiledClassBindingProvider;

public class AllwaysEmptyBindingProvider extends AbstractClassBindingProvider implements CompiledClassBindingProvider  {
	public AllwaysEmptyBindingProvider(String forClass) {
		super(forClass);
	}
}
