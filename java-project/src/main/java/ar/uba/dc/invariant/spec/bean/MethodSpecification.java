package ar.uba.dc.invariant.spec.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;

public class MethodSpecification {

	private String signature;

	private Set<String> parameters = new TreeSet<String>();
	
	private Set<DerivedVariable> new_parameters = new TreeSet<DerivedVariable>();
	
	private List<String> requirements = new ArrayList<String>();
	
	private List<InvariantSpecification> invariants = new ArrayList<InvariantSpecification>();
	
	private List<CallSiteSpecification> callSites = new ArrayList<CallSiteSpecification>();
	
	private List<CreationSiteSpecification> creationSites = new ArrayList<CreationSiteSpecification>();
	
	public MethodSpecification(String signature) {
		this.signature = signature;
	}

	public MethodSpecification() {
	}
	
	/**
	 * BugFix porque no se invoca al constructor por defecto con XStream
	 * 
	 * http://xstream.codehaus.org/faq.html
	 * 
	 * @return
	 */
	private Object readResolve() {
		if (parameters == null) {
			parameters = new TreeSet<String>();
		}
		
		if (requirements == null) {
			requirements = new ArrayList<String>();
		}
		
		if (invariants == null) {
			invariants = new ArrayList<InvariantSpecification>();
		}
		
		if (callSites == null) {
			callSites = new ArrayList<CallSiteSpecification>();
		}
		
		if (creationSites == null) {
			creationSites = new ArrayList<CreationSiteSpecification>();
		}
		
		// Eliminamos los espacios entre los parametros de la signatura. Soot no espera espacios.
		int idxParams = signature.lastIndexOf("(");
		if (idxParams >= 0) {
			signature = signature.substring(0, idxParams) + signature.substring(idxParams).replaceAll(" ", StringUtils.EMPTY);
		}
		
	    return this;
	}
	
	public String getSignature() {
		return signature;
	}

	public Set<String> getParameters() {
		return new TreeSet<String>(parameters);
	}
	
	public Set<DerivedVariable> getNewParameters() {
		return new TreeSet<DerivedVariable>(new_parameters);
	}
	
	public void addAllParameters(Set<String> params) {
		parameters.addAll(params);
	}

	public List<InvariantSpecification> getInvariants() {
		return new ArrayList<InvariantSpecification>(invariants);
	}

	public InvariantSpecification getInvariant(String id) {
		return (InvariantSpecification) CollectionUtils.find(invariants, new BeanPropertyValueEqualsPredicate("id", id));
	}

	

	public void addAllNewParameters(Set<DerivedVariable> new_params) {
		new_parameters.addAll(new_params);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && 
			   (obj instanceof MethodSpecification) &&
			   signature.equals(((MethodSpecification) obj).getSignature());
	}

	@Override
	public int hashCode() {
		return signature.hashCode();
	}

	public List<CallSiteSpecification> getCallSites() {
		return new ArrayList<CallSiteSpecification>(callSites);
	}

	public List<CreationSiteSpecification> getCreationSites() {
		return new ArrayList<CreationSiteSpecification>(creationSites);
	}

	public List<SiteSpecification> getSitesSpecification() {
		List<SiteSpecification> sites = new ArrayList<SiteSpecification>(callSites);
		sites.addAll(creationSites);
		return sites;
	}

	public void add(CallSiteSpecification callSpec) {
		callSites.add(callSpec);
	}
	
	public void add(CreationSiteSpecification creationSpec) {
		creationSites.add(creationSpec);
	}

	public List<String> getRequirements() {
		return new ArrayList<String>(requirements);
	}

	public void addAllRequirements(List<String> requirements) {
		this.requirements.addAll(requirements);
	}

	public void addRequirement(String requirement) {
		this.requirements.add(requirement);
	}
}
