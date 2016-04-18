package ar.uba.dc.analysis.common.method.information.rules;

public class Resources {

	private String temporal;
	private String residual;
	private String memReq;
	private String syntax;
	
	public String getTemporal() {
		return temporal;
	}

	public String getResidual() {
		return residual;
	}

	//BILLY: estoy agregando en unanalizable_rules el valor inicial de memReq. eso esta bien? Donde y para que se usa?
	public String getMemoryRequirement() {
		return memReq;
	}

	public String getSyntax() {
		return syntax;
	}
}
