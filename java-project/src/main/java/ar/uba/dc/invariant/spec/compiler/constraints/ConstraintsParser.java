package ar.uba.dc.invariant.spec.compiler.constraints;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;
import ar.uba.dc.util.List;

public interface ConstraintsParser {

	public ConstraintsInfo parse(String constraints);
	public void parse(SiteSpecification site, ConstraintsInfo info, Set<String> parameters, Set<DerivedVariable> new_parameters);
}
