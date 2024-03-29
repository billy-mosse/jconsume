package ar.uba.dc.invariant.spec.compiler.compilation.fullreferences;

import ar.uba.dc.invariant.spec.bean.InvariantSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.constraints.ReferenceResolver;

public class MethodInvariantAdapter extends InvariantAdapter {

	protected InvariantSpecification spec;
	
	public MethodInvariantAdapter(InvariantSpecification spec, ConstraintsInfo constraintsInfo, MethodInvariantScope methodInvariantScope) {
		super(constraintsInfo, methodInvariantScope);
		this.spec = spec;
	}

	@Override
	public String getId() {
		return spec.getId();
	}

	@Override
	public void replaceIn(InvariantAdapter target, MethodSpecification methodSpec, ReferenceResolver refResolver) {
		target.addAllVariables(constraintsInfo.getVariables());
		target.setConstraints(refResolver.resolve(target.getConstraints(), getId(), this.getConstraints()));
	}

	@Override
	protected String getConstraints() {
		return spec.getConstraints();
	}

	@Override
	protected void setConstraints(String constraints) {
		spec.setConstraints(constraints);
	}
	
	@Override
	public boolean equals(Object to) {
	    if ( this == to ) return true;

	    if ( !(to instanceof MethodInvariantAdapter) ) return false;

	    MethodInvariantAdapter that = (MethodInvariantAdapter) to;

	    return this.spec.equals(that.spec);
	}

	@Override
	public int hashCode() {
		return spec.hashCode();
	}
}
