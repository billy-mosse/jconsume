package ar.uba.dc.jolden.bh;


/**
 * Modificamos el codigo original para invocarlo con parametros fijos y 
 * asi poder obtener una expresion en funcion de los parametros relevantes.
 *  
 * @author kgtesis
 */
public class BHTest {
	
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		for(int i=1;i<=count;i++) 
		{
			String[] argsTest=new String[2];
			argsTest[0]="-b";
			argsTest[1]=Integer.toString( i + 10 );
			BH.mainOrig(argsTest);
		} 
	}
}
