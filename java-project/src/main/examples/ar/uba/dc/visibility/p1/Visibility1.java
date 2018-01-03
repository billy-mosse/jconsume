package ar.uba.dc.visibility.p1;

import ar.uba.dc.visibility.p2.PublicClass2;

public class Visibility1
{
	public static void main(String[] args)
	{
		PrivateClass1 c1 = new PrivateClass1();
		PublicClass2 c2 = new PublicClass2();
		c2.createObjectFromPrivateClass();
	}
}