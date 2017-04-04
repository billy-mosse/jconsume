package ar.uba.dc.analysis.common;

public class LineWithParent {
	public LineWithParent(Line line, String belongsTo) {
		this.line = line;
		this.belongsTo = belongsTo;
	}
	public Line line;
	public String belongsTo;
}
