package ar.uba.dc.daikon;

public class Field2
{
	
		
	public static final void main(String args[])
	{		
		A cosa = new A();
		cosa.a = 0;

		cosa = new A();
		cosa.a = cosa.a + 1;

		A.pi = 600;

	}
}