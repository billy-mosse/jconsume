package ar.uba.dc.simple;

public class EjemploSimple05 {

	/**
	 * 
	 * @temporal: 10 si args >= 1
	 * @residual: 0
	 */
	public static void main(String[] args) {
		int times = Integer.parseInt(args[0]);
		for(int i = 0; i < times; i++)
			m0(i); //maxCall = 10 si args>=1
	}
	
	/**
	 * 
	 * @temporal: 10
	 * @residual: 0
	 */
	public static  void m0(int n) {
		for(int j=1;j<10;j++){
			@SuppressWarnings("unused")
			Object object = new Integer(j); //tempCall = 10
		}
	}
}
