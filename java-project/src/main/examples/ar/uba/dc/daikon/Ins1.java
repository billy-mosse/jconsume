package ar.uba.dc.daikon;

/**
 * Ejemplo bien basico con loop simple
 * @author billy
 *
 */
public class Ins1 {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		mainParameters(n);
	}
	
	public static void mainParameters(int n)
	{
		A obj = new A();
		obj.a = n;
		for(int i = 1; i < obj.a; i++)
		{
			//System.out.println(i);
			Integer entero = new Integer(4);
			entero+=1;
		}
		
	}
}


