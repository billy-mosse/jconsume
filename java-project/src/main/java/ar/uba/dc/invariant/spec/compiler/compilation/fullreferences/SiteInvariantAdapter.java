package ar.uba.dc.invariant.spec.compiler.compilation.fullreferences;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.invariant.spec.bean.CallSiteSpecification;
import ar.uba.dc.invariant.spec.bean.CreationSiteSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.bean.SiteSpecificationVisitor;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.constraints.ReferenceResolver;

public class SiteInvariantAdapter extends InvariantAdapter implements SiteSpecificationVisitor<String> {

	protected SiteSpecification spec;
	
	public SiteInvariantAdapter(SiteSpecification site, ConstraintsInfo constraintsInfo, AbstractInvariantScope scope) {
		super(constraintsInfo, scope);
		this.spec = site;
	}

	@Override
	public String getId() {
		String id = spec.getId(); 
		if (StringUtils.isBlank(id)) {
			id = spec.apply(this);
		}

		return id;
	}
	
	public String visit(CallSiteSpecification site) {
		return "Call at offset [" + site.getOffset() + "]";
	}

	public String visit(CreationSiteSpecification site) {
		return "Creation at offset [" + site.getOffset() + "]";
	}

	@Override
	public void replaceIn(InvariantAdapter target, MethodSpecification methodSpec, ReferenceResolver refResolver) {
		target.addAllVariables(constraintsInfo.getVariables());
		target.setConstraints(refResolver.resolve(target.getConstraints(), getId(), this.getConstraints()));
	}

	@Override
	protected String getConstraints() {
		return spec.getConstraints();
	}

	@Override
	protected void setConstraints(String constraints) {
		spec.setConstraints(constraints);
	}
	
	@Override
	public boolean equals(Object to) {
	    if ( this == to ) return true;

	    if ( !(to instanceof SiteInvariantAdapter) ) return false;

	    SiteInvariantAdapter that = (SiteInvariantAdapter) to;

	    return this.spec.equals(that.spec);
	}

	@Override
	public int hashCode() {
		return spec.hashCode();
	}
}
