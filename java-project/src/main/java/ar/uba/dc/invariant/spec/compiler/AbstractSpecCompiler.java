package ar.uba.dc.invariant.spec.compiler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.operation.DomainUnifier;
import ar.uba.dc.invariant.spec.SpecCompiler;
import ar.uba.dc.invariant.spec.bean.CallSiteSpecification;
import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.CreationSiteSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.bean.SiteSpecificationVisitor;
import ar.uba.dc.invariant.spec.compiler.binding.BindingInfo;
import ar.uba.dc.invariant.spec.compiler.compilation.DefaultClassInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.compilation.DefaultMethodInvariantAndBindingProvider;
import ar.uba.dc.invariant.spec.compiler.compilation.InvariantId;
import ar.uba.dc.invariant.spec.compiler.compilation.InvariantId.Type;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsParser;
import ar.uba.dc.invariant.spec.compiler.exceptions.CompileException;
import ar.uba.dc.invariant.spec.compiler.exceptions.DuplicateIdentifierException;
import ar.uba.dc.invariant.spec.compiler.exceptions.SiteWithoutOffsetException;
import ar.uba.dc.invariant.spec.compiler.exceptions.UnknownParameterException;
import decorations.Binding;
import decorations.CallDecoration;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Template para los compiladores
 * 
 * @author testis
 */
public abstract class AbstractSpecCompiler implements SpecCompiler {

	
	
	private static Log log = LogFactory.getLog(AbstractSpecCompiler.class);
	protected Map<SiteSpecification, ConstraintsInfo> sitesConstraintsInfo = new HashMap<SiteSpecification, ConstraintsInfo>(); // site -> info de variables y referencias
	
	protected Map<SiteSpecification, BindingInfo> sitesBindingInfo = new HashMap<SiteSpecification, BindingInfo>(); // site -> info de variables y referencias
	
	
	protected ConstraintsInfo requirementsInfo = null; // info de variables y referencias para los requerimientos
	protected DomainSet requirementsInvariant = null;
	
	protected ConstraintsParser parser = null;
	
	protected DomainUnifier domainUnifier;
	
	public AbstractSpecCompiler() {
		super();
	}
	
	public CompiledClassInvariantProvider compile(ClassSpecification classSpec) throws CompileException {
		if (classSpec == null) {
			throw new IllegalArgumentException("La especificacion dada es nula");
		}
		
		DefaultClassInvariantProvider classProvider = new DefaultClassInvariantProvider(classSpec.getClassName());
		
		processClassInvariants(classSpec);
		
		try {
			for (MethodSpecification method : classSpec.getMethods()) {
				DefaultMethodInvariantAndBindingProvider methodProvider = compile(method, classSpec);
				
				methodProvider.setDomainUnifier(this.domainUnifier);
				
				
				classProvider.put(method.getSignature(), methodProvider);
			}
		} catch (DuplicateIdentifierException e) {
			throw new DuplicateIdentifierException("Al procesar la clase [" + classSpec.getClassName() + "] se detectó al id [" + e.getDuplicatedId() + "] duplicado.", e.getDuplicatedId());
		}
		
		return classProvider;
	}
	
	
	
	

