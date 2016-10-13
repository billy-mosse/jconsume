package ar.uba.dc.analysis.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import soot.SootMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.config.Container;

public class ChainSummaryRepository<T, S> implements SummaryRepository<T, S>, Container<SummaryRepository<T, S>> {

	private List<SummaryRepository<T,S>> repositories = new LinkedList<SummaryRepository<T,S>>();

	public boolean contains(S method) {
		boolean result = false;
		
		Iterator<SummaryRepository<T,S>> itRepositories = repositories.iterator();
		
		while (itRepositories.hasNext() && !result) {
			result = itRepositories.next().contains(method);
		}

		return result;
	}

	public T get(S method) {
		T summary = null;
		Iterator<SummaryRepository<T, S>> itRepositories = repositories.iterator();
		
		while (itRepositories.hasNext() && summary == null) {
			summary = itRepositories.next().get(method);
		}
		
		return summary;
	}
	
	public void register(SummaryRepository<T,S> repository) {
		repositories.add(repository);
	}

	public void unregister(SummaryRepository<T,S> repository) {
		repositories.remove(repository);
	}
	public String toString() {
		String res = "";
		Iterator<SummaryRepository<T,S>> itRepositories = repositories.iterator();
		
		while (itRepositories.hasNext() ) {
			SummaryRepository<T,S> sr =  itRepositories.next();
			res+=sr.toString()+" ";
		}
		return res;
	}
}