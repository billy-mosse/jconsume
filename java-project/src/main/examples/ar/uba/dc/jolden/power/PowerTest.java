package ar.uba.dc.jolden.power;

import ar.uba.dc.jolden.perimeter.Perimeter;

/**
 * Modificamos el codigo original para invocarlo con parametros fijos y 
 * asi poder obtener una expresion en funcion de los parametros relevantes.
 *  
 * @author kgtesis
 */
public class PowerTest {

	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		for(int i=1;i<=count;i++) 
		{
			String[] argsTest = new String[] {};
			Perimeter.mainOrig(argsTest);
		} 
	}
}

