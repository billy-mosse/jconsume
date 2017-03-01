package ar.uba.dc.barvinok.expression;

import java.util.Set;
import java.util.TreeSet;

import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.operation.DomainUnifier;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultDomainUnifier;
import decorations.Binding;

public class DomainSetUtils {

	public static void unify(DomainSet invariant, Binding binding)
	{
		//TODO: usar fatory-config
		
		DomainUnifier unif = new DefaultDomainUnifier();		
		
		String newConstraints = unif.unify(invariant.getConstraints(), binding.toString());	
		
		
		//Esto esta re mal. Pensar como deberia quedar realmente!
		if(binding.getCalleesAsVariables() != null)
			invariant.addAllVariables(binding.getCalleesAsVariables());
		
		if(binding.getCalleesAsInductives() != null)
			invariant.addAllInductives(binding.getCalleesAsInductives());
		
		invariant.setConstraints(newConstraints);		
	}

	public static Set<String> removePrefix(Set<String> variables) {
		Set<String> new_vars = new TreeSet<String>();
		for(String s : variables)
		{
			if(s.startsWith("$t."))
				new_vars.add(s.substring(3));
			else
				new_vars.add(s);				
		}
		return new_vars;
	}
	
}
