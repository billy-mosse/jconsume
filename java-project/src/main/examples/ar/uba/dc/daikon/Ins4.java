package ar.uba.dc.daikon;

import ar.uba.dc.util.ListE;
import ar.uba.dc.util.ListItrE;

/**
 * Tiene un foreach, pero tambien un contador agregado a mano
 * que igual se podria agregar durante la instrumentacion
 * @author billy
 *
 */
public class Ins4 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{
		ListE l = new ListE();
		for(int i = 1; i < n; i++)
		{
			l.add(new Integer(i));
		}

		ListItrE it = (ListItrE) l.iterator();
		while(it.hasNext())
		{	
			Integer j = (Integer) it.next();
		}
	}
}