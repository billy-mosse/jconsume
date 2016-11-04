package ar.uba.dc.analysis.common;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.config.Container;

public class PaperChainSummaryRepository <T, S> implements SummaryRepository<T, S>, Container<SummaryRepository<T, S>>{

	@Override
	public void register(SummaryRepository<T, S> o) {
		throw new NotImplementedException();
		
	}

	@Override
	public void unregister(SummaryRepository<T, S> o) {
		throw new NotImplementedException();
		
	}

	@Override
	public T get(S method) {
		throw new NotImplementedException();
	}

	@Override
	public boolean contains(S method) {
		throw new NotImplementedException();
	}
	
	

}
