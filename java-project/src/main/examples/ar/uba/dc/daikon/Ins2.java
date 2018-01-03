package ar.uba.dc.daikon;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Ignorar
 * @author billy
 *
 */
public class Ins2 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		
		mainParameters(n);
	}
	
	public static void mainParameters(int n)
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		
		for(int i = 0; i < n; i++)
		{
			l.add(new Integer(i));
		}
		
		Iterator<Integer> it = l.iterator();
		
		while(it.hasNext())
		{	
			it.next();
			Integer k = new Integer(4);
		}
		
	}
}