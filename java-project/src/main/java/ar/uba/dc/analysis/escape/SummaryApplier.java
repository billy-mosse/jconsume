package ar.uba.dc.analysis.escape;

import soot.jimple.Stmt;
import ar.uba.dc.soot.Box;

public interface SummaryApplier {

	public void applySummary(Box<EscapeSummary> caller, Stmt callStmt, EscapeSummary callee, Box<EscapeSummary> accum);
	
}
