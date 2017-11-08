package ar.uba.dc.daikon;


/**
 * Tiene un for que va hasta un field
 * @author billy
 *
 */
public class Ins5 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{
		A cosa = new A();
		cosa.setb(n);
		//f(cosa.a);
		for(cosa.a = 1; cosa.a < n; cosa.a++)
		{
			A otra_cosa = new A();
		}
		
		//System.out.print(cosa.getb());
		//A.createNew(4);
	}

}