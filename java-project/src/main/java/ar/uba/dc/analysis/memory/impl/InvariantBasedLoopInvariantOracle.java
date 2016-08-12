package ar.uba.dc.analysis.memory.impl;

import ar.uba.dc.analysis.memory.LoopInvariantOracle;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.invariant.InvariantProvider;

public class InvariantBasedLoopInvariantOracle implements LoopInvariantOracle {

	InvariantProvider invariantProvider;
	
	@Override
	public boolean isLoopInvariant(CallStatement callStmt) {
		return invariantProvider.isLoopInvariant(callStmt);
	}

	public void setInvariantProvider(InvariantProvider invariantProvider) {
		this.invariantProvider = invariantProvider;
	}
}
