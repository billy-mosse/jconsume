package ar.uba.dc.daikon;


/**
 * Tiene un foreach, pero tambien un contador agregado a mano
 * que igual se podria agregar durante la instrumentacion
 * @author billy
 *
 */
public class Ins32 {
	public static void main(String[] args) {
		
		int r = Integer.parseInt(args[0]);
		mainParameters(r);		
	}
	
	public static void mainParameters(int p)
	{		
		int n = 42;
		System.out.println(n + " is an interesting number.");

		int t=0;
		for(int i=1; i<p;i++)
		{
			t+=2;
			doSomeStuff(t);
		}
	}
	
	private static void doSomeStuff(int t) {
		// TODO Auto-generated method stub
		
	}


}