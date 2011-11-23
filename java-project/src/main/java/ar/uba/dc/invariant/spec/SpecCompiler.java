package ar.uba.dc.invariant.spec;

import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.compiler.CompiledClassInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.exceptions.CompileException;

/**
 * Compila una especificacion de invariantes retornando una estructura
 * capaz de responder a la peticion de un invariante para un metodo de una clase.
 * 
 * @author testis
 */
public interface SpecCompiler {

	public CompiledClassInvariantProvider compile(ClassSpecification classSpec) throws CompileException;
	
}
