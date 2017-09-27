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
		InstrumentedMethod2.a4(n,args);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{
		ListC l = new ListC();
		for(int i = 1; i < n; i++)
		{
			l.add(new Integer(i));			
			InstrumentedMethod2.a5(i,n,l);
		}
		
		int suma = 0;
		ListItr it = (ListItr) l.iterator();
		
		//Le agrego un contador
		int k = 0;
		while(it.hasNext())
		{	
			Integer j = (Integer) it.next();
			
			//Le agrego un contador			
			k+=1;
			InstrumentedMethod2.a6(j,it,l,n,k);
			suma += j;	
		}
	}
}