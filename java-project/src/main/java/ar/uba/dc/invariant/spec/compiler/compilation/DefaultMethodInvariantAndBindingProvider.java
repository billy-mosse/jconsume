package ar.uba.dc.invariant.spec.compiler.compilation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.memory.code.impl.visitor.CalledImplementationVisitor;
import ar.uba.dc.analysis.memory.code.impl.visitor.StatementTypeVisitor;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.operation.DomainUnifier;
import ar.uba.dc.invariant.InvariantProvider.Operation;
import ar.uba.dc.invariant.spec.compiler.compilation.InvariantId.Type;
import decorations.Binding;
import decorations.CallDecoration;

/**
 * Resultado de compilar una especificacion. Permite acceder facilmente a los
 * invariantes de un metodo
 * 
 * @author testis
 */
public class DefaultMethodInvariantAndBindingProvider {

	
	private static Log log = LogFactory.getLog(DefaultMethodInvariantAndBindingProvider.class);
	protected Set<String> parameters = new TreeSet<String>();
	protected DomainSet requirements = null;
	
	
	private DomainUnifier domainUnifier;
	
	protected Map<InvariantId, CallDecoration> invariants = new HashMap<InvariantId, CallDecoration>(); // offset -> invariant
	protected Set<InvariantId> loopInvariant = new HashSet<InvariantId>();
	protected Set<InvariantId> captureAllPartitions = new HashSet<InvariantId>();
		
	public DomainSet getInvariant(Statement stmt) {
		return getInvariant(stmt, null);
	}
	
	public Binding getBinding(Statement stmt) {
		return getBinding(stmt, null);
	}
	
	public DomainSet getInvariantWithBinding(Statement stmt, Operation operation) {
		String implementation = stmt.apply(CalledImplementationVisitor.INSTANCE);
		Type type = stmt.apply(StatementTypeVisitor.INSTANCE);
		
		DomainSet invariant = null;

		// Primero probamos recuperar con el operador + la implementacion
		if (operation != null && StringUtils.isNotBlank(operation.getString())) {
			invariant = getInvariantAndBinding(new InvariantId(type, stmt.getCounter(), implementation, operation.getString()));
			
			// Si no encontramos buscamos operador sin implementacion
			if (invariant == null) {
				invariant = getInvariantAndBinding(new InvariantId(type, stmt.getCounter(), StringUtils.EMPTY, operation.getString()));
			}
		}
		
		// Si no encontramos buscamos solo con implementacion
		if (invariant == null) {
			invariant = getInvariantAndBinding(new InvariantId(type, stmt.getCounter(), implementation));
		}
		
		// Si no encontramos, buscamos sin implementacion (el default)
		if (invariant == null) {
			invariant = getInvariantAndBinding(new InvariantId(type, stmt.getCounter()));
		}
		
		// Si no encontramos nada, devolvemos los requires del metodo
		if (invariant == null) {
			invariant = requirements;
		}
		
		// Si asi y todo no encontramos nada, devolvemos un invariante vacio
		if (invariant == null) {
			invariant = new DomainSet(StringUtils.EMPTY);
		}
		
		return invariant;
	}
	
	
	public DomainSet getInvariant(Statement stmt, Operation operation) {
		String implementation = stmt.apply(CalledImplementationVisitor.INSTANCE);
		Type type = stmt.apply(StatementTypeVisitor.INSTANCE);
		
		DomainSet invariant = null;

		// Primero probamos recuperar con el operador + la implementacion
		if (operation != null && StringUtils.isNotBlank(operation.getString())) {
			invariant = getInvariant(new InvariantId(type, stmt.getCounter(), implementation, operation.getString()));
			
			// Si no encontramos buscamos operador sin implementacion
			if (invariant == null) {
				invariant = getInvariant(new InvariantId(type, stmt.getCounter(), StringUtils.EMPTY, operation.getString()));
			}
		}
		
		// Si no encontramos buscamos solo con implementacion
		if (invariant == null) {
			invariant = getInvariant(new InvariantId(type, stmt.getCounter(), implementation));
		}
		
		// Si no encontramos, buscamos sin implementacion (el default)
		if (invariant == null) {
			invariant = getInvariant(new InvariantId(type, stmt.getCounter()));
		}
		
		// Si no encontramos nada, devolvemos los requires del metodo
		if (invariant == null) {
			invariant = requirements;
		}
		
		// Si asi y todo no encontramos nada, devolvemos un invariante vacio
		if (invariant == null) {
			invariant = new DomainSet(StringUtils.EMPTY);
		}
		
		return invariant;
	}
	
	
	//probablemente esto este mal
	public Binding getBinding(Statement stmt, Operation operation) {
		String implementation = stmt.apply(CalledImplementationVisitor.INSTANCE);
		Type type = stmt.apply(StatementTypeVisitor.INSTANCE);
		
		Binding binding = null;

		// Primero probamos recuperar con el operador + la implementacion
		if (operation != null && StringUtils.isNotBlank(operation.getString())) {
			binding = getBinding(new InvariantId(type, stmt.getCounter(), implementation, operation.getString()));
			
			// Si no encontramos buscamos operador sin implementacion
			if (binding == null) {
				binding = getBinding(new InvariantId(type, stmt.getCounter(), StringUtils.EMPTY, operation.getString()));
			}
		}
		
		// Si no encontramos buscamos solo con implementacion
		if (binding == null) {
			binding = getBinding(new InvariantId(type, stmt.getCounter(), implementation));
		}
		
		// Si no encontramos, buscamos sin implementacion (el default)
		if (binding == null) {
			binding = getBinding(new InvariantId(type, stmt.getCounter()));
		}
		
		
		// Si asi y todo no encontramos nada, devolvemos un invariante vacio
		if (binding == null) {
			binding = new Binding();
		}
		
		return binding;
	}
	
	
	
