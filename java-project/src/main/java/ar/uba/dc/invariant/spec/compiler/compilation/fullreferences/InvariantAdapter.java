package ar.uba.dc.invariant.spec.compiler.compilation.fullreferences;

import java.util.Set;

import ar.uba.dc.invariant.spec.bean.CallSiteSpecification;
import ar.uba.dc.invariant.spec.bean.CreationSiteSpecification;
import ar.uba.dc.invariant.spec.bean.InvariantSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.constraints.ReferenceResolver;

/**
 * Me permite ver a los {@link CallSiteSpecification}, {@link CreationSiteSpecification} e {@link InvariantSpecification}
 * Como un solo tipo de objeto y entenderlos de la misma forma a efectos de resolver las referencias
 * 
 * @author testis
 */
public abstract class InvariantAdapter {

	protected AbstractInvariantScope scope;
	protected ConstraintsInfo constraintsInfo;
	
	public InvariantAdapter(ConstraintsInfo constraintsInfo, AbstractInvariantScope scope) {
		this.constraintsInfo = constraintsInfo;
		this.scope = scope;
	}
	
	public static InvariantAdapter adapt(InvariantSpecification spec, ConstraintsInfo constraintsInfo, ClassInvariantScope classInvariantScope) {
		return new ClassInvariantAdapter(spec, constraintsInfo, classInvariantScope);
	}
	
	public static InvariantAdapter adapt(InvariantSpecification spec, ConstraintsInfo constraintsInfo, MethodInvariantScope methodInvariantScope) {
		return new MethodInvariantAdapter(spec, constraintsInfo, methodInvariantScope);
	}

	public static InvariantAdapter adapt(SiteSpecification site, ConstraintsInfo constraintsInfo, SiteInvariantScope siteScope) {
		return new SiteInvariantAdapter(site, constraintsInfo, siteScope);
	}

	public Set<String> getReferences() {
		return constraintsInfo.getReferences();
	}
	
	public void addAllVariables(Set<String> variables) {
		constraintsInfo.addAllVariables(variables);
	}

	public AbstractInvariantScope getScope() {
		return scope;
	}

	public void remove(String invariantId) {
		this.getReferences().remove(invariantId);	
	}
	
	@Override
	public String toString() {
		return getId() + ": " + getConstraints();
	}
	
	/**
	 * Reemplaza en el invariante <code>target</code> el invariante representado por la instancia actual.
	 * 
	 * Las constraints del invariante <code>target</code> seran modificadas para resolver aquellas referencias
	 * al invariante representado por la instancia actual.
	 * 
	 * @param target - Invariante sobre el que se desea hacer el reemplazo
	 * @param methodSpec - Metodo al que pertencece el invariante target
	 * @param refResolver - Resolver de referencias que puede ser usado como soporte para el reemplazo de referencias por constraints
	 */
	public abstract void replaceIn(InvariantAdapter target, MethodSpecification methodSpec, ReferenceResolver refResolver);
	
	/**
	 * Devuelve un ID que sirve para identificar dentro de la especificacion al Invariante.
	 */
	public abstract String getId();	
	
	protected abstract void setConstraints(String constraints);
	
	protected abstract String getConstraints();
}
