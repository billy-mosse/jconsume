package ar.uba.dc.barvinok.expression;

import ar.uba.dc.barvinok.expression.operation.DomainUnifier;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultDomainUnifier;
import decorations.Binding;

public class DomainSetUtils {

	public static void unify(DomainSet invariant, Binding binding)
	{
		//TODO: usar fatory-config
		
		DomainUnifier unif = new DefaultDomainUnifier();		
		
		if(binding != null && binding.getBindingPairs().size() > 0)
		{
			String s = "";
			s = "jose";
			s += "jososo";
		}
		
		String newConstraints = unif.unify(invariant.getConstraints(), binding.toString());	
		
		
		
		if(binding.getVariables() != null)
			invariant.addAllVariables(binding.getVariables());
		
		invariant.setConstraints(newConstraints);		
	}
	
}
