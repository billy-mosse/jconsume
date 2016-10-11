package ar.uba.dc.analysis.common.intermediate_representation;

public class IntermediateRepresentationParameter{
	
	protected String name;
	protected boolean isRefLikeType;
	protected String type;
	
	public IntermediateRepresentationParameter(String name, boolean isRefLikeType)
	{
		this.name = name;
		this.isRefLikeType = isRefLikeType;
	}
	
	public IntermediateRepresentationParameter(String name, String type, boolean isRefLikeType)
	{
		this.name = name;
		this.type = type;
		this.isRefLikeType = isRefLikeType;
	}
	
	
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}	
	
	public boolean isRefLikeType()
	{
		return this.isRefLikeType;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
