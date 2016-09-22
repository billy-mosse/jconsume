package ar.uba.dc.invariant.spec.compiler;

import java.util.TreeSet;

import ar.uba.dc.invariant.spec.bean.InvariantSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.exceptions.UnknownInvariantException;

/**
 * Este compilador a diferencia del {@link FastSpecCompiler} permite la existencia
 * de referencias dentro de los sites. Las unicas referencias permitidas son a invariantes. Los invariantes no tienen permitido
 * referenciar a nada. Esto será verificado por el compilador.  
 * 
 * El compilador valida unicidad de IDs dentro de los invariantes pero no verifica si los sites tiene IDs
 * 
 * Al igual que {@link FastSpecCompiler}, el compilador soporta rangos en los offsets de los sites.
 * 
 * @author testis
 */
public class SimpleReferencesSpecCompiler extends AbstractReferencesSpecCompiler {
	
	public SimpleReferencesSpecCompiler() {
		super(false);
	}
	
	@Override
	protected void toCanonicalForm(MethodSpecification method) {
		Integer index = 0;
		for (SiteSpecification siteSpec : method.getSitesSpecification()) {
			ConstraintsInfo info = sitesConstraintsInfo.get(siteSpec);
			
			for (String ref : new TreeSet<String>(info.getReferences())) {
				if (!classInvariants.containsKey(ref) && !methodInvariants.containsKey(ref)) {
					throw new UnknownInvariantException(method.getSignature(), index, ref);
				}
				
				// Referencia a un invariante de clase?
				InvariantSpecification refSpec = classInvariants.get(ref);
				if (refSpec != null) {
					ConstraintsInfo refInfo = classInvariantInfo.get(refSpec);
					// Sumamos las variables que hay en las constraints como parametros del metodo
					method.addAllParameters(refInfo.getVariables());
				} else {
					// referencia a un invariante de metodo
					refSpec = methodInvariants.get(ref);
					ConstraintsInfo refInfo = methodInvariantInfo.get(refSpec);
					// Agregamos las variables como variables del site
					info.addAllVariables(refInfo.getVariables());
				}
				
				// Eliminamos la referencia
				info.removeReference(ref);
				
				siteSpec.setConstraints(resolver.resolve(siteSpec.getConstraints(), ref, refSpec.getConstraints()));
			}
			index++;
		}
	}
}
