package ar.uba.dc.basic.lazy;


public class BasicTest {

	/**
	 * LAZY
	 * @residual: 0
	 * @temporal: max(2 + 1/2*args_0, 53) si args_0 >= 1; 53 si args_0 <= 0
	 * 
	 * ADD
	 * @residual: 0
	 * @temporal: 70 + args_0 si args_0 >= 1; 70 si args_0 <= 0 
	 */
	public static void main(String[] args) {
		int n = Integer.valueOf(args[0]); // 1 tempLocal
		showDiffIfTemporalIsConstant(n);  // 1 + 1/2*size; 1 /  1 + 1/2*size; 1 maxCall
		showDiffIfTemporalIsConstant(n);  // 1 + 1/2*size; 1  /  2 + size; 1 maxCall
		showExampleWithCandidates();	  // max(1 + 1/2*size, 52) / 69 + size; 69 maxCall
	}
	
	/**
	 * Este metodo demuestra que usar la estrategia lazy mucho mejor en cuanto a al cota
	 * del temporal en un caso simple: Si llamo N veces al mismo metodo, no tendre N*temporal sino que solo temporal como resultado
	 * 
	 * LAZY
	 * @residual: 0
	 * @temporal: 1 + 1/2*size si size >= 1; 1 si size <= 0
	 * 
	 * ADD
	 * @residual: 0
	 * @temporal: 1 + 1/2*size si size >= 1; 1 si size <= 0
	 */
	public static void showDiffIfTemporalIsConstant(int size) {
		int[] arreglo = new int[size];
		
		for (int i = 0; i < arreglo.length; i++) {
			arreglo[i] = arreglo.length - i;
		}
		
		for (int i = 0; i < arreglo.length/2; i++) {
			int j = arreglo.length - i;
			Integer aux = new Integer(arreglo[i]);
			arreglo[i] = arreglo[j];
			arreglo[j] = aux;
		}
	}
	
	/**
	 * El objetivo de esta prueba es mostrar un ejemplo donde la estrategia lazy mejore la precision al 
	 * dejar los posibles candidatos y no sumarlos
	 * 
	 * Lazy
	 * @temporal: 52
	 * @residual: 0
	 * 
	 * Add
	 * @temporal: 67
	 * @residual: 0
	 */
	public static void showExampleWithCandidates() {
							// Lazy  -> Add
		sumPositions(0, 0); //	2	  -> 2 		// 0 tempLocal, 0 tempCall, 2 maxCall
		sumPositions(0, 4); //	52	  -> 52		// 0 tempLocal, 0 tempCall, 52 maxCall
		sumPositions(1, 4); // 51    -> 58		// 0 tempLocal, 0 tempCall, 52/58 maxCall
		sumPositions(3, 3); // 20    -> 40		// 0 tempLocal, 0 tempCall, 52/58 maxCall
		sumPositions(3, 4); // 44	  -> 67		// 0 tempLocal, 0 tempCall, 52/67 maxCall
	}
	
	/**
	 * LAZY:
	 * @temporal: max(2 - 7/6*from + 1/2*from^2 - 1/3*from^3 + (7/6 + from)*to + 1/2*to^2 + 1/3*to^3, 2 - 7/6*from + 3/2*from^2 - 1/3*from^3 + 7/6*to + 1/2*to^2 + 1/3*to^3, 2 - 7/6*from + 1/2*from^2 - 1/3*from^3 + 7/6*to + 3/2*to^2 + 1/3*to^3) si to >= from and from >= 1
	 * @temporal: 2 + 7/6*to + 3/2*to^2 + 1/3*to^3		 		si from <= 0 and to >= 1 
	 * @temporal: 2 - from + to									si to = 0 and from <= 0
	 * @temporal: 2 - from + to									si to < 0 and from <= 0
	 * @temporal: 0												si si to + 1 <= from
	 * @residual: 1
	 * 
	 * ADD:
	 * @temporal: 4 - 7/6*from + 3/2*from^2 - 1/3*from^3 + (7/6 + from)*to + 3/2*to^2 + 1/3*to^3 	si to >= from and from >= 1
	 * @temporal: 2 - from + 7/6*to + 3/2*to^2 + 1/3*to^3											si from <= 0 and to >= 1 
	 * @temporal: 2 - from + to																		si to = 0 and from <= 0
	 * @temporal: 2 - from + to																		si to < 0 and from <= 0
	 * @temporal: 0																					si si to + 1 <= from 
	 * @residual: 0
	 */
	public static int sumPositions(int from, int to) {
		int acum = 0;													// 1 residual, 0 tempLocal, 0 tempCall, 0 maxCall
		for (int i = from; i <= to; i++) {				
			T[] posiblesPares = posiblesPares(i);						// 1 residual, 0 tempLocal, 0 tempCall, 0 maxCall
			acum += posiblesPares[i].first + posiblesPares[i].second;
		}
		
		return acum;
	}
	
	/**
	 * 
	 * @temporal: 1 + size^2 	si size >= 1
	 * @temporal: 1				si size == 0  
	 * @residual: 1 + size^2  	si size >= 1
	 * @residual: 1 			si size == 0
	 */
	public static T[] posiblesPares(int size) {
		T[] tuplas = new T[size*2]; 								// 1 residual, 0 tempLocal						, 0 tempCall, 0 maxCall 
		Integer contador = 0; 										// 1 residual, 1 tempLocal						, 0 tempCall, 0 maxCall
		for (int i = 0; i < size; i++) { 					
			for (int j = 0; j < size; j++) {				
				tuplas[contador] = new T(i, j);						// 1 + arreglo.length^2 residual, 1 + arreglo.length^2 tempLocal, 0 tempCall, 0 maxCall
				contador++;
			}
		}
		
		return tuplas;
	}
}

class T {
	int first;
	int second;
	
	public T(int first, int second) {
		this.first = first;
		this.second = second;
	}
}
