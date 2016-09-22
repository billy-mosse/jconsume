package ar.uba.dc.binding.spec.compiler;

import ar.uba.dc.binding.spec.SpecCompiler;
import ar.uba.dc.binding.spec.compiler.compilation.DefaultClassBindingProvider;
import ar.uba.dc.binding.spec.compiler.compilation.DefaultMethodBindingProvider;
import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.compiler.compilation.DefaultClassInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.compilation.DefaultMethodInvariantAndBindingProvider;
import ar.uba.dc.invariant.spec.compiler.exceptions.CompileException;
import ar.uba.dc.invariant.spec.compiler.exceptions.DuplicateIdentifierException;

public class AbstractSpecCompiler implements SpecCompiler{

	public CompiledClassBindingProvider compile(ClassSpecification classSpec) throws CompileException
	{
		if (classSpec == null) {
			throw new IllegalArgumentException("La especificacion dada es nula");
		}
		
		DefaultClassBindingProvider classProvider = new DefaultClassBindingProvider(classSpec.getClassName());
		
		processClassBindings(classSpec);
		
		try {
			for (MethodSpecification method : classSpec.getMethods()) {
				DefaultMethodBindingProvider methodProvider = compile(method, classSpec);
				
				
				
				classProvider.put(method.getSignature(), methodProvider);
			}
		} catch (DuplicateIdentifierException e) {
			throw new DuplicateIdentifierException("Al procesar la clase [" + classSpec.getClassName() + "] se detectó al id [" + e.getDuplicatedId() + "] duplicado.", e.getDuplicatedId());
		}
		
		return classProvider;
	}

	private DefaultMethodBindingProvider compile(MethodSpecification method, ClassSpecification classSpec) {
		// TODO Auto-generated method stub
		return null;
	}

	private void processClassBindings(ClassSpecification classSpec) {
		// TODO Auto-generated method stub
		
	}
}
