package ar.uba.dc.daikon;

import ar.uba.dc.util.ListCE;
import ar.uba.dc.util.ListItrE;

/**
 * Tiene polimorfismo
 * @author billy
 *
 */
public class Ins8 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		//InstrumentedMethod..a4(n,args);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{
		B myb = new B();
		myb.setb(n);
		for(int i = 1; i <= myb.get2b(); i++)
		{
			////InstrumentedMethod.a12(i,myb,n);
			
			//si .get2b es readonly puedo hacer esto
			//InstrumentedMethod..a13(i,myb,n, myb.get2b());
		}
		
		
	}
}