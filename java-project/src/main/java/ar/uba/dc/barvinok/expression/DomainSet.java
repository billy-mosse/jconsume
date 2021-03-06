package ar.uba.dc.barvinok.expression;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;

public class DomainSet {

	@XStreamImplicit(itemFieldName="parameter")
	private Set<String> parameters = new TreeSet<String>(); // AKA, free variables
	private Set<DerivedVariable> new_parameters = new TreeSet<DerivedVariable>(); // AKA, free variables
	
	@XStreamImplicit(itemFieldName="variable")
	// (NO! aqui estan poniendo las que aparecen en el invariante)
	private Set<String> variables = new TreeSet<String>(); // AKA, inductive variables 	
	@XStreamImplicit(itemFieldName="inductive")
	private Set<String> inductives = new TreeSet<String>(); 
	
	private String constraints;
	
	//private String binding;
	
	private boolean hasInductives = false;
	
	private boolean class_called_changed_during_loop = false;

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
	
	public DomainSet()
	{
		
	}
	public DomainSet(String constraints) {
		this.constraints = constraints;
	}
	
	private static Log log = LogFactory.getLog(DomainSet.class);
	
	public DomainSet(String expr, Set<String> variables, boolean _)
	{
		this.variables = variables;
		if(expr.equals("{}"))
		{
			this.constraints = "";
			return;
		}
		
		if(expr.length() < 2)
		{
			throw new Error("Expression should be between brackets. " + expr);
		}
		log.debug(expr);
		String[] parts = expr.split("\\{|}");		
		
		if(parts[0].contains("->"))
		{
			String params = parts[0].substring(parts[0].indexOf("[") + 1, parts[0].indexOf("]"));
		
			String[] paramsArray = params.split(",");
			for(String param : paramsArray)
			{
				this.parameters.add(param);
				
			}
		}
		
		if(parts.length == 2)
		{
			this.constraints = parts[1].contains(":") ? parts[1].substring(parts[1].indexOf(":") + 1).trim() : "";
			
			if(parts[1].contains("[") && parts[1].contains("]"))
			{
				String inductives = parts[1].substring(parts[1].indexOf("[") + 1, parts[1].indexOf("]"));
				
				if(inductives.length() > 0)
				{
					String[] inductivesArray = inductives.split(",");
					for(String inductive : inductivesArray)
					{
						this.inductives.add(inductive);
						
					}
				}
			}
		}
		else
		{
			this.constraints = "";
		}
		
		//TODO: faltan las variables (ver si las necesito)

		
		
	}
	
	
	public DomainSet(String constraints, Set<String> inductives) {
		this.constraints = constraints;
		this.inductives = inductives;
		hasInductives = !inductives.isEmpty();
	}
	
	public DomainSet(String constraints, Set<String> inductives, Set<String> vars) {
		this.constraints = constraints;
		this.inductives = inductives;
		this.variables = vars;
		hasInductives = !inductives.isEmpty();
	}
	
	public String toString() {
		String ret = StringUtils.EMPTY;
		
		Set<String> all_parameters = new TreeSet();
		
		all_parameters.addAll(parameters);
		
		for(DerivedVariable dv : new_parameters)
		{
			all_parameters.add(dv.toString());
		}
		
		
		variables.removeAll(all_parameters);
		
		
		String params = StringUtils.join(all_parameters, ",");
		String vars = StringUtils.join(variables, ",");
		String inds = StringUtils.join(inductives, ",");
		
		if (params.length() > 0 || true) {
			ret += "[" + params + "] -> ";
		}
		
		ret += "{";
		
		
		//cambio importante: estabamos tomando las variables como inductivas!
		if (vars.length() > 0 || true) {
			ret += "[" + inds + "]: ";
		} 
		
		ret += constraints + "}";
	
		//TODO: agregar los exists
		
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
		hasInductives = !inductives.isEmpty();
	}
	
	public void removeInductive(String var) {
		this.inductives.remove(var.trim());
		hasInductives = !inductives.isEmpty();
	}


	public String getConstraints() {
		return constraints.trim();
	}
	
	/*public String getBinding() {
		return binding.trim();
	}*/
	
	public Set<String> getVariables() {
		return new TreeSet<String>(variables);
	}
	
	public Set<String> getInductives() {
		return new TreeSet<String>(inductives);
	}


	public Set<String> getParameters() {
		return new TreeSet<String>(parameters);
	}

	public Set<DerivedVariable> getNewParameters() {
		return new TreeSet<DerivedVariable>(new_parameters);
	}
	
	
	public boolean checkIfClassCalledChangedDuringLoop() {
		return class_called_changed_during_loop;
	}

	public void setClassCalledChangedDuringLoop(boolean classCalledChangedDuringLoop) {
		this.class_called_changed_during_loop = classCalledChangedDuringLoop;
	}
	
	public DomainSet clone() {
		DomainSet d = new DomainSet(new String(constraints));
		d.parameters = new TreeSet<String>(parameters);
		d.variables = new TreeSet<String>(variables);
		d.inductives = new TreeSet<String>(inductives);
		d.hasInductives = !inductives.isEmpty();
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
	
	/*public void setBinding(String newValue) {
		this.binding = newValue;
	}*/

	public void addAllParameters(Set<String> params) {
		this.parameters.addAll(params);
	}
	
	public void addAllNewParameters(Set<DerivedVariable> new_params) {
		this.new_parameters.addAll(new_params);
	}

	public void addAllVariables(Set<String> vars) {
		this.variables.addAll(vars);
	}
	
	public void addAllInductives(Set<String> vars) {
		this.inductives.addAll(vars);
		hasInductives = !inductives.isEmpty();
	}
	public Set<String> variablesToExclude()
	{
		Set<String> res = new TreeSet<String>(variables);
		res.removeAll(inductives);
		return res;
	}

	public boolean hasInductives()
	{
		return hasInductives;
	}

	public String toHumanReadableString() {
		return this.toString();
	}

	public void setInductives(Set<String> list) {
		this.inductives = list;
		
	}
	
}