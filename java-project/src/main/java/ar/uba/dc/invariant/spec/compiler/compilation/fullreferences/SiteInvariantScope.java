package ar.uba.dc.invariant.spec.compiler.compilation.fullreferences;

import java.util.Map;

import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;

/**
 * Este es el scope para los sites que tienen ID y por tanto son referenciados como invariantes.
 * 
 * Estos sites pueden referenciar a los invariantes de metodo y de clase.
 * 
 * @author testis
 */
public class SiteInvariantScope extends AbstractInvariantScope {

	protected Map<String, SiteSpecification> siteInvariants;
	protected Map<SiteSpecification, ConstraintsInfo> sitesInfo;
	
	public SiteInvariantScope(Map<String, SiteSpecification> siteInvariants, Map<SiteSpecification, ConstraintsInfo> sitesInfo, MethodInvariantScope methodScope) {
		super(methodScope);
		this.siteInvariants = siteInvariants;
		this.sitesInfo = sitesInfo;
	}

	@Override
	protected InvariantAdapter search(String invariantId) {
		InvariantAdapter result = null;
		
		if (siteInvariants.containsKey(invariantId)) {
			SiteSpecification spec = siteInvariants.get(invariantId);
			result = InvariantAdapter.adapt(spec, sitesInfo.get(spec), this);
		}
		
		return result;
	}
}
