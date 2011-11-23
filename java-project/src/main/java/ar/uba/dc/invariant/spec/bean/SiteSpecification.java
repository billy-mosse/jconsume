package ar.uba.dc.invariant.spec.bean;


public interface SiteSpecification {

	public String getId();
	
	public String getOffset();
	
	public String getConstraints();
	
	public <T> T apply(SiteSpecificationVisitor<T> visitor);

	public void setConstraints(String constraints);
	
	public Boolean isLoopInvariant();
	
	public Boolean getCaptureAllPartitions();
	
	public String forImplementation();

	public String forOperator();
	
	public void setImplementation(String implementation);

	public void setOperator(String operator);
}
