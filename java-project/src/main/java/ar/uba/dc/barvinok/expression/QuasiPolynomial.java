package ar.uba.dc.barvinok.expression;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

public class QuasiPolynomial {

	private Set<String> variables;
	
	private String constraints;
	
	private String polynomial;

	@Override
	public int hashCode() {
		return variables.hashCode() + polynomial.hashCode() + constraints.hashCode(); 
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof QuasiPolynomial)) return false;
		QuasiPolynomial q = (QuasiPolynomial) o;
		return variables.equals(q.variables) 
				&& polynomial.equals(q.polynomial)
				&& constraints.equals(q.constraints);
	}
	
	public QuasiPolynomial(String polynomial) {
		this(polynomial, StringUtils.EMPTY);
	}
	
	public QuasiPolynomial(String polynomial, String constraints) {
		this(polynomial, constraints, new TreeSet<String>());
	}
	
	public QuasiPolynomial(String polynomial, String constraints, Set<String> variables) {
		super();
		this.variables = variables;
		this.polynomial = polynomial;
		this.constraints = constraints;
	}
	
	public String asString() {
		String ret = polynomial;
		
		if (!StringUtils.isEmpty(constraints)) {
			ret += ": " + constraints;
		}
		
		return ret;
	}
	
	public String toString() {
		String vars = StringUtils.join(variables, ',');
		
		String ret = "[" + vars + "] -> " + polynomial;
		
		if (!StringUtils.isEmpty(constraints)) {
			ret += ": " + constraints;
		}
		
		return ret;
	}
	
	public QuasiPolynomial clone() {
		return new QuasiPolynomial(new String(polynomial), new String(constraints), variables);
	}

	public String getPolynomial() {
		return polynomial;
	}
	
	public Set<String> getVariables() {
		return variables;
	}
	
	public String getConstraints() {
		return constraints;
	}

	public void setPolynomial(String polynomial) {
		this.polynomial = polynomial;
	}
}
