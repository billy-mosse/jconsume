package ar.uba.dc.barvinok.expression;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

public class QuasiPolynomial {

	private Set<String> variables;
	private Set<String> inductives;

	
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
	
	//TODO hacer regexp
	public static QuasiPolynomial fromRawString(String s)
	{
		//ejemplo
		//{[] -> 2 + n_init: n_init >= 2; [] -> 3: n_init <= 1}
		
		String[] v = s.split(":");
		assert(v.length <= 2);
		
		QuasiPolynomial p;
		if(v.length ==1)
			p = new QuasiPolynomial(v[0]);
		else
			p = new QuasiPolynomial(v[0],v[1]);
		
		
		
		//no se si los quasipolynomials tienen un campo inductivas
		//para saber de donde venia el consumo
		//y esto se usa mas adelante.
		//En ese caso, tal vez va a haber problemas si usamos anotaciones para el consumo
		//porque no tenemos inductivas asociadas
		return p;
	}
	
	public QuasiPolynomial(String polynomial) {
		this(polynomial, StringUtils.EMPTY);
	}
	
	public QuasiPolynomial(String polynomial, String constraints) {
		this(polynomial, constraints, new TreeSet<String>());
	}
	
	public QuasiPolynomial(String polynomial, String constraints, Set<String> variables) {
		this(polynomial,constraints, variables, new TreeSet<String>(variables));
	
	}
	public QuasiPolynomial(String polynomial, String constraints, Set<String> variables, Set<String> inductives) {
		super();
		this.variables = variables;
		this.polynomial = polynomial;
		this.constraints = constraints;
		this.inductives = inductives;
	}

	public QuasiPolynomial() {
		// TODO Auto-generated constructor stub
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
		
		//Por que esto es necesario?? Por que siempre termino agregandolo de nuevo?
		/*if (!StringUtils.isEmpty(constraints)) {
			ret += ": " + constraints;
		}*/

		
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
		//return variables;
		return new TreeSet<String>(variables);
	}
	
	public Set<String> getInductives() {
		//return inductives;
		return new TreeSet<String>(inductives);
	}

	
	public String getConstraints() {
		return constraints;
	}
	
	public Set<String> variablesToExclude()
	{
		Set<String> res = new TreeSet<String>(variables);
		res.removeAll(inductives);
		
		// No sirve porque ya estan cambiando los nombres
//		Set<String> res = new TreeSet<String>();
//		for (String v : variables) {
//			if(!inductives.contains(v) || v.startsWith("$t.") )
//				res.add(v);
//		}
		return res;
	}

	public void setPolynomial(String polynomial) {
		this.polynomial = polynomial;
	}

	//Maybe we should also fix multiplication by constants
	public void fixImplicitMultiplication() {
		if(constraints.contains("/"))
		{
			/* 4a -> 4*a
			 * 23a -> 23*a
			 * 23,5a -> 23,5*a
			 * Usar punto considero que esta de mas, porque barvinok no usa. Pero quien sabe si algun dia usemos otra herramienta que si?
			 * 10.000.000a -> 10.000.000*a
			 */
			constraints = constraints.replaceAll("([0-9.,]+)([a-zA-Z]+)", "$1*$2");
		}
		else
		{			
			/*
			 * 4/3a -> 4/(3*a)
			 */
			constraints = constraints.replaceAll("([0-9.,]+)([a-zA-Z]+)", "$1/($2 * $3)");
		}
				
	}
}
