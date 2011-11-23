package ar.uba.dc.barvinok.syntax;

public class ExistsInfo {

	private String definitions;
	private String constratins;
	
	public ExistsInfo(String definitions, String constraints) {
		this.definitions = definitions;
		this.constratins = constraints;
	}

	public String getDefinitions() {
		return definitions;
	}

	public String getConstratins() {
		return constratins;
	}

}
