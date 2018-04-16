package ar.uba.dc.analysis.memory;

import java.util.Set;

import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.expression.DomainSet;

public interface CountingTheory {

	public ParametricExpression count(Statement stmt);
	
	//ya no es mas solamente newLine
	public ParametricExpression count(Line line);
	
	public Set<String> getUnboundedInductives(DomainSet inv);
	public Set<String> getUnboundedBindingVariables(DomainSet lineInvariant, Set<String> calleeVariables);

}
