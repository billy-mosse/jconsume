package ar.uba.dc.daikon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Tiene polimorfismo
 * @author billy
 *
 */
public class Ins9 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		//InstrumentedMethod.a4(n,args);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{		
		//System.out.print("hola");
		List<Integer> l = new ArrayList<Integer>();

		int s = 1;
		int s2 = 1;
		int s3 = 1;
		int s4= s+s2+s3;
		System.out.println(s4);
		s4+=1;
		//n = n+1;
		for(int i = 0; i <n; i++)
		{
			//InstrumentedMethod.a14(i,n);
			l.add(new Integer(i));
		}
			
		
		//InstrumentedMethod.a17(l,n/2);
		/*int index1 = search2(l,n/2);
		
		int a = index1 + 1;
		a = a+1;
		
		
		//InstrumentedMethod.a18(l,n/2);
		int index2 = search(l, n/2);
		
		
		int b = f();*/
		
		
		
	}
	
	public static int f()
	{
		Integer i = new Integer(4);
		return i;
	}
	

	public static int search2(List<Integer> l, int k) {
		Iterator<Integer> it = l.iterator();
		int index = 0;
		int cont = 0;
		while(it.hasNext())
		{
			
			//InstrumentedMethod.a15(l,k,index,cont,it);
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

	public static int search(List<Integer> l, int k) {
		Iterator<Integer> it = l.iterator();
		int index = 0;
		int cont = 0;
		while(it.hasNext())
		{
			//InstrumentedMethod.a16(l,k,index,cont,it);

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