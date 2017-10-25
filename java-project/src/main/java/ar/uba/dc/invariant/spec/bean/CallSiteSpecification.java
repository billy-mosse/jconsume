package ar.uba.dc.invariant.spec.bean;

import org.apache.commons.lang.StringUtils;

public class CallSiteSpecification implements SiteSpecification {

	public CallSiteSpecification() {
		super();
	}

	private String id;
	private String offset;
	private String constraints;
	//private String constraintVariables;
	private String annotations;
	private String variables;
	private String inductives;
	private String callee;
	private Boolean loopInvariant;
	private Boolean captureAllPartitions;
	private String implementation;
	private String operator;
	
	private String binding;
	
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
		if (annotations == null) {
			annotations = StringUtils.EMPTY;
		}
		if (inductives == null) {
			inductives = StringUtils.EMPTY;
		}
		if (variables == null) {
			variables = StringUtils.EMPTY;
		}

		if (binding == null) {
			binding = StringUtils.EMPTY;
		}
		
	    return this;
	}
	
	public CallSiteSpecification(String offset) {
		this.offset = offset;
	}
	
	public CallSiteSpecification(String offset, boolean captureAllPartitions) {
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
	
	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getBinding() {
		return binding;
	}
	
	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public String getAnnotations() {
		return annotations;
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
		// TODO Auto-generated method stub
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