package ar.uba.dc.invariant.spec.compiler.compilation.fullreferences;

import java.util.Map;

import ar.uba.dc.invariant.spec.bean.InvariantSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;

/**
 * Un invariante de clase solo conoce a otros invariantes de clase.
 * 
 * @author testis
 */
public class ClassInvariantScope extends AbstractInvariantScope {

	protected Map<String, InvariantSpecification> classInvariants;
	protected Map<InvariantSpecification, ConstraintsInfo> classInvariantInfo;
	
	public ClassInvariantScope(Map<String, InvariantSpecification> classInvariants, Map<InvariantSpecification, ConstraintsInfo> classInvariantInfo) {
		super(null);
		this.classInvariants = classInvariants; 
		this.classInvariantInfo = classInvariantInfo;
	}

	@Override
	protected InvariantAdapter search(String invariantId) {
		InvariantAdapter result = null;
		
		if (classInvariants.containsKey(invariantId)) {
			InvariantSpecification spec = classInvariants.get(invariantId);
			result = InvariantAdapter.adapt(spec, classInvariantInfo.get(spec), this);
		}
		
		return result;
	}	
}
