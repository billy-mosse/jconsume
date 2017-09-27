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
public class Ins18 {
	public static void main(String[] args) {	
		
		int number = Integer.parseInt(args[0]);
		
		RichNumberPublic r = new RichNumberPublic(number);
		r.number =number;
		mainParameters(r);
		
	}
	
	public static void mainParameters(RichNumberPublic r)
	{		
		for(int i = 1; i < r.number;i++)
		{
			InstrumentedMethod2.a31(r,i,r.number);
			Integer k = new Integer(i);
		}
	}
	
	
	private static RichNumber DuplicateNumber(RichNumber r) {
		return r;
		
	}



	public static void doNothing(int s)
	{
	}
}