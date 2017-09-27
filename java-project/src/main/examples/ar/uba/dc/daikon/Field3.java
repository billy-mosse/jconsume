package ar.uba.dc.daikon;

public class Field3
{
	
	public static A cosa;
		
	public static final void main(String args[])
	{		
		int N = Integer.parseInt(args[0]);
		cosa = new A();
		cosa.a = 0;
		for(int i = 1; i <= N; i++)
		{
			cosa = new A();
			cosa.a = cosa.a + 1;
		}
		A.pi = 600;

	}
}