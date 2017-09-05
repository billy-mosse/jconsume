package ar.uba.dc.daikon;

import java.util.ArrayList;
import java.util.List;

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
			m(i);
		}
		
	}
	
	public static void m(int i)
	{
		Integer entero = new Integer(i);
		entero += 1;
	}
}


