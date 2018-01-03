package ar.uba.dc.daikon;

public class Field4
{
	
	public static A cosa;
	public static final void main(String args[])
	{		
		cosa = new A();
		cosa.a = 10;
		for(int i = 1; i < 5; i++)
		{
			cosa.a = cosa.a + 1;
		}

	}
}