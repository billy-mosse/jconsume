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
public class Ins14 {
	public static void main(String[] args) {	
		List<String> l = new ArrayList<String>();
		
		for(int i = 0; i < args.length; i++)
		{
			l.add(args[i]);
		}
		
		mainWithList(l);
		
	}
	
	public static void mainWithList(List<String> argsList)
	{
		
		//Hago nada
		for(String s : argsList)
		{
			doNothing(s);
		}
				
		
		argsList = duplicate(argsList);
		
		//Hago nada el doble de veces
		for(String s : argsList)
		{
			doNothing(s);
		}
		
	}
	
	
	private static List<String> duplicate(List<String> argsList) {
		argsList.addAll(argsList);
		return argsList;
		
	}


	public static void doNothing(String s)
	{
	}
}