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
public class Ins11 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		//InstrumentedMethod..a4(n,args);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{		
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i <n; i++)
		{
			//InstrumentedMethod..a14(i,n);
			l.add(new Integer(i));
		}
			
		
		//InstrumentedMethod..a17(l,n);
		iterate(l);
		
		//InstrumentedMethod..a18(l,n);
		iterate2(l);
		
		
	}
	
	public static void iterate(List<Integer> l) {
		Iterator<Integer> it = l.iterator();

		int j = 0;
		while(it.hasNext())
		{
			j+=1;
			//InstrumentedMethod..a19(l,it,j);
			Integer a = it.next();
		}
		
		l.addAll(l);
		
	}

	public static void iterate2(List<Integer> l) {
		Iterator<Integer> it = l.iterator();
		
		int i = 0;
		while(it.hasNext())
		{
			i+=1;
			////InstrumentedMethod..a20(l,it,i);
			Integer a = it.next();
		}
	}
}