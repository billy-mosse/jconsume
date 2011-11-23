package ar.uba.dc.invariant.spec.bean;

import org.apache.commons.lang.StringUtils;

public class CreationSiteSpecification implements SiteSpecification {

	private String id;
	private String offset;
	private String constraints;
	private Boolean loopInvariant;
	private Boolean captureAllPartitions;
	private String implementation;
	private String operator;

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
}
