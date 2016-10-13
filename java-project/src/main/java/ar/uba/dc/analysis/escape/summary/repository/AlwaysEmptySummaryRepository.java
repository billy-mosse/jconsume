package ar.uba.dc.analysis.escape.summary.repository;

import soot.SootMethod;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;

/**
 * Esta implementacion devuelve siempre un summary vacio. Es util para testing
 * 
 * @author testis
 */
public class AlwaysEmptySummaryRepository implements SummaryRepository<EscapeSummary, SootMethod> {

	public EscapeSummary get(SootMethod method) {
		return new EscapeSummary(method);
	}

	public boolean contains(SootMethod m) {
		return true;
	}
	
}
