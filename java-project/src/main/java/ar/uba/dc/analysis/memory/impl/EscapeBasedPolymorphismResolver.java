package ar.uba.dc.analysis.memory.impl;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.memory.PolymorphismResolver;
import ar.uba.dc.analysis.common.code.CallStatement;

public class EscapeBasedPolymorphismResolver implements PolymorphismResolver {

	private SummaryRepository<EscapeSummary, SootMethod> escapeSummaryProvider;
	
	private ar.uba.dc.analysis.escape.PolymorphismResolver escapeResolver;
	
	@Override
	public SootClass getTarget(CallStatement callStmt) {
		return escapeResolver.getTarget(callStmt.getStatement(), escapeSummaryProvider.get(callStmt.belongsTo()));
	}

	public void setEscapeSummaryProvider(
			SummaryRepository<EscapeSummary, SootMethod> escapeSummaryProvider) {
		this.escapeSummaryProvider = escapeSummaryProvider;
	}

	public void setEscapeResolver(ar.uba.dc.analysis.escape.PolymorphismResolver escapeResolver) {
		this.escapeResolver = escapeResolver;
	}

}
