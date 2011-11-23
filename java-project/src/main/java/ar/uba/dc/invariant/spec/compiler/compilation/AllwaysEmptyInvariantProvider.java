package ar.uba.dc.invariant.spec.compiler.compilation;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import soot.SootMethod;

import ar.uba.dc.invariant.InvariantProvider.Operation;
import ar.uba.dc.invariant.spec.compiler.CompiledClassInvariantProvider;
import ar.uba.dc.analysis.memory.code.Statement;
import ar.uba.dc.barvinok.expression.DomainSet;

public class AllwaysEmptyInvariantProvider extends AbstractClassInvariantProvider implements CompiledClassInvariantProvider {

	public AllwaysEmptyInvariantProvider(String forClass) {
		super(forClass);
	}

	public DomainSet getInvariant(Statement stmt) {
		return new DomainSet(StringUtils.EMPTY);
	}

	public Set<String> getRelevantParameters(SootMethod method) {
		return new TreeSet<String>();
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
	public DomainSet getInvariant(Statement stmt, Operation operation) {
		return getInvariant(stmt);
	}
}
