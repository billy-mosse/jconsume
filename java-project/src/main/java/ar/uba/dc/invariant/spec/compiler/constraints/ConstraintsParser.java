package ar.uba.dc.invariant.spec.compiler.constraints;

import java.util.Set;

import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;

public interface ConstraintsParser {

	public ConstraintsInfo parse(String constraints);
	public void parse(SiteSpecification site, ConstraintsInfo info, Set<String> parameters, Set<DerivedVariable> new_parameters);
}
