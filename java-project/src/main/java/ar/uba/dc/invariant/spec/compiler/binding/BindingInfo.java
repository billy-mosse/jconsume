package ar.uba.dc.invariant.spec.compiler.binding;

import java.util.Set;
import java.util.TreeSet;

/**
 * Por ahora es una clase simple pero puede complejizarse
 * @author billy
 *
 */
public class BindingInfo {
private Set<String> variables = new TreeSet<String>();
	
	public void addVariable(String var) {
		variables.add(var);
	}
	
	public void addAllVariables(Set<String> vars) {
		variables.addAll(vars);
	}
	
	public Set<String> getVariables() {
		return variables;
	}
	

}
