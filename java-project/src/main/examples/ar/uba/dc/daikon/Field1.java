package ar.uba.dc.daikon;

public class Field1
{
	
	public static A cosa;
	public static final void main(String args[])
	{		
		cosa = new A();
		cosa.a = 0;
		cosa.a = cosa.a + 1;
		
		f();

	}
	
	
	public static void f()
	{
	}
}