	public DomainSet getInvariant(InvariantId invariantId) {
		CallDecoration calldec = invariants.get(invariantId);
		
		if(calldec != null) 
			return calldec.getInvariant();
		else
			return null;
	}
	
	public Binding getBinding(InvariantId invariantId)
	{
		CallDecoration calldec = invariants.get(invariantId);
		
		if(calldec != null) 
			return calldec.getBinding();
		else
			return null;
	}
	
	
	
	
	//funcion horrible. odio tener que chequear nulls	
	public DomainSet getInvariantAndBinding(InvariantId invariantId) {
 		CallDecoration calldec = invariants.get(invariantId);
		
		if(calldec != null) 
		{
			DomainSet inv = calldec.getInvariant().clone();			
			Binding binding = calldec.getBinding();
			
			
			if(binding != null)
			{
				
				
				log.debug(binding.toString());
				inv.setConstraints(this.domainUnifier.unify(calldec.getBinding().toString(), inv.getConstraints()));
				inv.addAllVariables(binding.getVariables());
				return inv;
			}
			else
			{
				return inv;
			}
		}
		return null;
	}

	public void putInvariant(InvariantId invariantId, CallDecoration callDecoration) {
		invariants.put(invariantId, callDecoration);		
	}
	
	public boolean isLoopInvariant(Statement stmt) {
		Type type = stmt.apply(StatementTypeVisitor.INSTANCE);
		return loopInvariant.contains(new InvariantId(type, stmt.getCounter()));
	}
	
	public void addLoopInvariant(InvariantId invariantId) {
		loopInvariant.add(invariantId);
	}
	
	public boolean captureAllPartitions(Statement stmt) {		
		Type type = stmt.apply(StatementTypeVisitor.INSTANCE);
		return getCaptureAllPartitions(new InvariantId(type, stmt.getCounter()));
	}
	
	public boolean getCaptureAllPartitions(InvariantId invariantId) {
		return captureAllPartitions.contains(invariantId);
	}
	
	public void addCaptureAllPartitions(InvariantId invariantId) {
		captureAllPartitions.add(invariantId);	
	}

	public Set<Long> callOffsets() {
		return getOffsets(Type.CALL);
	}
	
	public Set<Long> creationOffsets() {
		return getOffsets(Type.NEW);
	}
	
	protected Set<Long> getOffsets(Type type) {
		Set<Long> result = new HashSet<Long>();
		
		for (InvariantId id : invariants.keySet()) {
			if (type.equals(id.getType())) {
				result.add(id.getOffset());
			}
		}
		
		return result;
	}
	
	public DomainSet getCallInvariant(Long offset) {
		return getInvariant(new InvariantId(Type.CALL, offset));
	}

	public DomainSet getCreationInvariant(Long offset) {
		return getInvariant(new InvariantId(Type.NEW, offset));
	}

	public void setParameters(Set<String> params) {
		this.parameters = params;
	}
	
	public Set<String> getParameters() {
		return new TreeSet<String>(parameters);
	}

	public void setRequirements(DomainSet requirements) {
		this.requirements = requirements;
	}

	public DomainSet getRequirements() {
		return requirements;
	}

	@SuppressWarnings("unchecked")
	public Set<InvariantId> getInvariantIdSet() {
		Comparator<InvariantId>[] comparators = new Comparator[4];
		comparators[0] = ComparatorUtils.reversedComparator(new BeanComparator("type"));
		comparators[1] = new BeanComparator("offset");
		comparators[2] = new BeanComparator("implementation");
		comparators[3] = new BeanComparator("operator");
		
		Set<InvariantId> ret = new TreeSet<InvariantId>(ComparatorUtils.chainedComparator(comparators));
		
		ret.addAll(invariants.keySet());
		
		return ret;
	}

	public DomainUnifier getDomainUnifier() {
		return domainUnifier;
	}

	public void setDomainUnifier(DomainUnifier domainUnifier) {
		this.domainUnifier = domainUnifier;
	}
}