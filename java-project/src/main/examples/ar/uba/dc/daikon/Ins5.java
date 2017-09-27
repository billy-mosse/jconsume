package ar.uba.dc.daikon;

import ar.uba.dc.util.ListC;
import ar.uba.dc.util.ListItr;

/**
 * Tiene un for que va hasta un field
 * @author billy
 *
 */
public class Ins5 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		InstrumentedMethod2.a4(n,args);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{
		A cosa = new A();
		for(cosa.a = 1; cosa.a < n; cosa.a++)
		{
			A otra_cosa = new A();
			InstrumentedMethod2.a7(cosa,n);
		}
	}
}