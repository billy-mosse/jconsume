package ar.uba.dc.daikon;


/**
 * Tiene un for que va hasta un field
 * @author billy
 *
 */
public class Ins25 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		A obj = new A();
		obj.a =10;
		mainParameters(obj);
	}
	
	public static void mainParameters(A obj)
	{
		String s = "hola";
		
		//Integer k = new Integer (s.length() + t.length());
		for(int i = 1; i < obj.a; i++)
		{
			A otra_cosa = new A();
		}
		
		//System.out.print(cosa.getb());
		//A.createNew(4);
	}

}