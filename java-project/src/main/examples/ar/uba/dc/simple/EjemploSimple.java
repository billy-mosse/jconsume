package ar.uba.dc.simple;

/**
 * @author martin
 * 
 * Ejemplos similares al ejemplo de ISMM.
 */
public class EjemploSimple {
	
	
	/**
	 * Main con iteracion para que Daikon pueda obtener invariantes.
	 * @param args
	 * 
	 * ArrayCountSize
	 * @residual: 0
	 * @temporal: 231 + 43 * args + 2*args^2 si arg0 >= 1
	 * @temporal: 1							 si arg0 <= 0
	 * 
	 * ArrayCountOne
	 * @residual: 0
	 * @temporal: 141 + 24 * args + args^2 si arg0 >= 1
	 * @temporal: 1							 si arg0 <= 0
	 */
	public static void main(String[] args) {
		EjemploSimple es = new EjemploSimple(); //tempLocal = 1
		int arg0 = Integer.parseInt(args[0]); 
		for(int i=1;i<=arg0;i++)
			es.m0(10 + i);  //maxCall = 140 + 24 * args + args^2 : args >= 1 (230 + 43 * args + 2*args^2 : args >= 1 ArrayCountSize) 
	}

	/**
	 * ArrayCountSize
	 * @residual: 0
	 * @temporal: 3*m + 2*m^2 : m >= 1
	 * 
	 * ArrayCountOne
	 * @residual: 0
	 * @temporal: 4 * m + m^2 si m >= 1
	 */
	void m0(int m) {
		for(int c = 1;c <= m;c++) {
			m1(c); //maxCall = 5/2 * c + 1/2 * c^2 si c >= 1 (2*c + c^2 : c >= 1 ArrayCountSize) 
			B[] m2Arr = m2(c); //maxCall = 5/2 * c + 1/2 * c^2 si c >= 1 (2*c + c^2 : c >= 1 ArrayCountSize), tempCall = 3/2 * k + 1/2 * k^2 si k >= 1 (k + k^2 : k >= 1ArrayCountSize)  
			m2Arr[0].b = c;
		}
	}
	
	/**
	 * 
	 * ArrayCountSize
	 * @residual: 0
	 * @temporal: 2*k + k^2 si k >= 1
	 * 
	 * ArrayCountOne
	 * @residual: 0
	 * @temporal: 5/2 * k + 1/2 * k^2 si k >= 1
	 */
	void  m1(int k) {
		for (int i = 1; i <= k; i++) {   
			A a = new A(); 	//tempLocal = k si k >= 1. Esto es safe, pero en realidad podria dar 1 tambien no?
			a.a = 5;	
			B[] dummyArr= m2(i); //maxCall = 0 , tempCall = 3/2 * k + 1/2 * k^2 si k >= 1 (k + k^2 : k >= 1  ArrayCountSize)
			dummyArr[0].b = 10;
		}
	}
	
	/**
	 * 
	 * ArrayCountSize
	 * @residual: 2*n : n >= 1
	 * @temporal: 0
	 * 
	 * ArrayCountOne
	 * @residual: 1 + n si n >= 1
	 * @residual: 1     si n <= 0 
	 * @temporal: 0
	 */
	B[] m2(int n) {
		B[] arrB = new B[n]; //residual = 1 (n ArrayCountSize)
		for (int j = 0; j < n; j++) {
			B b = new B(4, 7); //residual = n si n >=1
			b.b = j;
			arrB[j] = b;
		}
		return arrB;
	}
	
	
}