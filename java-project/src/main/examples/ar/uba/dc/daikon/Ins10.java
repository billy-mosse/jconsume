package ar.uba.dc.daikon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.uba.dc.util.ListC;
import ar.uba.dc.util.ListItr;

/**
 * @author billy
 *
 */
public class Ins10 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		InstrumentedMethod2.a4(n,args);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{		
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i <n; i++)
		{
			InstrumentedMethod2.a14(i,n);
			l.add(new Integer(i));
		}
		
		int size_l_init = l.size();
			
		
		
		//Registro solamente l@s variables/objetos del metodo y los relevant parameters
		InstrumentedMethod2.a17(l,n/2, size_l_init, n);
		int index1 = search_and_duplicate(l,n/2,size_l_init);
		

		InstrumentedMethod2.a18(l,n/2, size_l_init, n);
		int index2 = search(l, n/2,size_l_init);
	}

	private static int search_and_duplicate(List<Integer> l, int k, int size_l_init) {
		Iterator<Integer> it = l.iterator();
		int index = 0;
		int cont = 0;
		while(it.hasNext())
		{
			
			InstrumentedMethod2.a19(l,k,index,cont,it, size_l_init);
			cont+=1;
			Integer i = it.next();
			if (i == k)
			{
				index = i;
				break;
			}
		}
		
		l.addAll(l);
		
		return index;
	}

	private static int search(List<Integer> l, int k, int size_l_init) {
		Iterator<Integer> it = l.iterator();
		int index = 0;
		int cont = 0;
		while(it.hasNext())
		{
			InstrumentedMethod2.a20(l,k,index,cont,it, size_l_init);

			cont+=1;
			Integer i = it.next();
			if (i == k)
			{
				index = i;
				break;
			}
		}
				
		return index;	
	}
}