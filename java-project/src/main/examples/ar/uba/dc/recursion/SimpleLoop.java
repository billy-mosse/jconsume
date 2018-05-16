package ar.uba.dc.recursion;

import ar.uba.dc.util.ListE;

public class SimpleLoop {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		mainParameters(n);
	}
	
	public static void mainParameters(int n)
	{
		ListE l =createList(n);
	}
	
	public static ListE createList(int n)
	{
		ListE l = new ListE();
		for(int i = 1; i<=n;i++)
		{
			l.add(new Object());
		}
		
		return l;
	}

}
