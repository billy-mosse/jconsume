package ar.uba.dc.jolden.perimeter;


/**
 * Modificamos el codigo original para invocarlo con parametros fijos y 
 * asi poder obtener una expresion en funcion de los parametros relevantes.
 *  
 * @author kgtesis
 */
public class PerimeterTest {

	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		for(int i=1;i<=count;i++) 
		{
			String[] argsTest=new String[2];
			argsTest[0]="-p";
			argsTest[1]=Integer.toString( i + 10 );
			
			Perimeter.mainOrig(argsTest);
		} 
	}
}
