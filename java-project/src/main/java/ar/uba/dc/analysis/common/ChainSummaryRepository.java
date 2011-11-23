package ar.uba.dc.analysis.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import soot.SootMethod;
import ar.uba.dc.config.Container;

public class ChainSummaryRepository<T> implements SummaryRepository<T>, Container<SummaryRepository<T>> {

	private List<SummaryRepository<T>> repositories = new LinkedList<SummaryRepository<T>>();

	public boolean contains(SootMethod method) {
		boolean result = false;
		
		Iterator<SummaryRepository<T>> itRepositories = repositories.iterator();
		
		while (itRepositories.hasNext() && !result) {
			result = itRepositories.next().contains(method);
		}

		return result;
	}

	public T get(SootMethod method) {
		T summary = null;
		Iterator<SummaryRepository<T>> itRepositories = repositories.iterator();
		
		while (itRepositories.hasNext() && summary == null) {
			summary = itRepositories.next().get(method);
		}
		
		return summary;
	}
	
	public void register(SummaryRepository<T> repository) {
		repositories.add(repository);
	}

	public void unregister(SummaryRepository<T> repository) {
		repositories.remove(repository);
	}
	
}