package ar.uba.dc.binding;

import ar.uba.dc.daikon.A;

public class Binding1{
	public static void main(String[] args) {
		A a = new A();
		m1(a);
	}
	public static void m1(A a)
	{
		m2(a);
	}
	
	public static void m2(A b)
	{
		m3(b);	
	}
	
	public static void m3(A c)
	{
		c.a = 4;
		
		for(int i = 1; i < c.a; i ++)
		{
			Integer k = new Integer(10);
		}		
	}
}
