package ar.uba.dc.basic;

import java.util.Iterator;

import ar.uba.dc.basic.Numero;
import ar.uba.dc.basic.ArrayList;
import ar.uba.dc.basic.EnlList;

@SuppressWarnings({"unused"})
public class EjemploPolimorfismo01 {

	/**
	 * 
	 * @temporal 0
	 * @residual 0
	 */
	public static void main(String[] args) {
		/*ArrayList list1 = new ArrayList();
		Numero numero1 = new Numero(1);
		Numero numero2 = new Numero(2);
		Numero numero3 = new Numero(3);
		
		list1.add(numero1);
		list1.add(numero2);
		list1.add(numero3);
		
		System.out.println(list1.head().toString());
		IIterator it = list1.iterator(); //maxCall = 0, tempCall = 1
		while (it.hasNext()) { 
			System.out.println(it.next().toString()); 
		}*/
		ArrayList arrIList1 = new ArrayList();
		agregarPrimerosNIList(arrIList1, 10);
		int suma1 = sumaIList(arrIList1);
		Numero numero1 = obtenerPrimeroIList(arrIList1);
		
		ArrayList arrList1 = new ArrayList();
		agregarPrimerosNArrayList(arrList1, 10);
		int suma2 = sumaArrayList(arrList1);
		Numero numero2 = obtenerPrimeroArrayList(arrList1);
		
		EnlList enlList1 = new EnlList();
		agregarPrimerosNEnlList(enlList1, 10);
		int suma3 = sumaEnlList(enlList1);
		Numero numero3 = obtenerPrimeroEnlList(enlList1);
	}
	
	/**
	 * Agrega los numeros del 1 a n a la lista. Sirve para ver que cuando usa interfaces, suma lo de las implemetnaciones, pero solo en esas partes.
	 * 
	 * ArrayCountSize
	 * @temporal 0
	 * @residual (5/2)*n + (1/2)*n^2 : n>=1
	 * 
	 * ESTA DANDO ArrayCountOne
	 * @residual 3*n : n>=1
	 * 
	 * DEBERIA DAR ArrayCountOne
	 * @temporal 0
	 * @residual 2*n : n>=1
	 */
	public static void agregarPrimerosNIList(IList list, int n) { 
		for (int i = 1; i <= n ; i++) {
			Numero numero = new Numero(i); //residual = n si n>=1 
			list.add(numero); //residual = n : n>=1		   
		} 
	}
	
	/**
	 * Suma los elementos de una lista de tipo iList. Sirve para ver q cuando hay interafces, suma lo de las implementacioens
	 * 
	 * ESTA DANDO
	 * @temporal 2
	 * @residual 0
	 * 
	 * DEBERIA DAR
	 * @temporal 1
	 * @residual 0
	 */
	public static int sumaIList(IList list) { 
		int suma = 0;
		IIterator it = list.iterator(); //tempCall = 1 
		
		while (it.hasNext()) {   //maxCall = 0, tempCall = 0
			suma += it.next().numero; //maxCall = 0, tempCall = 0 
		}
		return suma;
	}
	
	
	
	/**
	 * Devuelve el primer elemento de la lista
	 * 
	 * @temporal 0
	 * @residual 0
	 */
	public static Numero obtenerPrimeroIList(IList list) { 
		return list.head(); 
	}

	/**
	 * Agrega los numeros del 1 a n a la ArrayList
	 * 
	 * ArrayCountSize
	 * @temporal (3/2)*n + (1/2)*n^2 : n>=1
	 * @residual 2*n : n>=1
	 * 
	 * ArrayCountOne
	 * @temporal 0
	 * @residual 2*n : n>=1
	 */
	public static void agregarPrimerosNArrayList(ArrayList list, int n) { 
		for (int i = 1; i <= n ; i++) {
			Numero numero = new Numero(i); //residual = n : n>=1 
			list.add(numero); //residual = n : n>=1		   
		} 
	}
	
	/**
	 * Suma los elementos de una lista de tipo ArrayList
	 * 
	 * @temporal 1
	 * @residual 0
	 */
	public static int sumaArrayList(ArrayList list) { 
		int suma = 0;
		IIterator it = list.iterator(); //tempCall = 1
		
		while (it.hasNext()) {  //tempCall = 0, maxCall = 0
			suma += it.next().numero; //tempCall = 0, maxCall = 0
		}
		return suma;
	}
	
	
	
	/**
	 * Devuelve el primer elemento de la ArrayList
	 * 
	 * @temporal 0
	 * @residual 0
	 */
	public static Numero obtenerPrimeroArrayList(ArrayList list) { 
		return list.head(); //maxCall = 0, tempCall = 0
	}
	
	/**
	 * Agrega los numeros del 1 a n a la EnlList
	 * 
	 * @temporal 0
	 * @residual 2*n : n>=1
	 */
	public static void agregarPrimerosNEnlList(EnlList list, int n) { 
		for (int i = 1; i <= n ; i++) {
			Numero numero = new Numero(i); //residual = n : n>=1 
			list.add(numero); //residual = n : n>=1		    
		} 
	}
	
	/**
	 * Suma los elementos de una lista de tipo EnlList
	 * 
	 * @temporal 1
	 * @residual 0
	 */
	public static int sumaEnlList(EnlList list) { 
		int suma = 0;
		IIterator it = list.iterator(); //tempCall = 1
		
		while (it.hasNext()) {  //maxCall = 0, tempCall = 0 
			suma += it.next().numero; 
		}
		return suma;
	}
	
	
	
	/**
	 * Devuelve el primer elemento de la EnlList
	 * 
	 * @temporal 0
	 * @residual 0
	 */
	public static Numero obtenerPrimeroEnlList(EnlList list) { 
		return list.head(); //maxCall = 0, tempCall = 0 
	}
	
}


