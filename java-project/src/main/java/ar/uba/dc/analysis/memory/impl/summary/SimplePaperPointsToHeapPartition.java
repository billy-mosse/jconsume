package ar.uba.dc.analysis.memory.impl.summary;

public class SimplePaperPointsToHeapPartition implements PaperPointsToHeapPartition {
	public int number;
	
	

	
	public SimplePaperPointsToHeapPartition(int number)
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
	
	public int getNumber()
	{
		return this.number;
	}
	
	@Override
	public int hashCode()
	{
		return this.number;
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
	
}
