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
public class Ins13 {
	public static void main(String[] args) {				
		
		//Hago nada
		for(String s : args)
		{
			doNothing();
		}
				
		
		args = duplicate(args);
		
		//Hago nada el doble de veces
		for(String s : args)
		{
			doNothing();
		}
		
	}
	
	
	private static String[] duplicate(String[] args) {
		String[] new_args = new String[2*args.length];

		for(int i = 0; i < args.length; i++)
		{
			//Inserto cada elemento dos veces
			new_args[2*i] = args[i];
			new_args[2*i+1] = args[i];
		}
		return new_args;
		
	}


	public static void doNothing()
	{
	}
}