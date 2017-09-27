package ar.uba.dc.daikon;

import ar.uba.dc.util.ListC;
import ar.uba.dc.util.ListItr;

/**
 * Tiene un boolean
 * este ejemplo es medio al pedo
 * @author billy
 *
 */
public class Ins6 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		InstrumentedMethod2.a4(n,args);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{
		
		Integer[] l = new Integer[n];
		for(int i = 0; i < n; i++)
		{
			l[i] = new Integer(2*i - 3 * i * i);
			InstrumentedMethod2.a9(l,n,i);
		}
		
		int pos = get_pos(l,4);
		
		System.out.println("La posicion del numero 4 es " + pos);
	}
	
	/**
	 * busca el numero m y si lo encuentra devuelve  la pos
	 * si no lo encuentra devuelve length + 1
	 * @param l
	 * @param m
	 * @return
	 */
	public static int get_pos(Integer[] l, int m)
	{
		boolean found = false;
		int k = 0;
		while(!found && k < l.length)
		{
			InstrumentedMethod2.a8(found,k,l,m);
			int temp = l[k];
			if(temp == m)
			{
				found = true;
			}
			k = k+1;
		}
		
		return k;
	}
}