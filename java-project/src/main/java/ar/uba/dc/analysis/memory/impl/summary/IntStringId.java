package ar.uba.dc.analysis.memory.impl.summary;

public class IntStringId {
	public int number;
	public String name;
	
	public boolean equals(IntStringId intStringId)
	{
		return intStringId.number == this.number && intStringId.name.equals(this.name);
	}
	

}
