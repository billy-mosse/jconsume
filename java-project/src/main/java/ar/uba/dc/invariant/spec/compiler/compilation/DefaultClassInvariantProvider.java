package ar.uba.dc.invariant.spec.compiler.compilation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import soot.SootMethod;

import ar.uba.dc.invariant.InvariantProvider.Operation;
import ar.uba.dc.invariant.spec.compiler.CompiledClassInvariantProvider;
import ar.uba.dc.analysis.memory.code.Statement;
import ar.uba.dc.barvinok.expression.DomainSet;

public class DefaultClassInvariantProvider extends AbstractClassInvariantProvider implements CompiledClassInvariantProvider {

	private Map<String, DefaultMethodInvariantProvider> providers = new HashMap<String, DefaultMethodInvariantProvider>(); // signature -> provider
	
	public DefaultClassInvariantProvider(String forClass) {
		super(forClass);
	}

	public DomainSet getInvariant(Statement stmt) {
		DefaultMethodInvariantProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		
		if (provider != null) {
			d = provider.getInvariant(stmt);
		}
		
		return d;
	}
	
	public DomainSet getInvariant(Statement stmt, Operation operation) {
		DefaultMethodInvariantProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		
		if (provider != null) {
			d = provider.getInvariant(stmt, operation);
		}
		
		return d;
	}

	public Set<String> getRelevantParameters(SootMethod method) {
		DefaultMethodInvariantProvider provider = providers.get(method.getSubSignature());
		Set<String> params = new TreeSet<String>();
		
		if (provider != null) {
			params = provider.getParameters();
		}
		
		return params;
	}

	public DomainSet getRequirements(SootMethod method) {
		DefaultMethodInvariantProvider provider = providers.get(method.getSubSignature());
		DomainSet requirements = null;
		
		if (provider != null) {
			requirements = provider.getRequirements();
		}
		
		return requirements;
	}

	public boolean isLoopInvariant(Statement stmt) {
		DefaultMethodInvariantProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		boolean ret = false;
		
		if (provider != null) {
			ret = provider.isLoopInvariant(stmt);
		}
		
		return ret;
	}

	public boolean captureAllPartitions(Statement stmt) {
		DefaultMethodInvariantProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		boolean ret = false;
		
		if (provider != null) {
			ret = provider.captureAllPartitions(stmt);
		}
		
		return ret;
	}

	public DefaultMethodInvariantProvider get(String signature) {
		return providers.get(signature);
	}
	
	public void put(String signature, DefaultMethodInvariantProvider methodProvider) {
		providers.put(signature, methodProvider);
	}
}
