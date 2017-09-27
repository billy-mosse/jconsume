package ar.uba.dc.daikon;

public class Daikon1 {
	public static int a = 1;
	public static int b = 10;
	public static int d = 0;
	public static void main(String[] args)
	{
		a = 1;
		b = 10;
		for (int i = 0; i < b; i++)
		{
			a+=1;
			f(a);
		}
		a+=2;
	}
	
	public static void f(int c)
	{
		d+=3;
		c+=1;
	}
}
