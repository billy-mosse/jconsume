package ar.uba.dc.daikon;

/**
 * Ejemplo bien basico con loop simple
 * @author billy
 *
 */
public class Ins0 {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		mainParameters(n);
	}
	
	public static void mainParameters(int n)
	{
		for(int i = 1; i < n; i++)
		{
			System.out.println(i);
			Integer obj = new Integer(4);
			obj+=1;
		}
		
	}
}


