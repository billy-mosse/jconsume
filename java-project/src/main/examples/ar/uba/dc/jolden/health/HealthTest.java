package ar.uba.dc.jolden.health;


/**
 * Modificamos el codigo original para invocarlo con parametros fijos y 
 * asi poder obtener una expresion en funcion de los parametros relevantes.
 *  
 * @author kgtesis
 */
public class HealthTest {

	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		for(int i=1;i<=count;i++) 
		{
			String[] argsTest=new String[6];
			argsTest[0]="-l";
			argsTest[1]=Integer.toString( i + 10 );
			argsTest[2]="-t";
			argsTest[3]=Integer.toString( i + 10 );
			argsTest[4]="-s";
			argsTest[5]=Integer.toString( i + 10 );
			
			Health.mainOrig(argsTest);
		} 
	}
}
