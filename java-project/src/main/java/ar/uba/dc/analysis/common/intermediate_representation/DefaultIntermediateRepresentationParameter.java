package ar.uba.dc.analysis.common.intermediate_representation;

public class DefaultIntermediateRepresentationParameter implements IntermediateRepresentationParameter{
	
	public DefaultIntermediateRepresentationParameter(String name, String type)
	{
		this.name = name;
	}
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
