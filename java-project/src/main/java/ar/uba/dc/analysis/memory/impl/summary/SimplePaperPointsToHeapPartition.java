package ar.uba.dc.analysis.memory.impl.summary;

public class SimplePaperPointsToHeapPartition implements PaperPointsToHeapPartition{
	public Integer number;
	
	

	
	public SimplePaperPointsToHeapPartition(Integer number)
	{
		this.number = number;
	}
	
	public SimplePaperPointsToHeapPartition(String fromName)
	{
		this.number = Integer.valueOf(fromName.substring(3));
	}
	
	public SimplePaperPointsToHeapPartition clone()
	{
		return new SimplePaperPointsToHeapPartition(this.number);
	}
	
	public Integer getNumber()
	{
		return this.number;
	}
	
	@Override
	public int hashCode()
	{
		return this.number.intValue();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o.getClass() == SimplePaperPointsToHeapPartition.class)
		{
			SimplePaperPointsToHeapPartition hp = (SimplePaperPointsToHeapPartition) o;
			return hp.number == this.number;
		}
		return false;
	}
	
	public String toString()
	{
		return "SH_" + this.number;
	}

	@Override
	public int compareTo(PaperPointsToHeapPartition o) {
		return this.number.compareTo(o.getNumber());
	}

	@Override
	public void setNumber(int number) {
		this.number = number;		
	}	
}
