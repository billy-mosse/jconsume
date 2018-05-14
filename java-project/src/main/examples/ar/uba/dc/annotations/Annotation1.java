package ar.uba.dc.annotations;

/**
 * Ejemplo bien basico con loop simple
 * @author billy
 *
 */
public class Annotation1 {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		mainParameters(n);
	}
	
	public static ICalculator calc;
	public static void mainParameters(int n)
	{
		for(int i = 1; i < n; i++)
		{
			Integer k = new Integer(i);
		}
		
		if(n==1)
		{
			Object o = new Object();
		}
		else
		{
			Object o = new Object();
			Object p = new Object();
		}

		calc.calculate1(n);
		calc.calculate2(n);
		
	}
	
}


