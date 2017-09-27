package ar.uba.dc.daikon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.uba.dc.util.ListC;
import ar.uba.dc.util.ListItr;

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
			//InstrumentedMethod2.a32(r,i);
			//InstrumentedMethod2.a33(n,i);
			Integer k = new Integer(i);
			//System.out.println(k);
		}
	}
	
	
	/*private static RichNumber DuplicateNumber(RichNumber r) {
		return r;
		
	}*/

}