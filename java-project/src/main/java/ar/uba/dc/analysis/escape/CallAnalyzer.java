package ar.uba.dc.analysis.escape;

import soot.jimple.Stmt;
import ar.uba.dc.soot.Box;

public interface CallAnalyzer {

	void analyseCall(Box<EscapeSummary> src, Stmt callStmt, Box<EscapeSummary> dst);
	
}