	protected DefaultMethodInvariantAndBindingProvider compile(MethodSpecification methodSpec, ClassSpecification classSpec) {
		final DefaultMethodInvariantAndBindingProvider provider = new DefaultMethodInvariantAndBindingProvider();
		log.debug("Generating provider for " + methodSpec.getSignature());

		try {
			processRequirements(methodSpec);
		} catch (UnknownParameterException e) {
			throw new UnknownParameterException(classSpec.getClassName(), methodSpec.getSignature(), requirementsInvariant.getConstraints(), e.getParameterName());
		}
		
		processInvariants(methodSpec);
		
			// Cargamos la informacion de las variables y referencias de los sites antes de 
			// comenzar con el proceso. De paso vemos si hay sites que no tengan offset
		sitesConstraintsInfo.clear();
		
		sitesBindingInfo.clear();
		
		
		for (SiteSpecification site : methodSpec.getSitesSpecification()) {
			if (StringUtils.isBlank(site.getOffset())) {
				throw new SiteWithoutOffsetException("Site at index " + sitesConstraintsInfo.size() + " of method " + methodSpec.getSignature() +  " at class " + classSpec.getClassName() + " has no offset specified");
			}
			// ConstraintsInfo info = processConstraints(site.getConstraints(), methodSpec.getParameters());
			ConstraintsInfo cInfo = processConstraints(site, methodSpec.getParameters());
			sitesConstraintsInfo.put(site, cInfo);
			
			BindingInfo bInfo = processBinding(site, methodSpec.getParameters());
			sitesBindingInfo.put(site,  bInfo);
		}
		
			// Este metodo puede cambiar la informacion de variables y referencias mientras lleva todo a forma canonica
		toCanonicalForm(methodSpec);
		
		provider.setRequirements(requirementsInvariant);
		
		Set<String> params = methodSpec.getParameters();
		provider.setParameters(params);
		for (SiteSpecification site : methodSpec.getSitesSpecification()) {
			for (final Long offset : expandOffset(site.getOffset())) {
					// Creamos el invariante
				final DomainSet invariant = new DomainSet(site.getConstraints());
				
				
				// Recuperamos la informacion (que pudo haber cambiado)
				ConstraintsInfo cInfo = sitesConstraintsInfo.get(site);
				invariant.addAllParameters(params);
				
				Set<String> variables = cInfo.getVariables();
				invariant.addAllVariables(variables);
				// Podria usar el constructor...
				invariant.addAllInductives(cInfo.getinductives());
				
				invariant.setClassCalledChangedDuringLoop(cInfo.checkIfClassCalledChangedDuringLoop());				
				
				
				
				BindingInfo bInfo = sitesBindingInfo.get(site);
				
				Binding binding = null;
				
				if(site.getClass() == CallSiteSpecification.class)
				{
					binding = new Binding(((CallSiteSpecification)site).getBinding(), bInfo.getVariables());
				}
				
				
				
				// Obtenemos un identificador para el invariante que estamos procesando
				InvariantId invariantId = site.apply(new SiteSpecificationVisitor<InvariantId>() {

					@Override
					public InvariantId visit(CallSiteSpecification site) {
						return new InvariantId(Type.CALL, offset, site.forImplementation(), site.forOperator());
					}

					@Override
					public InvariantId visit(CreationSiteSpecification site) {
						return new InvariantId(Type.NEW, offset, site.forImplementation(), site.forOperator());
					}
				
				});
				
					// Para loop-invariant y capture manda el default (aquel que no tiene impl ni op) 
				if (invariantId.isDefault()) {
					if (Boolean.TRUE.equals(site.isLoopInvariant())) {
						provider.addLoopInvariant(invariantId);
					}
					
					if (Boolean.TRUE.equals(site.getCaptureAllPartitions())) {
						provider.addCaptureAllPartitions(invariantId);
					}
				}
				
					// Recuperamos lo que hay hasta ahora (se devuelve la union de todos los sites que encontremos para el mismo offset)
				DomainSet actualInvariant = provider.getInvariant(invariantId);
				
				if (actualInvariant != null) {
						// Si ya habia algo, devolvemos la union (el and) de las restricciones
					actualInvariant.setConstraints(domainUnifier.unify(actualInvariant.getConstraints(), invariant.getConstraints()));
					
					//actualInvariant.setBinding(domainUnifier.unify(actualInvariant.getBinding(), invariant.getBinding()));
					
					actualInvariant.addAllVariables(invariant.getVariables());
					actualInvariant.addAllParameters(invariant.getParameters());
					
					actualInvariant.setClassCalledChangedDuringLoop(invariant.checkIfClassCalledChangedDuringLoop());
				} else {
						// Si no habia nada y tengo requires para el metodo, los agrego al invariante
					if (requirementsInvariant != null && StringUtils.isNotBlank(requirementsInvariant.getConstraints())) {
						
						invariant.setConstraints(domainUnifier.unify(requirementsInvariant.getConstraints(), invariant.getConstraints()));	
						//El binding aca no hace falta agregarlo
						
						//no estoy seguro de si hace falta esto.
						invariant.setClassCalledChangedDuringLoop(requirementsInvariant.checkIfClassCalledChangedDuringLoop());
					}
					provider.putInvariant(invariantId, new CallDecoration(invariant, binding));
				}
			}
		}	
		
		return provider;
	}

	protected ConstraintsInfo processConstraints(String constraints, Set<String> parameters) {
		ConstraintsInfo info = new ConstraintsInfo();
		if (StringUtils.isNotBlank(constraints)) {
			info = parser.parse(constraints);
			info.getVariables().removeAll(parameters);
		}
		
		return info;
	}
	
