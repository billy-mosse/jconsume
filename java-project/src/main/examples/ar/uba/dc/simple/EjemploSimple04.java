package ar.uba.dc.simple;

public class EjemploSimple04 {

	/**
	 * ArrayCountOne 
	 * @temporal: 38 + 3/2 * args__length + 1/2 * args__length^2 	si args_length 	>= 2
	 * @temporal: 39 + args__length 								si args__length = 1
	 * @temporal: 37 												si args__length <= 0
	 * @residual: 0
	 */
	public static void main(String[] args) {
		String[] strings = new String[10]; //tempLocal = 1
		strings[0] = new String("Suma 1 al temporal local"); //tempLocal = 1
		int aux = 10; 
		@SuppressWarnings("unused")
		Integer[] vector = m0(aux); //maxCall = 1 , tempCall = 11
		vector = m0(aux); //maxCall = 2,  tempCall = 22 
		m1(aux); //maxCall = 14, tempCall = 22
		
		@SuppressWarnings("unused")
		Integer[] sumArray = m3(args.length); //maxCall = 14 si args.length < 1 ; 16 si args.length == 1 ; 15 + 1/2 * args.length + 1/2 * args.length^2 si  args.length >= 2 
											  //tempCall= 23 si args.length < 1 ; 23 + args.length si args.length >=1

	}
	
	/**
	 *
 	 * ArrayCountSize
	 * @temporal: 1 
	 * @residual: 2*n : n>=1

	 * ArrayCountOne
	 * @temporal: 1 
	 * @residual: n + 1 si n >= 1
	 * @residual: 1     si n <= 0
	 */
	public static Integer[] m0(int n) {
		@SuppressWarnings("unused")
		Integer tmp = new Integer(n);		// tempLocal = 1
		Integer[] vector = new Integer[n];	// residual = 1 (n : n>= 1 ArrayCountSize)
		for(int i = 0;i<n ;i++)  {			// 
			vector[i] =  new Integer(i);	// residual = n si n>=1
		}
		return vector;						
	}
	
	/**
	 * 
	 * ArrayCountSize
	 * @temporal: 2*j + 1 si j >= 1
	 * @temporal: 2     si j <= 0
	 * @residual: 0
	 * 
	 * ArrayCountOne
	 * @temporal: j + 2 si j >= 1
	 * @temporal: 2     si j <= 0
	 * @residual: 0
	 */
	public static void m1(int j) {
		Integer[] vector = new Integer[j]; //tempLocal = 1 (j ArrayCountSize)
		for(int i = 0;i<j ;i++)  {
			vector[i] =  new Integer(i); //tempLocal = j si j>=1
		}
		@SuppressWarnings("unused")
		Integer sum = m2(vector); //maxCall = 0, tempCall = 1
	}
	
	/** 
	 * @temporal: 0
	 * @residual: 1
	 */
	public static Integer m2(Integer[] values) {
		int sum = 0;		
		for(int i = 0;i<values.length ;i++)  {
			sum += values[i].intValue(); 
		}	
		return new Integer(sum); //residual = 1
	}
	
	/**
	 * 
	 * ArrayCountSize
	 * @temporal: 1 - k + k^2 : k >= 2
	 * @temporal: 1 		  : k == 1 
	 * @residual: 2*k : k >= 1
	 * 
	 * ArrayCountOne
	 * @temporal: 1 + 1/2 * k + 1/2 * k^2 si k >= 2
	 * @temporal: 1 + k		 			  si k == 1  viene de (1 + k)
	 * @residual: 1 + k si k >= 1
	 * @residual: 1     si k <= 0
	 */
	public static Integer[] m3(int k) {
		Integer[] sumArray = new Integer[k]; //residual = 1 (k : k >= 1 ArrayCountSize)
		for(int i=0;i<k;i++) {
			Integer[] vector = m0(i); //maxCall = 1 si k>=1 , tempCall = 1/2 * k + 1/2 * k^2 si k >= 2 ; 1 si k = 1 (- k + k^2 : k >= 2 ArrayCountSize)
			sumArray[i] = m2(vector); //maxCall = 1 si k>=1 , residual = k si k >= 1
		}
		return sumArray;
	}
}

/**
 * @temporal: 1 
 * @residual: n + 1 si n >= 1
 * @residual: 1     si n <= 0
 */