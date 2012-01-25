package ar.uba.dc.barvinok.expression;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class DomainSet {

	@XStreamImplicit(itemFieldName="parameter")
	private Set<String> parameters = new TreeSet<String>(); // AKA, free variables
	
	@XStreamImplicit(itemFieldName="variable")
	// (NO! aqui estan poniendo las que aparecen en el invariante)
	private Set<String> variables = new TreeSet<String>(); // AKA, inductive variables 	
	@XStreamImplicit(itemFieldName="inductive")
	private Set<String> inductives = new TreeSet<String>(); 
	
	private String constraints;

	/**
	 * BugFix porque no se invoca al constructor por defecto con XStream
	 * 
	 * http://xstream.codehaus.org/faq.html
	 * 
	 * @return
	 */
	private Object readResolve() {
		if (parameters == null) {
			parameters = new TreeSet<String>();
		}
		
		if (variables == null) {
			variables = new TreeSet<String>();
		}
		
		if (inductives == null) {
			inductives = new TreeSet<String>();
		}
		
		if (constraints == null) {
			constraints = StringUtils.EMPTY;
		}
		
	    return this;
	}
	
	public DomainSet(String constraints) {
		this.constraints = constraints;
	}
	public DomainSet(String constraints, Set<String> inductives) {
		this.constraints = constraints;
		this.inductives = inductives;
	}
	
	public DomainSet(String constraints, Set<String> inductives, Set<String> vars) {
		this.constraints = constraints;
		this.inductives = inductives;
		this.variables = vars;
	}
	
	public String toString() {
		String ret = StringUtils.EMPTY;
		String params = StringUtils.join(parameters, ",");
		String vars = StringUtils.join(variables, ",");
		
		if (params.length() > 0) {
			ret += "[" + params + "] -> ";
		}
		
		ret += "{";
		
		if (vars.length() > 0) {
			ret += "[" + vars + "]: ";
		} 
		
		ret += constraints + "}";
	
		return ret;
	}
	
	public void addVariable(String var) {
		this.variables.add(var.trim());
	}
	
	public void removeVariable(String var) {
		this.variables.remove(var.trim());
	}
	
	public void addParameter(String param) {
		this.parameters.add(param.trim());
	}
	
	public void removeParameter(String param) {
		this.parameters.remove(param.trim());
	}
	
	
	public void addInductive(String var) {
		this.inductives.add(var.trim());
	}
	
	public void removeInductive(String var) {
		this.inductives.remove(var.trim());
	}

	
	public String getConstraints() {
		return constraints;
	}
	
	public Set<String> getVariables() {
		return new TreeSet<String>(variables);
	}
	
	public Set<String> getInductives() {
		return new TreeSet<String>(inductives);
	}


	public Set<String> getParameters() {
		return new TreeSet<String>(parameters);
	}
	
	public DomainSet clone() {
		DomainSet d = new DomainSet(new String(constraints));
		d.parameters = new TreeSet<String>(parameters);
		d.variables = new TreeSet<String>(variables);
		
		return d;
	}

	public void addConstraints(String constraints) {
		if (StringUtils.isNotBlank(constraints)) {
			if (!StringUtils.isBlank(this.constraints)) {
				this.constraints = "(" + this.constraints + ") and ";
			}
			
			this.constraints += "(" + constraints + ")";
		}
	}
	
	public void setConstraints(String newValue) {
		this.constraints = newValue;
	}

	public void addAllParameters(Set<String> params) {
		this.parameters.addAll(params);
	}

	public void addAllVariables(Set<String> vars) {
		this.variables.addAll(vars);
	}
	
	public void addAllInductives(Set<String> vars) {
		this.inductives.addAll(vars);
	}
	public Set<String> variablesToExclude()
	{
		Set<String> res = new TreeSet<String>(variables);
		res.removeAll(inductives);
		return res;
	}
	
	
}