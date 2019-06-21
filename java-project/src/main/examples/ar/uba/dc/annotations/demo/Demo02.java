package ar.uba.dc.annotations.demo;

import java.util.Iterator;

import ar.uba.dc.util.ListE;

public class Demo02 {

	public static void main(String[] args)
	{
		ListE result = mainParameters(Integer.parseInt(args[0]));
	}

	public static ListE mainParameters(int k)
	{
		ListE l = new ListE();
		for(int i = 0; i < k; i++)
		{
			l.add(new Integer(i));
		}
		ListE l_copy = copy(l);
		return l_copy;
	}
	
	//me gustaria que termine escapando el head de l por culpa de los parametros. Me parece que sale si digo que l2 es writable
	//no, al reves.
	public static ListE copy(ListE l)
	{
		ListE ret = new ListE();
		Iterator it = l.iterator();
		while(it.hasNext())
		{
			Integer x = (Integer) it.next();
			ret.add(x);
		}
		doSomething(ret, l);
		return ret;
	}
	
	public static void doSomething(ListE l1, ListE l2)
	{
		
	}
}