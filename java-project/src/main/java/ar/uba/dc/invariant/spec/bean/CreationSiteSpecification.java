package ar.uba.dc.invariant.spec.bean;

import org.apache.commons.lang.StringUtils;

public class CreationSiteSpecification implements SiteSpecification {

	public CreationSiteSpecification() {
		super();
	}

	private String id;
	private String offset;
	private String constraints;
	//private String constraintVariables;
	private String variables;
	private String inductives;
	private Boolean loopInvariant;
	private Boolean captureAllPartitions;
	private String implementation;
	private String operator;
	
	//Los Creation Sites no tienen binding claramente

	/**
	 * BugFix porque no se invoca al constructor por defecto con XStream
	 * 
	 * http://xstream.codehaus.org/faq.html
	 * 
	 * @return
	 */
	private Object readResolve() {
		if (constraints == null) {
			constraints = StringUtils.EMPTY;
		}
	    return this;
	}
	
	public CreationSiteSpecification(String offset) {
		this.offset = offset;
	}

	public CreationSiteSpecification(String offset, boolean captureAllPartitions) {
		this(offset);
		if (captureAllPartitions) {
			this.captureAllPartitions = true;
		}
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	public String getConstraints() {
		return constraints;
	}

	public String getOffset() {
		return offset;
	}

	public String getId() {
		return id;
	}

	public <T> T apply(SiteSpecificationVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public Boolean isLoopInvariant() {
		return loopInvariant;
	}

	public Boolean getCaptureAllPartitions() {
		return captureAllPartitions;
	}

	public String forImplementation() {
		return implementation;
	}
	
	public String forOperator() {
		return operator;
	}
	
	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String getInductives() {
		return inductives;
	}

	@Override
	public String getVariables() {
		return variables;
	}
	
	@Override
	public void addConstraints(String new_constraints) {
		if(new_constraints.length() > 0)
			this.constraints = this.constraints + " and " + new_constraints;
		
	}


	/*@Override
	public String getConstraintVariables() {
		return constraintVariables;
	}*/
}
