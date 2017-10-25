package ar.uba.dc.invariant.spec.compiler.constraints;

import java.util.Set;
import java.util.TreeSet;

public class ConstraintsInfo {

	private Set<String> variables = new TreeSet<String>();
	private Set<String> constraintVariables = new TreeSet<String>();
	private Set<String> inductives = new TreeSet<String>();
	
	/**
	 * Nombres de los invariantes a los que hace referencia
	 */
	private Set<String> references = new TreeSet<String>();
	
	private boolean class_called_changed_during_loop;
	
	public void addVariable(String var) {
		variables.add(var);
	}
	
	public void addAllVariables(Set<String> vars) {
		variables.addAll(vars);
	}
	
	public void addInductive(String var) {
		inductives.add(var);
	}
	
	public void addAllInductives(Set<String> vars) {
		inductives.addAll(vars);
	}
	
	public Set<String> getVariables() {
		return variables;
	}
	public Set<String> getinductives() {
		return inductives;
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

	public boolean checkIfClassCalledChangedDuringLoop() {
		return class_called_changed_during_loop;
	}

	public void setClassCalledChangedDuringLoop(boolean classCalledChangedDuringLoop) {
		this.class_called_changed_during_loop = classCalledChangedDuringLoop;
	}

	public void addConstraintVariable(String variable) {
		
		this.constraintVariables.add(variable);
	}
	
	public Set<String> getConstraintVariables()
	{
		return this.constraintVariables;
	}
}
