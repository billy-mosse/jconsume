package decorations;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import ar.uba.dc.analysis.common.Invocation;

public class Binding {
	private Set<BindingPair> bindingPairs;	
	//private Set<String> variables = new TreeSet<String>(); 
	
	public Binding(String binding)
	{

		bindingPairs = new HashSet<BindingPair>();
		if(binding != "")
		{
			String[] bindings = binding.split(" and ");
			for(String b : bindings)
			{
				bindingPairs.add(new BindingPair(b));
			}
		}
	}
	
	public void setBindingPairs(Set<BindingPair> bindingPairs)
	{
		this.bindingPairs = bindingPairs;
	}
		
	public String toString()
	{
		return (this.bindingPairs != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.bindingPairs, new Function<BindingPair, String >()
		{
			//esto seguro se puede hacer mejor
			public String apply(BindingPair bindingPair) { return bindingPair.toString(); }
		}
		
		
		)) : "");

		
	}
	
	public Set<BindingPair> getBindingPairs()
	{
		return this.bindingPairs;
	}

	
	public Set<String> getVariables() {
		//return new TreeSet<String>(variables);
		return null; //retorno todas las variables de los pairs
	}
	
	
	public Binding()
	{
		bindingPairs = new HashSet<BindingPair>();
	}
	
	/*
	public Binding(String binding, Set<String> variables)
	{
		this.binding = binding;
		this.variables = variables;
	}*/

}
