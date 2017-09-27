package ar.uba.dc.daikon;

/**
 * Ignorar
 * @author billy
 *
 */
public class Ins2 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		//InstrumentedMethod2.a4(n,args);
		A obj = new A();
		
		obj.setb(n);
		//InstrumentedMethod2.a3(obj,n);
		
		for(int i = 1; i < obj.getb(); i++)
		{
			//InstrumentedMethod2.a2(i,obj);
			Integer b = new Integer(1);
		}
	}
}