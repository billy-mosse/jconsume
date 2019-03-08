package ar.uba.dc.barvinok.expression;

import java.util.Set;
import java.util.TreeSet;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
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
		//if(binding.getCallees() != null)
			//invariant.addAllInductives(binding.getCallees());
		
		if(binding.getCallees() != null)
			invariant.addAllVariables(binding.getCallees());
		
		
		//que pasa si agrego las callers como inductives tambien???
		//TODO REVISAR
		
		if(binding.getCallers() != null)
			invariant.addAllVariables(binding.getCallers());
		
		//if(binding.getCallers() != null)
			//invariant.addAllInductives(binding.getCallers());
		
		
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
	
	
	
	//Esto no se va a poder hacer porque barvinok solo soporta cosas lineales
	//mejor...hacer un LineWithConsumption o algo asi
	/*public static String dummyInvariantFromParametricExpression(ParametricExpression expr)
	{
		DomainSet inv = new DomainSet();
		
		inv.addAllParameters(expr.getParameters());
		BarvinokParametricExpression bexpr = (BarvinokParametricExpression) expr;
		
		for( QuasiPolynomial q : bexpr.getExpression().getPieces())
		{
			QuasiPolynomial
			q.getPolynomial();
			q.getConstraints();
		}
		
		return null;
		
	}*/
	
}
