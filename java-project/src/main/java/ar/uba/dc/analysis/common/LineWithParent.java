package ar.uba.dc.analysis.common;

import java.util.Set;

public class LineWithParent {
	public LineWithParent(Line line, String belongsTo, Set<String> unboundedInductives) {
		this.line = line;
		this.belongsTo = belongsTo;
		this.unboundedInductives = unboundedInductives;
		this.isCall= false;
	}
	
	public LineWithParent(Line line, String belongsTo, Set<String> unboundedInductives, Set<String> unboundedBindingVariables) {
		this.line = line;
		this.belongsTo = belongsTo;
		this.unboundedInductives = unboundedBindingVariables;
		this.isCall = true;
	}
	public Line line;
	public String belongsTo;
	public Set<String> unboundedInductives;
	public Set<String> unboundedBindingVariables;
	public boolean isCall;
}
