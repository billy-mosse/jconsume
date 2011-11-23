package ar.uba.dc.jolden.tsp;

import ar.uba.dc.jolden.treeadd.TreeAdd;

/**
 * Modificamos el codigo original para invocarlo con parametros fijos y 
 * asi poder obtener una expresion en funcion de los parametros relevantes.
 *  
 * @author kgtesis
 */
public class TSPTest {

	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		for(int i=1;i<=count;i++) 
		{
			String[] argsTest=new String[2];
			argsTest[0]="-c";
			argsTest[1]=Integer.toString( i + 10 );
			TreeAdd.mainOrig(argsTest);
		} 
	}
}

