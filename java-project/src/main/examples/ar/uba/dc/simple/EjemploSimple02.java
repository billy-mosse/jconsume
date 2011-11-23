package ar.uba.dc.simple;

public class EjemploSimple02 {

	/**ESTE ESTA DANDO MAL
	 * 
	 * DEBERIA DAR
	 * @temporal 3
	 * @residual 0
	 * 
	 * ESTA DANDO
	 * @temporal 2
	 * @residual 1
	 */
	public static void main(String[] args) {
		String escape = testJimpleSingleVariable(); //maxCall = 2, tempCall = 1
		System.out.println(escape); 
	}
	
	
	/**
	 * @temporal 2
	 * @residual 1
	 */
	public static String testJimpleSingleVariable() {
		@SuppressWarnings("unused")
		String a = new String("A"); //tempLocal = 1
		@SuppressWarnings("unused")
		String b = new String("B"); //tempLocal = 1 
		String c = new String("C"); //residual = 1
		return c;
	}
	

}
