package ar.uba.dc.simple;

/**
 * Temporal indica los objetos que no escapan al mua.
 *   Temporal local = # { objetos creados en el cuerpo del mua que no escapan } 
 *   Temporal callees  = # { objetos residuales producidos por las call que son capturados por mua }
 *   
 * Residual indica los objetos que escapan al mua.
 *    Residual local = # { objetos creados en el cuerpo del mua que escapan }
 *    Temporal callees  = # { objetos residuales producidos por las call que a su vez escapan a mua }
 *    
 * @author tincho
 *
 */

public class EjemploSimple01 {

	/**
	 * @temporal 15
	 * @residual 0
	 */
	public static void main(String[] args) {
		Tupla tupla = dameTupla(); //maxCall = 0, tempCall=3
		cargarTupla(tupla); //maxCall = 0, tempCall = 2
		test(); //maxCall = 6, tempCall = 4
	}
	
	/** DA MAL POR PROBLEMA DE CONTEXTO
	 * Temporal ( Local = 1 , Callees = 5 )
     * Residual ( Local = 0 , Callees = 4)
	 * @return Tupla
	 * 
	 * DEBERIA DAR
	 * @residual 4
	 * @temporal 6
	 * 
	 * ESTA DANDO
	 * @residual 6
	 * @temporal 4
	 */
	public static Tupla test() {
		Tupla tupla = nuevaTupla(); //maxCall=0  ,tempCall=3 
		cargarTupla(tupla); //maxCall=0  ,tempCall=2
		@SuppressWarnings("unused")
		Tupla otraTupla = new Tupla(); //tempLocal = 1
		Tupla returnedTupla = dameTupla(); //residual = 3
		returnedTupla.a = newInteger(); //residual = 1
		return returnedTupla;
	}
	
	/**
	 * @return Tupla
	 * 
	 * @temporal 0
	 * @residual 3
	 */
	static Tupla nuevaTupla() {
		Tupla tupla = new Tupla(); //residual = 1
		tupla.a = newInteger(); //residual = 1, maxCall = 0 
		tupla.b = newInteger(); //residual = 1, maxCall = 0
		return tupla; 
	}
	
	/**
	 * @param tupla
	 * 
	 * @temporal 0
	 * @residual 1
	 */
	static Integer newInteger() {
		Integer integer = new Integer(2); //residual = 1
		return integer; 
	}
	/**
	 * @param tupla
	 * 
	 * @temporal 0
	 * @residual 2
	 */
	static void cargarTupla(Tupla tupla) {
		tupla.a = new Integer(0); //residual = 1
		tupla.b = new Integer(0); //residual = 1
	}
	
	/**
    * @param Tupla
    * 
    * @temporal 0
    * @residual 3
    */
	static Tupla dameTupla() {
		Tupla tupla = new Tupla(); //residual=1
		tupla.a = new Integer(1); //residual=1
		tupla.b = new Integer(2); //residual=1
		return tupla;
	}
	
}
