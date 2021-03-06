package ar.uba.dc.invariant.spec.compiler.compilation;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import soot.SootMethod;

import ar.uba.dc.invariant.InvariantProvider.Operation;
import ar.uba.dc.invariant.spec.compiler.CompiledClassInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;
import decorations.Binding;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.barvinok.expression.DomainSet;

public class AllwaysEmptyInvariantProvider extends AbstractClassInvariantProvider implements CompiledClassInvariantProvider {

	public AllwaysEmptyInvariantProvider(String forClass) {
		super(forClass);
	}

	public DomainSet getInvariant(Statement stmt) {
		return new DomainSet(StringUtils.EMPTY);
	}
	
	public Binding getBinding(Statement stmt) {
		return new Binding();
	}

	public Set<String> getRelevantParameters(SootMethod method) {
		return new TreeSet<String>();
	}
	
	public Set<DerivedVariable> getNewRelevantParameters(SootMethod method) {
		return new TreeSet<DerivedVariable>();
	}

	public DomainSet getRequirements(SootMethod method) {
		return null;
	}

	public boolean isLoopInvariant(Statement stmt) {
		return false;
	}

	@Override
	public boolean captureAllPartitions(Statement stmt) {
		return false;
	}

	@Override
	public DomainSet getInvariantWithBinding(Statement stmt, Operation operation) {
		return getInvariant(stmt);
	}
	
	@Override
	public DomainSet getInvariant(Statement stmt, Operation operation) {
		return getInvariant(stmt);
	}
}
