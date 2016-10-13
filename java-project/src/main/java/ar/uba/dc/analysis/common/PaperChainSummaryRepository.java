package ar.uba.dc.analysis.common;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.config.Container;
import soot.SootMethod;

public class PaperChainSummaryRepository <T, S> implements SummaryRepository<T, S>, Container<SummaryRepository<T, S>>{

	@Override
	public void register(SummaryRepository<T, S> o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregister(SummaryRepository<T, S> o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T get(S method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(S method) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
