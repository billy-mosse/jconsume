package ar.uba.dc.daikon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.uba.dc.util.ListE;
import ar.uba.dc.util.ListItrE;

/**
 * @author billy
 * Modifico un relevant parameter en el medio del codigo (con la funcion g)
 * por lo cual necesito crear n_init si o si porque si no me va a crear un invariante "falso" para la segunda llamada
 * al metodo anotheriterate
 */
public class Ins12 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{		
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i <n; i++)
		{
			l.add(new Integer(i));
		}
			
		
		anotheriterate(l);
		
		
		n = g(n);	
		
		
		anotheriterate(l);
		
		
	}
	
	public static int g(int n)
	{
		return 2*n;
	}
	
	public static void iterate(List<Integer> l) {
		Iterator<Integer> it = l.iterator();

		int j = 0;
		while(it.hasNext())
		{
			j+=1;
			Integer a = it.next();
		}
		
		l.addAll(l);
		
	}

	public static void anotheriterate(List<Integer> l) {
		Iterator<Integer> it = l.iterator();
		
		int i = 0;
		while(it.hasNext())
		{
			i+=1;
			Integer a = it.next();
		}
	}
}