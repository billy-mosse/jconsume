package ar.uba.dc.invariant.spec.compiler.constraints.parser;


//TODO hacer una clase que herede de Set y que implemente un set de DerivedVariables
public class DerivedVariable implements Comparable{
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
			return this.name + "__f__" + this.field;
		}
		
	}
	public String getField()
	{
		return this.field;
	}
	public String getName() {
		return this.name;
	}
	
	public int compareTo(DerivedVariable dv)
	{
		return this.toString().compareTo(dv.toString());
	}
	@Override
	public int compareTo(Object o) {
		if(o.getClass().equals(DerivedVariable.class) || o.getClass().equals(String.class))
		{
			return this.toString().compareTo(o.toString());
		}
		else
		{
			throw new Error("Can only compare to Derived Variables or Strings");
		}
		
	}
}
