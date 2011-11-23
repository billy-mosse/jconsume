package ar.uba.dc.invariant.spec.compiler.constraints.resolver;

import ar.uba.dc.invariant.spec.compiler.constraints.ReferenceResolver;

public class RegexpReferenceResolver implements ReferenceResolver {

	public String resolve(String target, String id, String constraints) {
		
		return target.replaceAll("\\B@" + id + "\\b", constraints.replaceAll("\\$", "\\\\\\$"));
	}

}
