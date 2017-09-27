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
public class Ins16 {
	public static void main(String[] args) {	
		
		int number = Integer.parseInt(args[0]);
		
		RichNumber r = new RichNumber();
		r.setNumber(number);
		mainParameters(r);
		
	}
	
	public static void mainParameters(RichNumber r)
	{
		
		
		int r_number_init = r.getNumber();
		
		//Hago nada
		for(int i = 0; i <  r.getNumber(); i++)
		{

			InstrumentedMethod2.a20(r,i,r.getNumber(),r_number_init);
			InstrumentedMethod2.a23(r,i);
			doNothing(i);
		}
				
		InstrumentedMethod2.a21(r,r_number_init);
		r = DuplicateNumber(r);
		
		//Hago nada el doble de veces

		for(int i = 0; i <  r.getNumber(); i++)
		{

			InstrumentedMethod2.a22(r,i,r.getNumber(),r_number_init);
			doNothing(i);
		}
		
	}
	
	
	private static RichNumber DuplicateNumber(RichNumber r) {
		r.setNumber(2*r.getNumber());
		return r;
		
	}



	public static void doNothing(int s)
	{
	}
}