package ar.uba.dc.analysis.common;

import java.util.Iterator;
import java.util.Map;

import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;

import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.Transformer;

public class RepositoryLoaderTransformer extends SceneTransformer {
	
	private RAMSummaryRepository target;
	private SummaryRepository<EscapeSummary> source;
	
	public RepositoryLoaderTransformer(SummaryRepository<EscapeSummary> source, RAMSummaryRepository target) {
		this.source = source;
		this.target = target;
	}

	public static Transformer v(RAMSummaryRepository target, SummaryRepository<EscapeSummary> source) {
		return new RepositoryLoaderTransformer(source, target);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void internalTransform(String phaseName, Map options) {
	
		Iterator<SootClass> classIt = Scene.v().getClasses(SootClass.BODIES).iterator();
		while(classIt.hasNext()) {
			SootClass cl = classIt.next();
	        Iterator<SootMethod> methodIt = cl.methodIterator();
			while (methodIt.hasNext()) {
				SootMethod method = methodIt.next();
				target.put(method, source.get(method));
			}
		}	
	}

}
