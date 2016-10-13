package ar.uba.dc.analysis.escape.summary.repository;

import soot.SootMethod;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;

/**
 * Esta implementacion devuelve siempre null. Es util para hacer tests
 * 
 * @author testis
 */
public class AlwaysNullSummaryRepository implements SummaryRepository<EscapeSummary, SootMethod> {

	public EscapeSummary get(SootMethod method) {
		return null;
	}

	public boolean contains(SootMethod m) {
		return false;
	}
}
