package ar.uba.dc.msr.snippet;

import java.util.Date;
import java.util.Random;

/**
 * @author martin
 *
 */
public class Snippet01 {

	/**
	   * 
	   * ArrayCountSize
	   * @temporal 3 + 5*size : size >=1 ; 3 : size <= 0
	   * @residual 0
	   * 
	   * ArrayCountOne
	   * @temporal  6 + 2*size : size >=1 ; 6 : size <= 0
	   * @residual 0 
	   */	
	public static void main(String[] args) {
		int size = Integer.valueOf(args[0]); //tempCall = 1
		Integer[] vector = randomVector(size);

		@SuppressWarnings("unused")
		Integer[] copy = copy(vector);
		
		@SuppressWarnings("unused")
		Integer[] deepCopy = deepCopy(vector); 
	}
	
	
	/**
	   * 
	   * ArrayCountSize
	   * @temporal 2
	   * @residual 2*size : size >=1
	   * 
	   * ArrayCountOne
	   * @temporal 2
	   * @residual size + 1 : size >= 1  ; 1 : size <= 0
	   */
	public static Integer[] randomVector(int size) {
		Date now = new Date(); //temporal = 1 				
		long time = now.getTime();			
		Random random = new Random(time); //temporal = 1	
		Integer[] vector = new Integer[size]; //residual = 1  (size arrayCountSize )
		for(int i = 0; i<size; i++) {
			Integer value = new Integer(random.nextInt()); //residual = size
			vector[i] = value;
		}
		return vector;
	}
	
	/**
	   * 
	   * ArrayCountSize
	   * @temporal 0 
	   * @residual vector.length : vector.length >=1
	   * 
	   * ArrayCountOne
	   * @temporal 0
	   * @residual 1
	   */
	public static Integer[] copy(Integer[] vector) {
		Integer[] newVector = new Integer[vector.length]; //residual = 1 (vector.length arrayCountSize )
		for (int i=0; i<vector.length;i++)
			newVector[i] = vector[i];
		
		return newVector;
	}	
	
	/**
	   * 
	   * ArrayCountSize
	   * @temporal 0
	   * @residual 2*vector.length : vector.length >=1 
	   * 
	   * ArrayCountOne
	   * @temporal 0
	   * @residual vector.length + 1 : vector.length >= 1; 1 vector.length<=0 
	   */	
	public static Integer[] deepCopy(Integer[] vector) {
		Integer[] newVector = new Integer[vector.length]; //residual = 1 (vector.length arrayCountSize ) 
		for (int i=0; i<vector.length;i++)
			newVector[i] = new Integer(vector[i]);  //residual = vector.length 
		
		return newVector;
	}	
}
