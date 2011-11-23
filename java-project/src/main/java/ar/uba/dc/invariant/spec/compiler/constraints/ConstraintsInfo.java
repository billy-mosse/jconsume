package ar.uba.dc.invariant.spec.compiler.constraints;

import java.util.Set;
import java.util.TreeSet;

public class ConstraintsInfo {

	private Set<String> variables = new TreeSet<String>();
	
	/**
	 * Nombres de los invariantes a los que hace referencia
	 */
	private Set<String> references = new TreeSet<String>();
	
	public void addVariable(String var) {
		variables.add(var);
	}
	
	public void addAllVariables(Set<String> vars) {
		variables.addAll(vars);
	}
	
	public Set<String> getVariables() {
		return variables;
	}
	
	public void addReference(String invariantId) {
		references.add(invariantId);
	}
	
	public void removeReference(String id) {
		references.remove(id);
	}
	
	public Set<String> getReferences() {
		return references;
	}
}
