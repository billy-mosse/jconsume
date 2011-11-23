package ar.uba.dc.rinard;

import java.util.Iterator;

/**
 * This example is from the article "A Combined Pointer and Purity Analysis for
 * Java Programs" by Alexandru Salcianu and Martin Rinard. It is supposed to
 * demonstrate the purity analysis (-annot-purity)
 * 
 * by Antoine Mine, 2005/02/08
 */

/*
interface Iterator {
	boolean hasNext();

	Object next();
}*/

class Point {

	Float x, y;
	
	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public Point(Float x, Float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public void flip() {
		Float t = x;
		x = y;
		y = t;
	}
}

@SuppressWarnings("unchecked")
public class BasicTest {

	/**
	 * @temporal: 1
	 * @residual: 0
	 */
	static float sumX(List list) {
		float s = 0f; 
		Iterator it = list.iterator(); //maxCall = 0, tempCall = 1
		while (it.hasNext()) { 
			Point p = (Point) it.next(); //maxCall = 0, tempCall = 0 
			s += p.x; 
		}
		return s;
	}

	/**
	 * @temporal: 1
	 * @residual: 0
	 */
	static void flipAll(List list) {
		Iterator it = list.iterator(); //maxCall = 0, tempCall = 1
		while (it.hasNext()) {
			Point p = (Point) it.next(); //maxCall = 0, tempCall = 0
			p.flip();
		}
	}

	/**
	 * 
	 * Lazy Strategy (K = -1)
	 * @temporal: max(5 + 4*n, 38) : n >= 2; 38: n == 1; 38 : n <= 0 
	 * @residual: 2 + 4*n : n>=1 ; 2 : n<=0
	 * 
	 * Lazy Strategy (K = 0)
	 * @temporal: max(7 + 8*n, 50 + 4*n) : n >= 2; max(11 + 4*n, 54) : n == 1; 52 : n <= 0 
	 * @residual: 2 + 4*n : n>=1 ; 2 : n<=0
	 * 
	 * 
	 * Add Strategy (K = -1)
	 * @temporal: 58 + 8*n : n>= 2 ; 52 + 4*n : n == 1 ; 60 : n <= 0
	 * @residual: 2 + 4*n : n>=1 ; 2 : n<=0
	 * 
	 * Add Strategy (SI K = 0)
	 * @temporal: 68 + 8*n : n>= 2 ; 70 + 6*n : n == 1 ; 70 : n <= 0
	 * @residual: 2 + 4*n : n>=1 ; 2 : n<=0
	 */
	public static void main(String args[]) {
		Integer n = Integer.valueOf(args[0]); //tempLocal = 1,  
		sumAndFlipFixedPoints(args); //maxCall = 14
		sumNumbersUpToParameter(n); //maxCall = 17 + 4*n si n>=1 ; 17 si n<=0
		sumNumbersUpToParameterUsingStaticList(n); //maxCall = 18 + 3*n si n>=1 ; 18 si n<=0 , residual = 2 + 4*n si n>=1 ; 2 si n<=0
		sumOperator(n); //maxCall = 18 + 3*n si n>=1 ;  18 si n<=0 , tempCall = 3 + 4*n si n>= 2 ; 5 + 2*n si n == 1 ; 5 si n <= 0
		constantParameterCall(); //maxCall = 64 + 3*n si n>=1 ; 64 si n<=0 
	}
	
	/**
	 * Ejemplo SIMPLE: Este main consume 0 residual y 14 de temporal
	 * 
	 * @temporal: 14
	 * @residual: 0
	 */
	public static void sumAndFlipFixedPoints(String args[]) { 
		List list = new List(); 	 // tempLocal = 1
		list.add(new Point(1f, 2f)); // maxCall = 0, tempCall = 1, tempLocal = 3
		list.add(new Point(2f, 3f)); // maxCall = 0, tempCall = 1, tempLocal = 3
		list.add(new Point(3f, 4f)); // maxCall = 0, tempCall = 1, tempLocal = 3
		sumX(list); 				 // maxCall = 1, tempCall = 0
		flipAll(list); 				 // maxCall = 1, tempCall = 0
	}
	
