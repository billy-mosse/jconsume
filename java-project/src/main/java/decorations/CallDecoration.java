package decorations;

import ar.uba.dc.barvinok.expression.DomainSet;

/**
 * Esta clase jamas va a ser usada
 * @author billy
 *
 */
public class CallDecoration {
	protected DomainSet invariant;
	protected Binding binding;
	
	public CallDecoration(DomainSet invariant, Binding binding)
	{
		this.invariant = invariant;
		this.binding = binding;
	}
	
	
	public DomainSet getInvariant() {
		return invariant;
	}
	public void setInvariant(DomainSet invariant) {
		this.invariant = invariant;
	}
	public Binding getBinding() {
		return binding;
	}
	public void setBinding(Binding binding) {
		this.binding = binding;
	}

}