	protected BindingInfo processBinding(SiteSpecification site, Set<String> parameters)
	{
		BindingInfo bInfo = new BindingInfo();
		if(site instanceof CallSiteSpecification)
		{
			String binding = ((CallSiteSpecification)site).getBinding();

			if (StringUtils.isNotBlank(binding)) {
				
				Set<String> variables = parser.parse(binding).getVariables();
				bInfo.addAllVariables(variables);
				bInfo.getVariables().removeAll(parameters);
			}			
		}
		
		return bInfo;
	}
	
	protected ConstraintsInfo processConstraints(SiteSpecification site, Set<String> parameters) {
		String constraints = site.getConstraints();
		
		
		boolean class_called_changed_during_loop = false;
		
		ConstraintsInfo info = new ConstraintsInfo();
		
		if (StringUtils.isNotBlank(site.getConstraints())) {
			info = parser.parse(constraints);			
			info.getVariables().removeAll(parameters);
		}
		
		
		if(site instanceof CallSiteSpecification)
		{
			String annotations = ((CallSiteSpecification)site).getAnnotations();
			if (annotations.equals("class_called_changed_during_loop"))
			{
				class_called_changed_during_loop = true;
			}
		}
		
		if(site.getInductives()!=null) {
			StringTokenizer stn = new StringTokenizer(site.getInductives(), ",");
			while (stn.hasMoreTokens()) {
		        info.addInductive(stn.nextToken().trim());
		     }
		}
		// Ojo!! Habria que borrarlas, ver lo de $t
		// info.getinductives().removeAll(parameters);
		
		info.setClassCalledChangedDuringLoop(class_called_changed_during_loop);
		return info;
	}

	
	protected Set<Long> expandOffset(String offset) {
		Set<Long> offsets = new HashSet<Long>();
		
		for (String part : offset.split(",")) {
			if (!StringUtils.isBlank(part)) {
				if (part.indexOf("-") >= 0) {
					String[] range = part.split("-");
					if (range.length >= 2 && !StringUtils.isBlank(range[0]) && !StringUtils.isBlank(range[1])) {
						Long start = Long.valueOf(range[0].trim());
						Long stop = Long.valueOf(range[1].trim());
						for (Long i = start; i <= stop; i++) {
							offsets.add(i);
						}
					}
				} else {
					offsets.add(Long.valueOf(part.trim()));
				}
			}
		}
		
		return offsets;
	}

	protected void processRequirements(MethodSpecification methodSpec) throws UnknownParameterException {
		requirementsInfo = null;
		requirementsInvariant = null;
		String requirements = StringUtils.EMPTY;
		for (String requirement : methodSpec.getRequirements()) {
			requirements = domainUnifier.unify(requirements, requirement);
		}
		
		if (StringUtils.isNotEmpty(requirements)) {
			requirementsInfo = new ConstraintsInfo();
			requirementsInfo = processConstraints(requirements, methodSpec.getParameters());
			requirementsInvariant = new DomainSet(requirements);
			requirementsInvariant.addAllParameters(methodSpec.getParameters());
			
		}
		
		if (requirementsInfo != null && !requirementsInfo.getVariables().isEmpty()) {
			throw new UnknownParameterException(null, methodSpec.getSignature(), requirements, requirementsInfo.getVariables().iterator().next());
		}
	}
	
	protected abstract void processClassInvariants(ClassSpecification classSpec);
	protected abstract void processInvariants(MethodSpecification methodSpec);
	
	/**
	 * La forma canonica de una especificacion es aquella donde las referencias han sido resueltas las
	 * referencias a otros inviarantes (de clase o de metodo) y/o otros sites.
	 * 
	 * En la forma canonica una especificacion es un conjunto de call-sites y creation-site sin referencias.
	 * Luego de expandir los rangos de la forma canonica deberiamos tener una especificacion con un 
	 * call-site por cada call y un creation-site por cada new del codigo.
	 */
	protected abstract void toCanonicalForm(MethodSpecification method);

	public void setParser(ConstraintsParser parser) {
		this.parser = parser;
	}

	public void setDomainUnifier(DomainUnifier domainUnifier) {
		this.domainUnifier = domainUnifier;
	}
}
