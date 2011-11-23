package ar.uba.dc.invariant.spec.compiler;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.InvariantSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.constraints.ReferenceResolver;
import ar.uba.dc.invariant.spec.compiler.exceptions.DuplicateIdentifierException;
import ar.uba.dc.invariant.spec.compiler.exceptions.InvalidReferenceException;
import ar.uba.dc.invariant.spec.compiler.exceptions.UnidentifiedInvariantException;
import ar.uba.dc.invariant.spec.compiler.exceptions.UnknownInvariantException;

/**
 * Clase abstracta para compiladores que soportan referencias.
 * Tiene metodos utiles para estos tipos de compiladores
 * 
 * @author testis
 */
public abstract class AbstractReferencesSpecCompiler extends AbstractSpecCompiler {

	protected Map<String, InvariantSpecification> classInvariants = new HashMap<String, InvariantSpecification>();
	protected Map<String, InvariantSpecification> methodInvariants = new HashMap<String, InvariantSpecification>();
	
	protected Map<InvariantSpecification, ConstraintsInfo> classInvariantInfo = new HashMap<InvariantSpecification, ConstraintsInfo>(); // class invariant -> info de variables y referencias
	protected Map<InvariantSpecification, ConstraintsInfo> methodInvariantInfo = new HashMap<InvariantSpecification, ConstraintsInfo>(); // class invariant -> info de variables y referencias

	protected ReferenceResolver resolver;
	
	protected Boolean allowReferencesInInvariants = false;
	
	public AbstractReferencesSpecCompiler(Boolean allowReferencesInInvariants) {
		this.allowReferencesInInvariants = allowReferencesInInvariants; 
	}
	
	@Override
	protected void processClassInvariants(ClassSpecification classSpec) {
		try {
			checkConsistency(classSpec.getClassInvariants(), classInvariants, null);
		} catch (UnidentifiedInvariantException e) {
			throw new UnidentifiedInvariantException("La clase [" + classSpec.getClassName() + "] posee un invariante de clase sin Id");
		} catch (DuplicateIdentifierException e) { 
			throw new DuplicateIdentifierException("La clase [" + classSpec.getClassName() + "] posee un invariante con el id [" + e.getDuplicatedId() + "] duplicado", e.getDuplicatedId());
		}
		
		try {
			Set<String> knownInvariants = new HashSet<String>(classInvariants.keySet());
			checkReferences(classSpec.getClassInvariants(), classInvariantInfo, knownInvariants, new HashSet<String>(), allowReferencesInInvariants);
		} catch (InvalidReferenceException e) {
			throw new InvalidReferenceException("La clase [" + classSpec.getClassName() + "] posee una referencia en el invariante [" + e.getInvariant().getId() + "]. Esto no esta permitido en el modo SimpleReferences", e.getInvariant());
		}
	}

	@Override
	protected void processInvariants(MethodSpecification methodSpec) {
		try {
			checkConsistency(methodSpec.getInvariants(), methodInvariants, classInvariants);
		} catch (UnidentifiedInvariantException e) {
			throw new UnidentifiedInvariantException("El metodo [" + methodSpec.getSignature() + "] posee un invariante de clase sin Id");
		} catch (DuplicateIdentifierException e) {
			throw new DuplicateIdentifierException("El metodo [" + methodSpec.getSignature() + "] posee un invariante con el id [" + e.getDuplicatedId() + "] duplicado", e.getDuplicatedId());
		}
		
		try {
			Set<String> knownInvariants = new HashSet<String>(classInvariants.keySet());
			knownInvariants.addAll(methodInvariants.keySet());
			checkReferences(methodSpec.getInvariants(), methodInvariantInfo, knownInvariants, methodSpec.getParameters(), allowReferencesInInvariants);
		} catch (InvalidReferenceException e) {
			throw new InvalidReferenceException("El metodo [" + methodSpec.getSignature() + "] posee una referencia en el invariante [" + e.getInvariant().getId() + "]. Esto no esta permitido en el modo SimpleReferences", e.getInvariant());
		}
	}
	
	protected void checkConsistency(List<InvariantSpecification> targets, Map<String, InvariantSpecification> toFill, Map<String, InvariantSpecification> known) {
		toFill.clear();
		
		for (InvariantSpecification inv : targets) {
			if (StringUtils.isBlank(inv.getId())) {
				throw new UnidentifiedInvariantException("Se encontró un Invariante sin Id");
			} else {
				String id = inv.getId().trim();
				if (toFill.containsKey(id) || (known != null && known.containsKey(id))) {
					throw new DuplicateIdentifierException("El Id [" + id + "] esta duplicado", id);
				} else {
					toFill.put(id, inv);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void checkReferences(List<InvariantSpecification> targets, Map<InvariantSpecification, ConstraintsInfo> toFill, Set<String> knownReferences, Set<String> parameters, Boolean allowReferences) {
		toFill.clear();
		
		for (InvariantSpecification inv : targets) {
			ConstraintsInfo info = processConstraints(inv.getConstraints(), parameters);
			if (!allowReferences && !info.getReferences().isEmpty()) {
				throw new InvalidReferenceException("No estan permitidas las referencias", inv); 
			} else {
				Collection<String> rest = CollectionUtils.subtract(info.getReferences(), knownReferences);
				if (!rest.isEmpty()) {
					throw new UnknownInvariantException(inv.getId(), rest.iterator().next());
				}				
			}
			toFill.put(inv, info);
		}
	}

	public void setResolver(ReferenceResolver resolver) {
		this.resolver = resolver;
	}
}
