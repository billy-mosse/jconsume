package decorations;

import java.util.Set;
import java.util.TreeSet;

public class Binding {
	private String binding;	
	private Set<String> variables = new TreeSet<String>(); 
	
	public Binding(String binding)
	{
		this.binding = binding;
	}
		
	public String toString()
	{
		return this.binding;
	}
	
	public Set<String> getVariables() {
		return new TreeSet<String>(variables);
	}

	public Binding()
	{
	}
	
	public Binding(String binding, Set<String> variables)
	{
		this.binding = binding;
		this.variables = variables;
	}

}
