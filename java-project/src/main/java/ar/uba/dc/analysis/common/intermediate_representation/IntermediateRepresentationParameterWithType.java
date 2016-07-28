package ar.uba.dc.analysis.common.intermediate_representation;

public class IntermediateRepresentationParameterWithType implements IntermediateRepresentationParameter{
	private String type;
	private String name;

	public IntermediateRepresentationParameterWithType()
	{
		
	}
	
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

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}
}