	@SuppressWarnings("unused")
	/**
	 * Ejemplo Loop: Toma de parametro el valor del loop. Este ejemplo calcula la sumatoria de 0 hasta n-1 usando la clase point
	 * 
	 * 
	 * @temporal: 3 + 4*n si n >= 1
	 * @temporal: 3 	  si n <= 0
	 * @residual: 0
	 */
	public static void sumNumbersUpToParameter(Integer n) {
		List list = new List();				  // tempLocal = 1 	 
		for (Float i = 1.0f; i <= n ; i++) {  // maxCall = 0 , tempCall = n si n>=1 , tempLocal = 2*n+1 si n>=1 ; 1 si n<=0 (Total FOR)
			list.add(new Point(i, 0f));		  // maxCall = 0 , tempCall = n si n>=1 , tempLocal = 2*n si n>=1 
		} 
		float s = sumX(list);				  // maxCall = 1, tempCall = 0
	}
	
	
	static List lista;
	/**
	 * Este ejemplo pretende mostrar el mismo caso de ejemplo que ejSalcianu pero agregando los puntos a una lista estatica.
	 * Esto deberia hacer que todo lo temporal del metodo (salvo los iteradores) escapen y se vuelvan residuales.
	 * 
	 * Algo interesante es que aun con la tecnica de point-to hay casos que no podemos alcanzar. Por ejemplo, en este caso 
	 * no podemos distinguir entre un nodo de value_of residual que no escapa de otro que si (porque se encancha a un parametro estatico).
	 * Este problema lo vamos a tener siempre por usar un enfoque de nodo por allocation site.
	 * 
	 * @temporal: 1
	 * @residual: 2 + 4*upTo	si upTo >= 1
	 * @residual: 2				si upTo <= 0 
	 */
	@SuppressWarnings("unused")
	public static void sumNumbersUpToParameterUsingStaticList(Integer upTo) { // Este metodo consume: 1 temporal + (2 + 4*n) residual
		lista = new List();						// residual = 1
		for (Float i = 1.0f; i <= upTo; i++) {  // maxCall = 0 , residual = 3*n+1 si n>=1 ; 1 si n<=0 (Total FOR)     
			lista.add(new Point(i, 0f));        // maxCall = 0 , residual = 3*n si n>=1 (la lista estatica, todo escapa)
		}										
		
		float s = sumX(lista);					// tempCall = 1
	}

	/**
	 * ATENCION: Si esto se aplana bien, deberia dar  MUY DISTINTO
	 * 
	 * Este ejemplo intenta forzar el hecho de tener como resultado de la memoria temporal dos polinomios.
	 * Este metodo calcula la suma de los numeros entre [1, n] y a eso le suma la suma de los numeros entre [1, n)
	 * 
	 * (si K = -1)
	 * @temporal: 2 + 4*n : n>=2; 4 + 2*n : n==1 ; 4 : n<=0
	 * @residual: 1
	 * 
	 * (si K = 0)
	 * @temporal: 0
	 * @residual: 3 + 4*n si n >= 2 ; 5 + 2*n si n == 1 ; 5 si n <= 0 
	 */
	public static Integer sumOperator(Integer n) {
		Integer result1 = 0; 				// residual = 1
		Integer result2 = 0; 				// residual = 1
		
		for (Integer i = 1; i <= n; i++) { 	// residual = 2n+1 (total del for)  
			result1 += i; 					// residual = n 
		}
		
		for (Integer i = 1; i < n; i++) { 	// residual = 2n-1 si n>1 , 1 si n<=1
			result2 += i;				  	// residual = n-1 si n>1
		}
		
		return result1 + result2; 		  	//  residual = 1
	}

	/** ha
	 * Esta prueba tiene como objetivo ver como podemos "Bajar a tierra" algunos parametros, es decir, asignarles valores
	 * constantes y ver como el analisis soporta esto.
	 * 
	 * (Si K = -1)
	 * @residual 0  
	 * @temporal 36
	 * 
	 * (Si K = 0)
	 * @residual 0  
	 * @temporal 46
	 */
	public static void constantParameterCall() {
		sumOperator(0); // maxCall = 0, tempCall = 5  
		sumOperator(1); // maxCall = 0, tempCall = 7
		sumOperator(7); // maxCall = 0, tempCall = 31
	}
}
