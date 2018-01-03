package ar.uba.dc.daikon;


/**
 * Tiene un foreach, pero tambien un contador agregado a mano
 * que igual se podria agregar durante la instrumentacion
 * @author billy
 *
 */
public class Ins31 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		A obj = new A(); 
		obj.a = n;
		mainParameters(obj);		
	}
	
	public static void mainParameters(A obj2)
	{		
		f(obj2);
	}
	
	public static void f(A obj3)
	{
		for(int i = 1; i < obj3.a; i++)
		{
			Integer r = new Integer(4);
		}
	}
}