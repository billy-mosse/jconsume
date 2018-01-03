package ar.uba.dc.invariant.spec.compiler.compilation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;

import ar.uba.dc.invariant.InvariantProvider.Operation;
import ar.uba.dc.invariant.spec.compiler.CompiledClassInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;
import decorations.Binding;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.barvinok.expression.DomainSet;

public class DefaultClassInvariantProvider extends AbstractClassInvariantProvider implements CompiledClassInvariantProvider {

	private Map<String, DefaultMethodInvariantAndBindingProvider> providers = new HashMap<String, DefaultMethodInvariantAndBindingProvider>(); // signature -> provider
	
	public DefaultClassInvariantProvider(String forClass) {
		super(forClass);
	}
	
	private static Log log = LogFactory.getLog(DefaultClassInvariantProvider.class);

	public DomainSet getInvariant(Statement stmt) {
		log.debug(stmt.belongsTo().getSubSignature());
		DefaultMethodInvariantAndBindingProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		
		if (provider != null) {
			d = provider.getInvariant(stmt);
		}
		
		return d;
	}
	
	
	public Binding getBinding(Statement stmt) {
		log.debug(stmt.belongsTo().getSubSignature());
		DefaultMethodInvariantAndBindingProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		Binding b = new Binding();
		
		if (provider != null) {
			b = provider.getBinding(stmt);
		}
		
		return b;
	}
	
	public DomainSet getInvariantWithBinding(Statement stmt, Operation operation) {
		DefaultMethodInvariantAndBindingProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		
		if (provider != null) {
			d = provider.getInvariantWithBinding(stmt, operation);
		}
		
		
		
		return d;
	}
	
	
	public DomainSet getInvariant(Statement stmt, Operation operation) {
		DefaultMethodInvariantAndBindingProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		
		if (provider != null) {
			d = provider.getInvariant(stmt, operation);
		}
		
		
		
		return d;
	}

	public Set<String> getRelevantParameters(SootMethod method) {
		DefaultMethodInvariantAndBindingProvider provider = providers.get(method.getSubSignature());
		Set<String> params = new TreeSet<String>();
		
		if (provider != null) {
			params = provider.getParameters();
		}
		
		return params;
	}
	
	public Set<DerivedVariable> getNewRelevantParameters(SootMethod method) {
		DefaultMethodInvariantAndBindingProvider provider = providers.get(method.getSubSignature());
		Set<DerivedVariable> new_params = new TreeSet<DerivedVariable>();
		
		if (provider != null) {
			new_params = provider.getNewParameters();
		}
		
		return new_params;
	}

	public DomainSet getRequirements(SootMethod method) {
		DefaultMethodInvariantAndBindingProvider provider = providers.get(method.getSubSignature());
		DomainSet requirements = null;
		
		if (provider != null) {
			requirements = provider.getRequirements();
		}
		
		return requirements;
	}

	public boolean isLoopInvariant(Statement stmt) {
		DefaultMethodInvariantAndBindingProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		boolean ret = false;
		
		if (provider != null) {
			ret = provider.isLoopInvariant(stmt);
		}
		
		return ret;
	}

	public boolean captureAllPartitions(Statement stmt) {
		DefaultMethodInvariantAndBindingProvider provider = providers.get(stmt.belongsTo().getSubSignature());
		boolean ret = false;
		
		if (provider != null) {
			ret = provider.captureAllPartitions(stmt);
		}
		
		return ret;
	}

	public DefaultMethodInvariantAndBindingProvider get(String signature) {
		return providers.get(signature);
	}
	
	public void put(String signature, DefaultMethodInvariantAndBindingProvider methodProvider) {
		providers.put(signature, methodProvider);
	}
}
