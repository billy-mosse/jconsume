package ar.uba.dc.binding.spec.compiler.compilation;

import java.util.HashMap;
import java.util.Map;

import ar.uba.dc.binding.spec.compiler.AbstractClassBindingProvider;
import ar.uba.dc.binding.spec.compiler.CompiledClassBindingProvider;
import ar.uba.dc.invariant.spec.compiler.compilation.DefaultMethodInvariantAndBindingProvider;

public class DefaultClassBindingProvider extends AbstractClassBindingProvider implements CompiledClassBindingProvider {

	
	
	private Map<String, DefaultMethodBindingProvider> providers = new HashMap<String, DefaultMethodBindingProvider>(); // signature -> provider
	
	
	
	public DefaultClassBindingProvider(String forClass) {
		super(forClass);
		// TODO Auto-generated constructor stub
	}
	
	
	public DefaultMethodBindingProvider get(String signature) {
		return providers.get(signature);
	}
	
	public void put(String signature, DefaultMethodBindingProvider methodProvider) {
		providers.put(signature, methodProvider);
	}

}
