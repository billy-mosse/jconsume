package ar.uba.dc.daikon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.uba.dc.util.ListCE;
import ar.uba.dc.util.ListItrE;

/**
 * @author billy
 * Modifico un relevant parameter en el medio del codigo, pero es un array
 * por lo cual necesito crear size_args_init si o si porque si no me va a crear un invariante "falso" para la segunda llamada
 * al metodo anotheriterate
 */
public class Ins15 {
	public static void main(String[] args) {	
		
		int number = Integer.parseInt(args[0]);
		
		RichNumber r = new RichNumber();
		r.setNumber(number);
		mainParameters(r);
		
	}
	
	public static void mainParameters(RichNumber r)
	{
		
		//Hago nada
		for(int i = 0; i <  r.getNumber(); i++)
		{
			doNothing(i);
		}
				
		
		r = DuplicateNumber(r);
		
		//Hago nada el doble de veces

		for(int i = 0; i <  r.getNumber(); i++)
		{
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