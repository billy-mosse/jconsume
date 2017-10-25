package ar.uba.dc.invariant.spec.compiler.constraints.parser;

public class DerivedVariable {
	public DerivedVariable(String field, String name)
	{
		this.field = field;
		this.name = name;
	}
	private String field;
	private String name;
	
	public String toString()
	{
		if (this.field=="size")
		{
			return this.field + "_" + this.name;
		}
		else
		{
			//TODO: ver como era el formato cuando era un field. Falta un  "_f_"
			return this.name + "_" + this.field;
		}
		
	}
	public String getField()
	{
		return this.field;
	}
	public String getName() {
		return this.name;
	}
}
