package ar.uba.dc.daikon;


/**
 * Tiene un foreach, pero tambien un contador agregado a mano
 * que igual se podria agregar durante la instrumentacion
 * @author billy
 *
 */
public class Ins30 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{
		int s = n+1;
		f(s);
	}
	
	public static void f(int r)
	{
		Integer t = new Integer(r);
	}
}