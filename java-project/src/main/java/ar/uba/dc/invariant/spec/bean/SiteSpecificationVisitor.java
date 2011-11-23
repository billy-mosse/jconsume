package ar.uba.dc.invariant.spec.bean;

public interface SiteSpecificationVisitor<T> {

	public T visit(CallSiteSpecification site);
	
	public T visit(CreationSiteSpecification site);
}
