package ar.uba.dc.recursion;

import ar.uba.dc.util.ListE;

public class LoopWithRecursion {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		mainParameters(n);
	}
	
	public static void mainParameters(int n)
	{
		ListE l = new ListE();
		ListE new_l = createList(n, l);
	}
	
	public static ListE createList(int n, ListE l)
	{
		if (n<= 0) return l;
		
		l.add(new Object());
		return createList(n-1,l);		
	}

}
