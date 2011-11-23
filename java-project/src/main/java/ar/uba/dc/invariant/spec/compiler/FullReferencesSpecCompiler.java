package ar.uba.dc.invariant.spec.compiler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.compiler.compilation.fullreferences.ClassInvariantScope;
import ar.uba.dc.invariant.spec.compiler.compilation.fullreferences.InvariantAdapter;
import ar.uba.dc.invariant.spec.compiler.compilation.fullreferences.MethodInvariantScope;
import ar.uba.dc.invariant.spec.compiler.compilation.fullreferences.SiteInvariantScope;
import ar.uba.dc.invariant.spec.compiler.exceptions.CyclicDependenciesException;
import ar.uba.dc.invariant.spec.compiler.exceptions.DuplicateIdentifierException;
import ar.uba.dc.invariant.spec.compiler.exceptions.UnknownInvariantException;

/**
 * Este compilador es el más potente de los desarrollados pues soporta referencias transitivas y valida ciclos.
 * 
 * También soporta rangos en los offsets de los sites así como invaraintes que refieran a otros invariantes.
 * 
 * En otras palabras, cubre todos los aspectos que {@link SimpleReferencesSpecCompiler} no cubre.
 * 
 * Además, chequea que no se produzcan ciclos de referencias partiendo de cualquier site en la especificacion.
 * Esto es importante porque si el ciclo se forma sólamente entre invariantes, no será detectado a menos que 
 * algun site haga referencia a algún invariante del ciclo.
 *
 * @author testis
 */
public class FullReferencesSpecCompiler extends AbstractReferencesSpecCompiler {

	// id de call-site/creation-site -> call-site/creation-site. Esto es util para poder recuperar un site que fue referenciado
	// desde otro site
	protected Map<String, SiteSpecification> siteInvariants = new HashMap<String, SiteSpecification>(); 
	
	public FullReferencesSpecCompiler() {
		super(true);
	}
	
	@Override
	protected void toCanonicalForm(MethodSpecification methodSpec) {
			// Completamos el map de siteInvariants para usarlo despues para resolver las referencias.
			// De paso validamos que no hayan ids duplicados
		checkConsistency(methodSpec.getSitesSpecification(), siteInvariants);

		ClassInvariantScope classScope = new ClassInvariantScope(classInvariants, classInvariantInfo);
		MethodInvariantScope methodScope = new MethodInvariantScope(methodInvariants, methodInvariantInfo, classScope);
		SiteInvariantScope siteScope = new SiteInvariantScope(siteInvariants, sitesInfo, methodScope);
		
		for (SiteSpecification site : methodSpec.getSitesSpecification()) {
				// Pila de invariantes que faltan procesar para terminar de llevar el site a forma canonica
			Stack<InvariantAdapter> toProcess = new Stack<InvariantAdapter>();
			
			toProcess.push(InvariantAdapter.adapt(site, sitesInfo.get(site), siteScope));
			
			while (!toProcess.isEmpty()) {
				InvariantAdapter inv = toProcess.pop();
				
				Boolean haveToRepushed = true; // Si encuentro referencias, tengo que volver a poner el inv en la pila a procesar?
				for (String refId : new TreeSet<String>(inv.getReferences())) {
						// Busco el invariante al que estoy haciendo referencia
					InvariantAdapter ref = inv.getScope().find(refId);

					if (ref == null) {
						throw new UnknownInvariantException(inv.getId(), refId);
					}
					
						// Si este invariante refiere a otras cosas, hay que procesarlo
					if (!ref.getReferences().isEmpty()) {
							// Si todavia no volvi a poner al invariante que estaba procesando
							// hay que ponerlo en la pila porque necesito procesarlo cuando termino
							// de resolver las otras referencias
						if (haveToRepushed) {
							toProcess.push(inv);
							haveToRepushed = false;
						}
						
							// Hay ciclos
						if (toProcess.contains(ref)) {
							throw new CyclicDependenciesException(methodSpec.getSignature(), retriveCycle(ref, toProcess));
						}
						toProcess.push(ref);
					} else {
						ref.replaceIn(inv, methodSpec, resolver);
						inv.remove(refId);
					}
				}
			}
		}
	}

	protected void checkConsistency(List<SiteSpecification> targets, Map<String, SiteSpecification> toFill) {
		toFill.clear();
		
		for (SiteSpecification site : targets) {
			if (StringUtils.isNotBlank(site.getId())) {
				if (classInvariants.containsKey(site.getId())) {
					throw new DuplicateIdentifierException("El Id [" + site.getId() + "] esta duplicado", site.getId());
				}
				
				if (methodInvariants.containsKey(site.getId())) {
					throw new DuplicateIdentifierException("El Id [" + site.getId() + "] esta duplicado", site.getId());	
				}
				
				if (toFill.containsKey(site.getId())) {
					throw new DuplicateIdentifierException("El Id [" + site.getId() + "] esta duplicado", site.getId());
				}
				
				toFill.put(site.getId(), site);
			}
		}
	}
	
	protected List<String> retriveCycle(InvariantAdapter ref, Stack<InvariantAdapter> toProcess) {
		LinkedList<String> cycle = new LinkedList<String>();
		
		cycle.addLast(ref.getId());
		while (!toProcess.peek().equals(ref)) {
			cycle.addFirst(toProcess.pop().getId());
		}
		cycle.addFirst(ref.getId());
		
		return cycle;
	}
}