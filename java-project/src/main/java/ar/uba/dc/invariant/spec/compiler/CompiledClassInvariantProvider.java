package ar.uba.dc.invariant.spec.compiler;

import java.util.Set;

import soot.SootMethod;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider.Operation;
import decorations.Binding;

public interface CompiledClassInvariantProvider {

	boolean forClass(String className);

	DomainSet getInvariant(Statement stmt);
	
	Binding getBinding(Statement stmt);

	Set<String> getRelevantParameters(SootMethod method);

	DomainSet getRequirements(SootMethod method);

	boolean isLoopInvariant(Statement stmt);

	boolean captureAllPartitions(Statement stmt);

	DomainSet getInvariantWithBinding(Statement stmt, Operation operation);
	DomainSet getInvariant(Statement stmt, Operation operation);

}
