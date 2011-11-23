package ar.uba.dc.invariant.spec.compiler;

import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;

/**
 * Este compilador de expresiones asume que los creation-sites y call-sites que
 * existen en la especificacion no possen referencias. De esta forma, lo unico que 
 * hace es "expandir" los sites (podrian tener offsets que indiquen rangos) y hacer la union 
 * de los invariantes que caigan en un mismo offset (por si varios sites predican sobre el mismo offset).
 * 
 * Al ignorar los invariantes, no hace ninguna validacion de IDs duplicados ni ciclos
 * 
 * @author testis
 */
public class FastSpecCompiler extends AbstractSpecCompiler {

	public FastSpecCompiler() {
		super();
	}

	@Override
	protected void processClassInvariants(ClassSpecification classSpac) {
		// No debemos hacer nada. Asumimos que no hay invariantes
	}

	@Override
	protected void processInvariants(MethodSpecification methodSpec) {
		// No debemos hacer nada. Asumimos que no hay invariantes
	}

	@Override
	protected void toCanonicalForm(MethodSpecification methodSpec) {
		// No debemos hacer nada. Asumimos forma canonica
	}
}
