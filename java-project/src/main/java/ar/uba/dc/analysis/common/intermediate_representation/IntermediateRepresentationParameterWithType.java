package ar.uba.dc.analysis.common.intermediate_representation;

public class IntermediateRepresentationParameterWithType extends IntermediateRepresentationParameter{
	private String type;

	
	public IntermediateRepresentationParameterWithType(String name, String type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}