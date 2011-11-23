package ar.uba.dc.basic;

@SuppressWarnings("unused")
public class EjemploOr01 {
	/**
	 * 
	 * @temporal n+1
	 * @residual 0
	 */
	public void main(String[] args) {
		//testOr1(10);
		int n = 10;
		
		Integer num = new Integer(2); //residual = 1
		for (int i = 1; i <= n; i++)//maxCall = 0, tempCall = n 
		{
			Integer integer = new Integer(2); //residual = 1
	    }
	}
	
	/**
	 * @temporal n+1
	 * @residual 0
	 */
	public void testOr1(int n) {
		 Integer num = new Integer(2); //residual = 1
		 for (int i = 1; i <= n; i++)//maxCall = 0, tempCall = n 
		 {
			 Integer numero = newInteger();
	     }
		 
	}
	
	/**
	 * @temporal 0
	 * @residual 1
	 */
	static Integer newInteger() {
		Integer integer = new Integer(2); //residual = 1
		return integer; 
	}
}
