package ar.uba.dc.invariant.spec.bean;


public interface SiteSpecification {

	public String getId();
	
	public String getOffset();

	public String getConstraints();
	
	public String getInductives();

	public String getVariables();
	//public String getConstraintVariables();
	
	//no debe tener estos metodos porque CreationSite no necesita annotations (por ahora)
	//public String getAnnotations();
	//public void setAnnotations(String annotations);

	
	public <T> T apply(SiteSpecificationVisitor<T> visitor);

	public void setConstraints(String constraints);
	
	
	public Boolean isLoopInvariant();
	
	public Boolean getCaptureAllPartitions();
	
	public String forImplementation();

	public String forOperator();
	
	public void setImplementation(String implementation);

	public void setOperator(String operator);

	public void addConstraints(String new_constraints_string);
}
