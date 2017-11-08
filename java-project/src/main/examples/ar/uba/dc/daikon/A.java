package ar.uba.dc.daikon;

/**
 * @author billy
 *
 * int container para Program1.
 */

public class A {

	public int a;
	public int c;
	private int b;
	
	public int getb()
	{
		return this.b;
	}
	
	public void setb(int b)
	{
		this.b = b;
	}
	
	public static void createNew(int i)
	{
		Integer k = new Integer(i);
	}
	
	public void doSomeHeavyStuff()
	{
		for(int i = 0; i < 100; i++)
		{
			this.a = i;
			for(int j = 0; j < 1100; j++)
			{
				this.b = this.a;
				Integer k = new Integer(4);
			}
		}
	}
	
	public static void doNothingForInstrmentedMethod()
	{}
}
