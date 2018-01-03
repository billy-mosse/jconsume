package ar.uba.dc.daikon;


/**
 * @author billy
 * Modifico un relevant parameter en el medio del codigo, pero es un array
 * por lo cual necesito crear size_args_init si o si porque si no me va a crear un invariante "falso" para la segunda llamada
 * al metodo anotheriterate
 */
public class Ins21 {
	public static void main(String[] args) {	
		
		int number = Integer.parseInt(args[0]);
		
		RichNumberPublic r = new RichNumberPublic(number);
		
		r.f();
		r.number =number;
		mainParameters(r);
		
	}
	
	public static void mainParameters(RichNumberPublic r)
	{		
		int n = r.number;
		RichNumberPublic r2 = new RichNumberPublic(0);
		for(r2.number = 1; r2.number < n;r2.number++)
		{
			////InstrumentedMethod..a32(r,i);
			////InstrumentedMethod..a33(n,i);
			Integer k = new Integer(r2.number);
			//System.out.println(k);
		}	
		
	}
	
	
	/*private static RichNumber DuplicateNumber(RichNumber r) {
		return r;
		
	}*/

}