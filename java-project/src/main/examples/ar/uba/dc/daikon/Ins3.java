package ar.uba.dc.daikon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Ignorar
 * @author billy
 *
 */
public class Ins3 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		
		mainParameters(n);
	}

	public static void mainParameters(int n) {
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i < n; i++)
		{
			l.add(new Integer(i));
		}
		
		m(l);
		
	}

	private static void m(List<Integer> d) {
		Iterator<Integer> it = d.iterator();
		while(it.hasNext())
		{
			Integer r = (Integer) it.next();
			Integer k = new Integer(r);
		}
		
	}
	
	
	
	
}