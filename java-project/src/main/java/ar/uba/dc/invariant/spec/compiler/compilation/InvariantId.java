package ar.uba.dc.invariant.spec.compiler.compilation;

import org.apache.commons.lang.StringUtils;

public class InvariantId {

	public enum Type { CALL, NEW };
	
	private Type type;
	private Long offset;
	private String implementation;
	private String operator;
	
	public InvariantId(Type type, Long offset) {
		this(type, offset, null, null);
	}
	
	public InvariantId(Type type, Long offset, String implementation) {
		this(type, offset, implementation, null);
	}
	
	public InvariantId(Type type, Long offset, String implementation, String operator) {
		super();
		this.type = type;
		this.offset = offset;
		this.implementation = StringUtils.defaultString(implementation);
		this.operator = StringUtils.defaultString(operator);
	}
	
	public int hashCode() {
		return type.hashCode()
				+ offset.hashCode()
				+ implementation.hashCode() 
				+ operator.hashCode();
	}

	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (!(o instanceof InvariantId))
			return false;
		InvariantId ii = (InvariantId) o;
		return type.equals(ii.type)
				&& offset.equals(ii.offset)
				&& implementation.equals(ii.implementation)
				&& operator.equals(ii.operator);
	}

	@Override
	public String toString() {
		return type.toString() + "-" + offset + "-" + implementation + "@" + operator;
	}

	public boolean isDefault() {
		return StringUtils.EMPTY.equals(implementation) && StringUtils.EMPTY.equals(operator);
	}

	public Type getType() {
		return type;
	}

	public Long getOffset() {
		return offset;
	}

	public String getImplementation() {
		return implementation;
	}

	public String getOperator() {
		return operator;
	}

}
