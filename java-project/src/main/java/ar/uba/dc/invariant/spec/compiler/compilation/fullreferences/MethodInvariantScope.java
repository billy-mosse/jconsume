package ar.uba.dc.invariant.spec.compiler.compilation.fullreferences;

import java.util.Map;

import ar.uba.dc.invariant.spec.bean.InvariantSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;

/**
 * Este es el scope para los invariantes de metodo.
 * 
 * Un invariantes de metodo conoce a los invariantes de clase y a los propios invariantes
 * del metodo
 * 
 * @author testis
 */
public class MethodInvariantScope extends AbstractInvariantScope {

	protected Map<String, InvariantSpecification> methodInvariants;
	protected Map<InvariantSpecification, ConstraintsInfo> methodInvariantInfo; 
	
	public MethodInvariantScope(Map<String, InvariantSpecification> methodInvariants, Map<InvariantSpecification, ConstraintsInfo> methodInvariantInfo, ClassInvariantScope parent) {
		super(parent);
		this.methodInvariants = methodInvariants;
		this.methodInvariantInfo = methodInvariantInfo;
	}

	@Override
	protected InvariantAdapter search(String invariantId) {
		InvariantAdapter result = null;
		
		if (methodInvariants.containsKey(invariantId)) {
			InvariantSpecification spec = methodInvariants.get(invariantId);
			result = InvariantAdapter.adapt(spec, methodInvariantInfo.get(spec), this);
		}
		
		return result;
	}

}
