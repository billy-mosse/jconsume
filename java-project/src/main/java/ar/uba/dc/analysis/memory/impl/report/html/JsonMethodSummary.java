package ar.uba.dc.analysis.memory.impl.report.html;

public class JsonMethodSummary {
	protected String parameters;
	protected String methodName;
	protected String[] residualPieces;
	protected String[] memorySummaryPieces;
	public JsonMethodSummary(String parameters, String methodName, String memorySummary, String residual) {
		this.parameters = parameters;
		this.methodName = methodName;
		this.memorySummaryPieces = memorySummary.split(";");
		this.residualPieces= residual.split(";");
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String[] getresidualPieces() {
		return residualPieces;
	}
	public void setresidualPieces(String[] residualPieces) {
		this.residualPieces = residualPieces;
	}
	public String[] getMemorySummaryPieces() {
		return memorySummaryPieces;
	}
	public void setMemorySummaryPieces(String[] memorySummaryPieces) {
		this.memorySummaryPieces = memorySummaryPieces;
	}
}
