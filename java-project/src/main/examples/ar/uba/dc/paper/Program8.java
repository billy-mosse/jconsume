package ar.uba.dc.paper;

import ar.uba.dc.util.ListE;

public class Program8
{
	
	
	public static void main(String[] args) {
		int i = Integer.parseInt(args[0]);
		l = new ListE();
		m(i);
	}
	
	public static ListE l;
	
	
	public static void m(int i)
	{
		if(i<= 0) return;
		Object o = new Object();
		l.add(o);
	}

}