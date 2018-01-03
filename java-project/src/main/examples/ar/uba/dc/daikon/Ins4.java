package ar.uba.dc.daikon;

import ar.uba.dc.util.ListC;
import ar.uba.dc.util.ListItr;

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
		ListC l = new ListC();
		for(int i = 1; i < n; i++)
		{
			l.add(new Integer(i));
		}

		ListItr it = (ListItr) l.iterator();
		while(it.hasNext())
		{	
			Integer j = (Integer) it.next();
		}
	}
}