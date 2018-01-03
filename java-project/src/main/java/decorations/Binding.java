package decorations;

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

		bindingPairs = new TreeSet<BindingPair>();
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
		return (this.bindingPairs != null ? Joiner.on(" and ").skipNulls().join(Iterables.transform(this.bindingPairs, new Function<BindingPair, String >()
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
	
	public void addBindingPairs(Set<BindingPair> newBindingPairs)
	{
		this.bindingPairs.addAll(newBindingPairs);
	}

	
	public Set<String> getVariables() {

		TreeSet<String> variables = new TreeSet<String>();
		
		for(BindingPair bindingPair : this.bindingPairs)
		{
			variables.add(bindingPair.getCallee_parameter_with_prefix());
			variables.add(bindingPair.getCaller_parameter());
		}
		
		return variables;
	}
	
	public Set<String> getCalleesAsVariables() {

		TreeSet<String> variables = new TreeSet<String>();
		
		for(BindingPair bindingPair : this.bindingPairs)
		{
			variables.add(bindingPair.getCallee_parameter_with_prefix());
			//variables.add(bindingPair.getCaller_parameter());
		}
		
		return variables;
	}
	

	public Set<String> getCalleesAsInductives() {

		TreeSet<String> inductives = new TreeSet<String>();
		
		for(BindingPair bindingPair : this.bindingPairs)
		{
			inductives.add(bindingPair.getCallee_parameter_with_prefix());
		}
		
		return inductives;
	}
	
	
	public Set<String> getCallersAsVariables() {

		TreeSet<String> variables = new TreeSet<String>();
		
		for(BindingPair bindingPair : this.bindingPairs)
		{
			variables.add(bindingPair.getCaller_parameter());
			//variables.add(bindingPair.getCaller_parameter());
		}
		
		return variables;
	}
	

	public Set<String> getCallersAsInductives() {

		TreeSet<String> inductives = new TreeSet<String>();
		
		for(BindingPair bindingPair : this.bindingPairs)
		{
			inductives.add(bindingPair.getCaller_parameter());
		}
		
		return inductives;
	}
	
	public Binding()
	{
		bindingPairs = new TreeSet<BindingPair>();
	}
	
	/*
	public Binding(String binding, Set<String> variables)
	{
		this.binding = binding;
		this.variables = variables;
	}*/

}
