package ar.uba.dc.daikon;

/**
 * @author billy
 * Modifico un relevant parameter en el medio del codigo, pero es un array
 * por lo cual necesito crear size_args_init si o si porque si no me va a crear un invariante "falso" para la segunda llamada
 * al metodo anotheriterate
 */
public class Ins19 {
	public static void main(String[] args) {	
		
		int number = Integer.parseInt(args[0]);
		
		RichNumberPublic r = new RichNumberPublic(number);
		//r.number =number;
		mainParameters(r);
		
	}
	
	public static void mainParameters(RichNumberPublic r)
	{		
		int n = r.number;
		for(int i = 1; i < n;i++)
		{
			//InstrumentedMethod2.a1(r,n,i);
			Integer k = new Integer(i);
			System.out.println(k);
		}
	}
	
	
	/*private static RichNumber DuplicateNumber(RichNumber r) {
		return r;
		
	}*/

}