package ar.uba.dc.annotations.omega;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListExample {
	public static void main(String[] args)
	{
		List<Integer> l = new ArrayList();
		l.add(new Integer(4));
		
		m(l);
	}
	
	/**
	 * iterator deberia ser Fresh
	 * hasNext debería ser Fresh
	 * next debería ser Pure y Fresh? WriteConfined, porque deberia ser capaz de modificar sus fields...
	 * Pero entonces necesitamos una anotacion mas.
	 * @param l
	 */
	public static void m(List<Integer> l)
	{
		Iterator<Integer> it = l.iterator();
		while(it.hasNext())
		{
			Integer i = it.next();
		}
	}
}