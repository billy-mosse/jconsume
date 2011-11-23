package ar.uba.dc.analysis.escape;

import ar.uba.dc.soot.Box;

public interface SummarySimplifier {

	void simplify(Box<EscapeSummary> box);

}
